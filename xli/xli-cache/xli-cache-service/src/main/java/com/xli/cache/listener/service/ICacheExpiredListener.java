package com.xli.cache.listener.service;

public interface ICacheExpiredListener {

    /**
     * 缓存因为超时失效后触发的通知
     *
     * @param region 缓存region
     * @param key    缓存key
     */
    void notifyElementExpired(String region, String key);
}
