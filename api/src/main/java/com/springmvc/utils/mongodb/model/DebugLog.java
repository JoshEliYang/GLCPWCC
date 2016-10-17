package com.springmvc.utils.mongodb.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author johnson
 *
 */
public class DebugLog {
	String dateTime;
	String className;
	String methodName;
	String argList;
	String status;
	String retrunDat;
	String errorMesg;

	public DebugLog(String className, String methodName, String argList, String status, String retrunDat,
			String errorMesg) {
		super();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		this.dateTime = df.format(new Date());
		this.className = className;
		this.methodName = methodName;
		this.argList = argList;
		this.status = status;
		this.retrunDat = retrunDat;
		this.errorMesg = errorMesg;
	}

	@Override
	public String toString() {
		return dateTime + " [debug] className=" + className + ", methodName=" + methodName + ", status=" + status
				+ "\r\nargList=" + argList + "\r\nretrunDat=" + retrunDat + "\r\nerrorMesg=" + errorMesg;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRetrunDat() {
		return retrunDat;
	}

	public void setRetrunDat(String retrunDat) {
		this.retrunDat = retrunDat;
	}

	public String getErrorMesg() {
		return errorMesg;
	}

	public void setErrorMesg(String errorMesg) {
		this.errorMesg = errorMesg;
	}

}
