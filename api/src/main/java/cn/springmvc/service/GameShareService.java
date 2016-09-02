package cn.springmvc.service;

import java.util.Map;


public interface GameShareService {
	public Map<String, String> getTicket(String url) throws Exception;
}
