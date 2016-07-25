package cn.springmvc.controller;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.springmvc.service.ButtonService;

/**
 * 
 * @author johsnon
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/button")
public class ButtonController {
	@Autowired
	private ButtonService service;

	Logger logger = Logger.getLogger(ButtonController.class);

	/**
	 * get all parents buttons
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/parents", method = RequestMethod.GET)
	public Map<String, Object> getParents() {
		return null;
	}

	/**
	 * get child buttons by parent id
	 * 
	 * @param parentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/childs/{parentId}", method = RequestMethod.GET)
	public Map<String, Object> getChilds(@PathVariable int parentId) {
		return null;
	}
}
