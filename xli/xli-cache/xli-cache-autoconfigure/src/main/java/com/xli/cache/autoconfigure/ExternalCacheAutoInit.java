package com.xli.cache.autoconfigure;

import com.xli.cache.anno.CacheConstants;
import com.xli.cache.builder.CacheBuilder;
import com.xli.cache.anno.support.ParserFunction;
import com.xli.cache.external.ExternalCacheBuilder;

public abstract class ExternalCacheAutoInit extends AbstractCacheAutoInit {
    public ExternalCacheAutoInit(String... cacheTypes) {
        super(cacheTypes);
    }

    @Override
    protected void parseGeneralConfig(CacheBuilder builder, ConfigTree ct) {
        super.parseGeneralConfig(builder, ct);
        ExternalCacheBuilder ecb = (ExternalCacheBuilder) builder;
        ecb.setKeyPrefix(ct.getProperty("keyPrefix"));
        ecb.setBroadcastChannel(parseBroadcastChannel(ct));
        ecb.setValueEncoder(new ParserFunction(ct.getProperty("valueEncoder", CacheConstants.DEFAULT_SERIAL_POLICY)));
        ecb.setValueDecoder(new ParserFunction(ct.getProperty("valueDecoder", CacheConstants.DEFAULT_SERIAL_POLICY)));
    }

    protected String parseBroadcastChannel(ConfigTree ct) {
        String broadcastChannel = ct.getProperty("broadcastChannel");
        if (broadcastChannel != null && !"".equals(broadcastChannel.trim())) {
            return broadcastChannel.trim();
        } else {
            return null;
        }
    }
}
