package com.xli.cache.proxy;

import com.xli.cache.AbstractCache;
import com.xli.cache.Cache;
import com.xli.cache.config.CacheConfig;
import com.xli.cache.event.CacheEvent;
import com.xli.cache.exception.CacheInvokeException;
import com.xli.cache.loader.CacheLoader;
import com.xli.cache.multi.MultiGetResult;
import com.xli.cache.result.CacheResultCode;
import com.xli.cache.support.CacheUtil;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class LoadingCache<K, V> extends SimpleProxyCache<K, V> {

    protected Consumer<CacheEvent> eventConsumer;

    protected CacheConfig<K, V> config;

    public LoadingCache(Cache<K, V> cache) {
        super(cache);
        this.config = config();
        eventConsumer = CacheUtil.getAbstractCache(cache)::notify;
    }

    @Override
    public V get(K key) throws CacheInvokeException {
        CacheLoader<K, V> loader = config.getLoader();
        if (loader != null) {
            return AbstractCache.computeIfAbsentImpl(key, loader, config.isCacheNullValue(), 0, null, this);
        } else {
            return cache.get(key);
        }
    }

    @Override
    public Map<K, V> getAll(Set<? extends K> keys) throws CacheInvokeException {
        CacheLoader<K, V> loader = config.getLoader();
        if (loader != null) {
            MultiGetResult<K, V> r = GET_ALL(keys);
            Map<K, V> kvMap;
            if (r.isSuccess() || r.getResultCode() == CacheResultCode.PART_SUCCESS) {
                kvMap = r.unwrapValues();
            } else {
                kvMap = new HashMap<>();
            }
            Set<K> keysNeedLoad = new LinkedHashSet<>();
            keys.forEach((k) -> {
                if (!kvMap.containsKey(k)) {
                    keysNeedLoad.add(k);
                }
            });
            if (!config.isCachePenetrationProtect()) {
                if (eventConsumer != null) {
                    loader = CacheUtil.createProxyLoader(cache, loader, eventConsumer);
                }
                Map<K, V> loadResult;
                try {
                    loadResult = loader.loadAll(keysNeedLoad);

                    CacheLoader<K, V> theLoader = loader;
                    Map<K, V> updateValues = new HashMap<>();
                    loadResult.forEach((k, v) -> {
                        if (needUpdate(v, theLoader)) {
                            updateValues.put(k, v);
                        }
                    });
                    if (!updateValues.isEmpty()) {
                        PUT_ALL(updateValues);
                    }
                } catch (Throwable e) {
                    throw new CacheInvokeException(e);
                }
                kvMap.putAll(loadResult);
            } else {
                AbstractCache<K, V> abstractCache = CacheUtil.getAbstractCache(cache);
                loader = CacheUtil.createProxyLoader(cache, loader, eventConsumer);
                for (K key : keysNeedLoad) {
                    Consumer<V> cacheUpdater = (v) -> {
                        if (needUpdate(v, config.getLoader())) {
                            PUT(key, v);
                        }
                    };
                    V v = AbstractCache.synchronizedLoad(config, abstractCache, key, loader, cacheUpdater);
                    kvMap.put(key, v);
                }
            }
            return kvMap;
        } else {
            return cache.getAll(keys);
        }
    }

    protected boolean needUpdate(V loadedValue, CacheLoader<K, V> loader) {
        if (loadedValue == null && !config.isCacheNullValue()) {
            return false;
        }
        return !loader.vetoCacheUpdate();
    }
}
