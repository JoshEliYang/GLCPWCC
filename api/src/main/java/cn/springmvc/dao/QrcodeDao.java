package cn.springmvc.dao;

import java.util.List;

import cn.springmvc.model.QrcodeModel;

public interface QrcodeDao {
	public List<QrcodeModel> getAll(int basicId) throws Exception;

	public List<QrcodeModel> createQrcode(QrcodeModel model) throws Exception;
}
