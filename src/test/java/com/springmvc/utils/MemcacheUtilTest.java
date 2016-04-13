package com.springmvc.utils;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MemcacheUtilTest {

	MemcacheUtil memcache = null;

	@Before
	public void setUp() throws Exception {
		memcache = MemcacheUtil.getInstance();
	}

	@After
	public void tearDown() throws Exception {
		memcache.destory();
	}

	@Test
	public void StringTest() {
		// fail("Not yet implemented");
		String key = "test";
		String value = "test data";

		try {
			memcache.setDat(key, value);
			String res = memcache.getDat(key, String.class);
			assertEquals(value, res);
		} catch (Exception e) {
			e.printStackTrace();
			fail("exception occured");
		}
	}

	@Test
	public void intTest() {
		String key = "test";
		int value = 10;

		try {
			memcache.setDat(key, value);
			int res = memcache.getDat(key, Integer.class);
			assertEquals(value, res);
		} catch (Exception e) {
			e.printStackTrace();
			fail("exception occured");
		}
	}

	@Test
	public void nullTest() {
		String key = "nullTest";

		try {
			// memcache.setDat(key, value);
			String res = memcache.getDat(key, String.class);
			System.out.println(res);
			assertEquals(null, res);
		} catch (Exception e) {
			e.printStackTrace();
			fail("exception occured");
		}
	}

}
