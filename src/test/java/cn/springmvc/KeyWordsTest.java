package cn.springmvc;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class KeyWordsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		// fail("Not yet implemented");
		System.out.println("subscribe auto reply: " + KeyWords.getInstance().REPLY_SUBSCRIBE);
		Iterator iter = KeyWords.getInstance().WORDS.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = (Entry<String, String>) iter.next();
			String key = entry.getKey();
			String value = entry.getValue();
			System.out.println(key + "=" + value);
		}
	}

}
