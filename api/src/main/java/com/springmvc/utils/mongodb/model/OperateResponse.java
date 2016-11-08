package com.springmvc.utils.mongodb.model;

import java.util.List;

public class OperateResponse extends MongoResponse {
	List<OperateLog> logs;

	public void setMongoResponse(MongoResponse res) {
		this.total = res.getTotal();
		this.count = res.getCount();
		this.docs = null;
	}

	public List<OperateLog> getLogs() {
		return logs;
	}

	public void setLogs(List<OperateLog> logs) {
		this.logs = logs;
	}

}
