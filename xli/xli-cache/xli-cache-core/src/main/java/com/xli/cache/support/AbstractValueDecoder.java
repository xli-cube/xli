package com.xli.cache.support;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.function.Function;

public abstract class AbstractValueDecoder implements Function<byte[], Object>, ValueEncoders {

    @Getter
    protected boolean useIdentityNumber;

    @Setter
    private DecoderMap decoderMap = DecoderMap.defaultInstance();

    public AbstractValueDecoder(boolean useIdentityNumber) {
        this.useIdentityNumber = useIdentityNumber;
    }

    protected int parseHeader(byte[] buf) {
        int x = 0;
        x = x | (buf[0] & 0xFF);
        x <<= 8;
        x = x | (buf[1] & 0xFF);
        x <<= 8;
        x = x | (buf[2] & 0xFF);
        x <<= 8;
        x = x | (buf[3] & 0xFF);
        return x;
    }

    protected abstract Object doApply(byte[] buffer) throws Exception;

    @Override
    public Object apply(byte[] buffer) {
        try {
            if (useIdentityNumber) {
                decoderMap.initDefaultDecoder();
                int identityNumber = parseHeader(buffer);
                AbstractValueDecoder decoder = decoderMap.getDecoder(identityNumber);
                Objects.requireNonNull(decoder, "no decoder for identity number:" + identityNumber);
                return decoder.doApply(buffer);
            } else {
                return doApply(buffer);
            }
        } catch (Throwable e) {
            throw new CacheEncodeException("decode error", e);
        }
    }

}
