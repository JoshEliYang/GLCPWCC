package cn.springmvc.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.utils.RequestUtil;

import cn.springmvc.service.WechartService;

/**
 * 
 * @author johsnon
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	public WechartService service;
	
	Logger logger=Logger.getLogger(TestController.class);
	
	/**
	 * token test
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/token", method = RequestMethod.GET)
	public String test() {
		String token =null;
		try{
			token=service.getAccessToken();
		}catch(Exception e){
			e.printStackTrace();
//			logger.error(e.toString());
		}
		return token;
	}

	@ResponseBody
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String test2() {
		String url = "http://120.26.54.131:8080/pricetag/selects";
		String response = null;

		try {
			response = RequestUtil.doGet(url);
			if (response == null) {
				logger.error("url error");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("exception occured when request url");
		}
		logger.error("request url success, response >>> " + response);
		return response;
	}
	
	@ResponseBody
	@RequestMapping(value = "/post", method = RequestMethod.GET)
	public String test3() {
		String url = "http://120.26.54.131:8080/pricetag/pricetags/query";
		String entity="{\"shopId\":\"KJG001\"}";
		String response = null;
		
		try{
			response=RequestUtil.doPost(url, entity);
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
		return response;
	}
}
