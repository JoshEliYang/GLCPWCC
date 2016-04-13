package com.springmvc.utils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

/**
 * 
 * @author johsnon
 *
 */
public class MemcacheUtil {

	// 内网IP
	static String url = "10.117.214.106:11211";
	// 外网IP
	// static String url = "120.26.58.34:11211";
	int expire = 3600;

	MemcachedClient memcachedClient = null;

	public MemcacheUtil() throws IOException {
		super();
		setup();
	}

	public static MemcacheUtil getInstance() throws IOException {
		return new MemcacheUtil();
	}

	// init
	public void setup() throws IOException {
		MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(url));
		memcachedClient = builder.build();
	}

	// set data into memcache
	public <T> void setDat(String key, T obj) throws TimeoutException, InterruptedException, MemcachedException {
		memcachedClient.set(key, expire, obj);
	}

	// get data from memcache
	public <T> T getDat(String key, Class<T> clazz) throws TimeoutException, InterruptedException, MemcachedException {
		T res = memcachedClient.get(key);
		return res;
	}

	public void destory() throws IOException {
		memcachedClient.shutdown();
	}
}
