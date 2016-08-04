package cn.springmvc.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.utils.HttpUtils;

import cn.springmvc.model.QrcodeModel;
import cn.springmvc.service.QrcodeService;

@Scope("prototype")
@Controller
@RequestMapping("/Qrcode")

public class QrcodeController {
	@Autowired
	private QrcodeService service;
	
	/**
	 * 获得所有标签
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping(value = "/select", method = RequestMethod.GET)
	public Map<String, Object>getAll(){
		List<QrcodeModel> result = null;
		try{
			result = service.getAll();
			return HttpUtils.generateResponse("0", "success", result);
		} catch(Exception e){
			e.printStackTrace();
			return HttpUtils.generateResponse("1", "fail", null);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Map<String, Object>createTag(@RequestBody String name){
		Map<String, String> result = null;
		try{
			result = service.createQrcode(name);
			return null;
		}catch(Exception e){
			return null;
		}
	}
	
	
}
