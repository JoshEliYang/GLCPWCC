package cn.springmvc.controller.manage;

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

import com.alibaba.fastjson.JSON;
import com.springmvc.utils.HttpUtils;
import com.springmvc.utils.RequestUtil;

import cn.springmvc.model.BasicModel;
import cn.springmvc.service.wechat.WechartService;

/**
 * 
 * @author johsnon
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/account")
public class AccountController {
	@Autowired
	public WechartService service;

	Logger logger = Logger.getLogger(AccountController.class);

	/**
	 * 请求二维码
	 * 
	 * @param scene_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/qrcode", method = RequestMethod.POST)
	public Map<String, Object> qrcode(@RequestBody String scene_id, HttpServletRequest request) {
		BasicModel basicModel = (BasicModel) request.getAttribute("BasicModel");

		// get access_token
		String access_token = null;
		try {
			access_token = service.getAccessToken(basicModel);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("get access_token error");
			logger.error(e.toString());
			return HttpUtils.generateResponse("2", "access_token 请求失败", null);
		}

		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + access_token;
		String json = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": " + scene_id
				+ "}}}";
		Map<String, String> response = null;
		try {
			response = (Map<String, String>) JSON.parse(RequestUtil.doPost(url, json));
			return HttpUtils.generateResponse("0", "请求成功", response);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("request qrcode error");
			logger.error(e.toString());
			return HttpUtils.generateResponse("1", "二维码请求失败", null);
		}
	}
}
