package com.xli.cache.event;


import com.xli.cache.Cache;
import com.xli.cache.result.CacheResult;
import lombok.Getter;

import java.util.Map;

@Getter
public class CachePutAllEvent extends CacheEvent {

    private final long millis;

    private final Map map;

    private final CacheResult result;

    public CachePutAllEvent(Cache cache, long millis, Map map, CacheResult result) {
        super(cache);
        this.millis = millis;
        this.map = map;
        this.result = result;
    }

}
