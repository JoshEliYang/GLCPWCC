package cn.springmvc.service.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.springmvc.utils.RedisUtil;
import com.springmvc.utils.RequestUtil;

import cn.springmvc.service.WechartService;

/**
 * 
 * @author johsnon
 *
 */
@Service
public class WechartServiceImpl implements WechartService {

	Logger logger = Logger.getLogger(WechartServiceImpl.class);

	// 测试账号
	// static String appID = "wx6953682e7b6eecdf";
	// static String appSercret = "d0c2bf8805a8bf8824f31830edde8750";

	// johsnon账号
	static String appID = "wx54ab9837e1967990";
	static String appSercret = "b83f38ad4f7401ca24a1f16fabb0dd98";

	// get token
	public String getAccessToken() throws Exception {
		// take access_token form redis first
		String key = "access_token";
		RedisUtil redis = RedisUtil.getRedis();
		String tokenRes = redis.getdat(key);
		if (tokenRes != null) {
			logger.error("access_token from redis: " + tokenRes);
			return tokenRes;
		}

		// request access_token from wechart
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appID + "&secret="
				+ appSercret;

		String response = RequestUtil.doGet(url);
		String token = ((Map<String, String>) JSON.parse(response)).get("access_token");

		redis.setdat(key, token);
		logger.error("access_token: " + token);
		return token;
	}

}
