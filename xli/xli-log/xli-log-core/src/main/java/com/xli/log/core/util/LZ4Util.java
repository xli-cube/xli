package com.xli.log.core.util;

import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;

public class LZ4Util {

    /**
     * @param srcByte 原始数据
     * @return 压缩后的数据
     */
    public static byte[] compressedByte(byte[] srcByte) {
        LZ4Factory factory = LZ4Factory.fastestInstance();
        LZ4Compressor compressor = factory.fastCompressor();
        return compressor.compress(srcByte);
    }

    /**
     * @param compressorByte 压缩后的数据
     * @param srcLength      压缩前的数据长度
     * @return byte[]
     */
    public static byte[] decompressorByte(byte[] compressorByte, int srcLength) {
        LZ4Factory factory = LZ4Factory.fastestInstance();
        LZ4FastDecompressor decompressor = factory.fastDecompressor();
        return decompressor.decompress(compressorByte, srcLength);
    }
}
