package com.xli.cache.autoconfigure;

import com.xli.cache.builder.CacheBuilder;
import com.xli.cache.embedded.LinkedHashMapCacheBuilder;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Component
@Conditional(LinkedHashMapAutoConfiguration.LinkedHashMapCondition.class)
public class LinkedHashMapAutoConfiguration extends EmbeddedCacheAutoInit {
    public LinkedHashMapAutoConfiguration() {
        super("linkedhashmap");
    }

    @Override
    protected CacheBuilder initCache(ConfigTree ct, String cacheAreaWithPrefix) {
        LinkedHashMapCacheBuilder builder = LinkedHashMapCacheBuilder.createLinkedHashMapCacheBuilder();
        parseGeneralConfig(builder, ct);
        return builder;
    }

    public static class LinkedHashMapCondition extends JetCacheCondition {
        public LinkedHashMapCondition() {
            super("linkedhashmap");
        }
    }
}
