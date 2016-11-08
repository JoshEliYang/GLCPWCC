package cn.springmvc.service.manage;

import java.util.Map;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.WechatUser;

/**
 * 
 * @author johsnon
 *
 */
public interface UserService {
	// 获得所有用户分组
	public Map<String, String> getAll(BasicModel basicModel) throws Exception;

	// 创建用户分组
	public Map<String, String> createGroup(String name, BasicModel basicModel) throws Exception;

	// 移动用户分组
	public boolean moveToGroup(String openId, String groupId, BasicModel basicModel) throws Exception;

	/**
	 * get user info
	 * 
	 * @author johnson
	 * @param openId
	 * @param basicModel
	 * @return
	 * @throws Exception
	 */
	public WechatUser getUserInfo(String openId, BasicModel basicModel) throws Exception;
}
