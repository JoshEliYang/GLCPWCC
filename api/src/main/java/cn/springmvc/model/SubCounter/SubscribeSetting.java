package cn.springmvc.model.SubCounter;

/**
 * 
 * @author johnson
 *
 */
public class SubscribeSetting {
	int id;
	int tagId;
	String tagName;
	String color;
	String uncolor;

	@Override
	public String toString() {
		return "SubQueryDB [id=" + id + ", tagId=" + tagId + ", tagName=" + tagName + ", color=" + color + ", uncolor="
				+ uncolor + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTagId() {
		return tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getUncolor() {
		return uncolor;
	}

	public void setUncolor(String uncolor) {
		this.uncolor = uncolor;
	}

}
