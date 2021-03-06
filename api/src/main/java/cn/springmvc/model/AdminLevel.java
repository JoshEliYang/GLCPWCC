package cn.springmvc.model;

public class AdminLevel {

	private int id;

	private String levelName;

	private String remark;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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
		return "AdminLevel [id=" + id + ", levelName=" + levelName + ", remark=" + remark + "]";
	}

}
