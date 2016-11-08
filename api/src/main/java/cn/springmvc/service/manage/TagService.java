package cn.springmvc.service.manage;

import java.util.Map;

import cn.springmvc.model.BasicModel;

public interface TagService {

	public Map<String, String> createTag(String jsonStr, BasicModel model) throws Exception;

	public Map<String, String> delete(String jsonStr, BasicModel model) throws Exception;

	public Map<String, String> update(String jsonStr, BasicModel model) throws Exception;

	public Map<String, String> getAll(BasicModel model) throws Exception;

	/**
	 * query all tags and do filter
	 * 
	 * @author johnson
	 * @param model
	 * @param queryDat
	 * @return
	 * @throws Exception
	 */
	public String getAll(BasicModel model, String queryDat) throws Exception;

	public Map<String, String> getUserByTag(String jsonStr, BasicModel model) throws Exception;

	public Map<String, String> createTagAndQrcode(String jsonStr, BasicModel model) throws Exception;
}
