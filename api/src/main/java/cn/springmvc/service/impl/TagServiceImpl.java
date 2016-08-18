package cn.springmvc.service.impl;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.springmvc.utils.RequestUtil;

import cn.springmvc.model.BasicModel;
import cn.springmvc.service.TagService;
import cn.springmvc.service.WechartService;


@Service
public class TagServiceImpl implements TagService {
	@Autowired
	public WechartService service;
	
	public Map<String, String> createTag(String jsonStr, BasicModel model) throws Exception{
		String accessToken = service.getAccessToken(model);
		String url = "https://api.weixin.qq.com/cgi-bin/tags/create?access_token=" + accessToken;
		String response = RequestUtil.doPost(url, jsonStr);
		Map<String, String> res = (Map<String, String>) JSON.parse(response);
		return res;
	}	
	
	public Map<String, String> delete(String jsonStr, BasicModel model) throws Exception{
		String accessToken = service.getAccessToken(model);
		String url = "https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=" + accessToken;
		String response = RequestUtil.doPost(url, jsonStr);
		Map<String, String> res = (Map<String, String>) JSON.parse(response);
		return res;
	}
	
	public Map<String, String> update(String jsonStr, BasicModel model) throws Exception{
		String accessToken = service.getAccessToken(model);
		String url  = "https://api.weixin.qq.com/cgi-bin/tags/update?access_token" + accessToken;
		String response = RequestUtil.doPost(url, jsonStr);
		Map<String, String> res  = (Map<String, String>) JSON.parse(response);
		return res;
	}
	

	public Map<String, String> getAll(BasicModel model) throws Exception {
		String accessToken = service.getAccessToken(model);
		String url = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token=" + accessToken;
		String response = RequestUtil.doGet(url);
		Map<String, String> tags = (Map<String, String>) JSON.parse(response);
		return tags;
	}
	
	public Map<String, String> getUserByTag(String jsonStr, BasicModel model)throws Exception {
		String accessToken = service.getAccessToken(model);
		String url  = "https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token" + accessToken;
		String response = RequestUtil.doPost(url, jsonStr);
		Map<String, String> res  = (Map<String, String>) JSON.parse(response);
		return res;
	}
	
	public Map<String, String> createTagAndQrcode(String jsonStr,BasicModel model) throws Exception{
		TagService tagService = null;
		Map<String, String> res = tagService.createTag(jsonStr, model);
		return null;
	}

}
