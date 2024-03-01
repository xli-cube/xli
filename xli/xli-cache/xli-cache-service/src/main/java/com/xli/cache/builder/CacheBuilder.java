package com.xli.cache.builder;

import com.xli.cache.config.CacheConfig;
import com.xli.cache.config.CacheProperties;
import com.xli.cache.config.CacheProviderHolder;
import com.xli.cache.policy.CacheChannel;
import com.xli.cache.policy.ClusterPolicyFactory;
import com.xli.cache.policy.IClusterPolicy;
import com.xli.cache.util.SerializationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class CacheBuilder {
    private final AtomicBoolean opened = new AtomicBoolean(false);
    private CacheChannel channel;
    private CacheProviderHolder holder;
    private IClusterPolicy policy;//不同的广播策略

    private CacheConfig config;

    private CacheBuilder(CacheConfig config) {
        this.config = config;
    }

    /**
     * 初始化Cache
     *
     * @return
     */
    public final static CacheBuilder init(CacheConfig config) {
        return new CacheBuilder(config);
    }

    public CacheChannel getChannel() {
        if (this.channel == null || !this.opened.get()) {
            synchronized (CacheBuilder.class) {
                if (this.channel == null || !this.opened.get()) {
                    this.initFromConfig(config);
                    /* 初始化缓存接口 */
                    this.channel = new CacheChannel(holder) {
                        @Override
                        public void sendEvictCmd(String region, String... keys) {
                            policy.sendEvictCmd(region, keys);
                        }

                        @Override
                        public void sendClearCmd(String region) {
                            policy.sendClearCmd(region);
                        }

                        @Override
                        public void close() {
                            super.close();
                            policy.disconnect();
                            holder.shutdown();
                            opened.set(false);
                        }
                    };
                    this.opened.set(true);
                }
            }
        }
        return this.channel;
    }

    public void close() {
        this.channel.close();
        this.channel = null;
    }

    /**
     * 从配置文件中加载两级缓存
     */
    private void initFromConfig(CacheConfig config) {
        SerializationUtils.init(null);
        //初始化两级缓存管理
        this.holder = CacheProviderHolder.init(config,(region, key) -> {
            //当一级缓存中的对象失效时，自动清除二级缓存中的数据
            this.holder.getLevel2Cache(region).evict(key);
            if (!holder.getLevel2Cache(region).supportTTL()) {
                //再一次清除一级缓存是为了避免缓存失效时再次从 L2 获取到值
                this.holder.getLevel1Cache(region).evict(key);
            }
            log.debug("Level 1 cache object expired, evict level 2 cache object [{},{}]", region, key);
            if (policy != null) {
                policy.sendEvictCmd(region, key);
            }
        });
        policy = ClusterPolicyFactory.init(holder,config.getBroadcast(), config.getBroadcastProperties());
        log.info("使用广播代理：{}", policy.getClass().getName());
    }
}
