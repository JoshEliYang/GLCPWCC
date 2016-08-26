package com.springmvc.utils;

import java.util.Random;

public class GLCPStringUtils {

	public static boolean isNull(String string){
		if(string == null){
			return true;
		}
		
		string = string.trim();
		if(string.isEmpty()){
			return true;
		}
		
		if ("null".equalsIgnoreCase(string)) {
			return true;
		}
		
		return false;
	}
	
	public static boolean notNull(String string){
		return !isNull(string);
	}
	
	public static String ignoreNotNum(String phone){
		if(notNull(phone)){
			phone = phone.replaceAll("[^\\d]", "");
		}
		
		return phone;
	}
	
	public static String generateRandomDigits(int digCount){
		StringBuilder sb = new StringBuilder(digCount);
		Random rnd = new Random();

		for(int i=0; i < digCount; i++) {
			sb.append((char)('0' + rnd.nextInt(10)));
		}

		return sb.toString();
	}
}
