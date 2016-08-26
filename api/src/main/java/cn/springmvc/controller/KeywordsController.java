package cn.springmvc.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
			result = service.getAll(basicModel, false);
			logger.error("get all keywords success >>> \n" + result);
			return HttpUtils.generateResponse("0", "关键字查询成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("get all keywords failed\n" + null);
			return HttpUtils.generateResponse("1", "关键字查询失败", result);
		}
	}

	/**
	 * 
	 * @param request
	 * @param keyword
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public Map<String, Object> insert(HttpServletRequest request, @RequestBody Keywords keyword) {
		try {
			service.insert(keyword);
			logger.error("insert keywords success >>> \n");
			return HttpUtils.generateResponse("0", "关键字插入成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("insert keywords error >>> \n");
			return HttpUtils.generateResponse("1", "关键字插入失败", null);
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

		List<Keywords> result = null;
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

	/**
	 * insert subscribe reply
	 * 
	 * @param request
	 * @param keyword
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/subscribe", method = RequestMethod.POST)
	public Map<String, Object> insertSubscribe(HttpServletRequest request, @RequestBody Keywords keyword) {
		try {
			service.insertSubscribe(keyword);
			logger.error("insert subscribe reply success >>> \n");
			return HttpUtils.generateResponse("0", "关注回复插入成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("insert subscribe reply error >>> \n");
			return HttpUtils.generateResponse("1", "关注回复插入失败", null);
		}
	}

	/**
	 * set inUsing
	 * 
	 * @param request
	 * @param keyword
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/setInUsing", method = RequestMethod.POST)
	public Map<String, Object> setInUsing(HttpServletRequest request, @RequestBody Keywords keyword) {
		try {
			service.setInUsing(keyword);
			logger.error("set inUsing flag success");
			return HttpUtils.generateResponse("0", "启用设置成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("set inUsing flag failed");
			return HttpUtils.generateResponse("1", "启用设置成功失败", null);
		}
	}

	/**
	 * set subscribe inUsing flag
	 * 
	 * @param request
	 * @param keyword
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/subscribe/setInUsing", method = RequestMethod.POST)
	public Map<String, Object> subscribeSetInUsing(HttpServletRequest request, @RequestBody Keywords keyword) {
		try {
			service.subscribeSetInUsing(keyword);
			logger.error("set subscribe inUsing flag success");
			return HttpUtils.generateResponse("0", "启用设置成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("set subscribe inUsing flag failed");
			return HttpUtils.generateResponse("1", "启用设置成功失败", null);
		}
	}

	/**
	 * edit keyword and auto-reply message
	 * 
	 * @param request
	 * @param keyword
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.PUT)
	public Map<String, Object> edit(HttpServletRequest request, @RequestBody Keywords keyword) {
		try {
			service.edit(keyword);
			logger.error("edit keyword success");
			return HttpUtils.generateResponse("0", "修改成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("edit keyword failed");
			return HttpUtils.generateResponse("1", "修改失败", null);
		}
	}

	/**
	 * delete keyword
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Map<String, Object> delete(HttpServletRequest request, @PathVariable int id) {
		try {
			service.delete(id);
			logger.error("delete keyword success");
			return HttpUtils.generateResponse("0", "删除成功", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("delete keyword failed");
			return HttpUtils.generateResponse("1", "删除失败", null);
		}
	}
}
