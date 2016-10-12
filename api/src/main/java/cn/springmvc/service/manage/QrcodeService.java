package cn.springmvc.service.manage;

import java.util.List;
import java.util.Map;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.QrcodeModel;

public interface QrcodeService {
	
	public List<QrcodeModel>getAll(BasicModel basicModel) throws Exception;
	
	public List<QrcodeModel> createQrcode(int basicId, long sceneId, String name, String accessToken) throws Exception;
	
	

}
