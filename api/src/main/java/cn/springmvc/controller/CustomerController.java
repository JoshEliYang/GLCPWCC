package cn.springmvc.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.utils.HttpUtils;

import cn.springmvc.model.BasicModel;
import cn.springmvc.service.CustomerService;

@Scope("prototype")
@Controller
@RequestMapping("/customer")

public class CustomerController {
	@Autowired
	public CustomerService custService;
	
	@ResponseBody
	@RequestMapping(value = "/select/{page}", method = RequestMethod.GET)
	public Map<String, Object> getAll(@PathVariable int page,HttpServletRequest request){
		BasicModel basicModel = (BasicModel) request.getAttribute("BasicModel");
		String result = null;
		try {
			result = custService.getAll(page,basicModel);
			return HttpUtils.generateResponse("0", "success", result);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return HttpUtils.generateResponse("1", "failed", null);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test(){
		String result = null;
		try {
			result = custService.test();
			return result;
//			return HttpUtils.generateResponse("0", "success", result);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
//			return HttpUtils.generateResponse("1", "failed", null);
			// TODO: handle exception
		}
	}
	
	
	

}
