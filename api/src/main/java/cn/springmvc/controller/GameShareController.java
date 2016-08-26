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

import com.springmvc.utils.HttpUtils;

import cn.springmvc.service.GameShareService;

@Scope("prototype")
@Controller
@RequestMapping("/game")
public class GameShareController {
	
	@Autowired
	public GameShareService gameShareService;
	Logger logger = Logger.getLogger(GameShareController.class);
	
	@ResponseBody
	@RequestMapping(value = "/ticket", method = RequestMethod.POST)
	public Map<String, Object> getTicket(@RequestBody Map<String, String> jsonUrl){
		Map<String, String> result = null;
		String url = jsonUrl.get("url");
		logger.error("url--" + url);
		try {
			result = gameShareService.getTicket(url);
			logger.error("success--"+result);
			return HttpUtils.generateResponse("0", "success", result);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("failed");
			return HttpUtils.generateResponse("1", "failed", null);
		}
	}
}
