package com.xli.cache.event;

import com.xli.cache.Cache;
import com.xli.cache.result.CacheGetResult;
import lombok.Getter;

@Getter
public class CacheGetEvent extends CacheEvent {

    private final long millis;

    private final Object key;

    private final CacheGetResult result;

    public CacheGetEvent(Cache cache, long millis, Object key, CacheGetResult result) {
        super(cache);
        this.millis = millis;
        this.key = key;
        this.result = result;
    }

}
