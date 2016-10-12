package cn.springmvc.controller.basic;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.utils.HttpUtils;

import cn.springmvc.model.BasicModel;
import cn.springmvc.service.function.MenuService;

/**
 * 
 * @author johsnon
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/menu")
public class MenuController {
	@Autowired
	public MenuService menuService;

	Logger logger = Logger.getLogger(MenuController.class);

	/**
	 * 创建菜单 参数和微信一样，直接转发到微信
	 * 
	 * @param jsonStr
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> menu(@RequestBody String jsonStr, HttpServletRequest request) {
		BasicModel basicModel = (BasicModel) request.getAttribute("BasicModel");

		Boolean result = false;
		try {
			result = menuService.setMenu(jsonStr, basicModel);
			if (result == true) {
				logger.error("set menu success");
				return HttpUtils.generateResponse("0", "请求成功", result);
			}
			logger.error("set menu error");
			return HttpUtils.generateResponse("1", "设置菜单请求失败", null);
		} catch (Exception e) {
			e.printStackTrace();
			return HttpUtils.generateResponse("2", "设置菜单请求失败", null);
		}
	}
}
