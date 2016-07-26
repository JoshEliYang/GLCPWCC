package cn.springmvc.dao;

import java.util.List;

import cn.springmvc.model.Keywords;

/**
 * 
 * @author johnson
 *
 */
public interface KeywordsDao {
	/**
	 * get all keywords and reply message
	 * 
	 * @return List<Keywords>
	 * @throws Exception
	 */
	public List<Keywords> getAll(int basicId) throws Exception;

	/**
	 * get subscribe reply message
	 * 
	 * @return Keywords
	 * @throws Exception
	 */
	public Keywords getSubscribeReply(int basicId) throws Exception;
}
