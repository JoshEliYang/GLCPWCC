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
import com.springmvc.utils.mongodb.model.MongoConfig;

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

	public MongoDBUtil(MongoConfig mongoConfig) {
		this.SERVER_ADDR = mongoConfig.getSERVER_ADDR();
		this.SERVER_PORT = mongoConfig.getSERVER_PORT();
		this.USER_NAME = mongoConfig.getUSER_NAME();
		this.DB_NAME = mongoConfig.getDB_NAME();
		this.DB_PWD = mongoConfig.getDB_PWD();
	}

	/**
	 * establish the connection with mongoDB server
	 */
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

	/**
	 * close the connection with mongoDB server
	 */
	public void destory() {
		if (mongoClient != null)
			mongoClient.close();
	}

}
