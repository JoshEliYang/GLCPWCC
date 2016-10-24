package cn.springmvc.service.impl.log;

import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Service;

import com.springmvc.utils.mongodb.model.ErrorLog;
import com.springmvc.utils.mongodb.model.ErrorResponse;
import com.springmvc.utils.mongodb.model.MongoConfig;
import com.springmvc.utils.mongodb.query.QueryFactory;
import com.springmvc.utils.mongodb.query.QueryType;
import com.springmvc.utils.mongodb.query.QueryUtil;

import cn.springmvc.service.log.ErrorLogService;

/**
 * 
 * @author johnson
 *
 */
@Service
public class ErrorLogServiceImpl implements ErrorLogService {

	public ErrorResponse getLog(String startTime, String endTime, MongoConfig mongoConfig) throws Exception {
		return getLog(startTime, endTime, 0, mongoConfig);
	}

	public ErrorResponse getLog(String startTime, String endTime, int skip, MongoConfig mongoConfig) throws Exception {
		QueryUtil queryUtil = QueryFactory.getInstance(QueryType.error, mongoConfig);
		return (ErrorResponse) queryUtil.query(startTime, endTime, skip);
	}

	public void clear(MongoConfig mongoConfig) throws Exception {
		QueryUtil queryUtil = QueryFactory.getInstance(QueryType.error, mongoConfig);
		queryUtil.clear();
	}

	public List<ErrorLog> getAll(MongoConfig mongoConfig) throws Exception {
		QueryUtil queryUtil = QueryFactory.getInstance(QueryType.error, mongoConfig);
		return queryUtil.query(new Document(), new Document("dateTime", -1));
	}

}
