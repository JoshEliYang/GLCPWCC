package com.springmvc.utils.mongodb.model;

import java.util.List;

public class DebugResponse extends MongoResponse {
	List<DebugLog> logs;

	public void setMongoResponse(MongoResponse res) {
		this.total = res.getTotal();
		this.count = res.getCount();
		this.docs = null;
	}

	public List<DebugLog> getLogs() {
		return logs;
	}

	public void setLogs(List<DebugLog> logs) {
		this.logs = logs;
	}

}
