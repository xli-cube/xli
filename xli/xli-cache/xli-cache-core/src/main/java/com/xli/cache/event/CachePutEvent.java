package com.xli.cache.event;


import com.xli.cache.Cache;
import com.xli.cache.result.CacheResult;
import lombok.Getter;

@Getter
public class CachePutEvent extends CacheEvent {

    private final long millis;

    private final Object key;

    private final Object value;

    private final CacheResult result;

    public CachePutEvent(Cache cache, long millis, Object key, Object value, CacheResult result) {
        super(cache);
        this.millis = millis;
        this.key = key;
        this.value = value;
        this.result = result;
    }
}
