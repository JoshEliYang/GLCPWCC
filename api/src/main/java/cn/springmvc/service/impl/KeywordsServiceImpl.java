package cn.springmvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.dao.KeywordsDao;
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
	 * get all keywords and reply message
	 * 
	 * @return List<Keywords>
	 * @throws Exception
	 */
	public List<Keywords> getAll() throws Exception {
		return dao.getAll();
	}

	/**
	 * get subscribe reply message
	 * 
	 * @return Keywords
	 * @throws Exception
	 */
	public Keywords getSubscribeReply() throws Exception {
		return dao.getSubscribeReply();
	}

}
