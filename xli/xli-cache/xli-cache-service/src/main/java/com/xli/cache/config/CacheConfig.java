package com.xli.cache.config;

import com.xli.cache.exception.CacheException;

import java.io.*;
import java.util.Properties;

public class CacheConfig {

    private Properties properties = new Properties();
    private Properties broadcastProperties = new Properties();
    private Properties l1CacheProperties = new Properties();
    private Properties l2CacheProperties = new Properties();

    private String broadcast;
    private String l1CacheName;
    private String l2CacheName;
    private String serialization;
    private boolean syncTtlToRedis;
    private boolean defaultCacheNullObject;

    public final static CacheConfig initFromConfig(String configResource) throws IOException {
        try (InputStream stream = getConfigStream(configResource)) {
            return initFromConfig(stream);
        }
    }

    public final static CacheConfig initFromConfig(InputStream stream) throws IOException {
        Properties properties = new Properties();
        properties.load(stream);
        return initFromConfig(properties);
    }

    public final static CacheConfig initFromConfig(Properties properties) {
        CacheConfig config = new CacheConfig();
        config.properties = properties;
        // TODO 读取配置文件参数
        return config;
    }

    private static InputStream getConfigStream(String resource) {
        File resourcePath =new File(resource);
        InputStream configStream = null;
        try{
            configStream = new FileInputStream(resourcePath);
        }catch (FileNotFoundException e){
            if(configStream == null){
                configStream = Cache.class.getResourceAsStream(resource);
            }

            if (configStream == null) {
                configStream = Cache.class.getClassLoader().getParent().getResourceAsStream(resource);
            }
            if (configStream == null) {
                throw new CacheException("Cannot find " + resource + " !");
            }
        }
        return configStream;

    }

    public String getL1CacheName() {
        return l1CacheName;
    }

    public void setL1CacheName(String provider1) {
        this.l1CacheName = provider1;
    }
    public String getL2CacheName() {
        return l2CacheName;
    }

    public void setL2CacheName(String provider2) {
        this.l2CacheName = provider2;
    }

    public Properties getL1CacheProperties() {
        return l1CacheProperties;
    }

    public void setL1CacheProperties(Properties l1CacheProperties) {
        this.l1CacheProperties = l1CacheProperties;
    }

    public Properties getL2CacheProperties() {
        return l2CacheProperties;
    }

    public void setL2CacheProperties(Properties l2CacheProperties) {
        this.l2CacheProperties = l2CacheProperties;
    }

    public Properties getProperties() {
        return properties;
    }

    public boolean isDefaultCacheNullObject() {
        return defaultCacheNullObject;
    }

    public void setDefaultCacheNullObject(boolean defaultCacheNullObject) {
        this.defaultCacheNullObject = defaultCacheNullObject;
    }

    public String getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(String broadcast) {
        this.broadcast = broadcast;
    }

    public Properties getBroadcastProperties() {
        return broadcastProperties;
    }

    public void setBroadcastProperties(Properties broadcastProperties) {
        this.broadcastProperties = broadcastProperties;
    }
}
