package cn.springmvc.dao;

import java.util.List;

import cn.springmvc.model.QrcodeModel;

public interface QrcodeDao {
	public List<QrcodeModel> getAll(int basicId) throws Exception;

	/**
	 * query in DB
	 * 
	 * @author johnson
	 * @param basicId
	 * @param queryDat
	 * @return
	 * @throws Exception
	 */
	public List<QrcodeModel> query(int basicId, String queryDat) throws Exception;

	public List<QrcodeModel> createQrcode(QrcodeModel model) throws Exception;
}
