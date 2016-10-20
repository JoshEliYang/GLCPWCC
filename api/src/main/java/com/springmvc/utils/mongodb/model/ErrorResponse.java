package com.springmvc.utils.mongodb.model;

import java.util.List;

/**
 * 
 * @author johnson
 *
 */
public class ErrorResponse extends MongoResponse {
	List<ErrorLog> logs;

	public void setMongoResponse(MongoResponse res) {
		this.total = res.getTotal();
		this.count = res.getCount();
		this.docs = null;
	}

	public List<ErrorLog> getLogs() {
		return logs;
	}

	public void setLogs(List<ErrorLog> logs) {
		this.logs = logs;
	}

}
