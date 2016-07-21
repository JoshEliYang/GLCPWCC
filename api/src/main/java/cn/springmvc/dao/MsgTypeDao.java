package cn.springmvc.dao;

import java.util.List;

import cn.springmvc.model.MsgType;

/**
 * 
 * @author johnson
 *
 */
public interface MsgTypeDao {
	/**
	 * get all msgTypes
	 * 
	 * @return List<MsgType>
	 * @throws Exception
	 */
	public List<MsgType> getAll() throws Exception;
}
