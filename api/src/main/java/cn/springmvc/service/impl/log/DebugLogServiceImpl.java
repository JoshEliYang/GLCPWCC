package cn.springmvc.service.impl.log;

import org.bson.Document;
import org.springframework.stereotype.Service;

import com.springmvc.utils.mongodb.model.DebugResponse;
import com.springmvc.utils.mongodb.model.MongoConfig;
import com.springmvc.utils.mongodb.query.QueryFactory;
import com.springmvc.utils.mongodb.query.QueryType;
import com.springmvc.utils.mongodb.query.QueryUtil;

import cn.springmvc.model.log.DebugLogQuery;
import cn.springmvc.service.log.DebugLogService;

/**
 * 
 * @author johnson
 *
 */
@Service
public class DebugLogServiceImpl implements DebugLogService {

	/**
	 * abandoned
	 */
	public DebugResponse getLog(String startTime, String endTime, MongoConfig mongoConfig) throws Exception {
		return getLog(startTime, endTime, 0, mongoConfig);
	}

	/**
	 * abandoned
	 */
	public DebugResponse getLog(String startTime, String endTime, int skip, MongoConfig mongoConfig) throws Exception {
		QueryUtil queryUtil = QueryFactory.getInstance(QueryType.debug, mongoConfig);
		return (DebugResponse) queryUtil.query(startTime, endTime, skip);
	}

	/**
	 * clear the whole collection
	 */
	public void clear(MongoConfig mongoConfig) throws Exception {
		QueryUtil queryUtil = QueryFactory.getInstance(QueryType.debug, mongoConfig);
		queryUtil.clear();
	}

	/**
	 * get debug log
	 */
	public DebugResponse getLog(DebugLogQuery queryDat, MongoConfig mongoConfig) throws Exception {
		Document queryDoc = new Document("dateTime", new Document("$gte", queryDat.getStartTime())).append("dateTime",
				new Document("$lt", queryDat.getEndTime()));
		if (queryDat.getLayer() != null)
			queryDoc.append("layer", queryDat.getLayer());
		if (queryDat.getStatus() != null)
			queryDoc.append("status", queryDat.getStatus());

		QueryUtil queryUtil = QueryFactory.getInstance(QueryType.debug, mongoConfig);
		return (DebugResponse) queryUtil.query(queryDoc, queryDat.getSkip());
	}

}
