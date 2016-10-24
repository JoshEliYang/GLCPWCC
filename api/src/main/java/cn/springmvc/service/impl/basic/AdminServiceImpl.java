package cn.springmvc.service.impl.basic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.utils.MD5Util;

import cn.springmvc.dao.AdminDao;
import cn.springmvc.model.AdminLevel;
import cn.springmvc.model.LevelRight;
import cn.springmvc.model.Right;
import cn.springmvc.model.User;
import cn.springmvc.model.UserLevel;
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