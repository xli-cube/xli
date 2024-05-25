package com.xli.cache.autoconfigure;

import com.xli.cache.anno.CacheConstants;
import com.xli.cache.builder.CacheBuilder;
import com.xli.cache.external.MockRemoteCacheBuilder;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Component
@Conditional(MockRemoteCacheAutoConfiguration.MockRemoteCacheCondition.class)
public class MockRemoteCacheAutoConfiguration extends ExternalCacheAutoInit {
    public MockRemoteCacheAutoConfiguration() {
        super("mock");
    }

    @Override
    protected CacheBuilder initCache(ConfigTree ct, String cacheAreaWithPrefix) {
        MockRemoteCacheBuilder builder = MockRemoteCacheBuilder.createMockRemoteCacheBuilder();
        parseGeneralConfig(builder, ct);
        return builder;
    }

    @Override
    protected void parseGeneralConfig(CacheBuilder builder, ConfigTree ct) {
        super.parseGeneralConfig(builder, ct);
        MockRemoteCacheBuilder b = (MockRemoteCacheBuilder) builder;
        b.limit(Integer.parseInt(ct.getProperty("limit", String.valueOf(CacheConstants.DEFAULT_LOCAL_LIMIT))));
    }

    public static class MockRemoteCacheCondition extends JetCacheCondition {
        public MockRemoteCacheCondition() {
            super("mock");
        }
    }
}
