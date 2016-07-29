package cn.springmvc.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.utils.HttpUtils;

import cn.springmvc.model.BasicModel;
import cn.springmvc.service.BasicService;

/**
 * set basic configuration
 * 
 * @author johnson
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/basic")
public class BasicController {
	@Autowired
	private BasicService service;

	Logger logger = Logger.getLogger(BasicController.class);

	/**
	 * get all basic configuration
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public Map<String, Object> getAll() {
		List<BasicModel> result = null;
		try {
			result = service.getAll();
			logger.error("get all basic configuration success >>> \n" + result);
			return HttpUtils.generateResponse("0", "基础配置查询成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("get all basic configuration failed >>> \n" + e.getMessage());
			return HttpUtils.generateResponse("1", "基础配置查询失败", null);
		}
	}

	/**
	 * get basic configuration which is in using
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/inusing", method = RequestMethod.GET)
	public Map<String, Object> getInusing() {
		List<BasicModel> result = null;
		try {
			result = service.getInusing();
			logger.error("get basic configuration which is in using success >>> \n" + result);
			return HttpUtils.generateResponse("0", "基础配置应用查询成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("get basic configuration which is in using failed >>> \n" + e.getMessage());
			return HttpUtils.generateResponse("1", "基础配置应用查询失败", null);
		}
	}

	/**
	 * get basic configuration by id
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public Map<String, Object> getById(@PathVariable int id) {
		BasicModel result = null;
		try {
			result = service.getById(id);
			logger.error("get basic configuration by id success >>> \n" + result);
			return HttpUtils.generateResponse("0", "通过ID基础配置查询成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("get basic configuration by id success >>> \n");
			return HttpUtils.generateResponse("0", "通过ID基础配置查询成功", null);
		}
	}

	/**
	 * get basic configuration by url
	 * 
	 * @param url
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/url/{url}", method = RequestMethod.GET)
	public Map<String, Object> getById(@PathVariable String url) {
		BasicModel result = null;
		try {
			result = service.getByUrl(url);
			logger.error("get basic configuration by url success >>> \n" + result);
			return HttpUtils.generateResponse("0", "通过url基础配置查询成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("get basic configuration by url failed >>> \n");
			return HttpUtils.generateResponse("0", "通过url基础配置查询失敗", null);
		}
	}

	/**
	 * insert basic configuration
	 * 
	 * @param basicModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insert}", method = RequestMethod.GET)
	public Map<String, Object> insert(@RequestBody BasicModel basicModel) {
		return null;
	}
}
