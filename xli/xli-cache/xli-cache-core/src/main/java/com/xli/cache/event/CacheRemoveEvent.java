package com.xli.cache.event;


import com.xli.cache.Cache;
import com.xli.cache.result.CacheResult;
import lombok.Getter;

@Getter
public class CacheRemoveEvent extends CacheEvent {

    private final long millis;

    private final Object key;

    private final CacheResult result;

    public CacheRemoveEvent(Cache cache, long millis, Object key, CacheResult result) {
        super(cache);
        this.millis = millis;
        this.key = key;
        this.result = result;
    }

}
