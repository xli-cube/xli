package com.xli.cache.policy.none;

import com.xli.cache.listener.service.ICacheExpiredListener;
import com.xli.cache.policy.CacheChannel;
import com.xli.cache.service.ICacheProvider;
import com.xli.cache.service.top.ICache;

import java.util.Collection;
import java.util.Properties;

public class NullCacheProvider implements ICacheProvider {

	private final static NullCache cache = new NullCache();

	@Override
	public String name() {
		return "none";
	}

	@Override
	public int level() {
		return 1|2;
	}

	@Override
	public ICache buildCache(String regionName, ICacheExpiredListener listener) {
		return cache;
	}

	@Override
	public ICache buildCache(String region, long timeToLiveInSeconds, ICacheExpiredListener listener) {
		return cache;
	}

	@Override
	public Collection<CacheChannel.Region> regions() {
		return null;
	}

	@Override
	public void removeCache(String region) {

	}

	@Override
	public void start(Properties props) {

	}

	@Override
	public void stop() {

	}
}
