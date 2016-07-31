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
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public Map<String, Object> insert(@RequestBody BasicModel basicModel) {
		try {
			service.insert(basicModel);
			logger.error("basic model insert success");
			return HttpUtils.generateResponse("0", "基础信息插入成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("basic model insert failed");
			return HttpUtils.generateResponse("0", "基础信息插入失败", null);
		}
	}

	/**
	 * set specific basic configuration in using
	 * 
	 * @param basicModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/setUsing", method = RequestMethod.PUT)
	public Map<String, Object> setUsing(@RequestBody BasicModel basicModel) {
		try {
			service.setUsing(basicModel.getId(), basicModel.isUsing());
			logger.error("set basicModel inUsing success");
			return HttpUtils.generateResponse("0", "设置成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("set basicModel inUsing failed" + e.getMessage());
			return HttpUtils.generateResponse("１", "设置失败", null);
		}
	}

	/**
	 * set specific basic configuration as default
	 * 
	 * @param basicModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/setDefault", method = RequestMethod.PUT)
	public Map<String, Object> setDefault(@RequestBody BasicModel basicModel) {
		try {
			service.setDefault(basicModel.getId());
			logger.error("set basicModel default success");
			return HttpUtils.generateResponse("0", "设置成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("set basicModel default failed" + e.getMessage());
			return HttpUtils.generateResponse("１", "设置失败", null);
		}
	}

	/**
	 * edit specific basic configuration
	 * 
	 * @param basicModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public Map<String, Object> edit(@RequestBody BasicModel basicModel) {
		try {
			service.edit(basicModel);
			logger.error("edit basicModel success");
			return HttpUtils.generateResponse("0", "修改成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("edit basicModel failed" + e.getMessage());
			return HttpUtils.generateResponse("１", "修改失败", null);
		}
	}
	
}
