package cn.springmvc.dao;

import java.util.List;

import cn.springmvc.model.AdminLevel;
import cn.springmvc.model.LevelRight;
import cn.springmvc.model.Right;
import cn.springmvc.model.User;
import cn.springmvc.model.UserLevel;
import cn.springmvc.model.admin.Admin;
import cn.springmvc.model.admin.UserMapping;

/**
 * 
 * @author johsnon
 *
 */
public interface AdminDao {

	public UserMapping getMapping(int adminId) throws Exception;

	public void addMissing(List<UserMapping> mappings) throws Exception;

	public void userClearn(List<UserMapping> delMappings) throws Exception;

	public List<UserMapping> getAllUserMapping() throws Exception;

	/**
	 * get all users
	 * 
	 * @return user list
	 * @throws Exception
	 */
	public List<User> getAllUsers() throws Exception;

	/**
	 * get all user levels
	 * 
	 * @return user level list
	 * @throws Exception
	 */
	public List<UserLevel> getAllUserLevels() throws Exception;

	/**
	 * get level id by user id
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public int getLevelId(int userId) throws Exception;

	/**
	 * check user name and password
	 * 
	 * @param username
	 * @param passwd
	 * @return
	 * @throws Exception
	 */
	public User checkUser(String username, String passwd) throws Exception;

	/**
	 * get user by userId
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public User getUserById(int userId) throws Exception;

	/**
	 * insert admin data
	 * 
	 * @param admin
	 * @throws Exception
	 */
	public void insert(User admin) throws Exception;

	/**
	 * edit admin info (except password)
	 * 
	 * @param admin
	 * @throws Exception
	 */
	public void edit(User admin) throws Exception;

	/**
	 * delete admin by id
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delete(int id) throws Exception;

	/**
	 * reset password
	 * 
	 * @param admin
	 * @throws Exception
	 */
	public void resetPassword(User admin) throws Exception;

	/**
	 * add level
	 * 
	 * @param adminLevel
	 * @throws Exception
	 */
	public void addLevel(AdminLevel adminLevel) throws Exception;

	/**
	 * set right is visible
	 * 
	 * @param right
	 * @throws Exception
	 */
	public void setRightAdd(Right right) throws Exception;

	/**
	 * set right is disable
	 * 
	 * @param right
	 * @throws Exception
	 */
	public void setRightDelete(Right right) throws Exception;

	/**
	 * get all level's rights
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<LevelRight> getAllLevelRights() throws Exception;

	/**
	 * enable levels' right
	 * 
	 * @param levelId
	 * @param groupId
	 * @throws Exception
	 */
	public void enableLevelRight(int levelId, int groupId) throws Exception;

	/**
	 * unable levels' right
	 * 
	 * @param levelId
	 * @param groupId
	 * @throws Exception
	 */
	public void unableLevelRight(int levelId, int groupId) throws Exception;

	/**
	 * edit user level
	 * 
	 * @param adminLevel
	 * @throws Exception
	 */
	public void editUserLevel(UserLevel adminLevel) throws Exception;

	/**
	 * remove user level
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void removeAdminLevel(int id) throws Exception;

	public void changeAdmins(Admin am) throws Exception;
}
