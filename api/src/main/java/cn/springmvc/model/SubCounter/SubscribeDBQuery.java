package cn.springmvc.model.SubCounter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.springmvc.model.BasicModel;

/**
 * 
 * @author johnson
 *
 */
public class SubscribeDBQuery {
	int startYear;
	int endYear;
	int startMonth;
	int endMonth;
	int startDay;
	int endDay;
	int tagId;
	int basicId;

	public SubscribeDBQuery() {
		super();
	}

	public SubscribeDBQuery(String startDate, String endDate, int tagId, BasicModel basic) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startD = df.parse(startDate);
		Date endD = df.parse(endDate);
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startD);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endD);

		this.startYear = startCal.get(Calendar.YEAR);
		this.startMonth = startCal.get(Calendar.MONTH) + 1;
		this.startDay = startCal.get(Calendar.DAY_OF_MONTH);

		this.endYear = endCal.get(Calendar.YEAR);
		this.endMonth = endCal.get(Calendar.MONTH) + 1;
		this.endDay = endCal.get(Calendar.DAY_OF_MONTH);

		this.tagId = tagId;
		this.basicId = basic.getId();
	}

	@Override
	public String toString() {
		return "SubscribeDBQuery [startYear=" + startYear + ", endYear=" + endYear + ", startMonth=" + startMonth
				+ ", endMonth=" + endMonth + ", startDay=" + startDay + ", endDay=" + endDay + ", tagId=" + tagId
				+ ", basicId=" + basicId + "]";
	}

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	public int getEndYear() {
		return endYear;
	}

	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}

	public int getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(int startMonth) {
		this.startMonth = startMonth;
	}

	public int getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(int endMonth) {
		this.endMonth = endMonth;
	}

	public int getStartDay() {
		return startDay;
	}

	public void setStartDay(int startDay) {
		this.startDay = startDay;
	}

	public int getEndDay() {
		return endDay;
	}

	public void setEndDay(int endDay) {
		this.endDay = endDay;
	}

	public int getTagId() {
		return tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	public int getBasicId() {
		return basicId;
	}

	public void setBasicId(int basicId) {
		this.basicId = basicId;
	}

}
