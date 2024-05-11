package com.xli.cache.template;


import com.xli.cache.Cache;
import com.xli.cache.manager.CacheManager;

public interface CacheMonitorInstaller {
    void addMonitors(CacheManager cacheManager, Cache cache, QuickConfig quickConfig);
}
