package cn.springmvc.service.impl.mq.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.springmvc.utils.RequestUtil;

import cn.springmvc.dao.CustomerDao;
import cn.springmvc.model.BasicModel;
import cn.springmvc.model.TaskResponse;
import cn.springmvc.model.User;
import cn.springmvc.model.report.Customer;
import cn.springmvc.service.mq.task.CustomerService;
import cn.springmvc.service.wechat.WechartService;
import cn.springmvc.websocket.ProgressSocket;

public class CustomerServiceImpl implements CustomerService {
	User admin;
	String taskTimestamp;

	Logger logger = Logger.getLogger(CustomerServiceImpl.class);
	
	@Autowired
	WechartService wewchatService;
	@Autowired
	CustomerDao customerDao;
	
	@SuppressWarnings({ "unchecked", "null" })
	public void send(User admin,BasicModel basicModel, String taskTimestamp) {
		this.admin = admin;
		this.taskTimestamp = taskTimestamp;
		
		try {
			sendMessage("刷新用户信息", 0, 100, true);
//			String user_url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + wewchatService.getAccessToken(basicModel);
			Map<String, Object> resJson = getOpenid(wewchatService.getAccessToken(basicModel), null);
			logger.error("resJson>>>>>>>>>>>>>>" + resJson);
			int total = (Integer) resJson.get("total");
			logger.error("total>>>>>>>>>" + total);
			String next_openid = (String) resJson.get("next_openid");
			Map<String, Map<String,ArrayList<String>>> data = (Map<String, Map<String, ArrayList<String>>>) resJson.get("data");
			logger.error("data>>>>>>>>>>>" + data);
			List<String> openid_list = (List<String>) data.get("openid");
			logger.error("openid_list>>>>>>>>>>>>>>>" + openid_list);
			sendMessage("获取用户openid列表", 1000000/total, 100, true);
//			for(int i = 1; i < total/10000; i++){
//				resJson = getOpenid(wewchatService.getAccessToken(basicModel), next_openid);
//				logger.error("progress>>>>>>>>>>>>" + 1000000*(i+1)/total);
//				sendMessage("获取用户openid列表", 1000000*(i+1)/total, 100, true);	
//				next_openid = (String) resJson.get("next_openid");
//				data = (Map<String, Map<String, ArrayList<String>>>) resJson.get("data");
//				openid_list.addAll((Collection<? extends String>) data.get("openid"));
//				logger.error("openid_list_size" + openid_list.size());
//				
//			};
			
			List<Customer> user_info_list = new ArrayList<Customer>();
			Map<String, Object> user_info_list_json = new HashMap<String, Object>();
			int temp = 0;
			List<String> per_openid_list = null;
			for(int i = 0; i <= openid_list.size() / 100; i ++){
				per_openid_list.add(openid_list.get(temp*100+i));
				sendMessage("刷新用户信息", i, openid_list.size(), true);
				if(per_openid_list.size() >= 100){
					user_info_list_json = getUserInfo(basicModel, per_openid_list);
					temp++;
					user_info_list = (List<Customer>) user_info_list_json.get("user_info_list");
					refreshUserInfo(user_info_list);
					per_openid_list.clear();
					break;
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error occurred in VoucheBindingTask >>> " + e.getMessage() + e.getStackTrace());
		}
	}
	
	private void refreshUserInfo(List<Customer> user_info_list){
		for(int i = 0; i < user_info_list.size(); i ++){
			Customer result = (Customer) customerDao.select(user_info_list.get(i).getOpenid());
			if(result.getOpenid() == ""){
				customerDao.insert(user_info_list.get(i));
			}else {
				customerDao.update(user_info_list.get(i));
			}
		}
	}
	
	private void sendMessage(String message, int progress, int max, boolean isRunning) {
		TaskResponse taskMessage = new TaskResponse(admin.getId(), taskTimestamp, "微信用户刷新任务", message, isRunning,
				progress, max);
		ProgressSocket.broadcast(taskMessage);
	}
	
	private Map<String, Object> getOpenid(String accessToken, String next_openid) throws ClientProtocolException, IOException {
		String user_url;
		if(next_openid == null){
			user_url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + accessToken;
		} else {
			user_url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + accessToken + "&next_openid=" + next_openid;
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> resJson = (Map<String, Object>) JSON.parse(RequestUtil.doGet(user_url));
		return resJson;
	}
	
	private Map<String, Object> getUserInfo(BasicModel basicModel,List<String> openid_list) throws Exception{
		String info_url = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=" + wewchatService.getAccessToken(basicModel);
		ArrayList<Map<String, String>> user_list = new ArrayList<Map<String,String>>();
		Map<String, Object> jsonStr = new HashMap<String, Object>();
		Map<String, Object> user_info_list_json = new HashMap<String, Object>();
		for(int j = 0; j < 100; j ++){
			Map<String, String> user = new HashMap<String, String>();
			user.put("openid", openid_list.get(j));
			user.put("lang", "zh-CN");
			user_list.add(user);
			logger.error("user_list>>>>>>>>>>>>>>>>>" + user_list);
		}
		logger.error("user_list>>>>>>>>>>>>>>>>>" + user_list);
		jsonStr.put("user_list", user_list);
		logger.error("jsonStr>>>>>>>>>>>>>>>>>" + jsonStr);
		user_info_list_json = (Map<String, Object>) JSON.parse(RequestUtil.doPost(info_url, JSON.toJSONString(jsonStr)));
		return user_info_list_json;
	}

}
