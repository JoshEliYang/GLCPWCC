package cn.springmvc.model.SubCounter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author johnson
 *
 */
public class SubscribeRealTime implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String time;
	boolean addSubscribe;
	boolean addUnsubscribe;

	public SubscribeRealTime() {
		super();
	}

	public SubscribeRealTime(boolean addSubscribe, boolean addUnsubscribe) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
		time = df.format(new Date());
		this.addSubscribe = addSubscribe;
		this.addUnsubscribe = addUnsubscribe;
	}

	@Override
	public String toString() {
		return "SubscribeRealTime [time=" + time + ", addSubscribe=" + addSubscribe + ", addUnsubscribe="
				+ addUnsubscribe + "]";
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public boolean isAddSubscribe() {
		return addSubscribe;
	}

	public void setAddSubscribe(boolean addSubscribe) {
		this.addSubscribe = addSubscribe;
	}

	public boolean isAddUnsubscribe() {
		return addUnsubscribe;
	}

	public void setAddUnsubscribe(boolean addUnsubscribe) {
		this.addUnsubscribe = addUnsubscribe;
	}

}
