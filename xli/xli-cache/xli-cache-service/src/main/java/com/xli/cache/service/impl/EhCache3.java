package com.xli.cache.service.impl;

import com.xli.cache.listener.service.ICacheExpiredListener;
import com.xli.cache.service.ILevel1Cache;
import org.ehcache.Cache;
import org.ehcache.config.ResourceType;
import org.ehcache.event.*;
import org.ehcache.expiry.Duration;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ehCache3.x缓存封装，3.x不向下兼容
 * 该封装类实现了缓存操作以及对缓存数据失效的侦听
 */
public class EhCache3 implements ILevel1Cache, CacheEventListener {

    private final String name;

    private final Cache<String, Object> cache;

    private final ICacheExpiredListener listener;

    public EhCache3(String name, Cache<String, Object> cache, ICacheExpiredListener listener) {
        this.name = name;
        this.cache = cache;
        this.cache.getRuntimeConfiguration().registerCacheEventListener(this, EventOrdering.ORDERED, EventFiring.ASYNCHRONOUS, EventType.EXPIRED);
        this.listener = listener;
    }

    @Override
    public Object get(String key) {
        return this.cache.get(key);
    }

    @Override
    public Map<String, Object> get(Collection<String> keys) {
        return cache.getAll(new HashSet<>(keys));
    }

    @Override
    public void put(String key, Object value) {
        this.cache.put(key, value);
    }

    @Override
    public void put(Map<String, Object> elements) {
        cache.putAll(elements);
    }

    @Override
    public Collection<String> keys() {
        //TODO emptyList
        return Collections.emptyList();
    }

    @Override
    public void evict(String... keys) {
        this.cache.removeAll(Arrays.stream(keys).collect(Collectors.toSet()));
    }

    @Override
    public void clear() {
        this.cache.clear();
    }

    @Override
    public long ttl() {
        Duration dur = this.cache.getRuntimeConfiguration().getExpiry().getExpiryForCreation(null, null);
        if (dur.isInfinite()) {
            return 0L;
        }
        return dur.getTimeUnit().toSeconds(dur.getLength());
    }

    @Override
    public long size() {
        return this.cache.getRuntimeConfiguration().getResourcePools().getPoolForResource(ResourceType.Core.HEAP).getSize();
    }

    @Override
    public void onEvent(CacheEvent cacheEvent) {
        if (cacheEvent.getType() == EventType.EXPIRED) {
            this.listener.notifyElementExpired(name, (String) cacheEvent.getKey());
        }
    }
}
