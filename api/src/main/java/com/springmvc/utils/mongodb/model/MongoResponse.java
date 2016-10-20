package com.springmvc.utils.mongodb.model;

import java.util.List;

import org.bson.Document;

/**
 * 
 * @author johnson
 *
 */
public class MongoResponse {
	long total;
	long count;
	List<Document> docs;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List<Document> getDocs() {
		return docs;
	}

	public void setDocs(List<Document> docs) {
		this.docs = docs;
	}

}
