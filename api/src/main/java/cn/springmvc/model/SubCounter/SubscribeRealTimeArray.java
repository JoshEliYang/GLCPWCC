package cn.springmvc.model.SubCounter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author johnson
 *
 */
public class SubscribeRealTimeArray implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String date;
	int basicId;
	int tagId;
	Map<Integer, SubscribeRealTimeArrayItem> realTimeMap;

	public SubscribeRealTimeArray() {
		super();
	}

	public SubscribeRealTimeArray(int basicId, int tagId) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		date = df.format(new Date());
		realTimeMap = new HashMap<Integer, SubscribeRealTimeArrayItem>();
		this.basicId = basicId;
		this.tagId = tagId;
	}

	/**
	 * generate the key of Map
	 * 
	 * @param basicId
	 * @param tagId
	 * @return
	 */
	public static String generateKey(int basicId, int tagId) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String date = df.format(new Date());
		return "SubscribeRealTimeArray_" + tagId + "_" + basicId + "_" + date;
	}

	/**
	 * generate the key of Map
	 * 
	 * @param basicId
	 * @param tagId
	 * @param date
	 * @return
	 */
	public static String generateKey(int basicId, int tagId, String date) {
		return "SubscribeRealTimeArray_" + tagId + "_" + basicId + "_" + date;
	}

	public enum Type {
		Subscribe, UnSubscribe
	}

	/**
	 * be careful! Put this in a sync block
	 * 
	 * @param type
	 */
	public void add(Type type) {
		Calendar now = Calendar.getInstance();
		int hour = now.get(Calendar.HOUR_OF_DAY);
		int minute = now.get(Calendar.MINUTE);
		int current_minutes = ((hour * 60) + minute);

		SubscribeRealTimeArrayItem item = realTimeMap.get(current_minutes);
		if (item == null)
			item = new SubscribeRealTimeArrayItem(current_minutes);
		item.add(type);
		realTimeMap.put(current_minutes, item);
	}

	@Override
	public String toString() {
		return "SubscribeRealTimeArray [date=" + date + ", basicId=" + basicId + ", tagId=" + tagId + ", realTimeMap="
				+ realTimeMap + "]";
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getBasicId() {
		return basicId;
	}

	public void setBasicId(int basicId) {
		this.basicId = basicId;
	}

	public int getTagId() {
		return tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	public Map<Integer, SubscribeRealTimeArrayItem> getRealTimeMap() {
		return realTimeMap;
	}

	public void setRealTimeMap(Map<Integer, SubscribeRealTimeArrayItem> realTimeMap) {
		this.realTimeMap = realTimeMap;
	}

}
