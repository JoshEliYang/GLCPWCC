package cn.springmvc.service.impl.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.springmvc.utils.RequestUtil;

import cn.springmvc.service.report.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	Logger logger = Logger.getLogger(CustomerServiceImpl.class);
	
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> get(String accessToken) throws Exception {
		String user_url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + accessToken;
//		String response = RequestUtil.doGet(user_url);
//		Object res = JSON.parse(RequestUtil.doGet(user_url));
		Map<String, Object> resJson = (Map<String, Object>) JSON.parse(RequestUtil.doGet(user_url));
		logger.error("resJson>>>>>>>>>>>>>>" + resJson);
		int total = (Integer) resJson.get("total");
		logger.error("total>>>>>>>>>" + total);
		String next_openid = (String) resJson.get("next_openid");
		Map<String, Map<String,ArrayList<String>>> data = (Map<String, Map<String, ArrayList<String>>>) resJson.get("data");
		logger.error("data>>>>>>>>>>>" + data);
		List<String> openid_list = (List<String>) data.get("openid");
		logger.error("openid_list>>>>>>>>>>>>>>>" + openid_list);
//		for(int i = 1; i < total/10000; i++){
//			resJson = (Map<String, Object>) JSON.parse(RequestUtil.doGet(user_url + "&next_openid=" + next_openid));
//			next_openid = (String) resJson.get("next_openid");
//			data = (Map<String, Object>) resJson.get("data");
//			openid_list.addAll((ArrayList<String>) data.get("openid"));
//		};
		
		String info_url = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=" + accessToken;
		ArrayList<Map<String, String>> user_list = new ArrayList<Map<String,String>>();
		Map<String, Object> jsonStr = new HashMap<String, Object>();
		Map<String, Object> user_info_list_json = new HashMap<String, Object>();
		List<Map<String, String>> user_info_list = new ArrayList<Map<String,String>>();
		for(int j = 0; j < 100; j ++){
			Map<String, String> user = new HashMap<String, String>();
			user.put("openid", openid_list.get(j));
			user.put("lang", "zh-CN");
			user_list.add(user);
			logger.error("user_list>>>>>>>>>>>>>>>>>" + user_list);
//			logger.error("user>>>>>>>>>>>" + user);
		}
		logger.error("user_list>>>>>>>>>>>>>>>>>" + user_list);
		jsonStr.put("user_list", user_list);
		logger.error("jsonStr>>>>>>>>>>>>>>>>>" + jsonStr);
		user_info_list_json = (Map<String, Object>) JSON.parse(RequestUtil.doPost(info_url, JSON.toJSONString(jsonStr)));
		logger.error("user_info_list_json>>>>>>>>>>>>>>"+user_info_list_json);
		user_info_list = (List<Map<String, String>>) user_info_list_json.get("user_info_list");
		logger.error("user_info_list>>>>>>>>>>>>>>>>>>>" + user_info_list);
//		for(int i = 1; i <= openid_list.size() / 100; i ++){
//			for(int j = 0; j < 100*i; j ++){
//				user.put("openid", openid_list.get(j));
//				user.put("lang", "zh-CN");
//				user_list.add(user);
//				user.clear();
//			}
//			jsonStr.put("user_list", user_list);
//			user_info_list_json = (Map<String, Object>) JSON.parse(RequestUtil.doPost(info_url, JSON.toJSONString(jsonStr)));
//			user_info_list = (ArrayList<Map<String, String>>) user_info_list_json.get("user_info_list");
//		}
		return user_info_list;
	}

}
