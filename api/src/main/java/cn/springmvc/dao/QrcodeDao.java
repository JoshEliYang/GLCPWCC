package cn.springmvc.dao;

import java.util.List;
import java.util.Map;

import cn.springmvc.model.QrcodeModel;

public interface QrcodeDao {
	public List<QrcodeModel> getAll() throws Exception;

	public Map<String, String> createQrcode(String name) throws Exception;
}
