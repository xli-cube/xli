package com.xli.cache.anno.support;

import lombok.Getter;

import java.util.function.Function;

@Getter
public class ParserFunction implements Function {

    private final String value;

    public ParserFunction(String value) {
        this.value = value;
    }

    @Override
    public Object apply(Object t) {
        throw new UnsupportedOperationException();
    }
}
