package cn.springmvc.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.dao.LoginDao;
import cn.springmvc.model.LoginData;
import cn.springmvc.model.User;
import cn.springmvc.model.UserAccess;
import cn.springmvc.service.LoginService;

/**
 * 
 * @author johsnon
 *
 */
@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	private LoginDao loginDao;

	/**
	 * check access token
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public UserAccess tokenCheck(String token) throws Exception {
		return loginDao.tokenCheck(token);
	}

	/**
	 * do login (save token)
	 * 
	 * @param loginData
	 * @return
	 * @throws Exception
	 */
	public UserAccess doLogin(User admin, LoginData loginData) throws Exception {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		UserAccess userAccess = new UserAccess();
		userAccess.setUserId(admin.getId());
		userAccess.setToken(String.valueOf(System.currentTimeMillis()));
		userAccess.setLoginTime(dateFormat.format(now));

		if (loginData.isLongTermFlag())
			now.setDate(now.getDate() + 30);
		else
			now.setDate(now.getDate() + 1);
		userAccess.setLapsedTime(dateFormat.format(now));
		loginDao.insertUserAccess(userAccess);

		return userAccess;
	}

	/**
	 * clear expired user access data
	 * 
	 * @throws Exception
	 */
	public void clearExpiredAccessDat() throws Exception {
		loginDao.clearExpiredAccessDat();
	}

	/**
	 * clear old access data
	 * 
	 * @throws Exception
	 */
	public void clearOldAccessDat(User admin) throws Exception {
		loginDao.clearOldAccessDat(admin.getId());
	}

}
