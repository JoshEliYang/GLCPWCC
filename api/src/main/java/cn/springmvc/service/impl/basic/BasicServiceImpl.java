package cn.springmvc.service.impl.basic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.dao.BasicDao;
import cn.springmvc.model.BasicModel;
import cn.springmvc.service.basic.BasicService;

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

	/**
	 * delete basic model by id
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delete(int id) throws Exception {
		dao.delete(id);
	}

	/**
	 * set token server available or unavailable
	 * 
	 * @throws Exception
	 */
	public void setTokenServer(int id, boolean usingTokenServer) throws Exception {
		dao.setTokenServer(id, usingTokenServer);
	}
}
