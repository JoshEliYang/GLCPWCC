package com.springmvc.utils;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * httpClient
 * 
 * @author johsnon
 *
 */
public class RequestUtil {

	/**
	 * do get request
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String doGet(String url) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);

		CloseableHttpResponse response = httpClient.execute(httpget);
		if (response.getStatusLine().getStatusCode() != 200) {
			return null;
		}
		String res = EntityUtils.toString(response.getEntity(), "UTF-8");

		if (httpClient != null) {
			httpClient.close();
		}
		return res;
	}

	/**
	 * do post request (JSON)
	 * @param url
	 * @param data
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String doPost(String url, String data) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);

		StringEntity entity = new StringEntity(data, "UTF-8");
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		httpPost.setEntity(entity);

		CloseableHttpResponse response = httpClient.execute(httpPost);
		if (response.getStatusLine().getStatusCode() != 200) {
			return null;
		}
		String res = EntityUtils.toString(response.getEntity(), "UTF-8");

		if (httpClient != null) {
			httpClient.close();
		}
		return res;
	}
}
