package cn.springmvc.service;

import java.util.Map;

/**
 * 
 * @author johsnon
 *
 */
public interface UserService {
	// 获得所有用户分组
	public Map<String, String> getAll() throws Exception;

	// 创建用户分组
	public Map<String,String> createGroup(String name) throws Exception;

	// 移动用户分组
	public boolean moveToGroup(String openId,String groupId) throws Exception;
}
