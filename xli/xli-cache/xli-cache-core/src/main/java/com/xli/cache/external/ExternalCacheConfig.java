package com.xli.cache.external;

import com.xli.cache.config.CacheConfig;
import com.xli.cache.support.DecoderMap;
import com.xli.cache.support.JavaValueEncoder;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Function;
import java.util.function.Supplier;

@Setter
@Getter
public class ExternalCacheConfig<K, V> extends CacheConfig<K, V> {

    private Supplier<String> keyPrefixSupplier;

    private Function<Object, byte[]> valueEncoder = JavaValueEncoder.INSTANCE;

    private Function<byte[], Object> valueDecoder = DecoderMap.defaultJavaValueDecoder();

    private String broadcastChannel;

    public String getKeyPrefix() {
        return keyPrefixSupplier == null ? null : keyPrefixSupplier.get();
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefixSupplier = () -> keyPrefix;
    }

}
