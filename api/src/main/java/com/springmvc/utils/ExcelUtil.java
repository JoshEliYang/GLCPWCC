package com.springmvc.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import cn.springmvc.model.SubCounter.SubscribeCountByTags;
import cn.springmvc.model.SubCounter.SubscribeCountQuery;

/**
 * a utility to operate Microsoft Excel files
 * 
 * @author johnson
 *
 */
public class ExcelUtil {
	private static class ExcelUtilHolder {
		private static final ExcelUtil INSTANCE = new ExcelUtil();
	}

	private ExcelUtil() {
	}

	public static final ExcelUtil getInstance() {
		return ExcelUtilHolder.INSTANCE;
	}

	/**
	 * read Excel file and convert it to array list
	 * 
	 * @param fileName
	 * @param clazz
	 * @param title
	 * @return
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public <T> ArrayList<T> readExcel(String fileName, Class<T> clazz,
			String[] title) throws EncryptedDocumentException,
			InvalidFormatException, IOException, InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		ArrayList<T> result = new ArrayList<T>();

		InputStream inp = new FileInputStream(fileName);
		Workbook wb = WorkbookFactory.create(inp);
		Sheet sheet = wb.getSheetAt(0);
		int rowNum = 1;
		while (true) {
			if (sheet.getRow(rowNum) == null)
				break;
			if (sheet.getRow(rowNum).getCell(0) == null)
				break;

			T obj = clazz.newInstance();
			for (int i = 0; i < title.length; i++) {
				Method[] methods = clazz.getDeclaredMethods();
				for (int j = 0; j < methods.length; j++) {
					String mName = methods[j].getName();
					if (mName.equalsIgnoreCase("set" + title[i])) {
						methods[j].invoke(obj, sheet.getRow(rowNum).getCell(i)
								.getStringCellValue());
						break;
					}
				}
			}
			result.add(obj);
			rowNum++;
		}

		wb.close();
		inp.close();
		return result;
	}

	/**
	 * support exporting SubscribeCount
	 * 
	 * @param data
	 * @param queryDat
	 * @return
	 * @throws ParseException
	 */
	public HSSFWorkbook exportSubscribeCount(List<SubscribeCountByTags> data,
			SubscribeCountQuery queryDat) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		// 声明一个工作薄
		HSSFWorkbook wb = new HSSFWorkbook();
		// 声明一个单子并命名
		HSSFSheet sheet = wb.createSheet("用户关注统计");
		// 给单子名称一个长度
		sheet.setDefaultColumnWidth((short) 15);
		// 生成一个样式
		HSSFCellStyle style = wb.createCellStyle();
		// 创建第一行（也可以称为表头）
		HSSFRow row = sheet.createRow(0);
		// 样式字体居中
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		// 给表头第一行一次创建单元格
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("ID");
		cell.setCellStyle(style);

		cell = row.createCell((short) 1);
		cell.setCellValue("门店");
		cell.setCellStyle(style);

		Date stDate = df.parse(queryDat.getStartDate());
		Date edDate = df.parse(queryDat.getEndDate());

		int days = ExcelUtil.getIntervalDays(stDate, edDate);
		for (int i = 0; i <= days; i++) {
			Date dateNow = new Date();
			dateNow.setDate(stDate.getDate() + i);

			System.out.println(df.format(dateNow));

			cell = row.createCell((short) 2 + i);
			cell.setCellValue(df.format(dateNow));
			cell.setCellStyle(style);
		}

		for (int i = 0; i < data.size(); i++) {
			SubscribeCountByTags item = data.get(i);
			row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue(item.getTagId());
			row.createCell(1).setCellValue(item.getTagName());

			ArrayList<Integer> counts = item.getCounts();
			for (int j = 0; j < counts.size(); j++) {
				row.createCell(2 + j).setCellValue(
						String.valueOf(counts.get(j)));
			}
		}

		return wb;

	}

	public static int getIntervalDays(Date fDate, Date oDate) {
		if (null == fDate || null == oDate) {
			return -1;
		}
		long intervalMilli = oDate.getTime() - fDate.getTime();
		return (int) (intervalMilli / (24 * 60 * 60 * 1000));

	}
}
