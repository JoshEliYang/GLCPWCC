package cn.springmvc.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.utils.HttpUtils;

import cn.springmvc.model.User;
import cn.springmvc.model.UserLevel;
import cn.springmvc.service.AdminService;

/**
 * 
 * @author johsnon
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService service;

	Logger logger = Logger.getLogger(AdminController.class);

	/**
	 * get all admin users
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public Map<String, Object> getAllUsers() {
		List<User> results = null;
		try {
			results = service.getAllUsers();
			logger.error("get all admin users success >>> \n" + results);
			return HttpUtils.generateResponse("0", "管理员查询成功", results);
		} catch (Exception e) {
			logger.error("get all admin users      failed\n");
			return HttpUtils.generateResponse("0", "管理员查询失败", null);
		}
	}

	/**
	 * get all levels of users
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/allLevels", method = RequestMethod.GET)
	public Map<String, Object> getAllUserLevels() {
		List<UserLevel> results = null;
		try {
			results = service.getAllUserLevels();
			logger.error("get all admin levels success >>> \n" + results);
			return HttpUtils.generateResponse("0", "管理员级别查询成功", results);
		} catch (Exception e) {
			logger.error("get all admin levels failed\n");
			return HttpUtils.generateResponse("0", "管理员级别查询失败", null);
		}
	}

}
