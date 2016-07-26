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

	/**
	 * get msgType by id
	 * 
	 * @param msgTypeId
	 * @return
	 * @throws Exception
	 */
	public MsgType getById(int msgTypeId) throws Exception;
}
