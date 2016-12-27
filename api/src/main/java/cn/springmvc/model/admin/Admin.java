package cn.springmvc.model.admin;

/**
 * 
 * @author johnson
 *
 */
public class Admin {

	int id;
	int oosId;
	String name;
	String realName;
	int userLevel;
	String levelName;

	@Override
	public String toString() {
		return "Admin [id=" + id + ", oosId=" + oosId + ", name=" + name + ", realName=" + realName + ", userLevel="
				+ userLevel + ", levelName=" + levelName + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOosId() {
		return oosId;
	}

	public void setOosId(int oosId) {
		this.oosId = oosId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
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
