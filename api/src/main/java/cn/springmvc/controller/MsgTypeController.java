package cn.springmvc.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.utils.HttpUtils;

import cn.springmvc.model.MsgType;
import cn.springmvc.service.MsgTypeService;

/**
 * 
 * @author johnson
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/msgtype")
public class MsgTypeController {
	@Autowired
	private MsgTypeService service;

	Logger logger = Logger.getLogger(MsgTypeController.class);

	/**
	 * get all msgTypes
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public Map<String, Object> getAll() {
		List<MsgType> result = null;
		try {
			result = service.getAll();
			logger.error("get all msgType success >>> \n" + result);
			return HttpUtils.generateResponse("0", "消息类型查询成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("get all msgType failed\n");
			return HttpUtils.generateResponse("0", "消息类型查询失败", null);
		}
	}
}
