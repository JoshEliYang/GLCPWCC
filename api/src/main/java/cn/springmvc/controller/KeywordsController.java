package cn.springmvc.controller;

import java.util.List;
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
import cn.springmvc.model.Keywords;
import cn.springmvc.service.KeywordsService;

/**
 * 
 * @author johnson
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/keywords")
public class KeywordsController {
	@Autowired
	private KeywordsService service;

	Logger logger = Logger.getLogger(KeywordsController.class);

	/**
	 * get all keywords and reply message
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public Map<String, Object> getAll(HttpServletRequest request) {
		BasicModel basicModel = (BasicModel) request.getAttribute("BasicModel");

		List<Keywords> result = null;
		try {
			result = service.getAll(basicModel);
			logger.error("get all keywords success >>> \n" + result);
			return HttpUtils.generateResponse("0", "关键字查询成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("get all keywords failed\n" + null);
			return HttpUtils.generateResponse("1", "关键字查询失败", result);
		}
	}

	/**
	 * get subscribe reply message
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/subscribe", method = RequestMethod.GET)
	public Map<String, Object> getSubscribeReply(HttpServletRequest request) {
		BasicModel basicModel = (BasicModel) request.getAttribute("BasicModel");

		Keywords result = null;
		try {
			result = service.getSubscribeReply(basicModel);
			logger.error("get subscribe reply message success" + result);
			return HttpUtils.generateResponse("0", "关注回复查询成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("get subscribe reply failed\n" + null);
			return HttpUtils.generateResponse("1", "关注回复查询失败", null);
		}
	}
}
