package cn.springmvc.service.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface CustomerService {
	public List<Map<String, String>> get(String accessToken) throws Exception;
}
