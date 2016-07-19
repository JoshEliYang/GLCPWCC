package cn.springmvc;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * @author johsnon
 *
 */
public class KeyWords {

	public KeyWords() {
		super();
		setup();
	}

	public static KeyWords getInstance() {
		return new KeyWords();
	}

	// 关注自动回复内容
	public String REPLY_SUBSCRIBE = null;

	// 关键字自动回复内容
	public Map<String, String> WORDS = null;

	void setup() {
		InputStreamReader reader = null;
		WORDS = new HashMap<String, String>();

		// 长期有效自动回复内容
		try {
			reader = new InputStreamReader(Consts.class.getResourceAsStream("../../conf/LTAKeyWord.properties"),
					"utf-8");
			Properties props = new Properties();
			props.load(reader);

			// 设置关注回复内容
			REPLY_SUBSCRIBE = props.getProperty("REPLY_SUBSCRIBE");

			Iterator<String> it = props.stringPropertyNames().iterator();
			while (it.hasNext()) {
				String key = it.next();
				// System.out.println(key + ":" + props.getProperty(key));
				if ("REPLY_SUBSCRIBE".equals(key))
					continue;

				WORDS.put(key, props.getProperty(key));
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

		// 读取 特定时间 自动回复内容
		try {
			reader = new InputStreamReader(Consts.class.getResourceAsStream("../../conf/keyWord.properties"), "utf-8");
			Properties props = new Properties();
			props.load(reader);

			int month = (new Date()).getMonth() + 1;
			int day = (new Date()).getDate();
//			System.out.println("date:" + month + ":" + day);

			int monthSet = Integer.parseInt(props.getProperty("MONTH"));
			int daySet = Integer.parseInt(props.getProperty("DAY"));

			// 判断是否为活动当日
			if (month == monthSet && day == daySet) {
				// 注册回复内容改为当日特定内容
				REPLY_SUBSCRIBE = props.getProperty("REPLY_SUBSCRIBE");

				// 添加当日特定回复内容
				Iterator<String> it = props.stringPropertyNames().iterator();
				while (it.hasNext()) {
					String key = it.next();
					// System.out.println(key + ":" + props.getProperty(key));
					if ("MONTH".equals(key))
						continue;
					if ("DAY".equals(key))
						continue;
					if ("REPLY_SUBSCRIBE".equals(key))
						continue;

					WORDS.put(key, props.getProperty(key));
				}
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
}
