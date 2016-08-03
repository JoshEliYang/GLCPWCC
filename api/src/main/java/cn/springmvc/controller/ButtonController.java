package cn.springmvc.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.utils.HttpUtils;

import cn.springmvc.model.Button;
import cn.springmvc.model.ButtonGroup;
import cn.springmvc.model.User;
import cn.springmvc.service.ButtonService;

/**
 * 
 * @author johsnon
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/button")
public class ButtonController {
	@Autowired
	private ButtonService buttonService;

	Logger logger = Logger.getLogger(ButtonController.class);

	/**
	 * get all button groups
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/group", method = RequestMethod.GET)
	public Map<String, Object> getParents(HttpServletRequest request) {
		User admin = (User) request.getAttribute("admin");

		List<ButtonGroup> result = null;
		try {
			result = buttonService.getButtonGroup(admin.getUserLevel());
			logger.error("get all button groups success >>> \n" + result);
			return HttpUtils.generateResponse("0", "按钮组查询成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("get all button groups failed >>> \n" + e.getMessage());
			return HttpUtils.generateResponse("1", "按钮组查询失败", null);
		}
	}

	/**
	 * get child buttons by parent id
	 * 
	 * @param parentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/button/{groupId}", method = RequestMethod.GET)
	public Map<String, Object> getChilds(@PathVariable int groupId) {
		List<Button> result = null;
		try {
			result = buttonService.getButtons(groupId);
			logger.error("get all buttons success >>> \n" + result);
			return HttpUtils.generateResponse("0", "按钮查询成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("get all buttons failed >>> \n" + e.getMessage());
			return HttpUtils.generateResponse("0", "按钮查询失败", null);
		}
	}
}
