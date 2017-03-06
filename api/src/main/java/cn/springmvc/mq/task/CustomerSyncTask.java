package cn.springmvc.mq.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.springmvc.utils.RequestUtil;
import com.sun.tools.javac.code.Attribute.Array;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.User;

public class CustomerSyncTask {
	User admin;
	String taskTimestamp;
	
	Logger logger = Logger.getLogger(CustomerSyncTask.class);
	
	public CustomerSyncTask(User admin, String taskTimestamp){
		this.admin = admin;
		this.taskTimestamp = taskTimestamp;
	}
	
	public void run(){
		
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Map<String, String>> doGet(String accessToken) throws ClientProtocolException, IOException{
		String user_url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + accessToken;
		String response = RequestUtil.doGet(user_url);
		Map<String, Object> resJson = (Map<String, Object>) JSON.parse(RequestUtil.doGet(user_url));
		int total = (Integer) resJson.get("total");
		String next_openid = (String) resJson.get("next_openid");
		Map<String, Object> data = (Map<String, Object>) resJson.get("data");
		ArrayList<String> openid_list = (ArrayList<String>) data.get("openid");
		for(int i = 1; i < total/10000; i++){
			resJson = (Map<String, Object>) JSON.parse(RequestUtil.doGet(user_url + "&next_openid=" + next_openid));
			next_openid = (String) resJson.get("next_openid");
			data = (Map<String, Object>) resJson.get("data");
			openid_list.addAll((ArrayList<String>) data.get("openid"));
		};
		
		String info_url = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=" + accessToken;
		ArrayList<Map<String, String>> user_list = new ArrayList<Map<String,String>>();
		Map<String, String> user = new HashMap<String, String>();
		Map<String, Object> jsonStr = new HashMap<String, Object>();
		Map<String, Object> user_info_list_json = new HashMap<String, Object>();
		ArrayList<Map<String, String>> user_info_list = new ArrayList<Map<String,String>>();
		for(int i = 1; i <= openid_list.size() / 100; i ++){
			for(int j = 0; j < 100*i; j ++){
				user.put("openid", openid_list.get(j));
				user.put("lang", "zh-CN");
				user_list.add(user);
				user.clear();
			}
			jsonStr.put("user_list", user_list);
			user_info_list_json = (Map<String, Object>) JSON.parse(RequestUtil.doPost(info_url, JSON.toJSONString(jsonStr)));
			user_info_list = (ArrayList<Map<String, String>>) user_info_list_json.get("user_info_list");
		}
		return user_info_list;
	}
}
