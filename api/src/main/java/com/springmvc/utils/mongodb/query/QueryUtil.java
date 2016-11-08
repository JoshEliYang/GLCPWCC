package com.springmvc.utils.mongodb.query;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.springmvc.utils.mongodb.MongoDBUtil;
import com.springmvc.utils.mongodb.model.MongoConfig;
import com.springmvc.utils.mongodb.model.MongoResponse;

/**
 * 
 * @author johnson
 * @param <logClazz>
 *
 */
public class QueryUtil {

	/**
	 * max number in a page
	 */
	static int max = 50;

	static MongoDBUtil mongoUtil = null;
	Class logClazz;
	Class responseClazz;
	String collectionName;

	/**
	 * 
	 * @param mongoConfig
	 * @param logClazz
	 * @param responseClazz
	 */
	public QueryUtil(MongoConfig mongoConfig, Class logClazz, Class responseClazz, String collectionName) {
		mongoUtil = MongoDBUtil.getInstance(mongoConfig);
		this.logClazz = logClazz;
		this.responseClazz = responseClazz;
		this.collectionName = collectionName;
	}

	/**
	 * query log
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public Object query(String startTime, String endTime) throws InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {

		return query(startTime, endTime, 0);
	}

	/**
	 * query log
	 * 
	 * @param startTime
	 * @param endTime
	 * @param skip
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public <logClazz> Object query(String startTime, String endTime, int skip)
			throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException,
			IllegalArgumentException, InvocationTargetException {

		MongoResponse result = mongoUtil.query(collectionName, startTime, endTime, skip, max);
		List<logClazz> logs = new ArrayList<logClazz>();
		for (int i = 0; i < result.getDocs().size(); i++) {
			try {
				logs.add((logClazz) getObj(logClazz, result.getDocs().get(i)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Object response = responseClazz.newInstance();
		Method addResponse = responseClazz.getMethod("setMongoResponse", new Class[] { MongoResponse.class });
		addResponse.invoke(response, new Object[] { result });
		Method addLogs = responseClazz.getMethod("setLogs", new Class[] { List.class });
		addLogs.invoke(response, new Object[] { logs });
		return response;
	}

	/**
	 * query （recommended method）
	 * 
	 * @param queryDoc
	 * @param skip
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public <logClazz> Object query(Document queryDoc, int skip, Document sortDoc)
			throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException,
			IllegalArgumentException, InvocationTargetException {

		MongoResponse result = mongoUtil.query(collectionName, queryDoc, skip, max, sortDoc);
		List<logClazz> logs = new ArrayList<logClazz>();
		for (int i = 0; i < result.getDocs().size(); i++) {
			try {
				logs.add((logClazz) getObj(logClazz, result.getDocs().get(i)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Object response = responseClazz.newInstance();
		Method addResponse = responseClazz.getMethod("setMongoResponse", new Class[] { MongoResponse.class });
		addResponse.invoke(response, new Object[] { result });
		Method addLogs = responseClazz.getMethod("setLogs", new Class[] { List.class });
		addLogs.invoke(response, new Object[] { logs });
		return response;
	}
	
	/**
	 * query all logs
	 * 
	 * @param queryDoc
	 * @param sortDoc
	 * @return
	 */
	public <logClazz> List<logClazz> query(Document queryDoc, Document sortDoc){
		MongoResponse result = mongoUtil.query(collectionName, queryDoc, sortDoc);
		List<logClazz> logs = new ArrayList<logClazz>();
		for (int i = 0; i < result.getDocs().size(); i++) {
			try {
				logs.add((logClazz) getObj(logClazz, result.getDocs().get(i)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return logs;
	}

	/**
	 * get data from Mongo document and pack it into specific class
	 * 
	 * @param clazz
	 * @param doc
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private <T> T getObj(Class<T> clazz, Document doc)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object obj = clazz.newInstance();

		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			String fieldName = field.getName().toLowerCase();

			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				String methodName = method.getName().toLowerCase();
				if ("set".equalsIgnoreCase(methodName.substring(0, 3))) {
					if (methodName.indexOf(fieldName) != -1) {
						method.invoke(obj, doc.get(field.getName()));
						break;
					}
				}
			}
		}
		return (T) obj;
	}

	/**
	 * drop collection
	 */
	public void clear() {
		mongoUtil.clear(collectionName);
	}
}
