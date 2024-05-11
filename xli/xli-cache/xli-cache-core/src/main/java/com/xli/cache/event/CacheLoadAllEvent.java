package com.xli.cache.event;


import com.xli.cache.Cache;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

@Getter
public class CacheLoadAllEvent extends CacheEvent {

    private final long millis;

    private final Set keys;

    private final Map loadedValue;

    private final boolean success;

    public CacheLoadAllEvent(Cache cache, long millis, Set keys, Map loadedValue, boolean success) {
        super(cache);
        this.millis = millis;
        this.keys = keys;
        this.loadedValue = loadedValue;
        this.success = success;
    }

}
