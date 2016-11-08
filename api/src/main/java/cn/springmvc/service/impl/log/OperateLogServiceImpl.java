package cn.springmvc.service.impl.log;

import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Service;

import com.springmvc.utils.mongodb.model.MongoConfig;
import com.springmvc.utils.mongodb.model.OperateLog;
import com.springmvc.utils.mongodb.model.OperateResponse;
import com.springmvc.utils.mongodb.query.QueryFactory;
import com.springmvc.utils.mongodb.query.QueryType;
import com.springmvc.utils.mongodb.query.QueryUtil;

import cn.springmvc.model.log.OperateLogQuery;
import cn.springmvc.service.log.OperateLogService;

/**
 * 
 * @author johnson
 *
 */
@Service
public class OperateLogServiceImpl implements OperateLogService {

	/**
	 * abandoned
	 */
	public OperateResponse getOperateLog(String startTime, String endTime, MongoConfig mongoConfig) throws Exception {
		return getOperateLog(startTime, endTime, 0, mongoConfig);
	}

	/**
	 * abandoned
	 */
	public OperateResponse getOperateLog(String startTime, String endTime, int skip, MongoConfig mongoConfig)
			throws Exception {
		QueryUtil queryUtil = QueryFactory.getInstance(QueryType.operate, mongoConfig);
		return (OperateResponse) queryUtil.query(startTime, endTime, skip);
	}

	/**
	 * drop the whole collection
	 */
	public void clear(MongoConfig mongoConfig) throws Exception {
		QueryUtil queryUtil = QueryFactory.getInstance(QueryType.operate, mongoConfig);
		queryUtil.clear();
	}

	/**
	 * get operate log
	 */
	public OperateResponse getOperateLog(OperateLogQuery logQuery, MongoConfig mongoConfig) throws Exception {

		Document queryDoc = new Document("dateTime", new Document("$gte", logQuery.getStartTime())).append("dateTime",
				new Document("$lt", logQuery.getEndTime()));
		if (logQuery.getAdminId() != -256)
			queryDoc.append("adminId", logQuery.getAdminId());
		if (logQuery.getBasicId() != -256)
			queryDoc.append("basicId", logQuery.getBasicId());
		if (logQuery.getAction() != null)
			queryDoc.append("action", logQuery.getAction());
		if (logQuery.getStatus() != null)
			queryDoc.append("status", logQuery.getStatus());

		QueryUtil queryUtil = QueryFactory.getInstance(QueryType.operate, mongoConfig);
		return (OperateResponse) queryUtil.query(queryDoc, logQuery.getSkip(), new Document("dateTime", -1));
	}

	/**
	 * get all operate logs (mainly for export)
	 */
	public List<OperateLog> getAll(MongoConfig mongoConfig) throws Exception {
		QueryUtil queryUtil = QueryFactory.getInstance(QueryType.operate, mongoConfig);
		return queryUtil.query(new Document(), new Document("dateTime", -1));
	}

}
