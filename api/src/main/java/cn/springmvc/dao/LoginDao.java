package cn.springmvc.dao;

import cn.springmvc.model.UserAccess;

/**
 * 
 * @author johsnon
 *
 */
public interface LoginDao {

	/**
	 * insert user access into DB
	 * 
	 * @throws Exception
	 */
	public void insertUserAccess(UserAccess userAccess) throws Exception;

	/**
	 * clear expired user access data
	 * 
	 * @throws Exception
	 */
	public void clearExpiredAccessDat() throws Exception;

	/**
	 * clear old access data
	 * 
	 * @throws Exception
	 */
	public void clearOldAccessDat(int userId) throws Exception;
}
