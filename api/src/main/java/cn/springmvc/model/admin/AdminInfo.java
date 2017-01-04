package cn.springmvc.model.admin;

/**
 * 
 * @author johnson
 *
 */
public class AdminInfo extends Admin {
	boolean isExist;

	@Override
	public String toString() {
		return "AdminInfo [isExist=" + isExist + ", id=" + id + ", oosId=" + oosId + ", name=" + name + ", realName="
				+ realName + ", userLevel=" + userLevel + ", levelName=" + levelName + "]";
	}

	public boolean isExist() {
		return isExist;
	}

	public void setExist(boolean isExist) {
		this.isExist = isExist;
	}

}
