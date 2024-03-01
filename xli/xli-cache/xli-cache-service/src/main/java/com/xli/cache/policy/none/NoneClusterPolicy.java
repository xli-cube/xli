package com.xli.cache.policy.none;

import com.xli.cache.config.CacheProviderHolder;
import com.xli.cache.config.Command;
import com.xli.cache.policy.IClusterPolicy;

import java.util.Properties;

/**
 * 实现空的集群通知策略
 */
public class NoneClusterPolicy implements IClusterPolicy {

    private final int LOCAL_COMMAND_ID = Command.genRandomSrc(); //命令源标识，随机生成，每个节点都有唯一标识

    @Override
    public boolean isLocalCommand(Command cmd) {
        return cmd.getSrc() == LOCAL_COMMAND_ID;
    }

    @Override
    public void connect(CacheProviderHolder holder) {
    }

    @Override
    public void disconnect() {
    }

    @Override
    public void publish(Command cmd) {
    }

    @Override
    public void evict(String region, String... keys) {
    }

    @Override
    public void clear(String region) {
    }
}
