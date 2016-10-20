package com.springmvc.utils.mongodb;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.springmvc.utils.mongodb.model.MongoConfig;
import com.springmvc.utils.mongodb.model.MongoResponse;

/**
 * 
 * @author johnson
 *
 */
public class MongoDBUtilTest {

	static MongoDBUtil mongoUtil;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mongoUtil = MongoDBUtil.getInstance(new MongoConfig() {
			{
				setSERVER_ADDR("120.26.36.13");
				setSERVER_PORT(27017);
				setUSER_NAME("logger");
				setDB_NAME("wechat");
				setDB_PWD("123");
			}
		});
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Ignore
	public void test() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		Date dateX = new Date();
		dateX.setDate(dateX.getDate() - 5);
		MongoResponse result = mongoUtil.query("operate_log", df.format(dateX), df.format(new Date()), 0, 50);
		System.out.println("total: " + result.getTotal());
		System.out.println("count: " + result.getCount());
		// fail("Not yet implemented");
	}

	@Test
	public void clearTest() {
		mongoUtil.clear("operate_log");
	}

	/**
	 * abandoned
	 */
	@Test
	@Ignore
	public void queryTest() {
		// OperateQueryUtil query = new OperateQueryUtil(new MongoConfig() {
		// {
		// setSERVER_ADDR("120.26.36.13");
		// setSERVER_PORT(27017);
		// setUSER_NAME("logger");
		// setDB_NAME("wechat");
		// setDB_PWD("123");
		// }
		// });
		// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		// Date dateX = new Date();
		// dateX.setDate(dateX.getDate() - 5);
		// List<OperateLog> logs = (List<OperateLog>)
		// query.query(df.format(dateX), df.format(new Date()));
		// for (OperateLog log : logs) {
		// System.out.println(log);
		// }
	}

}
