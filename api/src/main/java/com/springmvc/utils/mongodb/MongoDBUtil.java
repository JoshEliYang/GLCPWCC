package com.springmvc.utils.mongodb;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

/**
 * 
 * @author johnson
 *
 */
public class MongoDBUtil {

	private String SERVER_ADDR;
	private int SERVER_PORT;
	private String USER_NAME;
	private String DB_NAME;
	private String DB_PWD;

	private MongoClient mongoClient = null;
	private MongoDatabase mongoDatabase = null;

	public void setup() {
		ServerAddress addr = new ServerAddress(SERVER_ADDR, SERVER_PORT);

		MongoCredential credential = MongoCredential.createScramSha1Credential(USER_NAME, DB_NAME,
				DB_PWD.toCharArray());

		List<MongoCredential> credentials = new ArrayList<MongoCredential>();
		credentials.add(credential);

		MongoClientOptions options = MongoClientOptions.builder().serverSelectionTimeout(1000).build();
		mongoClient = new MongoClient(addr, Arrays.asList(credential), options);

		mongoDatabase = mongoClient.getDatabase(DB_NAME);
	}

	/**
	 * 
	 * @param collectionName
	 * @param obj
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public <T> void insert(String collectionName, T obj)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
		Document document = new Document();

		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			String key = field.getName();
			field.setAccessible(true);
			Object value = field.get(obj);
			document.append(key, value);
		}

		List<Document> documents = new ArrayList<Document>();
		documents.add(document);

		collection.insertMany(documents);
	}

	public void test() {
		MongoCollection<Document> collection = mongoDatabase.getCollection("operate_log");
		System.out.println("集合 test 选择成功");

		FindIterable<Document> findIterable = collection.find();
		MongoCursor<Document> mongoCursor = findIterable.iterator();
		while (mongoCursor.hasNext()) {
			System.out.println(mongoCursor.next());
		}
	}

	public <T> List<T> query(Class<?> T, String collectionName) {

		return null;
	}

	public <T> List<T> query(Class<?> T, String collectionName, String startTime) {

		return null;
	}

	public <T> List<T> query(Class<?> T, String collectionName, String startTime, String endTime) {
		List<T> result = new ArrayList<T>();

		MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
		FindIterable<Document> findIterable = collection.find(new Document("datetime", new Document("$gte", startTime))
				.append("datetime", new Document("$lt", endTime)));
		MongoCursor<Document> mongoCursor = findIterable.iterator();
		while (mongoCursor.hasNext()) {
			System.out.println(mongoCursor.next());
		}
		return result;
	}

	public void destory() {
		if (mongoClient != null)
			mongoClient.close();
	}

	public String getSERVER_ADDR() {
		return SERVER_ADDR;
	}

	public void setSERVER_ADDR(String sERVER_ADDR) {
		SERVER_ADDR = sERVER_ADDR;
	}

	public int getSERVER_PORT() {
		return SERVER_PORT;
	}

	public void setSERVER_PORT(int sERVER_PORT) {
		SERVER_PORT = sERVER_PORT;
	}

	public String getUSER_NAME() {
		return USER_NAME;
	}

	public void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}

	public String getDB_NAME() {
		return DB_NAME;
	}

	public void setDB_NAME(String dB_NAME) {
		DB_NAME = dB_NAME;
	}

	public String getDB_PWD() {
		return DB_PWD;
	}

	public void setDB_PWD(String dB_PWD) {
		DB_PWD = dB_PWD;
	}

}
