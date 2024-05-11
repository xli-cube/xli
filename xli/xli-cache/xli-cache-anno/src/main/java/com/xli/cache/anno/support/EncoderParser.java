package com.xli.cache.anno.support;

import java.util.function.Function;

public interface EncoderParser {
    Function<Object, byte[]> parseEncoder(String valueEncoder);
    Function<byte[], Object> parseDecoder(String valueDecoder);
}
