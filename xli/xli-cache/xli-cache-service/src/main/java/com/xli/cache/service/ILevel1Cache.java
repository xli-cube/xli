package com.xli.cache.service;

import com.xli.cache.service.top.ICache;

/**
 * 一级缓存接口
 */
public interface ILevel1Cache extends ICache {

    /**
     * 返回该缓存区域的TTL设置（单位：秒）
     *
     * @return 返回该缓存区域的TTL设置（单位：秒）
     */
    long ttl();

    /**
     * 返回该缓存区域中，内存存储对象的最大数量
     *
     * @return 返回该缓存区域中，内存存储对象的最大数量
     */
    long size();
}
