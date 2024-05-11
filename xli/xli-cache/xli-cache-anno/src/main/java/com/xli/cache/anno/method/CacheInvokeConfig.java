package com.xli.cache.anno.method;


import com.xli.cache.anno.support.CacheInvalidateAnnoConfig;
import com.xli.cache.anno.support.CacheUpdateAnnoConfig;
import com.xli.cache.anno.support.CachedAnnoConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CacheInvokeConfig {
    private CachedAnnoConfig cachedAnnoConfig;
    private List<CacheInvalidateAnnoConfig> invalidateAnnoConfigs;
    private CacheUpdateAnnoConfig updateAnnoConfig;
    private boolean enableCacheContext;

    @Getter
    private static final CacheInvokeConfig noCacheInvokeConfigInstance = new CacheInvokeConfig();

}
