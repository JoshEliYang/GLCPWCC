package cn.springmvc.service.mq.task;

import java.util.List;
import java.util.Map;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.User;

public interface CustomerService {
	public void send(User admin,BasicModel basicModel, String taskTimestamp);
	
	public void refreshUserInfo(List<Map<String, Object>> user_info_list);
	
	public Map<String, Object> getUserInfo(BasicModel basicModel,List<String> openid_list) throws Exception;
}
