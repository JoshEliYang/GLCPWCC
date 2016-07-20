package cn.springmvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.dao.BasicDao;
import cn.springmvc.model.BasicModel;
import cn.springmvc.service.BasicService;

/**
 * 
 * @author johnson
 *
 */
@Service
public class BasicServiceImpl implements BasicService {
	@Autowired
	private BasicDao dao;

	/**
	 * get all basic configuration
	 * 
	 * @return List<BasicModel>
	 * @throws Exception
	 */
	public List<BasicModel> getAll() throws Exception {
		return dao.getAll();
	}

	/**
	 * get basic configuration which is in using
	 * 
	 * @return BasicModel
	 * @throws Exception
	 */
	public BasicModel getInusing() throws Exception {
		return dao.getInusing();
	}
}
