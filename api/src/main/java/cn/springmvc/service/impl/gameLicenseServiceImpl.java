package cn.springmvc.service.impl;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.springmvc.utils.RequestUtil;

import cn.springmvc.service.gameLicenseService;


@Service
public class gameLicenseServiceImpl implements gameLicenseService {
	
	Logger logger = Logger.getLogger(gameLicenseServiceImpl.class);
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getUserInfo(String code) throws ClientProtocolException, IOException {
		String codeUrl = "https://api.weixin.qq.com/sns/oauth2/access_token"
				+ "?appid=wx7be611040e7ea590&secret=88faf705aabebda0fd877a2f73034ef1&code="+ 
				code + "&grant_type=authorization_code";
		logger.error("codeurl--" + code);
		String jsonToken = RequestUtil.doGet(codeUrl);
		logger.error("json to get code --" + jsonToken);
		Map<String, String> infoToken = (Map<String, String>) JSON.parse(jsonToken);
		logger.error("string to get code--" + infoToken);
		String accessToken = infoToken.get("access_token");
		logger.error("accesstoken--" + accessToken);
		String openID = infoToken.get("openid");
		logger.error("openid--" + openID);
		String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + 
				"&openid=" + openID +"&lang=zh_CN";
		String userInfo = RequestUtil.doGet(infoUrl);
		logger.error("userinfo--" + userInfo);
		return (Map<String, String>) JSON.parse(userInfo);
	}

}
