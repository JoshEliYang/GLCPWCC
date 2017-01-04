package cn.springmvc.model.admin;

/**
 * 
 * @author johnson
 *
 */
public class OOSAdminData extends OOSAdmin {
	boolean isExist;
	String token;

	@Override
	public String toString() {
		return "OOSAdminData [isExist=" + isExist + ", token=" + token + ", id=" + id + ", name=" + name + ", realName="
				+ realName + ", pwd=" + pwd + ", levels=" + levels + ", role=" + role + "]";
	}

	public boolean isExist() {
		return isExist;
	}

	public void setExist(boolean isExist) {
		this.isExist = isExist;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
