package cn.springmvc.service;

import java.util.List;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.Keywords;

public interface KeywordsService {
	/**
	 * get all keywords and reply message
	 * 
	 * @return List<Keywords>
	 * @throws Exception
	 */
	public List<Keywords> getAll(BasicModel basicModel) throws Exception;

	/**
	 * insert keyword
	 * 
	 * @param keyword
	 * @throws Exception
	 */
	public void insert(Keywords keyword) throws Exception;

	/**
	 * get subscribe reply message
	 * 
	 * @return Keywords
	 * @throws Exception
	 */
	public Keywords getSubscribeReply(BasicModel basicModel) throws Exception;
}
