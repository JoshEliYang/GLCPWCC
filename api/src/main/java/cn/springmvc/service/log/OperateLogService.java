package cn.springmvc.service.log;

import java.util.List;

import com.springmvc.utils.mongodb.model.MongoConfig;
import com.springmvc.utils.mongodb.model.OperateLog;
import com.springmvc.utils.mongodb.model.OperateResponse;

import cn.springmvc.model.log.OperateLogQuery;

/**
 * 
 * @author johnson
 *
 */
public interface OperateLogService {

	/**
	 * abandoned
	 * 
	 * @param startTime
	 * @param endTime
	 * @param mongoConfig
	 * @return
	 * @throws Exception
	 */
	public OperateResponse getOperateLog(String startTime, String endTime, MongoConfig mongoConfig) throws Exception;

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
	public OperateResponse getOperateLog(String startTime, String endTime, int skip, MongoConfig mongoConfig)
			throws Exception;

	/**
	 * drop the whole collection
	 * 
	 * @param mongoConfig
	 * @throws Exception
	 */
	public void clear(MongoConfig mongoConfig) throws Exception;

	/**
	 * get operate logs
	 * 
	 * @param logQuery
	 * @param mongoConfig
	 * @return
	 * @throws Exception
	 */
	public OperateResponse getOperateLog(OperateLogQuery logQuery, MongoConfig mongoConfig) throws Exception;

	/**
	 * get all operate logs
	 * 
	 * @param logQuery
	 * @param mongoConfig
	 * @return
	 * @throws Exception
	 */
	public List<OperateLog> getAll(MongoConfig mongoConfig) throws Exception;
}
