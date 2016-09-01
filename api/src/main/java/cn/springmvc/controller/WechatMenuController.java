package cn.springmvc.controller;

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

import com.alibaba.fastjson.JSON;
import com.springmvc.utils.HttpUtils;
import com.springmvc.utils.RequestUtil;

import cn.springmvc.model.BasicModel;
import cn.springmvc.service.WechartService;

/**
 * Customize WeChat menu
 * 
 * @author johnson
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/wechatMenu")
public class WechatMenuController {

	@Autowired
	private WechartService wechatService;

	Logger logger = Logger.getLogger(WechatMenuController.class);

	/**
	 * get customize WeChat menu from WeChat server
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> getMenu(HttpServletRequest request, HttpServletResponse response) {
		BasicModel basicModel = (BasicModel) request.getAttribute("BasicModel");
		String url = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=";

		try {
			String accessToken = wechatService.getAccessToken(basicModel);
			String results = RequestUtil.doGet(url + accessToken);
			logger.error("get all admin users success >>> \n" + results);
			return HttpUtils.generateResponse("0", "微信自定义菜单查询成功", results);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("get all admin users success >>> \n");
			return HttpUtils.generateResponse("1", "微信自定义菜单查询失败", null);
		}
	}

	/**
	 * customize WeChat menu
	 * 
	 * @param menu
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> setMenu(@RequestBody Map<String, Object> menu, HttpServletRequest request,
			HttpServletResponse response) {
		BasicModel basicModel = (BasicModel) request.getAttribute("BasicModel");
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";

		try {
			String accessToken = wechatService.getAccessToken(basicModel);
			String results = RequestUtil.doPost(url + accessToken, JSON.toJSONString(menu));
			logger.error("get all admin users success >>> \n" + results);
			return HttpUtils.generateResponse("0", "设置微信自定义菜单成功", results);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("get all admin users success >>> \n");
			return HttpUtils.generateResponse("1", "设置微信自定义菜单失败", null);
		}
	}
}
