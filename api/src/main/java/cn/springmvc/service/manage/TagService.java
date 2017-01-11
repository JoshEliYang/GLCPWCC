package cn.springmvc.service.manage;

import java.util.List;
import java.util.Map;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.TagDat;
import cn.springmvc.model.TagList;

public interface TagService {

	public Map<String, String> createTag(String jsonStr, BasicModel model) throws Exception;

	public Map<String, String> delete(String jsonStr, BasicModel model) throws Exception;

	public Map<String, String> update(String jsonStr, BasicModel model) throws Exception;

	public Map<String, String> getAll(BasicModel model) throws Exception;
	
	/**
	 * get all tags
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<TagDat> getTags(BasicModel model) throws Exception;

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

	public String addTag(TagList tl);
	
	public String deleteTag(TagList tl);
}
