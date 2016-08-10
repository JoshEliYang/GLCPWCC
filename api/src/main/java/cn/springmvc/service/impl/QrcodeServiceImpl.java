package cn.springmvc.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.springmvc.utils.RequestUtil;

import cn.springmvc.dao.QrcodeDao;
import cn.springmvc.model.BasicModel;
import cn.springmvc.model.QrcodeModel;
import cn.springmvc.service.QrcodeService;
import cn.springmvc.service.WechartService;



@Service
public class QrcodeServiceImpl implements QrcodeService {
	
	@Autowired
	private QrcodeDao dao;
	public WechartService service;
	
	
	public List<QrcodeModel>getAll() throws Exception{
		return dao.getAll();
	}
	
	public List<QrcodeModel> createQrcode(long ID, BasicModel basicModel) throws Exception{
		String accessToken = service.getAccessToken(basicModel);
		String qrcodeCreateUrl  = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + accessToken;
//		String jsonStr = "{\"group\":{\"name\":\"" + ID + "\"}}";
		String jsonStr = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\":\"" + ID + "\"}}}";
		String qrcodeResponse = RequestUtil.doPost(qrcodeCreateUrl, jsonStr);
		Map<String, String> res = (Map<String, String>) JSON.parse(qrcodeResponse);
		QrcodeModel model = null;
		model.setTicket(res.get("ticket"));
		model.setUrl(res.get("url"));
		model.setSceneid(ID);
//		model.setName();
		return dao.createQrcode();	
	}

}
