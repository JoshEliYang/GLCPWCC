package cn.springmvc.controller;

import java.util.Map;

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

import cn.springmvc.service.UserService;

/**
 * 
 * @author johsnon
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	public UserService service;
	
	Logger logger=Logger.getLogger(UserController.class);
	
	/**
	 * 获得所有分组信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public Map<String,Object> getAll(){
		Map<String,String> result=null;
		try {
			result=service.getAll();
			logger.error("get all group success >>> \n"+result);
			return HttpUtils.generateResponse("0", "分组查询请求成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("get all group error >>> \n"+e.getMessage());
			return HttpUtils.generateResponse("1", "分组查询请求失败", null);
		}
	}
	
	/**
	 * 创建分组
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/group", method = RequestMethod.POST)
	public Map<String,Object> createGroup(@RequestBody String name) {
		Map<String,String> result=null;
		try{
			result=service.createGroup(name);
			logger.error("create group success >>> \n"+result);
			return HttpUtils.generateResponse("0", "创建请求成功", result);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("create group error >>> \n"+e.getMessage());
			return HttpUtils.generateResponse("1", "创建请求失败", null);
		}
	}
	
	/**
	 * 移动用户分组
	 * @param indat
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/move", method = RequestMethod.POST)
	public Map<String,Object> moveToGroup(@RequestBody String indat) {
		@SuppressWarnings("unchecked")
		String openId=((Map<String,String>)JSON.parse(indat)).get("openid");
		@SuppressWarnings("unchecked")
		String groupId=((Map<String,String>)JSON.parse(indat)).get("to_groupid");
		Boolean res=null;
		try {
			res=service.moveToGroup(openId,groupId);
			logger.error("move user to group success >>> \n");
			return HttpUtils.generateResponse("0", "设置用户分组请求成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("move user to group error >>> \n"+e.getMessage());
			return HttpUtils.generateResponse("1", "设置用户分组请求失败", null);
		}
	}
}
