package cn.springmvc.service;

import cn.springmvc.model.BasicModel;

/**
 * 
 * @author johsnon
 *
 */
public interface WechartService {
	public String getAccessToken(BasicModel basicModel) throws Exception;
}
