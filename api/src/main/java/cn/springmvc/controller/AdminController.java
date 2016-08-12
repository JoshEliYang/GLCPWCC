package cn.springmvc.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.utils.GLCPStringUtils;
import com.springmvc.utils.HttpUtils;

import cn.springmvc.model.AdminLevel;
import cn.springmvc.model.Right;
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
			logger.error("get all admin users failed\n");
			return HttpUtils.generateResponse("1", "管理员查询失败", null);
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
			return HttpUtils.generateResponse("1", "管理员级别查询失败", null);
		}
	}

	/**
	 * get user
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public Map<String, Object> getById(HttpServletRequest request) {
		User admin = (User) request.getAttribute("admin");
		return HttpUtils.generateResponse("0", "管理员信息查询成功", admin);
	}

	/**
	 * insert admin data
	 * 
	 * @param admin
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public Map<String, Object> insert(@RequestBody User admin) {
		try {
			service.insert(admin);
			logger.error("admin insert success >>> \n");
			return HttpUtils.generateResponse("0", "管理员插入成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("admin insert failed >>> \n");
			return HttpUtils.generateResponse("1", "管理员插入失败", null);
		}
	}

	/**
	 * edit admin info (except password)
	 * 
	 * @param admin
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public Map<String, Object> edit(@RequestBody User admin) {
		try {
			service.edit(admin);
			logger.error("admin edit success >>> \n");
			return HttpUtils.generateResponse("0", "管理员修改成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("admin edit failed >>> \n");
			return HttpUtils.generateResponse("1", "管理员修改失败", null);
		}
	}

	/**
	 * delete admin by id
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> delete(@PathVariable int id) {
		try {
			service.delete(id);
			logger.error("admin delete success >>> \n");
			return HttpUtils.generateResponse("0", "管理员删除成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("admin delete failed >>> \n");
			return HttpUtils.generateResponse("1", "管理员删除失败", null);
		}
	}

	/**
	 * reset password
	 * 
	 * @param admin
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/resetPasswd", method = RequestMethod.PATCH)
	public Map<String, Object> resetPassword(@RequestBody User admin) {
		try {
			service.resetPassword(admin);
			logger.error("admin reset password success >>> \n");
			return HttpUtils.generateResponse("0", "管理员重置密码成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("admin reset password failed >>> \n");
			return HttpUtils.generateResponse("1", "管理重置密码失败", null);
		}
	}

	/**
	 * admin add level
	 * 
	 * @param adminLevel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addLevel", method = RequestMethod.POST)
	public Map<String, Object> addLevel(@RequestBody AdminLevel adminLevel) {
		try {
			if (adminLevel == null ||
					GLCPStringUtils.isNull(adminLevel.getLevelName())) {
				return HttpUtils.generateResponse("1", "请求错误", null);
			}

			service.addLevel(adminLevel);
			
			return HttpUtils.generateResponse("0", "管理员添加权限成功", null);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("admin add level >>> \n");
			return HttpUtils.generateResponse("1", "管理员添加权限", null);
		}
	}
	
	/**
	 * admin update button group
	 * 
	 * @param right
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/button/setRight", method = RequestMethod.PATCH)
	public Map<String, Object> setRight(@RequestBody Right right) {
		try {
			if (right == null) {
				return HttpUtils.generateResponse("1", "请求参数错误", null);
			}
			service.updateRight(right);
			return HttpUtils.generateResponse("0", "修改按钮成功", null);
		} catch (Exception ex) {
			ex.printStackTrace();
			return HttpUtils.generateResponse("1", "修改按钮错误", null);
		}
	}
}
