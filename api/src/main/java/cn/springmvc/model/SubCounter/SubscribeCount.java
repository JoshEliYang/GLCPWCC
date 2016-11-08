package cn.springmvc.model.SubCounter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.springmvc.model.BasicModel;

/**
 * 
 * @author johnson
 *
 */
public class SubscribeCount {
	int id;
	int year;
	int month;
	int day;
	int week;
	int tagId;
	int subscribe = 0;
	int unsubscribe = 0;
	int basicId;
	String date;

	public SubscribeCount() {
		super();
	};

	public SubscribeCount(Date date, int tagId, BasicModel basic) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		this.setYear(calendar.get(Calendar.YEAR));
		this.setMonth(calendar.get(Calendar.MONTH) + 1);
		this.setDay(calendar.get(Calendar.DAY_OF_MONTH));
		this.setWeek(calendar.getWeeksInWeekYear());
		this.tagId = tagId;
		this.basicId = basic.getId();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		this.date = df.format(date);
	};

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public int getTagId() {
		return tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	public int getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(int subscribe) {
		this.subscribe = subscribe;
	}

	public int getUnsubscribe() {
		return unsubscribe;
	}

	public void setUnsubscribe(int unsubscribe) {
		this.unsubscribe = unsubscribe;
	}

	public int getBasicId() {
		return basicId;
	}

	public void setBasicId(int basicId) {
		this.basicId = basicId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "SubscribeCount [id=" + id + ", year=" + year + ", month=" + month + ", day=" + day + ", week=" + week
				+ ", tagId=" + tagId + ", subscribe=" + subscribe + ", unsubscribe=" + unsubscribe + ", basicId="
				+ basicId + ", date=" + date + "]";
	}

}
