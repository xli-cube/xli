package com.xli.cache.policy.redis;

import cn.hutool.extra.spring.SpringUtil;
import com.xli.cache.config.CacheProperties;
import com.xli.cache.listener.service.ICacheExpiredListener;
import com.xli.cache.policy.CacheChannel;
import com.xli.cache.service.ICacheProvider;
import com.xli.cache.service.ILevel2Cache;
import com.xli.cache.service.top.ICache;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Redis 缓存管理，实现对多种 Redis 运行模式的支持和自动适配，实现连接池管理等
 */
@Slf4j
public class RedisCacheProvider implements ICacheProvider {

    private final ConcurrentHashMap<String, ILevel2Cache> regions = new ConcurrentHashMap<>();
    private RedisClient redisClient;
    private String namespace;
    private String storage;
    private int scanCount;

    @Override
    public String name() {
        return "redis";
    }

    @Override
    public int level() {
        return 2;
    }

    @Override
    public ICache buildCache(String region, ICacheExpiredListener listener) {
        return regions.computeIfAbsent(this.namespace + ":" + region, v -> new RedisGenericCache(this.namespace, region, redisClient, scanCount));
    }

    @Override
    public ICache buildCache(String region, long timeToLiveInSeconds, ICacheExpiredListener listener) {
        return buildCache(region, listener);
    }

    @Override
    public Collection<CacheChannel.Region> regions() {
        //TODO emptyList
        return Collections.emptyList();
    }

    public void start(Properties props) {
        this.scanCount = Integer.valueOf(props.getProperty("scanCount", "1000"));
        this.namespace = props.getProperty("namespace");
        this.storage = props.getProperty("storage");

        JedisPoolConfig poolConfig = RedisUtils.newPoolConfig(props, null);

        String hosts = props.getProperty("hosts", "127.0.0.1:6379");
        String mode = props.getProperty("mode", "single");
        String clusterName = props.getProperty("cluster_name");
        String password = props.getProperty("password");
        int database = Integer.parseInt(props.getProperty("database", "0"));
        boolean ssl = Boolean.valueOf(props.getProperty("ssl", "false"));

        long ct = System.currentTimeMillis();

        this.redisClient = new RedisClient.Builder()
                .mode(mode)
                .hosts(hosts)
                .password(password)
                .cluster(clusterName)
                .database(database)
                .poolConfig(poolConfig)
                .ssl(ssl)
                .newClient();

        log.info("Redis client starts with mode({}),db({}),storage({}),namespace({}),time({}ms)",
                mode,
                database,
                storage,
                namespace,
                (System.currentTimeMillis()-ct)
        );
    }

    @Override
    public void stop() {
        regions.clear();
        try {
            redisClient.close();
        } catch (IOException e) {
            log.warn("Failed to close redis connection.", e);
        }
    }
}
