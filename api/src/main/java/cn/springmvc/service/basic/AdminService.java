package cn.springmvc.service.basic;

import java.util.List;

import cn.springmvc.model.AdminLevel;
import cn.springmvc.model.LevelRight;
import cn.springmvc.model.Right;
import cn.springmvc.model.User;
import cn.springmvc.model.UserLevel;
import cn.springmvc.model.admin.Admin;
import cn.springmvc.model.admin.AdminInfo;
import cn.springmvc.model.admin.OOSAdmin;
import cn.springmvc.model.admin.UserMapping;

/**
 * 
 * @author johsnon
 *
 */
public interface AdminService {
	
	
	public AdminInfo verify(String token)throws Exception;
	
	public List<Admin> getAll()throws Exception;
	
	public List<OOSAdmin> getAllOosAdmins()throws Exception;
	
	public List<UserMapping> getAllUserMapping()throws Exception;
	

	
	
	
	
	
	/********************** Following methods are all abandoned (do not use these again !) *************************/
	
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
	 * set right is visible or disable
	 * 
	 * @param right
	 * @throws Exception
	 */
	public void updateRight(Right right) throws Exception;

	/**
	 * get all level's rights
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<LevelRight> getAllLevelRights() throws Exception;

	/**
	 * enable/unable levels' right
	 * 
	 * @param levelId
	 * @param groupId
	 * @param enable
	 * @throws Exception
	 */
	public void setLevelRight(int levelId, int groupId, boolean enable) throws Exception;

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
