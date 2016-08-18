package cn.springmvc.service;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

public interface gameLicenseService {
	public Map<String, String> getUserInfo(String code) throws ClientProtocolException, IOException;

}
