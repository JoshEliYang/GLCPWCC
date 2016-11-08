package cn.springmvc.controller.basic;

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
import cn.springmvc.model.LevelRight;
import cn.springmvc.model.User;
import cn.springmvc.model.UserLevel;
import cn.springmvc.service.basic.AdminService;

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
			if (adminLevel == null || GLCPStringUtils.isNull(adminLevel.getLevelName())) {
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
	 * get all level's rights
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/levelRight", method = RequestMethod.GET)
	public Map<String, Object> addLevelRight() {
		List<LevelRight> result = null;
		try {
			result = service.getAllLevelRights();
			return HttpUtils.generateResponse("0", "查询所有权限功能成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("get all level right error >>>> " + e.getMessage());
			return HttpUtils.generateResponse("1", "查询所有权限功能失败", null);
		}
	}

	/**
	 * enable/unable level's right
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/levelRight", method = RequestMethod.PATCH)
	public Map<String, Object> setLevelRight(@RequestBody Map<String, Object> param) {
		try {
			service.setLevelRight((Integer) (param.get("levelId")), (Integer) (param.get("groupId")),
					(Boolean) (param.get("enable")));
			return HttpUtils.generateResponse("0", "设置权限功能成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("edit level right error >>>> " + e.getMessage());
			return HttpUtils.generateResponse("1", "设置权限功能失败", null);
		}
	}

	/**
	 * edit user level
	 * 
	 * @param adminLevel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/userLevel", method = RequestMethod.PATCH)
	public Map<String, Object> editAdminLevel(@RequestBody UserLevel adminLevel) {
		try {
			service.editUserLevel(adminLevel);
			return HttpUtils.generateResponse("0", "修改权限成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("edit user level error >>>> " + e.getMessage());
			return HttpUtils.generateResponse("1", "修改权限失败", null);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/userLevel/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> removeAdminLevel(@PathVariable int id) {
		try {
			service.removeAdminLevel(id);
			return HttpUtils.generateResponse("0", "修改权限成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("remove user level error >>>> " + e.getMessage());
			return HttpUtils.generateResponse("1", "修改权限失败", null);
		}
	}

}
