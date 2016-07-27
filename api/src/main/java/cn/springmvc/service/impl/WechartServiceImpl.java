package cn.springmvc.service.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.springmvc.utils.RequestUtil;

import cn.springmvc.model.BasicModel;
import cn.springmvc.service.WechartService;

/**
 * 
 * @author johsnon
 *
 */
@Service
public class WechartServiceImpl implements WechartService {

	Logger logger = Logger.getLogger(WechartServiceImpl.class);

	// get access_token
	public String getAccessToken(BasicModel basicModel) throws Exception {
		// request access_token from wechart
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
				+ basicModel.getAppId() + "&secret=" + basicModel.getAppSecret();

		String response = null;
		String token = null;
		response = RequestUtil.doGet(url);
		token = ((Map<String, String>) JSON.parse(response)).get("access_token");

		logger.error("access_token: " + token);
		return token;
	}

}