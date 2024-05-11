package com.xli.cache.redis.springdata;

import com.xli.cache.external.ExternalCacheBuilder;
import com.xli.cache.manager.CacheManager;
import com.xli.cache.monitor.BroadcastManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

public class RedisSpringDataCacheBuilder<T extends ExternalCacheBuilder<T>> extends ExternalCacheBuilder<T> {

    public static class RedisSpringDataCacheBuilderImpl extends RedisSpringDataCacheBuilder<RedisSpringDataCacheBuilderImpl> {
    }

    public static RedisSpringDataCacheBuilderImpl createBuilder() {
        return new RedisSpringDataCacheBuilderImpl();
    }

    protected RedisSpringDataCacheBuilder() {
        buildFunc(config -> new RedisSpringDataCache((RedisSpringDataCacheConfig) config));
    }

    @Override
    public RedisSpringDataCacheConfig getConfig() {
        if (config == null) {
            config = new RedisSpringDataCacheConfig();
        }
        return (RedisSpringDataCacheConfig) config;
    }

    @Override
    public boolean supportBroadcast() {
        return true;
    }

    @Override
    public BroadcastManager createBroadcastManager(CacheManager cacheManager) {
        RedisSpringDataCacheConfig c = (RedisSpringDataCacheConfig) getConfig().clone();
        return new SpringDataBroadcastManager(cacheManager, c);
    }

    public T connectionFactory(RedisConnectionFactory connectionFactory) {
        getConfig().setConnectionFactory(connectionFactory);
        return self();
    }

    public void setConnectionFactory(RedisConnectionFactory connectionFactory) {
        getConfig().setConnectionFactory(connectionFactory);
    }

    public T listenerContainer(RedisMessageListenerContainer listenerContainer) {
        getConfig().setListenerContainer(listenerContainer);
        return self();
    }

    public void setListenerContainer(RedisMessageListenerContainer listenerContainer) {
        getConfig().setListenerContainer(listenerContainer);
    }
}
