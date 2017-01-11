package cn.springmvc.model.SubCounter;

import java.util.ArrayList;

public class SubscribeCountByTags {
	
	int tagId;
	String tagName;
	ArrayList<Integer> counts;
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
	public ArrayList<Integer> getCounts() {
		return counts;
	}
	public void setCounts(ArrayList<Integer> counts) {
		this.counts = counts;
	}
	@Override
	public String toString() {
		return "SubscribeCountByTags [tagId=" + tagId + ", tagName=" + tagName
				+ ", counts=" + counts + "]";
	}
	
}
