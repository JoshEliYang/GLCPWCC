package cn.springmvc.model;

public class Right {

	private int goupId;
	
	private int levelId;
	
	private boolean visible;

	public int getGoupId() {
		return goupId;
	}

	public void setGoupId(int goupId) {
		this.goupId = goupId;
	}

	public int getLevelId() {
		return levelId;
	}

	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Right(int goupId, int levelId, boolean visible) {
		super();
		this.goupId = goupId;
		this.levelId = levelId;
		this.visible = visible;
	}

	public Right() {
		super();
	}
}
