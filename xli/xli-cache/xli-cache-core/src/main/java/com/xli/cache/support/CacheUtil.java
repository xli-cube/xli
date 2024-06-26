package com.xli.cache.support;

import com.xli.cache.AbstractCache;
import com.xli.cache.Cache;
import com.xli.cache.event.CacheEvent;
import com.xli.cache.event.CacheLoadAllEvent;
import com.xli.cache.event.CacheLoadEvent;
import com.xli.cache.loader.CacheLoader;
import com.xli.cache.proxy.ProxyCache;

import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public class CacheUtil {

    public static <K, V> ProxyLoader<K, V> createProxyLoader(Cache<K, V> cache, CacheLoader<K, V> loader, Consumer<CacheEvent> eventConsumer) {
        if (loader instanceof ProxyLoader) {
            return (ProxyLoader<K, V>) loader;
        }
        return new ProxyLoader<>() {
            @Override
            public V load(K key) throws Throwable {
                long t = System.currentTimeMillis();
                V v = null;
                boolean success = false;
                try {
                    v = loader.load(key);
                    success = true;
                } finally {
                    t = System.currentTimeMillis() - t;
                    CacheLoadEvent event = new CacheLoadEvent(cache, t, key, v, success);
                    eventConsumer.accept(event);
                }
                return v;
            }

            @Override
            public Map<K, V> loadAll(Set<K> keys) throws Throwable {
                long t = System.currentTimeMillis();
                boolean success = false;
                Map<K, V> kvMap = null;
                try {
                    kvMap = loader.loadAll(keys);
                    success = true;
                } finally {
                    t = System.currentTimeMillis() - t;
                    CacheLoadAllEvent event = new CacheLoadAllEvent(cache, t, keys, kvMap, success);
                    eventConsumer.accept(event);
                }
                return kvMap;
            }

            @Override
            public boolean vetoCacheUpdate() {
                return loader.vetoCacheUpdate();
            }
        };
    }

    public static <K, V> ProxyLoader<K, V> createProxyLoader(Cache<K, V> cache, Function<K, V> loader, Consumer<CacheEvent> eventConsumer) {
        if (loader instanceof ProxyLoader) {
            return (ProxyLoader<K, V>) loader;
        }
        if (loader instanceof CacheLoader) {
            return createProxyLoader(cache, (CacheLoader) loader, eventConsumer);
        }
        return k -> {
            long t = System.currentTimeMillis();
            V v = null;
            boolean success = false;
            try {
                v = loader.apply(k);
                success = true;
            } finally {
                t = System.currentTimeMillis() - t;
                CacheLoadEvent event = new CacheLoadEvent(cache, t, k, v, success);
                eventConsumer.accept(event);
            }
            return v;
        };
    }

    public static <K, V> AbstractCache<K, V> getAbstractCache(Cache<K, V> c) {
        while (c instanceof ProxyCache) {
            c = ((ProxyCache) c).getTargetCache();
        }
        return (AbstractCache) c;
    }


    private interface ProxyLoader<K, V> extends CacheLoader<K, V> {
    }

}
