package com.springmvc.utils;

import java.util.HashMap;
import java.util.Map;

public class HttpUtils {
	
	public static Map<String, Object> generateResponse(String code,
			String msg,
			Object info) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("code", code);
		response.put("msg", msg);
		response.put("data", info);
		
		return response;
	}

}
