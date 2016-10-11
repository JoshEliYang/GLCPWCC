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

import com.springmvc.utils.HttpUtils;

import cn.springmvc.model.BasicModel;
import cn.springmvc.service.TagService;

@Scope("prototype")
@Controller
@RequestMapping("/tag")

public class TagController {
	@Autowired
	public TagService service;
	Logger logger = Logger.getLogger(TagController.class);
	
	/*
	 * 创建标签
	 */
	
	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Map<String, Object>create(@RequestBody String jsonStr, HttpServletRequest request){
		BasicModel model = (BasicModel)request.getAttribute("BasicModel");
		Map<String, String> result = null;
		try{
			result = service.createTag(jsonStr, model);
			logger.error("tags--"+result);
			return HttpUtils.generateResponse("0", "success", result);
		}catch (Exception e){
			e.printStackTrace();
			logger.error("error");
			return HttpUtils.generateResponse("1", "failed", null);
		}
	}
	
	/*
	 * 删除标签
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Map<String, Object>delete(@RequestBody String jsonStr, HttpServletRequest request){
		BasicModel model =  (BasicModel) request.getAttribute("BasicModel");
		Map<String, String> result = null;
		try {
			result = service.delete(jsonStr, model);
			return HttpUtils.generateResponse("0", "success", result);
		} catch (Exception e) {
			e.printStackTrace();
			return HttpUtils.generateResponse("1", "failed", null);
		}
	}
	
	/*
	 * 更新标签
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Map<String, Object>update(@RequestBody String jsonStr, HttpServletRequest request){
		BasicModel model = (BasicModel) request.getAttribute("BasicModel");
		logger.error("json--"+jsonStr);
		Map<String, String> result = null;
		try {
			result = service.update(jsonStr, model);
			logger.error("string response--"+result);
			return HttpUtils.generateResponse("0", "success", result);
		} catch (Exception e) {
			e.printStackTrace();
			return HttpUtils.generateResponse("1", "failed", null);
		}
	}
	
	/*
	 * 查询所有标签
	 */
	@ResponseBody
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public Map<String, Object>getAll(HttpServletRequest request){
		BasicModel model = (BasicModel) request.getAttribute("BasicModel");
		logger.error(model);
		Map<String, String> result = null;
		try{
			result = service.getAll(model);
//			List<TagModel> tagList = result.get("tags");
			logger.error("tags--"+result);
			return HttpUtils.generateResponse("0", "success", result);
		} catch(Exception e){
			e.printStackTrace();
			logger.error("error");
			return HttpUtils.generateResponse("1", "fail", null);
		}
	}
	
	/*
	 * 获取指定标签下的用户
	 */
	@ResponseBody
	@RequestMapping(value = "/getUser", method = RequestMethod.POST)
	public Map<String, Object> getUserByTag(@RequestBody String jsonStr, HttpServletRequest request){
		BasicModel model = (BasicModel) request.getAttribute("BasicModel");
		Map<String, String> result = null;
		try {
			result = service.getUserByTag(jsonStr, model);
			return HttpUtils.generateResponse("0", "success", result);
		} catch (Exception e) {
			return HttpUtils.generateResponse("1", "fail", null);
			// TODO: handle exception
		}
	}
	
	/*
	 * 创建标签同时生成二维码
	 */
	@ResponseBody
	@RequestMapping(value = "/tagAndQrcode", method = RequestMethod.POST)
	public Map<String, Object> createTagAndQrcode(@RequestBody String jsonStr, HttpServletRequest request){
		BasicModel model = (BasicModel) request.getAttribute("BasicModel");
		Map<String, String> result = null;
		try {
			result = service.getUserByTag(jsonStr, model);
			return HttpUtils.generateResponse("0", "success", result);
		} catch (Exception e) {
			return HttpUtils.generateResponse("1", "fail", null);
			// TODO: handle exception
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
