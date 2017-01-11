package com.springmvc.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
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
	@Ignore
	public void test() {
		ExcelUtil excelUtil = ExcelUtil.getInstance();
		ArrayList<Coupon> coupons;
		try {
			coupons = excelUtil.readExcel("test.xlsx", Coupon.class,
					Coupon.getFields());
			System.out.println("result:");
			for (int i = 0; i < coupons.size(); i++) {
				System.out.println(coupons.get(i).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("error");
		}

	}

	@Test
	public void testExcel() {
		// ExcelUtil excelUtil = ExcelUtil.getInstance();
		// HSSFWorkbook wb = excelUtil.test();
		// FileOutputStream out = null;
		// try {
		// out = new FileOutputStream(new File("E://test.xls"));
		// wb.write(out);
		// } catch (Exception e) {
		// e.printStackTrace();
		// } finally {
		// if (out != null)
		// try {
		// out.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
	}

}
