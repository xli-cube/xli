package com.xli.cache.anno.support;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CacheInvalidateAnnoConfig extends CacheAnnoConfig {
    private boolean multi;
}
