package cn.springmvc.service.wechat;

import java.util.List;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.SubCounter.SubscribeSetting;
import cn.springmvc.model.SubCounter.SubscribeCountResponse;
import cn.springmvc.model.SubCounter.SubscribeInfoQuery;

/**
 * 
 * @author johnson
 *
 */
public interface SubscribeCountService {

	/**
	 * add subscribe count for specific tag
	 * 
	 * tagId=-1 means this user haven't belonged to any group
	 * 
	 * @param tagId
	 * @throws Exception
	 */
	public void addSubscribe(int tagId, BasicModel basic) throws Exception;

	/**
	 * add unSubscribe count for specific tag
	 * 
	 * tagId=-1 means this user haven't belonged to any group
	 * 
	 * @param tagId
	 * @throws Exception
	 */
	public void addUnsubscribe(int[] tagList, BasicModel basic) throws Exception;

	/**
	 * query subscribe count
	 * 
	 * @param queryData
	 * @param basic
	 * @return
	 * @throws Exception
	 */
	public SubscribeCountResponse get(SubscribeInfoQuery queryData, BasicModel basic) throws Exception;

	/**
	 * query tag info from DB
	 * 
	 * @param basic
	 * @return
	 * @throws Exception
	 */
	public List<SubscribeSetting> getQueryList(BasicModel basic) throws Exception;
}
