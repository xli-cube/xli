package com.xli.cache.anno;

import java.util.function.Function;

public interface SerialPolicy {

    String JAVA = "JAVA";

    String KRYO = "KRYO";

    String KRYO5 = "KRYO5";

    String FASTJSON2 = "FASTJSON2";

    int IDENTITY_NUMBER_JAVA = 0x4A953A80;

    int IDENTITY_NUMBER_KRYO4 = 0x4A953A82;

    int IDENTITY_NUMBER_KRYO5 = 0xF6E0A5C0;

    int IDENTITY_NUMBER_FASTJSON2 = 0xF6E0A5C1;

    Function<Object, byte[]> encoder();

    Function<byte[], Object> decoder();
}
