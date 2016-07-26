package cn.springmvc.service.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.springmvc.utils.RequestUtil;

import cn.springmvc.model.BasicModel;
import cn.springmvc.service.MenuService;
import cn.springmvc.service.WechartService;

@Service
public class MenuServiceImpl implements MenuService {
	@Autowired
	public WechartService wechartService;

	Logger logger = Logger.getLogger(MenuServiceImpl.class);

	// 设置菜单
	public boolean setMenu(String jsonStr, BasicModel basicModel) throws Exception {
		String accessToken = wechartService.getAccessToken(basicModel);
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken;

		String response = RequestUtil.doPost(url, jsonStr);

		logger.error("set menu response message : >>> " + response);
		Map<String, Object> res = (Map<String, Object>) JSON.parse(response);
		logger.error("after fastJson parse <<< " + res);
		if (Integer.parseInt(res.get("errcode").toString()) == 0) {
			return true;
		}
		return false;
	}

}
