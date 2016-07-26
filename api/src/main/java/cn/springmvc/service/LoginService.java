package cn.springmvc.service;

import cn.springmvc.model.LoginData;
import cn.springmvc.model.User;
import cn.springmvc.model.UserAccess;

/**
 * 
 * @author johsnon
 *
 */
public interface LoginService {

	/**
	 * do login (save token)
	 * 
	 * @param loginData
	 * @return
	 * @throws Exception
	 */
	public UserAccess doLogin(User admins, LoginData loginData) throws Exception;

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
	public void clearOldAccessDat(User admin) throws Exception;
}
