package com.xli.cache.external;

import com.xli.cache.builder.AbstractCacheBuilder;
import com.xli.cache.manager.CacheManager;
import com.xli.cache.monitor.BroadcastManager;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class ExternalCacheBuilder<T extends ExternalCacheBuilder<T>> extends AbstractCacheBuilder<T> {

    @Override
    public ExternalCacheConfig getConfig() {
        if (config == null) {
            config = new ExternalCacheConfig();
        }
        return (ExternalCacheConfig) config;
    }

    public boolean supportBroadcast() {
        return false;
    }

    public BroadcastManager createBroadcastManager(CacheManager cacheManager) {
        return null;
    }

    public T broadcastChannel(String broadcastChannel) {
        getConfig().setBroadcastChannel(broadcastChannel);
        return self();
    }

    public void setBroadcastChannel(String broadcastChannel) {
        getConfig().setBroadcastChannel(broadcastChannel);
    }

    public T keyPrefix(String keyPrefix) {
        getConfig().setKeyPrefixSupplier(() -> keyPrefix);
        return self();
    }

    public T keyPrefixSupplier(Supplier<String> keyPrefixSupplier) {
        getConfig().setKeyPrefixSupplier(keyPrefixSupplier);
        return self();
    }

    public T valueEncoder(Function<Object, byte[]> valueEncoder){
        getConfig().setValueEncoder(valueEncoder);
        return self();
    }

    public T valueDecoder(Function<byte[], Object> valueDecoder){
        getConfig().setValueDecoder(valueDecoder);
        return self();
    }

    public void setKeyPrefix(String keyPrefix){
        if (keyPrefix != null) {
            getConfig().setKeyPrefixSupplier(() -> keyPrefix);
        } else {
            getConfig().setKeyPrefixSupplier(null);
        }
    }

    public void setKeyPrefixSupplier(Supplier<String> keyPrefixSupplier){
        getConfig().setKeyPrefixSupplier(keyPrefixSupplier);
    }

    public void setValueEncoder(Function<Object, byte[]> valueEncoder){
        getConfig().setValueEncoder(valueEncoder);
    }

    public void setValueDecoder(Function<byte[], Object> valueDecoder){
        getConfig().setValueDecoder(valueDecoder);
    }
}
