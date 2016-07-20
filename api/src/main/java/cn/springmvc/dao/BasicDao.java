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
	public BasicModel getInusing() throws Exception;
}
