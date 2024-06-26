package com.xli.cache.anno.support;

import com.xli.cache.manager.SimpleCacheManager;
import com.xli.cache.support.StatInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import java.util.function.Consumer;

public class JetCacheBaseBeans {

    protected SpringConfigProvider createConfigProvider() {
        return new SpringConfigProvider();
    }

    @Bean(destroyMethod = "shutdown")
    public SpringConfigProvider springConfigProvider(
            @Autowired ApplicationContext applicationContext,
            @Autowired GlobalCacheConfig globalCacheConfig,
            @Autowired(required = false) EncoderParser encoderParser,
            @Autowired(required = false) KeyConvertorParser keyConvertorParser,
            @Autowired(required = false) Consumer<StatInfo> metricsCallback) {
        SpringConfigProvider cp = createConfigProvider();
        cp.setApplicationContext(applicationContext);
        cp.setGlobalCacheConfig(globalCacheConfig);

        if (encoderParser != null) {
            cp.setEncoderParser(encoderParser);
        }

        if (keyConvertorParser != null) {
            cp.setKeyConvertorParser(keyConvertorParser);
        }

        if (metricsCallback != null) {
            cp.setMetricsCallback(metricsCallback);
        }
        cp.init();
        return cp;
    }

    @Bean(name = "jcCacheManager",destroyMethod = "close")
    public SimpleCacheManager cacheManager(@Autowired ConfigProvider configProvider) {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCacheBuilderTemplate(configProvider.getCacheBuilderTemplate());
        return cacheManager;
    }
}
