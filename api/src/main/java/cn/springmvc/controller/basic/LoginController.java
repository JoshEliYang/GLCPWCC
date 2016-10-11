package cn.springmvc.controller.basic;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.utils.HttpUtils;
import com.springmvc.utils.VerifyCodeUtils;

import cn.springmvc.model.LoginData;
import cn.springmvc.model.User;
import cn.springmvc.model.UserAccess;
import cn.springmvc.model.VerifyCode;
import cn.springmvc.service.AdminService;
import cn.springmvc.service.LoginService;
import cn.springmvc.service.VerifyCodeService;

/**
 * 
 * @author johsnon
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private VerifyCodeService verifyCodeService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private LoginService loginService;

	Logger logger = Logger.getLogger(LoginController.class);

	/**
	 * do login
	 * 
	 * @param loginData
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Map<String, Object> login(@RequestBody LoginData loginData, HttpServletRequest request) {
		String ip = request.getRemoteAddr();
		loginData.setIp(ip);

		VerifyCode verifyCode = null;
		try {
			verifyCode = verifyCodeService.checkVerify(loginData);
			verifyCodeService.deleteVerifyCode(verifyCode.getId());
		} catch (Exception e) {
			logger.error("Exception occured when checking verify code! >>> " + e.getMessage());
		}
		if (verifyCode == null) {
			logger.error("failed in checking verify code!");
			return HttpUtils.generateResponse("1", "验证码验证失败", null);
		}

		User admin = null;
		try {
			admin = adminService.checkUser(loginData.getUsername(), loginData.getPasswd());
		} catch (Exception e) {
			logger.error("Exception occured when checking user name and password ! >>> " + e.getMessage());
		}
		if (admin == null) {
			logger.error("failed in checking user name and password!");
			return HttpUtils.generateResponse("2", "用户名密码验证失败", null);
		}

		UserAccess userAccess = null;
		try {
			loginService.clearExpiredAccessDat();
			loginService.clearOldAccessDat(admin);

			userAccess = loginService.doLogin(admin, loginData);
			logger.error("login success !　getted user access data >>>> " + userAccess);
			return HttpUtils.generateResponse("0", "登录成功", userAccess);
		} catch (Exception e) {
			logger.error("login error! can't get user access data >>>> " + e.getMessage());
			return HttpUtils.generateResponse("3", "获得token失败", null);
		}
	}

	/**
	 * get verification code
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getVerification", method = RequestMethod.GET)
	public void getAllUsers(HttpServletRequest request, HttpServletResponse response) {

		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		// 生成随机字串
		String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
		String ip = request.getRemoteAddr();
		VerifyCode verifyCodeDat = new VerifyCode(verifyCode, ip);
		try {
			// 清除过期验证码
			verifyCodeService.clearExpiredCodes();
			// 保存验证码
			verifyCodeService.setVerifyCode(verifyCodeDat);
			logger.error("set verify code success >>>> " + verifyCode.toLowerCase());
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.error("set verify code failed ! >>>> " + verifyCode.toLowerCase());
		}

		// 存入会话session
		// HttpSession session = request.getSession(true);
		// session.setAttribute("rand", verifyCode.toLowerCase());
		// 生成图片
		int w = 200, h = 80;
		try {
			VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
			logger.error("get verification code success >>>> " + verifyCode.toLowerCase());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("transfer verification code failed ! >>>> " + verifyCode.toLowerCase());
		}
	}
}
