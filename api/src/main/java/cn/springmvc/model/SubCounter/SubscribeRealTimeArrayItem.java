package cn.springmvc.model.SubCounter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.springmvc.model.SubCounter.SubscribeRealTimeArray.Type;

/**
 * 
 * @author johnson
 *
 */
public class SubscribeRealTimeArrayItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<String> subscribeArray;
	List<String> unSubscribeArray;

	int minutesNumber;
	int subscribeCount = 0;
	int unSubscribeCount = 0;

	public SubscribeRealTimeArrayItem() {
		super();
		subscribeArray = new ArrayList<String>();
		unSubscribeArray = new ArrayList<String>();
	}

	public SubscribeRealTimeArrayItem(int currentMinutes) {
		this();
		this.minutesNumber = currentMinutes;
	}

	/**
	 * add to array
	 * 
	 * @param type
	 */
	public void add(Type type) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String now = df.format(new Date());
		if (type == Type.Subscribe) {
			subscribeArray.add(now);
			subscribeCount = subscribeArray.size();
		} else {
			unSubscribeArray.add(now);
			unSubscribeCount = unSubscribeArray.size();
		}
	}

	@Override
	public String toString() {
		return "SubscribeRealTimeArrayItem [subscribeArray=" + subscribeArray + ", unSubscribeArray=" + unSubscribeArray
				+ ", minutesNumber=" + minutesNumber + ", subscribeCount=" + subscribeCount + ", unSubscribeCount="
				+ unSubscribeCount + "]";
	}

	public List<String> getSubscribeArray() {
		return subscribeArray;
	}

	public void setSubscribeArray(List<String> subscribeArray) {
		this.subscribeArray = subscribeArray;
	}

	public List<String> getUnSubscribeArray() {
		return unSubscribeArray;
	}

	public void setUnSubscribeArray(List<String> unSubscribeArray) {
		this.unSubscribeArray = unSubscribeArray;
	}

	public int getMinutesNumber() {
		return minutesNumber;
	}

	public void setMinutesNumber(int minutesNumber) {
		this.minutesNumber = minutesNumber;
	}

	public int getSubscribeCount() {
		return subscribeCount;
	}

	public void setSubscribeCount(int subscribeCount) {
		this.subscribeCount = subscribeCount;
	}

	public int getUnSubscribeCount() {
		return unSubscribeCount;
	}

	public void setUnSubscribeCount(int unSubscribeCount) {
		this.unSubscribeCount = unSubscribeCount;
	}

}
