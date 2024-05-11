package com.xli.cache.event;

import com.xli.cache.Cache;
import lombok.Getter;

@Getter
public class CacheLoadEvent extends CacheEvent {

    private final long millis;

    private final Object key;

    private final Object loadedValue;

    private final boolean success;

    public CacheLoadEvent(Cache cache, long millis, Object key, Object loadedValue, boolean success) {
        super(cache);
        this.millis = millis;
        this.key = key;
        this.loadedValue = loadedValue;
        this.success = success;
    }

}
