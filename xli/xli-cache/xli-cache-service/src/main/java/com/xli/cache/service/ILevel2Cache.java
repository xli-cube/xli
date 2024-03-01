package com.xli.cache.service;

import com.xli.cache.exception.CacheException;
import com.xli.cache.exception.DeserializeException;
import com.xli.cache.service.top.ICache;
import com.xli.cache.util.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 二级缓存接口
 */
public interface ILevel2Cache extends ICache {

    Logger log = LoggerFactory.getLogger(ILevel2Cache.class);

    /**
     * 是否支持缓存TTL设置
     *
     * @return 是否支持缓存TTL设置
     */
    default boolean supportTTL() {
        return false;
    }

    /**
     * 读取缓存数据字节数组
     *
     * @param key 缓存key
     * @return 缓存对象
     */
    byte[] getBytes(String key);

    /**
     * 同时读取多个 Key
     *
     * @param keys 缓存key集合
     * @return 缓存对象集合
     */
    List<byte[]> getBytes(Collection<String> keys);

    /**
     * 设置缓存数据字节数组
     *
     * @param key   缓存key
     * @param bytes 缓存对象
     */
    void setBytes(String key, byte[] bytes);

    /**
     * 同时设置多个数据
     *
     * @param bytes 缓存对象集合
     */
    void setBytes(Map<String, byte[]> bytes);

    /**
     * 设置缓存数据字节数组（带有效期）
     *
     * @param key                 缓存key
     * @param bytes               缓存对象
     * @param timeToLiveInSeconds 缓存过期时间
     */
    default void setBytes(String key, byte[] bytes, long timeToLiveInSeconds){
        setBytes(key, bytes);
    }

    /**
     * 批量设置带 TTL 的缓存数据
     *
     * @param bytes               缓存键值对集合
     * @param timeToLiveInSeconds 缓存过期时间
     */
    default void setBytes(Map<String,byte[]> bytes, long timeToLiveInSeconds) {
        setBytes(bytes);
    }


    @Override
    default Object get(String key) {
        byte[] bytes = getBytes(key);
        try {
            return SerializationUtils.deserialize(bytes);
        } catch (DeserializeException e) {
            log.warn("Failed to deserialize object with key:" + key + ",message: " + e.getMessage());
            evict(key);
            return null;
        } catch (IOException e) {
            throw new CacheException(e);
        }
    }

    @Override
    default Map<String, Object> get(Collection<String> keys) {
        Map<String, Object> results = new HashMap<>();
        if (keys.size() > 0) {
            List<byte[]> bytes = getBytes(keys);
            int i = 0;
            for (String key : keys) {
                try {
                    results.put(key, SerializationUtils.deserialize(bytes.get(i++)));
                } catch (DeserializeException e) {
                    log.warn("Failed to deserialize object with key:" + key + ",message: " + e.getMessage());
                    evict(key);
                    return null;
                } catch (IOException e) {
                    throw new CacheException(e);
                }
            }
        }
        return results;
    }

    @Override
    default void put(String key, Object value) {
        try {
            setBytes(key, SerializationUtils.serialize(value));
        } catch (IOException e) {
            throw new CacheException(e);
        }
    }

    /**
     * 设置二级缓存有效期
     *
     * @param key                 缓存key
     * @param value               缓存值
     * @param timeToLiveInSeconds 过期时间
     */
    default void put(String key, Object value, long timeToLiveInSeconds) {
        try {
            setBytes(key, SerializationUtils.serialize(value), timeToLiveInSeconds);
        } catch (IOException e) {
            throw new CacheException(e);
        }
    }

    @Override
    default void put(Map<String, Object> elements) {
        if (elements.size() > 0) {
            setBytes(elements.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, p -> SerializationUtils.serializeWithoutException(p.getValue()))));
        }
    }

    /**
     * 批量设置缓存
     *
     * @param elements            缓存键值对集合
     * @param timeToLiveInSeconds 有效期
     */
    default void put(Map<String, Object> elements, long timeToLiveInSeconds) {
        if (elements.size() > 0) {
            setBytes(elements.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, p -> SerializationUtils.serializeWithoutException(p.getValue()))), timeToLiveInSeconds);
        }
    }

    default boolean exists(String key) {
        return getBytes(key) != null;
    }
}
