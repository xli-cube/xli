package com.xli.cache.autoconfigure;

import com.xli.cache.anno.CacheConstants;
import com.xli.cache.builder.CacheBuilder;
import com.xli.cache.embedded.EmbeddedCacheBuilder;

public abstract class EmbeddedCacheAutoInit extends AbstractCacheAutoInit {

    public EmbeddedCacheAutoInit(String... cacheTypes) {
        super(cacheTypes);
    }

    @Override
    protected void parseGeneralConfig(CacheBuilder builder, ConfigTree ct) {
        super.parseGeneralConfig(builder, ct);
        EmbeddedCacheBuilder ecb = (EmbeddedCacheBuilder) builder;

        ecb.limit(Integer.parseInt(ct.getProperty("limit", String.valueOf(CacheConstants.DEFAULT_LOCAL_LIMIT))));
    }
}
