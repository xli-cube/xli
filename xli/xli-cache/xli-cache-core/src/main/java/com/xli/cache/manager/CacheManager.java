package com.xli.cache.manager;


import com.xli.cache.Cache;
import com.xli.cache.anno.CacheConstants;
import com.xli.cache.monitor.BroadcastManager;
import com.xli.cache.template.QuickConfig;

public interface CacheManager {
    <K, V> Cache<K, V> getCache(String area, String cacheName);

    void putCache(String area, String cacheName, Cache cache);

    BroadcastManager getBroadcastManager(String area);

    void putBroadcastManager(String area, BroadcastManager broadcastManager);

    default <K, V> Cache<K, V> getCache(String cacheName) {
        return getCache(CacheConstants.DEFAULT_AREA, cacheName);
    }

    default void putCache(String cacheName, Cache cache){
        putCache(CacheConstants.DEFAULT_AREA, cacheName, cache);
    }

    <K, V> Cache<K, V> getOrCreateCache(QuickConfig config);

    default void putBroadcastManager(BroadcastManager broadcastManager){
        putBroadcastManager(CacheConstants.DEFAULT_AREA, broadcastManager);
    }

}
