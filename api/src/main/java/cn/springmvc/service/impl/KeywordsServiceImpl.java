package cn.springmvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.dao.KeywordsDao;
import cn.springmvc.model.BasicModel;
import cn.springmvc.model.Keywords;
import cn.springmvc.service.KeywordsService;

/**
 * 
 * @author johnson
 *
 */
@Service
public class KeywordsServiceImpl implements KeywordsService {
	@Autowired
	private KeywordsDao dao;

	/**
	 * get all keywords and reply message (only inUsing)
	 * 
	 * @return List<Keywords>
	 * @throws Exception
	 */
	public List<Keywords> getAll(BasicModel basicModel) throws Exception {
		return dao.getAllExceptUnused(basicModel.getId());
	}

	/**
	 * get all keywords and reply message
	 * 
	 * @return List<Keywords>
	 * @throws Exception
	 */
	public List<Keywords> getAll(BasicModel basicModel, boolean inUsing) throws Exception {
		if (inUsing) {
			return dao.getAllExceptUnused(basicModel.getId());
		}
		return dao.getAll(basicModel.getId());
	}

	/**
	 * insert keyword
	 * 
	 * @param keyword
	 * @throws Exception
	 */
	public void insert(Keywords keyword) throws Exception {
		keyword.setSubscribe(false);
		dao.insert(keyword);
	}

	/**
	 * get subscribe reply message
	 * 
	 * @return Keywords
	 * @throws Exception
	 */
	public List<Keywords> getSubscribeReply(BasicModel basicModel) throws Exception {
		return dao.getSubscribeReply(basicModel.getId());
	}

	/**
	 * insert subscribe reply
	 * 
	 * @param keyword
	 * @throws Exception
	 */
	public void insertSubscribe(Keywords keyword) throws Exception {
		keyword.setSubscribe(true);
		dao.insert(keyword);
	}

	/**
	 * set inUsing
	 * 
	 * @param keyword
	 * @throws Exception
	 */
	public void setInUsing(Keywords keyword) throws Exception {
		dao.setInUsing(keyword);
	}

	/**
	 * set subscribe inUsing
	 * 
	 * @param keyword
	 * @throws Exception
	 */
	public void subscribeSetInUsing(Keywords keyword) throws Exception {
		dao.subscribeSetInUsing(keyword);
	}

	/**
	 * get subscribe reply
	 * 
	 * @param basicModel
	 * @return
	 * @throws Exception
	 */
	public Keywords getSubscribe(BasicModel basicModel) throws Exception {
		return dao.getSubscribe(basicModel.getId());
	}

	/**
	 * edit keyword and auto-reply message
	 * 
	 * @param keyword
	 * @throws Exception
	 */
	public void edit(Keywords keyword) throws Exception {
		dao.edit(keyword);
	}

	/**
	 * delete keyword
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delete(int id) throws Exception {
		dao.delete(id);
	}

}
