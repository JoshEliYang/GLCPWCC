package cn.springmvc.service.impl;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.springmvc.utils.MemcacheUtil;
import com.springmvc.utils.RequestUtil;

import cn.springmvc.Consts;
import cn.springmvc.service.WechartService;

/**
 * 
 * @author johsnon
 *
 */
@Service
public class WechartServiceImpl implements WechartService {

	Logger logger = Logger.getLogger(WechartServiceImpl.class);

	// get token
	public String getAccessToken() {
		// take access_token form redis first
		String key = "access_token";
		// RedisUtil redis = RedisUtil.getRedis();
		// String tokenRes = redis.getdat(key);

		MemcacheUtil memcache = null;
		try {
			memcache = MemcacheUtil.getInstance();
			String tokenRes = memcache.getDat(key, String.class);

			if (tokenRes != null) {
				logger.error("access_token from memcache: " + tokenRes);

				memcache.destory();
				return tokenRes;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("get memcache error >>> " + e.getStackTrace());
		}

		// request access_token from wechart
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + Consts.APP_ID
				+ "&secret=" + Consts.APP_SERCRET;

		String response = null;
		String token = null;
		try {
			response = RequestUtil.doGet(url);
			token = ((Map<String, String>) JSON.parse(response)).get("access_token");
			memcache.setDat(key, token);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("insert memcache error >>> " + e.getStackTrace());
		} finally {
			try {
				memcache.destory();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("memcache close error >>> " + e.getStackTrace());
			}
		}

		// redis.setdat(key, token);
		logger.error("access_token: " + token);
		return token;
	}

}
