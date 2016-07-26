package cn.springmvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.dao.MsgTypeDao;
import cn.springmvc.model.MsgType;
import cn.springmvc.service.MsgTypeService;

/**
 * 
 * @author johnson
 *
 */
@Service
public class MsgTypeServiceImpl implements MsgTypeService {
	@Autowired
	private MsgTypeDao dao;

	/**
	 * get all msgTypes
	 * 
	 * @return List<MsgType>
	 * @throws Exception
	 */
	public List<MsgType> getAll() throws Exception {
		return dao.getAll();
	}

	/**
	 * get msgType by id
	 * 
	 * @param msgTypeId
	 * @return
	 * @throws Exception
	 */
	public MsgType getById(int msgTypeId) throws Exception {
		return dao.getById(msgTypeId);
	}

}
