package com.xli.cache.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class CacheProperties {

    /**
     * 序列化方式
     */
    private String serialization = "";

    /**
     * 广播方式（两级缓存同步）
     */
    private String broadcast = "redis";

    /**
     * 一级缓存
     */
    private String l1CacheName = "ehcache3";

    /**
     * 二级缓存（固定使用redis订阅方式）
     */
    private String l2CacheName = "redis";

    private boolean defaultCacheNullObject = true;

    //一级缓存配置
    private String defaultHeapSize = "1000";
    private String configXml = "/ehcache3.xml";

    //二级缓存配置
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port:6379}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.database:0}")
    private int database;

    @Value("${spring.redis.timeout:2000}")
    private int timeout;

    @Value("${spring.redis.ssl:false}")
    private Boolean ssl;

    //广播
    private String mode = "single";

    private String channel = "j2cache";

    private String channelHost = "";

    private String clusterName = "j2cache";
    private int scanCount = 1000;
    private String namespace = "";
    private String storage = "";

    private int maxTotal = 100;

    private int maxIdle = 10;

    private int maxWaitMillis = 5000;

    private int minEvictableIdleTimeMillis = 60000;

    private int minIdle = 1;

    private int numTestsPerEvictionRun = 10;

    private int softMinEvictableIdleTimeMillis = 10;

    private int timeBetweenEvictionRunsMillis = 300000;

    private Boolean lifo = false;

    private Boolean testOnBorrow = true;

    private Boolean testOnReturn = false;

    private Boolean testWhileIdle = true;

    private Boolean blockWhenExhausted = false;

    private Boolean jmxEnabled = false;
}
