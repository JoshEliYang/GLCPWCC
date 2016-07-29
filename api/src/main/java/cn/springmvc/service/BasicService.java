package cn.springmvc.service;

import java.util.List;

import cn.springmvc.model.BasicModel;

/**
 * 
 * @author johnson
 *
 */
public interface BasicService {
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
}
