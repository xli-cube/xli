package com.xli.cache.anno.support;

import lombok.Getter;
import lombok.Setter;

import java.util.function.Function;

@Setter
@Getter
public class CacheUpdateAnnoConfig extends CacheAnnoConfig {

    private String value;
    private boolean multi;

    private Function<Object, Object> valueEvaluator;

}
