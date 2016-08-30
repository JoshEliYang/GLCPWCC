package cn.springmvc.mq;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import cn.springmvc.Consts;

/**
 * 
 * @author johnson
 *
 */
public class MqUtility {
	String userName;
	String password;
	String brokerURL;

	private MqUtility() {
		InputStreamReader reader = null;

		try {
			reader = new InputStreamReader(Consts.class.getResourceAsStream("../../conf/mq.properties"), "utf-8");
			Properties props = new Properties();
			props.load(reader);

			this.setUserName(props.getProperty("userName"));
			this.setPassword(props.getProperty("password"));
			this.setBrokerURL(props.getProperty("brokerURL"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static class MqUtilHolder {
		private static final MqUtility INSTANCE = new MqUtility();
	}

	public static MqUtility getInstance() {
		return MqUtilHolder.INSTANCE;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBrokerURL() {
		return brokerURL;
	}

	public void setBrokerURL(String brokerURL) {
		this.brokerURL = brokerURL;
	}

}
