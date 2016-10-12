package com.springmvc.utils.mongodb;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.springmvc.utils.mongodb.model.ErrorLog;
import com.springmvc.utils.mongodb.model.OperateLog;

/**
 * 
 * @author johnson
 *
 */
public class LogUtil {

	static ExecutorService pool = Executors.newCachedThreadPool();

	private MongoDBUtil mongoDB;

	public MongoDBUtil getMongoDB() {
		return mongoDB;
	}

	public void setMongoDB(MongoDBUtil mongoDB) {
		this.mongoDB = mongoDB;
	}

	OperateLog operateLog;

	/**
	 * do operate log in a new thread
	 * 
	 * @param logDat
	 */
	public void operateLog(OperateLog logDat) {
		operateLog = logDat;

		Thread logThread = new Thread() {
			OperateLog log = operateLog;

			public void run() {
				try {
//					mongoDB.setup();
					mongoDB.insert("operate_log", log);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
//					mongoDB.destory();
				}
			}
		};
		pool.execute(logThread);
	}

	String className;

	public void errorLog(String message) {
		ErrorLog log = new ErrorLog(className, message);
		try {
//			mongoDB.setup();
			mongoDB.insert("error_log", log);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		mongoDB.destory();
	}

}
