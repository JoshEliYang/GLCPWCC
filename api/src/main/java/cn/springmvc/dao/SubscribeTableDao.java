package cn.springmvc.dao;

import java.util.List;

import cn.springmvc.model.SubCounter.SubscribeCount;
import cn.springmvc.model.SubCounter.SubscribeCountQuery;

public interface SubscribeTableDao {

	public List<SubscribeCount> query(SubscribeCountQuery queryDat)
			throws Exception;

	public List<SubscribeCount> queryDay(String format);

	

	

	

}
