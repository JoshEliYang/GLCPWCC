package com.springmvc.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

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
	public <T> ArrayList<T> readExcel(String fileName, Class<T> clazz, String[] title)
			throws EncryptedDocumentException, InvalidFormatException, IOException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
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
						methods[j].invoke(obj, sheet.getRow(rowNum).getCell(i).getStringCellValue());
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
}
