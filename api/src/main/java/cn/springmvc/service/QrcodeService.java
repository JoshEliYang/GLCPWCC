package cn.springmvc.service;

import java.util.List;
import java.util.Map;

import cn.springmvc.model.QrcodeModel;

public interface QrcodeService {
	
	public List<QrcodeModel>getAll() throws Exception;
	
	public Map<String, String> createQrcode(String name) throws Exception;
	
	

}
