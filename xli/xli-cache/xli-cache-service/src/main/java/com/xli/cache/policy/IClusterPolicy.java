package com.xli.cache.policy;

import com.xli.cache.config.CacheProviderHolder;
import com.xli.cache.config.Command;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

/**
 * 缓存集群策略
 */
public interface IClusterPolicy {

    /**
     * 连接到集群
     * @param props
     * @param holder
     */
    void connect(CacheProviderHolder holder);

    /**
     * 发送消息
     * @param cmd
     */
    void publish(Command cmd);

    /**
     * 发送清除缓存的命令
     * @param region
     * @param keys
     */
    default void sendEvictCmd(String region, String...keys) {
        publish(new Command(Command.OPT_EVICT_KEY, region, keys));
    }

    /**
     * 发送清除整个缓存区域的命令
     * @param region
     */
    default void sendClearCmd(String region) {
        publish(new Command(Command.OPT_CLEAR_KEY, region));
    }

    /**
     * 断开集群连接
     */
    void disconnect();

    /**
     * 删除本地某个缓存条目
     * @param region
     * @param keys
     */
    void evict(String region, String... keys);

    /**
     * 清除本地整个缓存区域
     * @param region
     */
    void clear(String region) ;

    /**
     * 判断是否本地实例的命令
     * @param cmd
     * @return
     */
    boolean isLocalCommand(Command cmd);

    default void handleCommand(Command cmd) {
        try {
            if (cmd == null || isLocalCommand(cmd))
                return;

            switch (cmd.getOperator()) {
                case Command.OPT_JOIN:
                    break;
                case Command.OPT_EVICT_KEY:
                    this.evict(cmd.getRegion(), cmd.getKeys());
                    break;
                case Command.OPT_CLEAR_KEY:
                    this.clear(cmd.getRegion());
                    break;
                case Command.OPT_QUIT:
                default:
            }
        } catch (Exception e) {
        }
    }
}
