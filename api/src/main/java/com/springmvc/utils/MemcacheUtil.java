package com.springmvc.utils;

import java.io.IOException;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;

/**
 * 
 * @author johsnon
 *
 */
public class MemcacheUtil {

	MemcachedClient mc = null;
	int exp = 60 * 60 * 24;

	static MemcacheUtil instance = new MemcacheUtil();

	private MemcacheUtil() {
		super();
		setup();
	}

	public static MemcacheUtil getInstance() throws IOException {
		return instance;
	}

	// init
	private void setup() {
		AuthDescriptor ad = new AuthDescriptor(new String[] { "PLAIN" },
				new PlainCallbackHandler("aa149643ad9b469f", "uWtj4nHU"));

		try {
			mc = new MemcachedClient(
					new ConnectionFactoryBuilder().setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
							.setAuthDescriptor(ad).build(),
					AddrUtil.getAddresses("aa149643ad9b469f.m.cnhzaliqshpub001.ocs.aliyuncs.com:11211"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// set data into memcache
	public <T> void setDat(String key, T obj) throws IOException {
		doing(1, key, obj, exp, null);
	}

	public <T> void setDat(String key, int exp, T obj) throws IOException {
		doing(1, key, obj, exp, null);
	}

	// get data from memcache
	public <T> T getDat(String key, Class<T> clazz) throws IOException {
		return doing(3, key, null, exp, clazz);
	}

	private synchronized <T> T doing(int type, String key, T obj, int exp, Class<T> clazz) {
		if (type == 1) {
			mc.set(key, this.exp, obj);
			return null;
		} else if (type == 2) {
			mc.set(key, exp, obj);
			return null;
		} else if (type == 3) {
			T res = (T) mc.get(key);
			return res;
		}
		return null;
	}

	public void destory() throws IOException {
		// if (mc != null)
		// mc.shutdown();
	}
}
