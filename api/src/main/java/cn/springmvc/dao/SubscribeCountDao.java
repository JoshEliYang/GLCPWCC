package cn.springmvc.dao;

import java.util.List;

import cn.springmvc.model.SubCounter.SubscribeSetting;
import cn.springmvc.model.SubCounter.SubscribeCount;
import cn.springmvc.model.SubCounter.SubscribeDBQuery;

/**
 * 
 * @author johnson
 *
 */
public interface SubscribeCountDao {

	public void insert(SubscribeCount subCounter) throws Exception;

	public SubscribeCount query(SubscribeCount subCounter) throws Exception;

	public void update(SubscribeCount subCounter) throws Exception;

	public List<SubscribeCount> queryByDay(SubscribeDBQuery queryData) throws Exception;

	public List<SubscribeCount> queryAllByDay(SubscribeDBQuery queryData) throws Exception;

	public List<SubscribeSetting> getQueryList(int basicId) throws Exception;

}
