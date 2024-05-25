package com.xli.cache.autoconfigure;

import com.xli.cache.builder.CacheBuilder;
import com.xli.cache.embedded.CaffeineCacheBuilder;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;


@Component
@Conditional(CaffeineAutoConfiguration.CaffeineCondition.class)
public class CaffeineAutoConfiguration extends EmbeddedCacheAutoInit {
    public CaffeineAutoConfiguration() {
        super("caffeine");
    }

    @Override
    protected CacheBuilder initCache(ConfigTree ct, String cacheAreaWithPrefix) {
        CaffeineCacheBuilder builder = CaffeineCacheBuilder.createCaffeineCacheBuilder();
        parseGeneralConfig(builder, ct);
        return builder;
    }

    public static class CaffeineCondition extends JetCacheCondition {
        public CaffeineCondition() {
            super("caffeine");
        }
    }
}
