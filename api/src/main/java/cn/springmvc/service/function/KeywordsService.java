package cn.springmvc.service.function;

import java.util.List;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.Keywords;

public interface KeywordsService {
	/**
	 * get all keywords and reply message (only inUsing)
	 * 
	 * @return List<Keywords>
	 * @throws Exception
	 */
	public List<Keywords> getAll(BasicModel basicModel) throws Exception;

	/**
	 * get all keywords and reply message
	 * 
	 * @return List<Keywords>
	 * @throws Exception
	 */
	public List<Keywords> getAll(BasicModel basicModel, boolean inUsing) throws Exception;

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
	public List<Keywords> getSubscribeReply(BasicModel basicModel) throws Exception;

	/**
	 * insert subscribe reply
	 * 
	 * @param keyword
	 * @throws Exception
	 */
	public void insertSubscribe(Keywords keyword) throws Exception;

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
	public Keywords getSubscribe(BasicModel basicModel) throws Exception;

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
