package cn.springmvc.service.wechat;

import cn.springmvc.model.BasicModel;

/**
 * 
 * @author johsnon
 *
 */
public interface WechartService {
	public String getAccessToken(BasicModel basicModel) throws Exception;

	public String getAccessTokenByServer(BasicModel basicModel) throws Exception;
}
