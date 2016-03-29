package com.springmvc.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * 单例：静态内部类
 * 
 * @author johsnon
 *
 */
public class RedisUtil {

	private RedisUtil() {
		super();
		setup();
	}

	public static RedisUtil getRedis() {
		return new RedisUtil();
	}

	private JedisPool pool;
	private Jedis jedis;

	static JedisPoolConfig config;
	static {
		config=new JedisPoolConfig();
		// 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
		// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
		config.setMaxTotal(50);
		// config.setMaxIdle(500);
		// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
		config.setMaxIdle(5);
		// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
		config.setMaxWaitMillis(1000 * 100);
		// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
		config.setTestOnBorrow(true);
	}
	
	/**
	 * jedis 初始化
	 */
	public void setup() {
		try {
			pool = new JedisPool(config, "120.26.58.34", 6379);
			jedis = pool.getResource();
		} catch (JedisConnectionException je) {
			je.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void setdat(String key, String value) {
		try {
			jedis.set(key, value);
			jedis.expire(key, 7200);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String getdat(String key) {
		try {
			String res = jedis.get(key);
			return res;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 *  释放jedis资源
	 */
	public void destroy() {
		if (jedis != null) {
			pool.returnResource(jedis);
		}
	}

}
