package cn.springmvc.service.impl.basic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.dao.VerifyCodeDao;
import cn.springmvc.model.LoginData;
import cn.springmvc.model.VerifyCode;
import cn.springmvc.service.basic.VerifyCodeService;

/**
 * 
 * @author johsnon
 *
 */
@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {
	@Autowired
	private VerifyCodeDao verifyCodeDao;

	/**
	 * save verify code
	 * 
	 * @param verifyCode
	 * @throws Exception
	 */
	public void setVerifyCode(VerifyCode verifyCode) throws Exception {
		verifyCodeDao.setVerifyCode(verifyCode);
	}

	/**
	 * get verify code
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public VerifyCode checkVerify(LoginData loginData) throws Exception {
		return verifyCodeDao.checkVerify(loginData);
	}

	/**
	 * clear expired codes
	 * 
	 * @throws Exception
	 */
	public void clearExpiredCodes() throws Exception {
		verifyCodeDao.clearExpiredCodes();
	}

	/**
	 * delete used verify verify code
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteVerifyCode(int id) throws Exception {
		verifyCodeDao.deleteVerifyCode(id);
	}

}
