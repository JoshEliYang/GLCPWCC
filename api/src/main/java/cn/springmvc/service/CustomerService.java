package cn.springmvc.service;

import java.util.Map;


import cn.springmvc.model.BasicModel;

public interface CustomerService {
	public String getAll(int page,BasicModel basicModel) throws Exception;

	public String test() throws Exception;
}
