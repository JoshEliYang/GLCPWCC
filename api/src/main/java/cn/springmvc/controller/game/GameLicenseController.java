package cn.springmvc.controller.game;

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

import cn.springmvc.service.game.GameLicenseService;

/**
 * 
 * @author summ
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/game")
public class GameLicenseController {
	
	@Autowired
	public GameLicenseService gameLicenseService;
	Logger logger = Logger.getLogger(GameLicenseController.class);
	
	/**
	 * 微信网页授权（目前停用）
	 * @param jsonCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/license", method = RequestMethod.POST)
	public Map<String, Object> getUserInfo(@RequestBody Map<String, String> jsonCode){
		Map<String, String> result = null;
		String code = jsonCode.get("code");
		logger.error("code--" + code);
		try {
			result = gameLicenseService.getUserInfo(code);
			logger.error("get code success --" + result);
			return HttpUtils.generateResponse("0", "success", result);
		} catch (Exception e) {
			logger.error("get code errer");
			return HttpUtils.generateResponse("1", "failed", null);
		}
	}

}
