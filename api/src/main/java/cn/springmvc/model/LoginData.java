package cn.springmvc.model;

/**
 * 
 * @author johsnon
 *
 */
public class LoginData {
	String username;
	String passwd;
	String verification;
	String ip;
	boolean longTermFlag = false;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getVerification() {
		return verification;
	}

	public void setVerification(String verification) {
		this.verification = verification;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public boolean isLongTermFlag() {
		return longTermFlag;
	}

	public void setLongTermFlag(boolean longTermFlag) {
		this.longTermFlag = longTermFlag;
	}

	@Override
	public String toString() {
		return "LoginData [username=" + username + ", passwd=" + passwd + ", verification=" + verification + ", ip="
				+ ip + ", longTermFlag=" + longTermFlag + "]";
	}

}
