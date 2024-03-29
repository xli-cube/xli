package com.xli.cache.policy.redis;

import com.xli.cache.config.CacheProperties;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Properties;

public class RedisUtils {

    /**
     * 初始化 Redis 连接池
     */
    public final static JedisPoolConfig newPoolConfig(Properties props, String prefix) {
        JedisPoolConfig cfg = new JedisPoolConfig();
        cfg.setMaxTotal(Integer.valueOf(props.getProperty(key(prefix,"maxTotal"), "-1")));
        cfg.setMaxIdle(Integer.valueOf(props.getProperty(key(prefix,"maxIdle"), "100")));
        cfg.setMaxWaitMillis(Integer.valueOf(props.getProperty(key(prefix,"maxWaitMillis"), "100")));
        cfg.setMinEvictableIdleTimeMillis(Integer.valueOf(props.getProperty(key(prefix,"minEvictableIdleTimeMillis"), "864000000")));
        cfg.setMinIdle(Integer.valueOf(props.getProperty(key(prefix,"minIdle"), "10")));
        cfg.setNumTestsPerEvictionRun(Integer.valueOf(props.getProperty(key(prefix,"numTestsPerEvictionRun"), "10")));
        cfg.setLifo(Boolean.valueOf(props.getProperty(key(prefix,"lifo"), "false")));
        cfg.setSoftMinEvictableIdleTimeMillis(Integer.valueOf((String)props.getOrDefault(key(prefix,"softMinEvictableIdleTimeMillis"), "10")));
        cfg.setTestOnBorrow(Boolean.valueOf(props.getProperty(key(prefix,"testOnBorrow"), "true")));
        cfg.setTestOnReturn(Boolean.valueOf(props.getProperty(key(prefix,"testOnReturn"), "false")));
        cfg.setTestWhileIdle(Boolean.valueOf(props.getProperty(key(prefix,"testWhileIdle"), "true")));
        cfg.setTimeBetweenEvictionRunsMillis(Integer.valueOf(props.getProperty(key(prefix,"timeBetweenEvictionRunsMillis"), "300000")));
        cfg.setBlockWhenExhausted(Boolean.valueOf(props.getProperty(key(prefix,"blockWhenExhausted"), "false")));
        return cfg;
    }

    private static String key(String prefix, String key) {
        return (prefix == null) ? key : prefix + "." + key;
    }

}
