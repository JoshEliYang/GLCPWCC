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
	public BasicModel getInusing() throws Exception;
}
