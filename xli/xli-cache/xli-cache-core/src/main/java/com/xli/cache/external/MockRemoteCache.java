package com.xli.cache.external;

import com.xli.cache.Cache;
import com.xli.cache.config.CacheConfig;
import com.xli.cache.embedded.LinkedHashMapCacheBuilder;
import com.xli.cache.exception.CacheException;
import com.xli.cache.multi.MultiGetResult;
import com.xli.cache.result.CacheGetResult;
import com.xli.cache.result.CacheResult;
import com.xli.cache.result.CacheValueHolder;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class MockRemoteCache<K, V> extends AbstractExternalCache<K, V> {

    private static final Method getHolder;

    static {
        try {
            getHolder = CacheGetResult.class.getDeclaredMethod("getHolder");
            getHolder.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new CacheException(e);
        }
    }

    private final Cache<ByteBuffer, byte[]> cache;
    private final ExternalCacheConfig<K, V> config;

    public MockRemoteCache(MockRemoteCacheConfig<K, V> config) {
        super(config);
        this.config = config;
        cache = LinkedHashMapCacheBuilder.createLinkedHashMapCacheBuilder().limit(config.getLimit()).expireAfterWrite(config.getExpireAfterWriteInMillis(), TimeUnit.MILLISECONDS).buildCache();
    }

    @Override
    public CacheConfig<K, V> config() {
        return config;
    }

    @Override
    public <T> T unwrap(Class<T> clazz) {
        return cache.unwrap(clazz);
    }

    private ByteBuffer genKey(K key) {
        return ByteBuffer.wrap(buildKey(key));
    }

    public CacheValueHolder getHolder(K key) {
        try {
            CacheGetResult<V> r = GET(key);
            return (CacheValueHolder) getHolder.invoke(r);
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    private CacheGetResult convertCacheGetResult(CacheGetResult originResult) {
        try {
            CacheValueHolder originHolder = (CacheValueHolder) getHolder.invoke(originResult);
            LinkedList<CacheValueHolder> list = new LinkedList<>();
            while (originHolder != null) {
                CacheValueHolder h = new CacheValueHolder();
                if (!list.isEmpty()) {
                    list.getLast().setValue(h);
                }
                list.add(h);
                h.setAccessTime(originHolder.getAccessTime());
                h.setExpireTime(originHolder.getExpireTime());

                Object v = originHolder.getValue();
                if (v != null && !(v instanceof CacheValueHolder)) {
                    h.setValue(config.getValueDecoder().apply((byte[]) v));
                    break;
                } else if (originHolder.getValue() == null) {
                    originHolder = (CacheValueHolder) originHolder.getValue();
                }
            }
            return new CacheGetResult(originResult.getResultCode(), originResult.getMessage(), list.peekFirst());
        } catch (Exception e) {
            throw new CacheException(e);
        }
    }

    @Override
    protected CacheGetResult<V> do_GET(K key) {
        CacheGetResult r = cache.GET(genKey(key));
        if (r.isSuccess()) {
            r = convertCacheGetResult(r);
        }
        return r;
    }

    @Override
    protected MultiGetResult<K, V> do_GET_ALL(Set<? extends K> keys) {
        ArrayList<K> keyList = new ArrayList<>(keys.size());
        ArrayList<ByteBuffer> newKeyList = new ArrayList<>(keys.size());
        keys.forEach((k) -> {
            ByteBuffer newKey = genKey(k);
            keyList.add(k);
            newKeyList.add(newKey);
        });
        MultiGetResult<ByteBuffer, byte[]> result = cache.GET_ALL(new HashSet<>(newKeyList));
        Map<ByteBuffer, CacheGetResult<byte[]>> resultMap = result.getValues();
        if (resultMap != null) {
            Map<K, CacheGetResult<V>> returnMap = new HashMap<>();
            for (int i = 0; i < keyList.size(); i++) {
                K key = keyList.get(i);
                ByteBuffer newKey = newKeyList.get(i);
                CacheGetResult r = resultMap.get(newKey);
                if (r.getValue() != null) {
                    r = convertCacheGetResult(r);
                }
                returnMap.put(key, r);
            }
            result = new MultiGetResult<ByteBuffer, byte[]>(result.getResultCode(), null, (Map) returnMap);
        }
        return (MultiGetResult) result;
    }

    @Override
    protected CacheResult do_PUT(K key, V value, long expireAfterWrite, TimeUnit timeUnit) {
        return cache.PUT(genKey(key), config.getValueEncoder().apply(value), expireAfterWrite, timeUnit);
    }

    @Override
    protected CacheResult do_PUT_ALL(Map<? extends K, ? extends V> map, long expireAfterWrite, TimeUnit timeUnit) {
        Map<ByteBuffer, byte[]> newMap = new HashMap<>();
        map.forEach((key, value) -> newMap.put(genKey(key), config.getValueEncoder().apply(value)));
        return cache.PUT_ALL(newMap, expireAfterWrite, timeUnit);
    }

    @Override
    protected CacheResult do_REMOVE(K key) {
        return cache.REMOVE(genKey(key));
    }

    @Override
    protected CacheResult do_REMOVE_ALL(Set<? extends K> keys) {
        return cache.REMOVE_ALL(keys.stream().map(this::genKey).collect(Collectors.toSet()));
    }

    @Override
    protected CacheResult do_PUT_IF_ABSENT(K key, V value, long expireAfterWrite, TimeUnit timeUnit) {
        return cache.PUT_IF_ABSENT(genKey(key), config.getValueEncoder().apply(value), expireAfterWrite, timeUnit);
    }
}
