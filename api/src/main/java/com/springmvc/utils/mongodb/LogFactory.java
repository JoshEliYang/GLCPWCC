package com.springmvc.utils.mongodb;

import java.util.Properties;

import javax.annotation.Resource;

import com.springmvc.utils.mongodb.model.MongoConfig;

/**
 * 
 * @author johnson
 *
 */
public class LogFactory {

	private MongoConfig mongoConfig;
	private boolean DEBUG_MODE = false;

	@Resource(name = "operateMapper")
	private Properties operateMapper;

	public MongoConfig getMongoConfig() {
		return mongoConfig;
	}

	public void setMongoConfig(MongoConfig mongoConfig) {
		this.mongoConfig = mongoConfig;
	}

	public boolean isDEBUG_MODE() {
		return DEBUG_MODE;
	}

	public void setDEBUG_MODE(boolean dEBUG_MODE) {
		DEBUG_MODE = dEBUG_MODE;
	}

	/**
	 * factory method
	 * 
	 * @param LogType
	 * @return
	 */
	public LogUtil getInstance(String LogType) {
		if ("operate_log".equals(LogType)) {
			return new OperateLogUtil(mongoConfig, DEBUG_MODE, operateMapper);
		} else if ("debug_log".equals(LogType)) {
			return new DebugLogUtil(mongoConfig);
		}
		return null;
	}
}
