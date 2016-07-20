package cn.springmvc.service;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author johsnon
 *
 */
public interface MediaService {

	// 获得图文信息
	public Map<String, List<Map<String, String>>> getNews(String mediaId) throws Exception;
}
