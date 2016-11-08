package cn.springmvc.service.game;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

public interface GameLicenseService {
	public Map<String, String> getUserInfo(String code) throws ClientProtocolException, IOException;

}
