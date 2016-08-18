package cn.springmvc.service.impl;

import java.lang.reflect.Array;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.springmvc.utils.RequestUtil;

import cn.springmvc.model.BasicModel;
import cn.springmvc.service.CustomerService;
import cn.springmvc.service.WechartService;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	public WechartService chartService;
	
	public String getAll(int page, BasicModel basicModel) throws Exception{
		String accessToken = chartService.getAccessToken(basicModel);
		String userUrl = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + accessToken;
		String response = RequestUtil.doGet(userUrl);
		Map<String, Object> res = (Map<String, Object>) JSON.parse(response);
		Map<String, Object> data = (Map<String, Object>) res.get("data");
		Array array = (Array) data.get("openid");
		String userInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=" +accessToken;
		String firstOpenID = (String) Array.get(array, 20*page-20);
		String openIDArray = null;
		String openIDArrays = "";
		String test = "cancel";
		for(int i = 0;i<20;++i){
			openIDArray = ",{\"openid\":\"" + test + "\",\"lang\":\"zh-CN\"}";
			openIDArrays = openIDArrays + openIDArray ;
		}
		String jsonStr = "{\"user_list\": [" + openIDArrays + "]}";
		return jsonStr;
	}

	public String test() throws Exception {
		String openIDArray = null;
		String openIDArrays = "";
		String test = "cancel";
		for(int i = 0;i<20;++i){
			openIDArray = ",{\"openid\":\"" + test + "\",\"lang\":\"zh-CN\"}";
			openIDArrays = openIDArrays + openIDArray ;
		}
		String jsonStr = "{\"user_list\": [" + openIDArrays + "]}";
		return jsonStr;
		
	}
}
