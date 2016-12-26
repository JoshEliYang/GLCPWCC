package cn.springmvc.service.impl.basic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.springmvc.utils.MD5Util;
import com.springmvc.utils.RequestUtil;

import cn.springmvc.dao.AdminDao;
import cn.springmvc.model.AdminLevel;
import cn.springmvc.model.LevelRight;
import cn.springmvc.model.Right;
import cn.springmvc.model.User;
import cn.springmvc.model.UserLevel;
import cn.springmvc.model.admin.Admin;
import cn.springmvc.model.admin.OOSAdmin;
import cn.springmvc.model.admin.OOSResponse;
import cn.springmvc.model.admin.UserMapping;
import cn.springmvc.service.basic.AdminService;

/**
 * 
 * @author johsnon
 *
 */
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminDao dao;

	public Admin verify(String token) throws Exception {

		return null;
	}

	/**
	 * get all user
	 */
	public List<Admin> getAll() throws Exception {
		List<OOSAdmin> oosAdminList = getAllOosAdmins();
		List<UserMapping> mappings = getAllUserMapping();

		userClearn(oosAdminList, mappings);
		addMissing(oosAdminList, mappings);

		mappings = getAllUserMapping();
		List<Admin> admins = new ArrayList<Admin>();
		OOSAdmin oosAdmin = null;
		UserMapping mapping = null;

		for (int i = 0; i < oosAdminList.size(); i++) {
			oosAdmin = oosAdminList.get(i);
			for (int j = 0; j < mappings.size(); j++) {
				mapping = mappings.get(j);
				if (oosAdmin.getId() == mapping.getAdminId()) {
					Admin admin = new Admin();
					admin.setId(mapping.getId());
					admin.setLevelName(mapping.getLevelName());
					admin.setUserLevel(mapping.getUserLevel());
					admin.setName(oosAdmin.getName());
					admin.setRealName(oosAdmin.getRealName());
					admin.setOosId(oosAdmin.getId());
					admins.add(admin);
					break;
				}
			}
		}
		return admins;
	}
	

	/**
	 * get all user from OOS
	 */
	public List<OOSAdmin> getAllOosAdmins() throws Exception {
		String url = "http://120.26.54.131:8080/utilservice/admin/getall/wechat";
		String response = RequestUtil.doGet(url);
		OOSResponse oosResponse = JSON.parseObject(response, OOSResponse.class);
		List<OOSAdmin> oosAdminList = oosResponse.getData();
		return oosAdminList;
	}

	/**
	 * get all user-OOS mapping
	 */
	public List<UserMapping> getAllUserMapping() throws Exception {
		return dao.getAllUserMapping();
	}

	/**
	 * add missing users
	 * 
	 * @param oosAdminList
	 * @param mappings
	 * @throws Exception
	 */
	private void addMissing(List<OOSAdmin> oosAdminList, List<UserMapping> mappings) throws Exception {
		List<UserMapping> newMapping = new ArrayList<UserMapping>();
		OOSAdmin oosAdmin = null;
		UserMapping mapping = null;

		for (int i = 0; i < oosAdminList.size(); i++) {
			oosAdmin = oosAdminList.get(i);
			int j = 0;
			for (j = 0; j < mappings.size(); j++) {
				mapping = mappings.get(j);
				if (oosAdmin.getId() == mapping.getAdminId())
					break;
			}
			if (j >= mappings.size()) {
				UserMapping newMapper = new UserMapping();
				newMapper.setAdminId(oosAdmin.getId());
				newMapping.add(newMapper);
			}
		}
		if (newMapping.size() > 0)
			dao.addMissing(newMapping);

	}

	/**
	 * delete users which isn't exist in OOS
	 * 
	 * @param oosAdminList
	 * @param mappings
	 * @throws Exception
	 */
	private void userClearn(List<OOSAdmin> oosAdminList, List<UserMapping> mappings) throws Exception {
		List<UserMapping> delMappings = new ArrayList<UserMapping>();
		OOSAdmin oosAdmin = null;
		UserMapping mapping = null;

		if (mappings.size() > 0)
			for (int i = mappings.size() - 1; i > 0; i--) {
				mapping = mappings.get(i);
				int j = 0;
				for (j = 0; j < oosAdminList.size(); j++) {
					oosAdmin = oosAdminList.get(j);
					if (oosAdmin.getId() == mapping.getAdminId())
						break;
				}
				if (j >= oosAdminList.size()) {
					UserMapping delItem = new UserMapping();
					delItem.setId(mapping.getId());
					delMappings.add(delItem);
				}
			}
		if (delMappings.size() > 0)
			dao.userClearn(delMappings);
	}

	/**
	 * 
	 * Following methods are all abandoned (do not use these again !)
	 * 
	 */

	/**
	 * get all users
	 * 
	 * @return user list
	 * @throws Exception
	 */
	public List<User> getAllUsers() throws Exception {
		return dao.getAllUsers();
	}

	/**
	 * get all user levels
	 * 
	 * @return user level list
	 * @throws Exception
	 */
	public List<UserLevel> getAllUserLevels() throws Exception {
		return dao.getAllUserLevels();
	}

	/**
	 * get level id by user id
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public int getLevelId(int userId) throws Exception {
		return dao.getLevelId(userId);
	}

	/**
	 * check user name and password
	 * 
	 * @param username
	 * @param passwd
	 * @return
	 * @throws Exception
	 */
	public User checkUser(String username, String passwd) throws Exception {
		return dao.checkUser(username, passwd);
	}

	/**
	 * get user by userId
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public User getUserById(int userId) throws Exception {
		return dao.getUserById(userId);
	}

	/**
	 * insert admin data
	 * 
	 * @param admin
	 * @throws Exception
	 */
	public void insert(User admin) throws Exception {
		String passwd = admin.getPasswd();
		passwd = MD5Util.string2MD5(passwd);
		admin.setPasswd(passwd);
		dao.insert(admin);
	}

	/**
	 * edit admin info (except password)
	 * 
	 * @param admin
	 * @throws Exception
	 */
	public void edit(User admin) throws Exception {
		dao.edit(admin);
	}

	/**
	 * delete admin by id
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delete(int id) throws Exception {
		dao.delete(id);
	}

	/**
	 * reset password
	 * 
	 * @param admin
	 * @throws Exception
	 */
	public void resetPassword(User admin) throws Exception {
		String password = admin.getPasswd();
		password = MD5Util.string2MD5(password);
		admin.setPasswd(password);
		dao.resetPassword(admin);
	}

	/**
	 * add level
	 * 
	 * @param Admin
	 *            Level
	 * @throws Exception
	 */
	public void addLevel(AdminLevel adminLevel) throws Exception {
		dao.addLevel(adminLevel);
	}

	/**
	 * set right is visible
	 * 
	 * @param Right
	 *            right
	 * @throws Exception
	 */
	public void setRightAdd(Right right) throws Exception {
		dao.setRightAdd(right);
	}

	/**
	 * set right is disable
	 * 
	 * @param Right
	 *            right
	 * @throws Exception
	 */
	public void setRightDelete(Right right) throws Exception {
		dao.setRightDelete(right);
	}

	/**
	 * set right is visible or disable
	 * 
	 * @param right
	 * @throws Exception
	 */
	public void updateRight(Right right) throws Exception {
		if (right.isVisible() == true) {
			dao.setRightAdd(right);
			return;
		}

		dao.setRightDelete(right);
	}

	/**
	 * get all level's rights
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<LevelRight> getAllLevelRights() throws Exception {
		return dao.getAllLevelRights();
	}

	/**
	 * enable/unable levels' right
	 * 
	 * @param levelId
	 * @param groupId
	 * @param enable
	 * @throws Exception
	 */
	public void setLevelRight(int levelId, int groupId, boolean enable) throws Exception {
		if (enable) {
			dao.enableLevelRight(levelId, groupId);
		} else {
			dao.unableLevelRight(levelId, groupId);
		}
	}

	/**
	 * edit user level
	 * 
	 * @param adminLevel
	 * @throws Exception
	 */
	public void editUserLevel(UserLevel adminLevel) throws Exception {
		dao.editUserLevel(adminLevel);
	}

	/**
	 * remove user level
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void removeAdminLevel(int id) throws Exception {
		dao.removeAdminLevel(id);
	}

}
