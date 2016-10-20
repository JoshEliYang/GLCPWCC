package cn.springmvc.service.log;

import com.springmvc.utils.mongodb.model.DebugResponse;
import com.springmvc.utils.mongodb.model.MongoConfig;

import cn.springmvc.model.log.DebugLogQuery;

/**
 * 
 * @author johnson
 *
 */
public interface DebugLogService {

	/**
	 * abandoned
	 * 
	 * @param startTime
	 * @param endTime
	 * @param mongoConfig
	 * @return
	 * @throws Exception
	 */
	public DebugResponse getLog(String startTime, String endTime, MongoConfig mongoConfig) throws Exception;

	/**
	 * abandoned
	 * 
	 * @param startTime
	 * @param endTime
	 * @param skip
	 * @param mongoConfig
	 * @return
	 * @throws Exception
	 */
	public DebugResponse getLog(String startTime, String endTime, int skip, MongoConfig mongoConfig) throws Exception;

	/**
	 * clear all debug log
	 * 
	 * @param mongoConfig
	 * @throws Exception
	 */
	public void clear(MongoConfig mongoConfig) throws Exception;

	/**
	 * get debug log
	 * 
	 * @param queryDat
	 * @param mongoConfig
	 * @return
	 * @throws Exception
	 */
	public DebugResponse getLog(DebugLogQuery queryDat, MongoConfig mongoConfig) throws Exception;
}
