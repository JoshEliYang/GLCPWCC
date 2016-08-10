package cn.springmvc.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.utils.HttpUtils;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.QrcodeModel;
import cn.springmvc.service.QrcodeService;

@Scope("prototype")
@Controller
@RequestMapping("/Qrcode")

public class QrcodeController {
	@Autowired
	private QrcodeService service;
	
	/**
	 * 查询已生成二维码
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
	
	/*
	 * 创建二维码
	 */
	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Map<String, Object>createQrcode(@RequestBody long ID, HttpServletRequest request){
		BasicModel model = (BasicModel) request.getAttribute("BasicModel");
		List<QrcodeModel> result = null;
		try{
			result = service.createQrcode(ID, model);
			return HttpUtils.generateResponse("0", "success", result);
		}catch(Exception e){
			return HttpUtils.generateResponse("1", "failed", null);
		}
	}
	
	/*
	 * 判断scene_id是否合法
	 */
	@ResponseBody
	@RequestMapping(value = "/valid", method = RequestMethod.POST)
	public Map<String, Object>isSceneIDLegal(@RequestBody String ID){
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
