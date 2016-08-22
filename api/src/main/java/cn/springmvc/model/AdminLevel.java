package cn.springmvc.model;

public class AdminLevel {
	
	private String levelName;
	
	private String remark;

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public AdminLevel(String levelName, String remark) {
		super();
		this.levelName = levelName;
		this.remark = remark;
	}

	public AdminLevel() {
		super();
	}

	@Override
	public String toString() {
		return "AdminLevel [levelName=" + levelName + ", remark=" + remark
				+ "]";
	}
}
