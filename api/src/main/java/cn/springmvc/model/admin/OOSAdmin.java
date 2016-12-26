package cn.springmvc.model.admin;

import java.util.List;

/**
 * 
 * @author johnson
 *
 */
public class OOSAdmin {

	int id;
	String name;
	String realName;
	String pwd;
	List<String> levels;
	String role;

	@Override
	public String toString() {
		return "Admin [id=" + id + ", name=" + name + ", realName=" + realName + ", pwd=" + pwd + ", levels=" + levels
				+ ", role=" + role +  "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public List<String> getLevels() {
		return levels;
	}

	public void setLevels(List<String> levels) {
		this.levels = levels;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
