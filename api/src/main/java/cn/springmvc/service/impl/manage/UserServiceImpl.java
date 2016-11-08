package cn.springmvc.service.impl.manage;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.springmvc.utils.RequestUtil;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.WechatUser;
import cn.springmvc.service.manage.UserService;
import cn.springmvc.service.wechat.WechartService;

/**
 * 
 * @author johsnon
 *
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	public WechartService wechartService;

	Logger logger = Logger.getLogger(UserServiceImpl.class);

	// 获得所有用户分组
	public Map<String, String> getAll(BasicModel basicModel) throws Exception {
		String accessToken = wechartService.getAccessToken(basicModel);
		String url = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=" + accessToken;
		return (Map<String, String>) JSON.parse(RequestUtil.doGet(url));
	}

	// todo
	// 创建用户分组
	public Map<String, String> createGroup(String name, BasicModel basicModel) throws Exception {
		String accessToken = wechartService.getAccessToken(basicModel);
		String url = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=" + accessToken;
		String postJson = "{\"group\":{\"name\":\"" + name + "\"}}";
		return (Map<String, String>) JSON.parse(RequestUtil.doPost(url, postJson));
	}

	// 移动用户分组
	public boolean moveToGroup(String openId, String groupId, BasicModel basicModel) throws Exception {
		String accessToken = wechartService.getAccessToken(basicModel);
		String url = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=" + accessToken;
		String strJson = "{\"openid\":\"" + openId + "\",\"to_groupid\":\"" + groupId + "\"}";
		String response = RequestUtil.doPost(url, strJson);
		@SuppressWarnings("unchecked")
		Map<String, Object> res = (Map<String, Object>) JSON.parse(response);
		if (Integer.parseInt(res.get("errcode").toString()) != 0) {
			return false;
		}
		return true;
	}

	/**
	 * get user info
	 * 
	 * @author johnson
	 */
	public WechatUser getUserInfo(String openId, BasicModel basicModel) throws Exception {
		String accessToken = wechartService.getAccessToken(basicModel);
		String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + accessToken + "&openid=" + openId
				+ "&lang=zh_CN";
		String response = RequestUtil.doGet(url);
		WechatUser user = JSON.parseObject(response, WechatUser.class);
		return user;
	}

}
