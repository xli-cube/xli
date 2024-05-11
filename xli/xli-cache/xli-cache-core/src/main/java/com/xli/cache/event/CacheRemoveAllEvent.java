package com.xli.cache.event;


import com.xli.cache.Cache;
import com.xli.cache.result.CacheResult;
import lombok.Getter;

import java.util.Set;

@Getter
public class CacheRemoveAllEvent extends CacheEvent {

    private final long millis;

    private final Set keys;

    private final CacheResult result;

    public CacheRemoveAllEvent(Cache cache, long millis, Set keys, CacheResult result) {
        super(cache);
        this.millis = millis;
        this.keys = keys;
        this.result = result;
    }

}
