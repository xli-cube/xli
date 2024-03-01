package com.xli.cache.policy;

import cn.hutool.extra.spring.SpringUtil;
import com.xli.cache.config.CacheConfig;
import com.xli.cache.config.CacheProperties;
import com.xli.cache.config.CacheProviderHolder;
import com.xli.cache.service.ILevel1Cache;
import com.xli.cache.service.ILevel2Cache;
import com.xli.cache.service.NullObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Closeable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public abstract class CacheChannel implements Closeable, AutoCloseable {

    /**
     * 临时存储缓存值
     */
    private static final Map<String, Object> _g_keyLocks = new ConcurrentHashMap<>();

    private CacheProviderHolder holder;

    private boolean defaultCacheNullObject;

    private boolean closed;

    public CacheChannel(CacheProviderHolder holder) {
        CacheProperties cacheProperties= SpringUtil.getBean(CacheProperties.class);
        this.holder = holder;
        this.defaultCacheNullObject = cacheProperties.isDefaultCacheNullObject();
        this.closed = false;
    }

    /**
     * 清空缓存域
     *
     * @param region
     */
    protected abstract void sendClearCmd(String region);

    /**
     * 清空缓存
     *
     * @param region
     * @param keys
     */
    protected abstract void sendEvictCmd(String region, String... keys);

    public CacheObject get(String region, String key, boolean... cacheNullObject) {
        this.assertNotClose();

        //先从一级缓存中读取
        CacheObject obj = new CacheObject(region, key, CacheObject.LEVEL_1);
        obj.setValue(holder.getLevel1Cache(region).get(key));
        if (obj.rawValue() != null) {
            //若一级缓存查到了，则直接返回
            return obj;
        }
        //若一级缓存没有读到
        String lock_key = key + '%' + region;
        //需锁线程，因为要对一级缓存和二级缓存同时操作
        synchronized (_g_keyLocks.computeIfAbsent(lock_key, v -> new Object())) {
            obj.setValue(holder.getLevel1Cache(region).get(key));
            if (obj.rawValue() != null) {
                return obj;
            }
            try {
                obj.setLevel(CacheObject.LEVEL_2);
                obj.setValue(holder.getLevel2Cache(region).get(key));
                if (obj.rawValue() != null) {
                    //若二级缓存读到了，则需要更新到一级缓存中
                    holder.getLevel1Cache(region).put(key, obj.rawValue());
                } else {
                    boolean cacheNull = (cacheNullObject.length > 0) ? cacheNullObject[0] : defaultCacheNullObject;
                    if (cacheNull) {
                        set(region, key, newNullObject(), true);
                    }
                }
            } finally {
                _g_keyLocks.remove(lock_key);
            }
        }

        return obj;
    }

    public Map<String, CacheObject> get(String region, Collection<String> keys) {
        this.assertNotClose();

        final Map<String, Object> objs = holder.getLevel1Cache(region).get(keys);
        List<String> level2Keys = keys.stream().filter(k -> !objs.containsKey(k) || objs.get(k) == null).collect(Collectors.toList());
        Map<String, CacheObject> results = objs.entrySet().stream().filter(p -> p.getValue() != null).collect(Collectors.toMap(Map.Entry::getKey, p -> new CacheObject(region, p.getKey(), CacheObject.LEVEL_1, p.getValue())));

        Map<String, Object> objs_level2 = holder.getLevel2Cache(region).get(level2Keys);
        objs_level2.forEach((k, v) -> {
            results.put(k, new CacheObject(region, k, CacheObject.LEVEL_2, v));
            if (v != null) {
                holder.getLevel1Cache(region).put(k, v);
            }
        });

        return results;
    }

    public void set(String region, String key, Object value) {
        set(region, key, value, defaultCacheNullObject);
    }

    public void set(String region, String key, Object value, boolean cacheNullObject) {
        this.assertNotClose();
        if (!cacheNullObject && value == null) {
            return;
        }
        try {
            ILevel1Cache level1 = holder.getLevel1Cache(region);
            level1.put(key, (value == null && cacheNullObject) ? newNullObject() : value);
            ILevel2Cache level2 = holder.getLevel2Cache(region);
            level2.put(key, (value == null && cacheNullObject) ? newNullObject() : value);
        } finally {
            //广播
            //清除原有的一级缓存的内容
            this.sendEvictCmd(region, key);
        }
    }

    public void set(String region, Map<String, Object> elements) {
        set(region, elements, defaultCacheNullObject);
    }

    public void set(String region, Map<String, Object> elements, boolean cacheNullObject) {
        this.assertNotClose();
        try {
            if (cacheNullObject && elements.containsValue(null)) {
                Map<String, Object> newElems = new HashMap<>(elements);
                newElems.forEach((k, v) -> {
                    if (v == null) newElems.put(k, newNullObject());
                });
                ILevel1Cache level1 = holder.getLevel1Cache(region);
                level1.put(newElems);
                holder.getLevel2Cache(region).put(newElems);
            } else {
                ILevel1Cache level1 = holder.getLevel1Cache(region);
                level1.put(elements);
                holder.getLevel2Cache(region).put(elements);
            }
        } finally {
            //广播
            //清除原有的一级缓存的内容
            this.sendEvictCmd(region, elements.keySet().toArray(String[]::new));
        }
    }



    public Collection<String> keys(String region)  {
        this.assertNotClose();

        Set<String> keys = new HashSet<>();
        keys.addAll(holder.getLevel1Cache(region).keys());
        keys.addAll(holder.getLevel2Cache(region).keys());
        return keys;
    }

    public boolean exists(String region, String key) {
        return check(region, key) > 0;
    }

    public void evict(String region, String...keys)  {
        this.assertNotClose();

        try {
            //先清比较耗时的二级缓存，再清一级缓存
            holder.getLevel2Cache(region).evict(keys);
            holder.getLevel1Cache(region).evict(keys);
        } finally {
            //发送广播
            this.sendEvictCmd(region, keys);
        }
    }

    public void clear(String region)  {
        this.assertNotClose();

        try {
            //先清比较耗时的二级缓存，再清一级缓存
            holder.getLevel2Cache(region).clear();
            holder.getLevel1Cache(region).clear();
        }finally {
            this.sendClearCmd(region);
        }
    }

    public int check(String region, String key) {
        this.assertNotClose();

        if(holder.getLevel1Cache(region).exists(key)) {
            return 1;
        }
        if(holder.getLevel2Cache(region).exists(key)) {
            return 2;
        }
        return 0;
    }

    @Override
    public void close() {
        this.closed = true;
    }

    private NullObject newNullObject() {
        return new NullObject();
    }

    private void assertNotClose() {
        if (closed) {
            throw new IllegalStateException("CacheChannel closed");
        }
    }

    public static class Region {

        private String name;
        private long size;
        private long ttl;

        public Region(String name, long size, long ttl) {
            this.name = name;
            this.size = size;
            this.ttl = ttl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }

        public long getTtl() {
            return ttl;
        }

        public void setTtl(long ttl) {
            this.ttl = ttl;
        }

        @Override
        public String toString() {
            return String.format("[%s,size:%d,ttl:%d]", name, size, ttl);
        }
    }
}
