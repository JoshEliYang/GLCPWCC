package cn.springmvc.model.SubCounter;

import java.util.Arrays;

/**
 * 
 * @author johnson
 *
 */
public class SubscribeInfoQuery {
	String startDate;
	String endDate;

	/**
	 * -1:means those users aren't belong to any group
	 * 
	 * -255:means total
	 */
	int[] tagList;

	/**
	 * 1.means year 2.means month 3.means day 4.means week
	 */
	int dateType;

	@Override
	public String toString() {
		return "SubscribeInfoQuery [startDate=" + startDate + ", endDate=" + endDate + ", tagList="
				+ Arrays.toString(tagList) + ", dateType=" + dateType + "]";
	}

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

	public int[] getTagList() {
		return tagList;
	}

	public void setTagList(int[] tagList) {
		this.tagList = tagList;
	}

	public int getDateType() {
		return dateType;
	}

	public void setDateType(int dateType) {
		this.dateType = dateType;
	}

}
