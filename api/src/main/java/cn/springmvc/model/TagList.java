package cn.springmvc.model;

public class TagList {

	int tagId;

	String tagname;

	String color;

	String uncolor;

	int basicId;

	int id;

	@Override
	public String toString() {
		return "TagList [tagId=" + tagId + ", tagname=" + tagname + ", color="
				+ color + ", uncolor=" + uncolor + ", basicId=" + basicId
				+ ", id=" + id + "]";
	}

	public int getTagId() {
		return tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	public String getTagname() {
		return tagname;
	}

	public void setTagname(String tagname) {
		this.tagname = tagname;
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

	public int getBasicId() {
		return basicId;
	}

	public void setBasicId(int basicId) {
		this.basicId = basicId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
