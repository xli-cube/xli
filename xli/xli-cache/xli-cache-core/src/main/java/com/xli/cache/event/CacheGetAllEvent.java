package com.xli.cache.event;

import com.xli.cache.Cache;
import com.xli.cache.multi.MultiGetResult;
import lombok.Getter;
import java.util.Set;

@Getter
public class CacheGetAllEvent extends CacheEvent {

    private final long millis;

    private final Set keys;

    private final MultiGetResult result;

    public CacheGetAllEvent(Cache cache, long millis, Set keys, MultiGetResult result) {
        super(cache);
        this.millis = millis;
        this.keys = keys;
        this.result = result;
    }
}
