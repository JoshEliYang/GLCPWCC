package com.springmvc.utils;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cn.springmvc.model.templateMesg.Coupon;

public class ExcelUtilTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
	public void test() {
		ExcelUtil excelUtil = ExcelUtil.getInstance();
		ArrayList<Coupon> coupons;
		try {
			coupons = excelUtil.readExcel("test.xlsx", Coupon.class, Coupon.getFields());
			System.out.println("result:");
			for (int i = 0; i < coupons.size(); i++) {
				System.out.println(coupons.get(i).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("error");
		}

	}

}
