package com.xli.cache.service;

import com.xli.cache.listener.service.ICacheExpiredListener;
import com.xli.cache.policy.CacheChannel;
import com.xli.cache.service.top.ICache;

import java.util.Collection;
import java.util.Properties;

/**
 * 缓存提供商
 */
public interface ICacheProvider {

    /**
     * 缓存标识名称
     *
     * @return 缓存标识名称
     */
    String name();

    /**
     * 缓存层级
     *
     * @return 缓存层级
     */
    int level();

    /**
     * 判断缓存层级
     *
     * @param level 缓存层级
     * @return 是否
     */
    default boolean isLevel(int level) {
        return (level() & level) != level;
    }

    /**
     * 构建缓存
     *
     * @param region   缓存区域名称
     * @param listener 监听器
     * @return 缓存实例
     */
    ICache buildCache(String region, ICacheExpiredListener listener);

    /**
     * 构建缓存
     *
     * @param region              缓存区域名称
     * @param timeToLiveInSeconds 过去时间
     * @param listener            监听器
     * @return 缓存实例
     */
    ICache buildCache(String region, long timeToLiveInSeconds, ICacheExpiredListener listener);

    /**
     * 获取所有定义的缓存区域名称
     *
     * @return 集合
     */
    Collection<CacheChannel.Region> regions();

    /**
     * 移除缓存区域
     *
     * @param region 缓存区域
     */
    default void removeCache(String region) {
    }

    /**
     * 缓存初始化
     */
    void start(Properties props);

    /**
     * 停止缓存
     */
    void stop();
}
