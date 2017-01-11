package cn.springmvc.service.wechat;

import java.util.List;

import cn.springmvc.model.SubCounter.SubscribeCount;
import cn.springmvc.model.SubCounter.SubscribeCountQuery;

public interface SubscribeTableService {

	/**
	 * query subscribe count
	 * 
	 * @param queryData
	 * @param basic
	 * @return
	 * @throws Exception
	 */
	public List<List<SubscribeCount>> get(SubscribeCountQuery queryDat)
			throws Exception;

}
