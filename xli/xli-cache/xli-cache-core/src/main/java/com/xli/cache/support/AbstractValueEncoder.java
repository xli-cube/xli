package com.xli.cache.support;

import lombok.Getter;

import java.util.function.Function;

@Getter
public abstract class AbstractValueEncoder implements Function<Object, byte[]>, ValueEncoders {

    protected boolean useIdentityNumber;

    public AbstractValueEncoder(boolean useIdentityNumber) {
        this.useIdentityNumber = useIdentityNumber;
    }

}
