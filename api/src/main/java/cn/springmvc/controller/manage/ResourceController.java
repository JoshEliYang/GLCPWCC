package cn.springmvc.controller.manage;

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

import com.alibaba.fastjson.JSON;
import com.springmvc.utils.HttpUtils;
import com.springmvc.utils.RequestUtil;

import cn.springmvc.model.BasicModel;
import cn.springmvc.service.wechat.WechartService;

/**
 * 
 * @author summ
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/resource")
public class ResourceController {
	@Autowired
	public WechartService service;

	Logger logger = Logger.getLogger(ResourceController.class);

	String resourceListUrl = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=";

	/**
	 * get image list from wechat server
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/{type}/{offset}/{count}", method = RequestMethod.GET)
	public Map<String, Object> getList(HttpServletRequest request, @PathVariable String type, @PathVariable int offset,
			@PathVariable int count) {
		BasicModel basicModel = (BasicModel) request.getAttribute("BasicModel");

		String token = null;
		try {
			token = service.getAccessToken(basicModel);
			logger.error("get access token success >>>>> " + token);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("get access tokenfailed ");
			return HttpUtils.generateResponse("2", "token请求失败", null);
		}

		String data = "{\"type\":\"" + type + "\",\"offset\":\"" + offset + "\",\"count\":\"" + count + "\"}";
		Map<String, Object> result = null;
		try {
			String response = RequestUtil.doPost(resourceListUrl + token, data);
			result = (Map<String, Object>) JSON.parse(response);
			logger.error("get image list success >>>>>> " + response);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("get image list failed");
			return HttpUtils.generateResponse("1", "素材列表请求失败", null);
		}
		return HttpUtils.generateResponse("0", "素材列表请求成功", result);
	}
}
