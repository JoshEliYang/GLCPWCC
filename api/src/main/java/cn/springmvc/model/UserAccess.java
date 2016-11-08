package cn.springmvc.model;

/**
 * 
 * @author johsnon
 *
 */
public class UserAccess {
	int id;
	int userId;
	String token;
	String loginTime;
	String lapsedTime;
	String bak1;
	String bak2;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getLapsedTime() {
		return lapsedTime;
	}

	public void setLapsedTime(String lapsedTime) {
		this.lapsedTime = lapsedTime;
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

	@Override
	public String toString() {
		return "UserAccess [id=" + id + ", userId=" + userId + ", token=" + token + ", loginTime=" + loginTime
				+ ", lapsedTime=" + lapsedTime + ", bak1=" + bak1 + ", bak2=" + bak2 + "]";
	}

}
