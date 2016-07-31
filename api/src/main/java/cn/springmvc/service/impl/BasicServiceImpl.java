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
	public List<BasicModel> getInusing() throws Exception {
		return dao.getInusing();
	}

	/**
	 * get basic configuration by id
	 * 
	 * @return
	 * @throws Exception
	 */
	public BasicModel getById(int id) throws Exception {
		return dao.getById(id);
	}

	/**
	 * get basic configuration by url
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public BasicModel getByUrl(String url) throws Exception {
		return dao.getByUrl(url);
	}

	/**
	 * insert basic configuration
	 */
	public void insert(BasicModel basicModel) throws Exception {
		dao.insert(basicModel);
	}

	/**
	 * set specific basic configuration in using
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void setUsing(int id, boolean isUsing) throws Exception {
		dao.setUsing(id, isUsing);
	}

	/**
	 * set specific basic configuration as default
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void setDefault(int id) throws Exception {
		dao.setDefault(id);
	}

	/**
	 * edit specific basic configuration
	 * 
	 * @param basicModel
	 * @throws Exception
	 */
	public void edit(BasicModel basicModel) throws Exception {
		dao.edit(basicModel);
	}
}
