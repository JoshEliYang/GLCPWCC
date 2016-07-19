package cn.springmvc.service.impl;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.springmvc.utils.MemcacheUtil;
import com.springmvc.utils.RequestUtil;

import cn.springmvc.Consts;
import cn.springmvc.service.RefreshService;

/**
 * 
 * @author johsnon
 *
 */
public class RefreshServiceImpl implements RefreshService {

	Logger logger = Logger.getLogger(RefreshServiceImpl.class);

	public void cacheRefresh() {
		// System.out.println("test OK");

		String key = "access_token";

		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + Consts.APP_ID
				+ "&secret=" + Consts.APP_SERCRET;

		MemcacheUtil memcache = null;
		String response = null;
		String token = null;
		try {
			memcache = MemcacheUtil.getInstance();
			response = RequestUtil.doGet(url);
			token = ((Map<String, String>) JSON.parse(response)).get("access_token");
			memcache.setDat(key, token);
			logger.error("insert memcache success <<< "+token);
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
	}
}
