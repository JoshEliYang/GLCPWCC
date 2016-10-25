package cn.springmvc.service.manage;

import java.util.List;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.QrcodeModel;

public interface QrcodeService {

	public List<QrcodeModel> getAll(BasicModel basicModel) throws Exception;

	public List<QrcodeModel> createQrcode(int basicId, long sceneId, String name, String accessToken) throws Exception;

	/**
	 * query QrCodes by given keywords
	 * 
	 * @author johnson
	 * @param basicModel
	 * @param queryDat
	 * @return
	 * @throws Exception
	 */
	public List<QrcodeModel> getAll(BasicModel basicModel, String queryDat) throws Exception;

}
