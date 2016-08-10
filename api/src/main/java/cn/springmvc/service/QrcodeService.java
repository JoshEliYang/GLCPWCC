package cn.springmvc.service;

import java.util.List;
import java.util.Map;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.QrcodeModel;

public interface QrcodeService {
	
	public List<QrcodeModel>getAll() throws Exception;
	
	public List<QrcodeModel> createQrcode(long ID, BasicModel basicModel) throws Exception;
	
	

}
