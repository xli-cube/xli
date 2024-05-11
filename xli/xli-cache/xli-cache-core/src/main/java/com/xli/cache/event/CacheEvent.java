package com.xli.cache.event;

import com.xli.cache.Cache;
import lombok.Data;

@Data
public class CacheEvent {

    protected Cache cache;

    public CacheEvent(Cache cache) {
        this.cache = cache;
    }
}
