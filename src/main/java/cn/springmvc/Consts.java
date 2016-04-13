package cn.springmvc;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * 全局常量
 * 
 * @author johsnon
 *
 */
public final class Consts {

	// token
	// public static String TOKEN="hKKl48yK4Jolq4dbX5R9RYE8YzYkNndb";
	//
	// // 测试账号
	// public static String APP_ID = "wx6953682e7b6eecdf";
	// public static String APP_SERCRET = "d0c2bf8805a8bf8824f31830edde8750";
	//
	// // 微信账号
	// public static String WECHART_ACCOUNT="gh_96c813516423";

	public static String TOKEN = null;
	// 测试账号
	public static String APP_ID = null;
	public static String APP_SERCRET = null;
	// 微信账号
	public static String WECHART_ACCOUNT = null;

	// 关注成功回复内容
	public static String REPLY_SUBSCRIBE = null;

	// johsnon账号
	// public static String APP_ID = "wx54ab9837e1967990";
	// public static String APP_SERCRET = "b83f38ad4f7401ca24a1f16fabb0dd98";

	static {

		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(Consts.class.getResourceAsStream("../../conf/wechart.properties"), "utf-8");
			Properties props = new Properties();
			props.load(reader);

			TOKEN = props.getProperty("TOKEN");
			APP_ID = props.getProperty("APP_ID");
			APP_SERCRET = props.getProperty("APP_SERCRET");
			WECHART_ACCOUNT = props.getProperty("WECHART_ACCOUNT");
			REPLY_SUBSCRIBE = props.getProperty("REPLY_SUBSCRIBE");

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

	public static Map<String,String> KEY_WORDS = null;

	static {
		InputStreamReader reader = null;

		try {
			KEY_WORDS=new HashMap();
			
			reader = new InputStreamReader(Consts.class.getResourceAsStream("../../conf/keyWord.properties"), "utf-8");
			Properties props = new Properties();
			props.load(reader);

			Iterator<String> it = props.stringPropertyNames().iterator();
			while (it.hasNext()) {
				String key = it.next();
				System.out.println(key + ":" + props.getProperty(key));
				KEY_WORDS.put(key, props.getProperty(key));
			}

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

	/**
	 * just for test
	 * @param args
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException {
		System.out.println(TOKEN);
		System.out.println(REPLY_SUBSCRIBE);
		
		System.out.println("KEY_WORKDS:");
		Iterator iter = KEY_WORDS.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry<String, String> entry=(Entry<String, String>) iter.next();
			String key = entry.getKey();
			System.out.println(key + ":" + KEY_WORDS.get(key));
		}
	}
}
