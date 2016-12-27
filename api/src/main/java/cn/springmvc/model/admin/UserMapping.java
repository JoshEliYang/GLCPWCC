package cn.springmvc.model.admin;

/**
 * 
 * @author johnson
 *
 */
public class UserMapping {
	int id;
	int adminId;
	int userLevel;
	String levelName;

	@Override
	public String toString() {
		return "UserMapping [id=" + id + ", adminId=" + adminId + ", userLevel=" + userLevel + ", levelName="
				+ levelName + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public int getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(int userLevel) {
		this.userLevel = userLevel;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

}
