package com.xli.cache.monitor;

import com.xli.cache.event.CacheEvent;

@FunctionalInterface
public interface CacheMonitor {

    void afterOperation(CacheEvent event);

}
