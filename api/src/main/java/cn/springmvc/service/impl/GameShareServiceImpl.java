package cn.springmvc.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.springmvc.utils.RequestUtil;
import com.springmvc.utils.ShaUtil;

import cn.springmvc.service.WechartService;
import cn.springmvc.service.GameShareService;

@Service
public class GameShareServiceImpl implements GameShareService {

	@Autowired
	public WechartService wechatService;
	
	Logger logger = Logger.getLogger(GameShareServiceImpl.class);
	
	public Map<String, String> getTicket(String url) throws Exception {
		logger.error("success to impl !");
		String response = RequestUtil.doGet("http://app.glcp.com.cn/g.manage/rdp/business/webservice/WeChatBizService.htm?method=getAccessToken&appid=wx7be611040e7ea590&secret=88faf705aabebda0fd877a2f73034ef1");
		Map<String, Object> tokenResult = (Map<String, Object>) JSON.parse(response);
		String accessToken = (String) tokenResult.get("access_token");
		logger.error("accessToken--"+accessToken);
		String ticketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken + "&type=jsapi";
		String ticketJson = RequestUtil.doGet(ticketUrl);
		logger.error("ticketJson--"+ticketJson);
		Map<String, String> res = (Map<String, String>) JSON.parse(ticketJson);
		String ticket = res.get("ticket");
		logger.error("ticket--"+ticket);
		String noncestr = UUID.randomUUID().toString();
		logger.error("noncestr--"+noncestr);
		String timestamp = Long.toString(System.currentTimeMillis() / 1000);
		logger.error("timestamp--"+timestamp);
		String str = "jsapi_ticket=" + ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" +url;
//		HashMap<String, String> hash = new HashMap<String, String>();
//		hash.put("ticket", ticket);
//		hash.put("noncestr", noncestr);
//		hash.put("timestamp", timestamp);
//		hash.put("url", url);
//		Collection<String> key = hash.keySet();
//		List<String> list = new ArrayList<String>(key);
//		Collections.sort(list);
//		String str = null;
//		for(int i=0; i<list.size(); i++){
//			str = str + list.get(i) + hash.get(list.get(i));
//		}
		logger.error("String ticket+noncestr+timestamp+url--"+str);
		String signature = ShaUtil.encryptSHA(str);
		logger.error("signature--"+signature);
		Map<String, String> result = new HashMap<String, String>();
		result.put("signature", signature);
		result.put("noncestr", noncestr);
		result.put("timestamp", timestamp);
		logger.error("result--"+result);
		return result;
	}

}
