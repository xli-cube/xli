package com.xli.cache.policy;

import com.xli.cache.config.CacheProviderHolder;
import com.xli.cache.policy.none.NoneClusterPolicy;
import com.xli.cache.policy.redis.RedisPubSubClusterPolicy;

import java.util.Properties;

/**
 * 集群策略工厂
 */
public class ClusterPolicyFactory {

    /**
     * 初始化集群消息通知机制
     *
     * @param holder
     * @return
     */
    public final static IClusterPolicy init(CacheProviderHolder holder, String broadcast, Properties props) {
        IClusterPolicy policy;
        if ("redis".equalsIgnoreCase(broadcast)) {
            policy = ClusterPolicyFactory.redis(props, holder);
        } else {
            policy = new NoneClusterPolicy();
        }
        return policy;
    }

    private static IClusterPolicy redis(Properties props, CacheProviderHolder holder) {
        String name = props.getProperty("channel");
        RedisPubSubClusterPolicy policy = new RedisPubSubClusterPolicy(name, props);
        policy.connect(holder);
        return policy;
    }
}
