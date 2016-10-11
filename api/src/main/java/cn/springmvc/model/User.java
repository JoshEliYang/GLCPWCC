package cn.springmvc.model;

/**
 * 
 * @author johsnon
 *
 */
public class User {
	int id;
	String username;
	String realname;
	String passwd;
	String email;
	int userLevel;
	String levelName;
	String bak1;
	String bak2;

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", realname=" + realname + ", passwd=" + passwd
				+ ", email=" + email + ", userLevel=" + userLevel + ", levelName=" + levelName + ", bak1=" + bak1
				+ ", bak2=" + bak2 + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getBak1() {
		return bak1;
	}

	public void setBak1(String bak1) {
		this.bak1 = bak1;
	}

	public String getBak2() {
		return bak2;
	}

	public void setBak2(String bak2) {
		this.bak2 = bak2;
	}

}
