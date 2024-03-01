package com.xli.cache.service.top;

import java.util.Collection;
import java.util.Map;

/**
 * 缓存操作接口
 */
public interface ICache {

    /**
     * 获取缓存对象
     *
     * @param key 缓存key
     * @return 缓存对象或者null
     */
    Object get(String key);

    /**
     * 批量获取缓存对象
     *
     * @param keys 缓存key
     * @return 缓存键值对集合
     */
    Map<String, Object> get(Collection<String> keys);

    /**
     * 以非事务方式向缓存中添加对象
     *
     * @param key   缓存key
     * @param value 缓存值
     */
    void put(String key, Object value);

    /**
     * 批量插入数据
     *
     * @param elements 键值对集合
     */
    void put(Map<String, Object> elements);


    /**
     * 判断缓存是否存在
     *
     * @param key 缓存key
     * @return 如果存在，则返回true
     */
    default boolean exists(String key) {
        return get(key) != null;
    }

    /**
     * 返回所有键集合
     *
     * @return 缓存key集合
     */
    Collection<String> keys();

    /**
     * 移除缓存对象
     *
     * @param keys 缓存key
     */
    void evict(String... keys);

    /**
     * 清空缓存
     */
    void clear();
}
