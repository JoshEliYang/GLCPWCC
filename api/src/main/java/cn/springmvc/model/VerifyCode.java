package cn.springmvc.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author johsnon
 *
 */
public class VerifyCode {
	int id;
	String verifyCode;
	String ip;
	String lapsedTime;

	public VerifyCode() {
		super();
	}

	public VerifyCode(String verifyCode, String ip) {
		super();
		this.verifyCode = verifyCode;
		this.ip = ip;

		Date now = new Date();
		now.setHours(now.getHours() + 1);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.lapsedTime = dateFormat.format(now);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLapsedTime() {
		return lapsedTime;
	}

	public void setLapsedTime(String lapsedTime) {
		this.lapsedTime = lapsedTime;
	}

}
