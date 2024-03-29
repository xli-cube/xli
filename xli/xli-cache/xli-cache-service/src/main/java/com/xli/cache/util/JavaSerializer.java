package com.xli.cache.util;

import com.xli.cache.exception.CacheException;

import java.io.*;

/**
 * 标准的 Java 序列化
 *
 */
public class JavaSerializer implements Serializer {

	@Override
	public String name() {
		return "java";
	}
	
	@Override
	public byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (ObjectOutputStream oos = new ObjectOutputStream(baos)){
			oos.writeObject(obj);
			return baos.toByteArray();
		}
	}

	@Override
	public Object deserialize(byte[] bits) throws IOException {
		if(bits == null || bits.length == 0)
			return null;
		ByteArrayInputStream bais = new ByteArrayInputStream(bits);
		try (ObjectInputStream ois = new ObjectInputStream(bais)){
			return ois.readObject();
		} catch (ClassNotFoundException e) {
			throw new CacheException(e);
		}
	}
	
}
