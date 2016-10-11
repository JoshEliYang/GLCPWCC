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
	String errorMessage;

	public ErrorLog() {
		super();
	}

	public ErrorLog(String className, String errorMessage) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.dateTime = df.format(new Date());
		this.className = className;
		this.errorMessage = errorMessage;
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

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
