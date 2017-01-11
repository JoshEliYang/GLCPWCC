package cn.springmvc.model.SubCounter;

public class SubscribeCountQuery {
	String startDate;
	String endDate;
	int tagId;
	
	
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getTagId() {
		return tagId;
	}
	public void setTagId(int tagId) {
		this.tagId = tagId;
	}
	@Override
	public String toString() {
		return "SubscribeCountQuery [startDate=" + startDate + ", endDate="
				+ endDate + ", tagId=" + tagId + "]";
	}

	
	
	
	
	
	
}
