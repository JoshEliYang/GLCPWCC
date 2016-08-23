package cn.springmvc.service;

import java.util.Map;

import cn.springmvc.model.BasicModel;

public interface GameShareService {
	public Map<String, String> getTicket(BasicModel basicModel) throws Exception;
}
