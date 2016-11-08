package com.springmvc.utils.mongodb.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author johnson
 *
 */
public class ErrorLog {
	String dateTime;
	String className;
	String methodName;
	String argList;
	String errorMessage;

	public ErrorLog() {
		super();
	}

	public ErrorLog(String className, String methodName, String argList, String errorMessage) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		this.dateTime = df.format(new Date());
		this.className = className;
		this.methodName = methodName;
		this.argList = argList;
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return dateTime + " Error \r\n类名=" + className + " 方法名=" + methodName + "\r\n参数列表=" + argList + "\r\n错误信息="
				+ errorMessage;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getArgList() {
		return argList;
	}

	public void setArgList(String argList) {
		this.argList = argList;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
