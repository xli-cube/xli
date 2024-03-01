package com.xli.cache.policy.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import java.io.Closeable;
import java.io.IOException;

/**
 * 封装各种模式的 Redis 客户端成统一接口
 */
public class RedisClient implements Closeable, AutoCloseable {

    private final static Logger log = LoggerFactory.getLogger(RedisClient.class);

    private final static int CONNECT_TIMEOUT = 5000;    //Redis连接超时时间

    private final ThreadLocal<Jedis> clients;
    private JedisPool single;

    /**
     * 各种模式 Redis 客户端的封装
     *
     * @param mode         Redis 服务器运行模式
     * @param hosts        Redis 主机连接信息
     * @param password     Redis 密码（如果有的话）
     * @param cluster_name 集群名称
     * @param database     数据库
     * @param poolConfig   连接池配置
     * @param ssl          使用ssl
     */
    private RedisClient(String mode, String hosts, String password, String cluster_name, int database, JedisPoolConfig poolConfig, boolean ssl) {
        password = (password != null && password.trim().length() > 0) ? password.trim() : null;
        this.clients = new ThreadLocal<>();
        for (String node : hosts.split(",")) {
            String[] infos = node.split(":");
            String host = infos[0];
            int port = (infos.length > 1) ? Integer.parseInt(infos[1]) : 6379;
            this.single = new JedisPool(poolConfig, host, port, CONNECT_TIMEOUT, password, database, ssl);
            break;
        }
        if (!"single".equalsIgnoreCase(mode)) log.warn("Redis mode [{}] not defined. Using 'single'.", mode);
    }

    /**
     * 获取客户端接口
     *
     * @return 返回基本的 Jedis 二进制命令接口
     */
    public Jedis get() {
        Jedis client = clients.get();
        if (client == null) {
            if (single != null) {
                //单体
                client = single.getResource();
            }
            clients.set(client);
        }
        return client;
    }

    /**
     * 释放当前 Redis 连接
     */
    public void release() {
        Closeable client = (Closeable) clients.get();
        if (client != null) {
            //JedisCluster 会自动释放连接
            if (client instanceof Closeable && !(client instanceof JedisCluster)) {
                try {
                    client.close();
                } catch (IOException e) {
                    log.error("Failed to release jedis connection.", e);
                }
            }
            clients.remove();
        }
    }

    /**
     * 释放连接池
     *
     * @throws IOException io close exception
     */
    @Override
    public void close() throws IOException {
        if (single != null) {
            single.close();
        }
    }

    /**
     * RedisClient 构造器
     */
    public static class Builder {
        private String mode;
        private String hosts;
        private String password;
        private String cluster;
        private int database;
        private JedisPoolConfig poolConfig;
        private boolean ssl;

        public Builder() {
        }

        public Builder mode(String mode) {
            if (mode == null || mode.trim().length() == 0) {
                //默认redis单机
                this.mode = "single";
            } else {
                this.mode = mode;
            }
            return this;
        }

        public Builder hosts(String hosts) {
            if (hosts == null || hosts.trim().length() == 0) {
                this.hosts = "127.0.0.1:6379";
            } else {
                this.hosts = hosts;
            }
            return this;
        }

        public Builder password(String password) {
            if (password != null && password.trim().length() > 0) {
                this.password = password;
            }
            return this;
        }

        public Builder cluster(String cluster) {
            if (cluster == null || cluster.trim().length() == 0) {
                this.cluster = "xli-cache";
            } else {
                this.cluster = cluster;
            }
            return this;
        }

        public Builder database(int database) {
            this.database = database;
            return this;
        }

        public Builder poolConfig(JedisPoolConfig poolConfig) {
            this.poolConfig = poolConfig;
            return this;
        }

        public Builder ssl(boolean ssl) {
            this.ssl = ssl;
            return this;
        }

        public RedisClient newClient() {
            return new RedisClient(mode, hosts, password, cluster, database, poolConfig, ssl);
        }
    }

}
