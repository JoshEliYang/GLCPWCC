package com.springmvc.utils.mongodb.log;

import java.util.Properties;

import javax.annotation.Resource;

import com.springmvc.utils.mongodb.log.impl.DebugLogUtil;
import com.springmvc.utils.mongodb.log.impl.ErrorLogUtil;
import com.springmvc.utils.mongodb.log.impl.OperateLogUtil;
import com.springmvc.utils.mongodb.model.MongoConfig;

import cn.springmvc.Consts;

/**
 * 
 * @author johnson
 *
 */
public class LogFactory {

	private MongoConfig mongoConfig;

	@Resource(name = "operateMapper")
	private Properties operateMapper;

	public MongoConfig getMongoConfig() {
		return mongoConfig;
	}

	public void setMongoConfig(MongoConfig mongoConfig) {
		this.mongoConfig = mongoConfig;
	}

	/**
	 * factory method
	 * 
	 * @param LogType
	 * @return
	 */
	public LogUtil getInstance(String LogType) {
		if ("operate_log".equals(LogType)) {
			return new OperateLogUtil(mongoConfig, Consts.DEBUG_MODE, operateMapper);
		} else if ("error_log".equals(LogType)) {
			return new ErrorLogUtil(mongoConfig);
		}
		return null;
	}

	public LogUtil getInstance(String LogType, String layer) {
		if ("debug_log".equals(LogType)) {
			return new DebugLogUtil(mongoConfig, layer);
		}
		return null;
	}
}
