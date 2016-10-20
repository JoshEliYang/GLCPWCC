package cn.springmvc.service.log;

import com.springmvc.utils.mongodb.model.ErrorResponse;
import com.springmvc.utils.mongodb.model.MongoConfig;

/**
 * 
 * @author johnson
 *
 */
public interface ErrorLogService {

	public ErrorResponse getLog(String startTime, String endTime, MongoConfig mongoConfig) throws Exception;

	public ErrorResponse getLog(String startTime, String endTime, int skip, MongoConfig mongoConfig) throws Exception;

	public void clear(MongoConfig mongoConfig) throws Exception;
}
