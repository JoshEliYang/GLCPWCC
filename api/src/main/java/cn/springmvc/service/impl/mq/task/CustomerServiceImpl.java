package cn.springmvc.service.impl.mq.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.springmvc.utils.RequestUtil;

import cn.springmvc.dao.CustomerDao;
import cn.springmvc.model.BasicModel;
import cn.springmvc.model.TaskResponse;
import cn.springmvc.model.User;
import cn.springmvc.model.report.Customer;
import cn.springmvc.service.mq.ProducerService;
import cn.springmvc.service.mq.task.CustomerService;
import cn.springmvc.service.wechat.WechartService;

@Service
public class CustomerServiceImpl implements CustomerService {
	User admin;
	String taskTimestamp;

	Logger logger = Logger.getLogger(CustomerServiceImpl.class);

	@Autowired
	WechartService wewchatService;
	@Autowired
	CustomerDao customerDao;
	@Autowired
	private ProducerService mqProducer;

	@SuppressWarnings({ "unchecked" })
	public void send(User admin, BasicModel basicModel, String taskTimestamp) throws Exception {
		this.admin = admin;
		this.taskTimestamp = taskTimestamp;

		try {
			sendMessage("刷新用户信息", 0, 100, true);
			String next_openid = null;
			Map<String, Object> resJson;
			int count;
			int total;
			Map<String, ArrayList<String>> data;
			List<String> openid_list = new ArrayList<String>();

			for (int i = 0;; i++) {
				resJson = getOpenid(wewchatService.getAccessToken(basicModel), next_openid);
				count = (Integer) resJson.get("count");
				if (count == 0) {
					break;
				}

				total = (Integer) resJson.get("total");
				next_openid = (String) resJson.get("next_openid");
				data = (Map<String, ArrayList<String>>) resJson.get("data");
				sendMessage("next_openid>>>>" + next_openid + ">>>>>>count>>>>>" + count, 10000 * (i + 1) + count,
						total, true);
				openid_list.addAll(data.get("openid"));
				logger.error("openid_list_size" + openid_list.size());

			}
			List<Map<String, Object>> user_info_list = new ArrayList<Map<String, Object>>();
			// Map<String, List<Customer>> user_info_list_json = new
			// HashMap<String, List<Customer>>();
			Map<String, Object> user_info_list_json;
			List<String> per_openid_list = new ArrayList<String>();
			for (int i = 0; i <= openid_list.size(); i++) {
				try {
					per_openid_list.add(openid_list.get(i));
					if (i % 1000 == 0)
						sendMessage("刷新用户信息", i, openid_list.size(), true);
					if (per_openid_list.size() >= 100) {
						user_info_list_json = getUserInfo(basicModel, per_openid_list);
						user_info_list = (List<Map<String, Object>>) user_info_list_json.get("user_info_list");
						refreshUserInfo(user_info_list);
						per_openid_list.clear();
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("error occurred in customer refresh circulation >>>> openidList="
							+ JSON.toJSONString(user_info_list));
				}

			}
			user_info_list_json = getUserInfo(basicModel, per_openid_list);
			user_info_list = (List<Map<String, Object>>) user_info_list_json.get("user_info_list");
			refreshUserInfo(user_info_list);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error occurred in customer refresh >>> " + e.getMessage() + e.getStackTrace());
			throw e;
		}
	}

	public void refreshUserInfo(List<Map<String, Object>> user_info_list) {
		for (int i = 0; i < user_info_list.size(); i++) {
			Customer user_info = new Customer();
			int subscribe = (Integer) user_info_list.get(i).get("subscribe");
			if (subscribe == 0)
				continue;

			user_info.setSubscribe(subscribe);
			user_info.setOpenid((String) user_info_list.get(i).get("openid"));
			user_info.setNickname((String) user_info_list.get(i).get("nickname"));
			user_info.setSex((Integer) user_info_list.get(i).get("sex"));
			user_info.setLanguage((String) user_info_list.get(i).get("language"));
			user_info.setCity((String) user_info_list.get(i).get("city"));
			user_info.setProvince((String) user_info_list.get(i).get("province"));
			user_info.setCountry((String) user_info_list.get(i).get("country"));
			user_info.setHeadimgurl((String) user_info_list.get(i).get("headimgurl"));
			user_info.setSubscribe_time((Integer) user_info_list.get(i).get("subscribe_time"));
			user_info.setUnionid((String) user_info_list.get(i).get("unionid"));
			user_info.setRemark((String) user_info_list.get(i).get("remark"));
			user_info.setGroupid((Integer) user_info_list.get(i).get("groupid"));
			// user_info.setTagid_list((String)user_info_list.get(i).get("tagid_list"));
			Customer result = customerDao.select((String) user_info_list.get(i).get("openid"));
			try {
				if (result == null) {
					customerDao.insert(user_info);
				} else {
					customerDao.update(user_info);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("error occurred when insert customer into DB >>>> customer: " + user_info);
			}
		}
	}

	private void sendMessage(String message, int progress, int max, boolean isRunning) {
		TaskResponse taskMessage = new TaskResponse(admin.getId(), taskTimestamp, "微信用户刷新任务", message, isRunning,
				progress, max);
		mqProducer.sendToBroadcast(taskMessage);
	}

	private Map<String, Object> getOpenid(String accessToken, String next_openid)
			throws ClientProtocolException, IOException {
		String user_url;
		if (next_openid == null) {
			user_url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + accessToken;
		} else {
			user_url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + accessToken + "&next_openid="
					+ next_openid;
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> resJson = (Map<String, Object>) JSON.parse(RequestUtil.doGet(user_url));
		return resJson;
	}

	public Map<String, Object> getUserInfo(BasicModel basicModel, List<String> openid_list) throws Exception {
		String accessToken = wewchatService.getAccessToken(basicModel);

		String info_url = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token="
				+ wewchatService.getAccessToken(basicModel);
		ArrayList<Map<String, String>> user_list = new ArrayList<Map<String, String>>();
		Map<String, ArrayList<Map<String, String>>> jsonStr = new HashMap<String, ArrayList<Map<String, String>>>();
		// Map<String, List<Customer>> user_info_list_json = new HashMap<String,
		// List<Customer>>();
		Map<String, Object> user_info_list_json = new HashMap<String, Object>();
		for (int j = 0; j < openid_list.size(); j++) {
			Map<String, String> user = new HashMap<String, String>();
			user.put("openid", openid_list.get(j));
			user.put("lang", "zh-CN");
			user_list.add(user);
		}
		jsonStr.put("user_list", user_list);
		String str = RequestUtil.doPost(info_url, JSON.toJSONString(jsonStr));
		user_info_list_json = (Map<String, Object>) JSON.parse(str);
		return user_info_list_json;
	}

}
