package cn.springmvc.dao;

import java.util.List;

import cn.springmvc.model.BasicModel;

public interface BasicDao {
	/**
	 * get all basic configuration
	 * 
	 * @return List<BasicModel>
	 * @throws Exception
	 */
	public List<BasicModel> getAll() throws Exception;

	/**
	 * get basic configuration which is in using
	 * 
	 * @return BasicModel
	 * @throws Exception
	 */
	public List<BasicModel> getInusing() throws Exception;

	/**
	 * get basic configuration by id
	 * 
	 * @return
	 * @throws Exception
	 */
	public BasicModel getById(int id) throws Exception;

	/**
	 * get basic configuration by url
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public BasicModel getByUrl(String url) throws Exception;

	/**
	 * insert basic configuration
	 */
	public void insert(BasicModel basicModel) throws Exception;

	/**
	 * set specific basic configuration in using
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void setUsing(int id, boolean isUsing) throws Exception;

	/**
	 * set specific basic configuration as default
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void setDefault(int id) throws Exception;

	/**
	 * edit specific basic configuration
	 * 
	 * @param basicModel
	 * @throws Exception
	 */
	public void edit(BasicModel basicModel) throws Exception;
}
