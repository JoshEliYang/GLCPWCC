package cn.springmvc.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.dao.QrcodeDao;
import cn.springmvc.model.QrcodeModel;
import cn.springmvc.service.QrcodeService;



@Service
public class QrcodeServiceImpl implements QrcodeService {
	
	@Autowired
	private QrcodeDao dao;
	
	
	public List<QrcodeModel>getAll() throws Exception{
		return dao.getAll();
	}
	
	public Map<String, String>createQrcode(String name) throws Exception{
		return dao.createQrcode(name);
	}

}
