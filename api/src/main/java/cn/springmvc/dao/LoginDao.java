package cn.springmvc.dao;

import cn.springmvc.model.UserAccess;

/**
 * 
 * @author johsnon
 *
 */
public interface LoginDao {

	/**
	 * check access token
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public UserAccess tokenCheck(String token) throws Exception;

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
