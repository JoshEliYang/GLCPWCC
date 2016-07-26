package cn.springmvc.dao;

import cn.springmvc.model.LoginData;
import cn.springmvc.model.VerifyCode;

/**
 * 
 * @author johsnon
 *
 */
public interface VerifyCodeDao {

	/**
	 * save verify code
	 * 
	 * @param verifyCode
	 * @throws Exception
	 */
	public void setVerifyCode(VerifyCode verifyCode) throws Exception;

	/**
	 * get verify code
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public VerifyCode checkVerify(LoginData loginData) throws Exception;

	/**
	 * clear expired codes
	 * 
	 * @throws Exception
	 */
	public void clearExpiredCodes() throws Exception;

	/**
	 * delete used verify verify code
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteVerifyCode(int id) throws Exception;
}
