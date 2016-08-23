package cn.springmvc.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.utils.HttpUtils;

import cn.springmvc.model.BasicModel;
import cn.springmvc.service.GameShareService;

@Scope("prototype")
@Controller
@RequestMapping("/game")
public class GameShareController {
	
	@Autowired
	public GameShareService gameShareService;
	Logger logger = Logger.getLogger(GameShareController.class);
	
	@ResponseBody
	@RequestMapping(value = "/ticket", method = RequestMethod.GET)
	public Map<String, Object> getTicket(HttpServletRequest request){
		BasicModel basicModel = (BasicModel) request.getAttribute("BasicModel");
		Map<String, String> result = null;
		try {
			result = gameShareService.getTicket(basicModel);
			logger.error("success--"+result);
			return HttpUtils.generateResponse("0", "success", result);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("failed");
			return HttpUtils.generateResponse("1", "failed", null);
		}
	}
}
