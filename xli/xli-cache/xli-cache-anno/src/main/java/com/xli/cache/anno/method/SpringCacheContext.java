package com.xli.cache.anno.method;

import com.xli.cache.anno.support.CacheContext;
import com.xli.cache.anno.support.GlobalCacheConfig;
import com.xli.cache.anno.support.SpringConfigProvider;
import com.xli.cache.manager.CacheManager;
import org.springframework.context.ApplicationContext;

public class SpringCacheContext extends CacheContext {

    private ApplicationContext applicationContext;

    public SpringCacheContext(CacheManager cacheManager, SpringConfigProvider configProvider,
                              GlobalCacheConfig globalCacheConfig, ApplicationContext applicationContext) {
        super(cacheManager, configProvider, globalCacheConfig);
        this.applicationContext = applicationContext;
    }

    @Override
    protected CacheInvokeContext newCacheInvokeContext() {
        return new SpringCacheInvokeContext(applicationContext);
    }

}
