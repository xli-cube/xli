package com.xli.cache.template;

import com.xli.cache.Cache;
import com.xli.cache.builder.CacheBuilder;
import com.xli.cache.external.ExternalCacheBuilder;
import com.xli.cache.manager.CacheManager;
import com.xli.cache.monitor.BroadcastManager;
import com.xli.cache.monitor.CacheMonitor;
import com.xli.cache.monitor.CacheNotifyMonitor;
import com.xli.cache.multi.MultiLevelCache;
import com.xli.cache.support.CacheUtil;

import java.util.function.Function;

public class NotifyMonitorInstaller implements CacheMonitorInstaller {

    private final Function<String, CacheBuilder> remoteBuilderTemplate;

    public NotifyMonitorInstaller(Function<String, CacheBuilder> remoteBuilderTemplate) {
        this.remoteBuilderTemplate = remoteBuilderTemplate;
    }

    @Override
    public void addMonitors(CacheManager cacheManager, Cache cache, QuickConfig quickConfig) {
        if (quickConfig.getSyncLocal() == null || !quickConfig.getSyncLocal()) {
            return;
        }
        if (!(CacheUtil.getAbstractCache(cache) instanceof MultiLevelCache)) {
            return;
        }
        String area = quickConfig.getArea();
        final ExternalCacheBuilder cacheBuilder = (ExternalCacheBuilder) remoteBuilderTemplate.apply(area);
        if (cacheBuilder == null || !cacheBuilder.supportBroadcast()
                || cacheBuilder.getConfig().getBroadcastChannel() == null) {
            return;
        }

        if (cacheManager.getBroadcastManager(area) == null) {
            BroadcastManager cm = cacheBuilder.createBroadcastManager(cacheManager);
            if (cm != null) {
                cm.startSubscribe();
                cacheManager.putBroadcastManager(area, cm);
            }
        }

        CacheMonitor monitor = new CacheNotifyMonitor(cacheManager, area, quickConfig.getName());
        cache.config().getMonitors().add(monitor);
    }
}
