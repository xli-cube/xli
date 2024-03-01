package com.xli.cache.config;

import com.xli.cache.builder.CacheBuilder;
import com.xli.cache.exception.CacheException;
import com.xli.cache.policy.CacheChannel;

/**
 * 缓存入口
 */
public class Cache {

    private final static String CONFIG_FILE = "/cache.properties";

    private final static CacheBuilder builder;

    static {
        try {
            builder = CacheBuilder.init(CacheConfig.initFromConfig(CONFIG_FILE));
        } catch (Exception e) {
            throw new CacheException("Failed to load xli-cache configuration ", e);
        }
    }

    public static CacheChannel getChannel() {
        return builder.getChannel();
    }

    public static void close() {
        builder.close();
    }
}
