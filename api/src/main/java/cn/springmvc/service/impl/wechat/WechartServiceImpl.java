package cn.springmvc.service.impl.wechat;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.springmvc.utils.RequestUtil;

import cn.springmvc.model.BasicModel;
import cn.springmvc.service.wechat.WechartService;

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
		if (basicModel.isUsingTokenServer()) {
			return getAccessTokenByServer(basicModel);
		}

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

	public String getAccessTokenByServer(BasicModel basicModel) throws Exception {
		String response = RequestUtil.doGet(basicModel.getTokenServer());
		Map<String, Object> result = (Map<String, Object>) JSON.parse(response);
		return (String) result.get("access_token");
	}

}
