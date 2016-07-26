package cn.springmvc.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.springmvc.utils.RequestUtil;

import cn.springmvc.model.BasicModel;
import cn.springmvc.service.MediaService;
import cn.springmvc.service.WechartService;

/**
 * 
 * @author johsnon
 *
 */
@Service
public class MediaServiceImpl implements MediaService {
	@Autowired
	public WechartService wechartService;

	Logger logger = Logger.getLogger(MediaServiceImpl.class);

	// 获得图文信息
	public Map<String, List<Map<String, String>>> getNews(String mediaId, BasicModel basicModel) throws Exception {
		String access_token = wechartService.getAccessToken(basicModel);
		String url = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=" + access_token;
		String media = "{\"media_id\":\"" + mediaId + "\"}";

		String response = null;
		response = RequestUtil.doPost(url, media);
		// @SuppressWarnings("unchecked")
		// Map<String,String> newsItem=(Map<String, String>)
		// JSON.parse(response);
		// @SuppressWarnings("unchecked")
		// List<News> list = (List<News>) JSON.parse(newsItem.get("news_item"));
		@SuppressWarnings("unchecked")
		Map<String, List<Map<String, String>>> mapx = (Map<String, List<Map<String, String>>>) JSON.parse(response);

		return mapx;
	}

}
