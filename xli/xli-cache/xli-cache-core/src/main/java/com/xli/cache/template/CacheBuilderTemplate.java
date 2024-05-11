package com.xli.cache.template;

import com.xli.cache.builder.AbstractCacheBuilder;
import com.xli.cache.builder.CacheBuilder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CacheBuilderTemplate {
    @Getter
    private final boolean penetrationProtect;
    @Getter
    private final List<CacheMonitorInstaller> cacheMonitorInstallers = new ArrayList<>();

    private final Map<String, CacheBuilder>[] cacheBuilders;

    @SafeVarargs
    public CacheBuilderTemplate(boolean penetrationProtect, Map<String, CacheBuilder>... cacheBuilders) {
        this.penetrationProtect = penetrationProtect;
        this.cacheBuilders = cacheBuilders;
    }

    public CacheBuilder getCacheBuilder(int level, String area) {
        CacheBuilder cb = cacheBuilders[level].get(area);
        if (cb instanceof AbstractCacheBuilder) {
            return (CacheBuilder) ((AbstractCacheBuilder<?>) cb).clone();
        } else {
            return cb;
        }
    }

}
