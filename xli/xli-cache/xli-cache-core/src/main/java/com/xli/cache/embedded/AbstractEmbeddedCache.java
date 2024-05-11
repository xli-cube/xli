package com.xli.cache.embedded;

import com.xli.cache.AbstractCache;
import com.xli.cache.config.CacheConfig;
import com.xli.cache.multi.MultiGetResult;
import com.xli.cache.result.CacheGetResult;
import com.xli.cache.result.CacheResult;
import com.xli.cache.result.CacheResultCode;
import com.xli.cache.result.CacheValueHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.stream.Collectors;


public abstract class AbstractEmbeddedCache<K, V> extends AbstractCache<K, V> {

    private final ReentrantLock lock = new ReentrantLock();
    protected EmbeddedCacheConfig<K, V> config;
    protected InnerMap innerMap;

    public AbstractEmbeddedCache(EmbeddedCacheConfig<K, V> config) {
        this.config = config;
        innerMap = createAreaCache();
    }

    protected abstract InnerMap createAreaCache();


    public Object buildKey(K key) {
        Object newKey = key;
        Function<K, Object> keyConvertor = config.getKeyConvertor();
        if (keyConvertor != null) {
            newKey = keyConvertor.apply(key);
        }
        return newKey;
    }

    @Override
    public CacheConfig<K, V> config() {
        return config;
    }

    @Override
    protected CacheGetResult<V> do_GET(K key) {
        Object newKey = buildKey(key);
        CacheValueHolder<V> holder = (CacheValueHolder<V>) innerMap.getValue(newKey);
        return parseHolderResult(holder);
    }

    @Override
    protected MultiGetResult<K, V> do_GET_ALL(Set<? extends K> keys) {
        ArrayList<K> keyList = new ArrayList<K>(keys.size());
        ArrayList<Object> newKeyList = new ArrayList<Object>(keys.size());
        keys.stream().forEach((k) -> {
            Object newKey = buildKey(k);
            keyList.add(k);
            newKeyList.add(newKey);
        });
        Map<Object, CacheValueHolder<V>> innerResultMap = innerMap.getAllValues(newKeyList);
        Map<K, CacheGetResult<V>> resultMap = new HashMap<>();
        for (int i = 0; i < keyList.size(); i++) {
            K key = keyList.get(i);
            Object newKey = newKeyList.get(i);
            CacheValueHolder<V> holder = innerResultMap.get(newKey);
            resultMap.put(key, parseHolderResult(holder));
        }
        MultiGetResult<K, V> result = new MultiGetResult<>(CacheResultCode.SUCCESS, null, resultMap);
        return result;
    }

    @Override
    protected CacheResult do_PUT(K key, V value, long expireAfterWrite, TimeUnit timeUnit) {
        CacheValueHolder<V> cacheObject = new CacheValueHolder(value, timeUnit.toMillis(expireAfterWrite));
        innerMap.putValue(buildKey(key), cacheObject);
        return CacheResult.SUCCESS_WITHOUT_MSG;
    }

    @Override
    protected CacheResult do_PUT_IF_ABSENT(K key, V value, long expireAfterWrite, TimeUnit timeUnit) {
        CacheValueHolder<V> cacheObject = new CacheValueHolder(value, timeUnit.toMillis(expireAfterWrite));
        if (innerMap.putIfAbsentValue(buildKey(key), cacheObject)) {
            return CacheResult.SUCCESS_WITHOUT_MSG;
        } else {
            return CacheResult.EXISTS_WITHOUT_MSG;
        }
    }

    @Override
    protected CacheResult do_PUT_ALL(Map<? extends K, ? extends V> map, long expireAfterWrite, TimeUnit timeUnit) {
        HashMap newKeyMap = new HashMap();
        for (Map.Entry<? extends K, ? extends V> en : map.entrySet()) {
            CacheValueHolder<V> cacheObject = new CacheValueHolder(en.getValue(), timeUnit.toMillis(expireAfterWrite));
            newKeyMap.put(buildKey(en.getKey()), cacheObject);
        }
        innerMap.putAllValues(newKeyMap);

        final HashMap resultMap = new HashMap();
        map.keySet().forEach((k) -> resultMap.put(k, CacheResultCode.SUCCESS));
        return CacheResult.SUCCESS_WITHOUT_MSG;
    }

    @Override
    protected CacheResult do_REMOVE(K key) {
        innerMap.removeValue(buildKey(key));
        return CacheResult.SUCCESS_WITHOUT_MSG;
    }

    @Override
    protected CacheResult do_REMOVE_ALL(Set<? extends K> keys) {
        Set newKeys = keys.stream().map(this::buildKey).collect(Collectors.toSet());
        innerMap.removeAllValues(newKeys);

        return CacheResult.SUCCESS_WITHOUT_MSG;
    }

    protected CacheGetResult<V> parseHolderResult(CacheValueHolder<V> holder) {
        long now = System.currentTimeMillis();
        if (holder == null) {
            return (CacheGetResult<V>) CacheGetResult.NOT_EXISTS_WITHOUT_MSG;
        } else if (now >= holder.getExpireTime()) {
            return (CacheGetResult<V>) CacheGetResult.EXPIRED_WITHOUT_MSG;
        } else {
            lock.lock();
            try {
                long accessTime = holder.getAccessTime();
                if (config.isExpireAfterAccess()) {
                    long expireAfterAccess = config.getExpireAfterAccessInMillis();
                    if (now >= accessTime + expireAfterAccess) {
                        return (CacheGetResult<V>) CacheGetResult.EXPIRED_WITHOUT_MSG;
                    }
                }
                holder.setAccessTime(now);
            } finally {
                lock.unlock();
            }

            return new CacheGetResult(CacheResultCode.SUCCESS, null, holder);
        }
    }

    public void __removeAll(Set<? extends K> keys) {
        innerMap.removeAllValues(keys);
    }
}
