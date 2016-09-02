package cn.springmvc.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.springmvc.utils.RequestUtil;

import cn.springmvc.dao.QrcodeDao;
import cn.springmvc.model.BasicModel;
import cn.springmvc.model.QrcodeModel;
import cn.springmvc.service.QrcodeService;



@Service
public class QrcodeServiceImpl implements QrcodeService {
	
	@Autowired
	private QrcodeDao dao;
	
	Logger logger = Logger.getLogger(QrcodeServiceImpl.class);
	
	public List<QrcodeModel>getAll(BasicModel basicModel) throws Exception{
		return dao.getAll(basicModel.getId());
	}
	
	public List<QrcodeModel> createQrcode(int basicId, long sceneId, String name, String accessToken) throws Exception{
//		String accessToken = service.getAccessToken(basicModel);
		String qrcodeCreateUrl  = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + accessToken;
		String jsonStr = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\":\"" + sceneId + "\"}}}";
		logger.error("jsonstr--" + jsonStr);
		String qrcodeResponse = RequestUtil.doPost(qrcodeCreateUrl, jsonStr);
		logger.error("qrcoderesponse--" + qrcodeResponse);
		Map<String, String> res = (Map<String, String>) JSON.parse(qrcodeResponse);
		QrcodeModel model = new QrcodeModel();
		model.setTicket(res.get("ticket"));
		model.setUrl(res.get("url"));
		model.setSceneid(sceneId);
		model.setName(name);
		model.setBasicId(basicId);
		return dao.createQrcode(model);
	}

}
