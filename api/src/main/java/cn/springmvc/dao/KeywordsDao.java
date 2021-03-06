package cn.springmvc.dao;

import java.util.List;

import cn.springmvc.model.BasicModel;
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
	 * get all keywords and reply message (except unused keywords)
	 * 
	 * @return List<Keywords>
	 * @throws Exception
	 */
	public List<Keywords> getAllExceptUnused(int basicId) throws Exception;

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
	public List<Keywords> getSubscribeReply(int basicId) throws Exception;

	/**
	 * set inUsing
	 * 
	 * @param keyword
	 * @throws Exception
	 */
	public void setInUsing(Keywords keyword) throws Exception;

	/**
	 * set subscribe inUsing
	 * 
	 * @param keyword
	 * @throws Exception
	 */
	public void subscribeSetInUsing(Keywords keyword) throws Exception;

	/**
	 * get subscribe reply
	 * 
	 * @param basicModel
	 * @return
	 * @throws Exception
	 */
	public Keywords getSubscribe(int basicId) throws Exception;

	/**
	 * edit keyword and auto-reply message
	 * 
	 * @param keyword
	 * @throws Exception
	 */
	public void edit(Keywords keyword) throws Exception;

	/**
	 * delete keyword
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delete(int id) throws Exception;
}
