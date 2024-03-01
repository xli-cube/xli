package com.xli.cache.util;

import com.xli.cache.config.Cache;
import com.xli.cache.policy.CacheObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CacheUtil {

    private static final String cacheName = "xli.cache";

    /**
     * 获取缓存对象
     *
     * @param key 缓存key
     * @return 缓存对象或者null
     */
    public static Object get(String key) {
        return get(cacheName, key);
    }

    /**
     * 获取缓存对象
     *
     * @param region 缓存区域
     * @param key    缓存key
     * @return 缓存对象或者null
     */
    public static Object get(String region, String key) {
        return Cache.getChannel().get(region, key).getValue();
    }

    /**
     * 批量获取缓存对象
     *
     * @param keys 缓存key
     * @return 缓存键值对集合
     */
    public static Map<String, Object> get(Collection<String> keys) {
        return get(cacheName, keys);
    }

    /**
     * 批量获取缓存对象
     *
     * @param region 缓存区域
     * @param keys   缓存key集合
     * @return 缓存键值对集合
     */
    public static Map<String, Object> get(String region, Collection<String> keys) {
        Map<String, Object> result = new HashMap<>();
        Map<String, CacheObject> cacheResult = Cache.getChannel().get(region, keys);
        for (String key : cacheResult.keySet()) {
            result.put(key, cacheResult.get(key).getValue());
        }
        return result;
    }

    /**
     * 以非事务方式向缓存中添加对象
     *
     * @param key   缓存key
     * @param value 缓存值
     */
    public static void put(String key, Object value) {
        put(cacheName, key, value);
    }

    /**
     * 以非事务方式向缓存中添加对象
     *
     * @param region 缓存区域
     * @param key    缓存key
     * @param value  缓存值
     */
    public static void put(String region, String key, Object value) {
        Cache.getChannel().set(region, key, value);
    }

    /**
     * 批量插入数据
     *
     * @param elements 键值对集合
     */
    public static void put(Map<String, Object> elements) {
        put(cacheName, elements);
    }

    /**
     * 批量插入数据
     *
     * @param region   缓存区域
     * @param elements 键值对集合
     */
    public static void put(String region, Map<String, Object> elements) {
        Cache.getChannel().set(region, elements);
    }


    /**
     * 判断缓存是否存在
     *
     * @param key 缓存key
     * @return 如果存在，则返回true
     */
    public static boolean exists(String key) {
        return exists(cacheName, key);
    }

    /**
     * 判断缓存是否存在
     *
     * @param region 缓存区域
     * @param key    缓存key
     * @return 如果存在，则返回true
     */
    public static boolean exists(String region, String key) {
        return get(region, key) != null;
    }

    /**
     * 返回所有键集合
     *
     * @return 缓存key集合
     */
    public static Collection<String> keys() {
        return keys(cacheName);
    }

    /**
     * 返回所有键集合
     *
     * @param region 缓存区域
     * @return 缓存key集合
     */
    public static Collection<String> keys(String region) {
        return Cache.getChannel().keys(region);
    }

    /**
     * 移除缓存对象
     *
     * @param keys 缓存key
     */
    public static void evict(String... keys) {
        evict(cacheName, keys);
    }

    /**
     * 移除缓存对象
     *
     * @param region 缓存区域
     * @param keys   缓存key
     */
    public static void evict(String region, String... keys) {
        Cache.getChannel().evict(region, keys);
    }

    /**
     * 清空缓存
     */
    public static void clear() {
        clear(cacheName);
    }

    /**
     * 清空缓存
     * @param region 缓存区域
     */
    public static void clear(String region) {
        Cache.getChannel().clear(region);
    }
}
