package cn.springmvc.service.impl.wechat;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.utils.MemcacheUtil;

import cn.springmvc.dao.SubscribeCountDao;
import cn.springmvc.model.BasicModel;
import cn.springmvc.model.SubCounter.SubscribeSetting;
import cn.springmvc.model.SubCounter.SubscribeCount;
import cn.springmvc.model.SubCounter.SubscribeCountResponse;
import cn.springmvc.model.SubCounter.SubscribeDBQuery;
import cn.springmvc.model.SubCounter.SubscribeInfoQuery;
import cn.springmvc.model.SubCounter.SubscribeRealTimeArray;
import cn.springmvc.model.SubCounter.SubscribeRealTimeArray.Type;
import cn.springmvc.service.wechat.SubscribeCountService;

/**
 * 
 * @author johnson
 *
 */
@Service
public class SubscribeCountServiceImpl implements SubscribeCountService {

	@Autowired
	private SubscribeCountDao subDao;

	Logger logger = Logger.getLogger(SubscribeCountServiceImpl.class);

	/**
	 * add subscribe count for specific tag
	 * 
	 * tagId=-1 means this user haven't belonged to any group
	 */
	public void addSubscribe(int tagId, BasicModel basic) throws Exception {
		synchronized (SubscribeCountService.class) {
			SubscribeCount subCounter = new SubscribeCount(new Date(), tagId, basic);
			SubscribeCount queryResult = subDao.query(subCounter);
			if (queryResult != null) {
				queryResult.setSubscribe(queryResult.getSubscribe() + 1);
				subDao.update(queryResult);
			} else {
				subCounter.setSubscribe(1);
				subDao.insert(subCounter);
			}

			/**
			 * storage as real-time info (storage into cache, and keep for 7
			 * days)
			 * 
			 * -255 means total
			 */
			// setSubscribeRealTimeArray(tagId, basic, Type.Subscribe);
			// setSubscribeRealTimeArray(-255, basic, Type.Subscribe);
		}
	}

	/**
	 * add unSubscribe count for specific tag
	 * 
	 * tagId=-1 means this user haven't belonged to any group
	 */
	public void addUnsubscribe(int[] tagList, BasicModel basic) throws Exception {
		synchronized (SubscribeCountService.class) {
			int tagId;
			if (tagList.length > 0) {
				tagId = tagList[tagList.length - 1];
			} else {
				tagId = -1;
			}

			SubscribeCount subCounter = new SubscribeCount(new Date(), tagId, basic);
			SubscribeCount queryResult = subDao.query(subCounter);
			if (queryResult != null) {
				queryResult.setUnsubscribe(queryResult.getUnsubscribe() + 1);
				subDao.update(queryResult);
			} else {
				subCounter.setUnsubscribe(1);
				subDao.insert(subCounter);
			}

			/**
			 * storage as real-time info (storage into cache, and keep for 7
			 * days)
			 * 
			 * -255 means total
			 */
			// setSubscribeRealTimeArray(tagId, basic, Type.UnSubscribe);
			// setSubscribeRealTimeArray(-255, basic, Type.UnSubscribe);
		}
	}

	/**
	 * storage as real-time info (storage into cache, and keep for 7 days)
	 * 
	 * 废弃
	 * 
	 * @param tagId
	 * @param basic
	 * @param type
	 * @throws IOException
	 */
	@Deprecated
	private void setSubscribeRealTimeArray(int tagId, BasicModel basic, Type type) throws IOException {
		String key = SubscribeRealTimeArray.generateKey(basic.getId(), tagId);
		MemcacheUtil memcacheUtil = MemcacheUtil.getInstance();
		SubscribeRealTimeArray arrayDat = memcacheUtil.getDat(key, SubscribeRealTimeArray.class);
		logger.error("get subscribe info from memcache >>>> " + arrayDat);
		if (arrayDat != null) {
			arrayDat.add(type);
			memcacheUtil.setDat(key, 3600 * 24 * 7, arrayDat);
		} else {
			SubscribeRealTimeArray newArray = new SubscribeRealTimeArray(basic.getId(), tagId);
			newArray.add(type);
			memcacheUtil.setDat(key, 3600 * 24 * 7, newArray);
		}
	}

	/**
	 * 1.means year 2.means month 3.means day 4.means week -255:means total
	 */
	public SubscribeCountResponse get(SubscribeInfoQuery queryData, BasicModel basic) throws Exception {
		SubscribeCountResponse subResponse = new SubscribeCountResponse();
		subResponse.setBasicId(basic.getId());
		subResponse.setDateType(queryData.getDateType());

		Map<Integer, List<SubscribeCount>> tagResult = new HashMap<Integer, List<SubscribeCount>>();

		for (int i = 0; i < queryData.getTagList().length; i++) {
			SubscribeDBQuery dbQuery = new SubscribeDBQuery(queryData.getStartDate(), queryData.getEndDate(),
					queryData.getTagList()[i], basic);

			switch (queryData.getDateType()) {
			case 1:
				break;
			case 2:
				break;
			case 3:
				if (queryData.getTagList()[i] != -255) {
					tagResult.put(queryData.getTagList()[i], subDao.queryByDay(dbQuery));
				} else {
					tagResult.put(-255, subDao.queryAllByDay(dbQuery));
				}
				break;
			case 4:
				break;
			default:
			}
		}

		subResponse.setResult(tagResult);
		return subResponse;
	}

	/**
	 * query tag info from DB
	 */
	public List<SubscribeSetting> getQueryList(BasicModel basic) throws Exception {
		return subDao.getQueryList(basic.getId());
	}
}
