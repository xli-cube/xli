package com.xli.cache.redis.springdata;

import com.xli.cache.external.ExternalCacheConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Setter
@Getter
public class RedisSpringDataCacheConfig<K, V> extends ExternalCacheConfig<K, V> {

    private RedisConnectionFactory connectionFactory;

    /**
     * optional.
     */
    private RedisMessageListenerContainer listenerContainer;

}
