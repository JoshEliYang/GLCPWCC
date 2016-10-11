package cn.springmvc.service;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.Keywords;

/**
 * 
 * @author johsnon
 *
 */
public interface MessageService {

	// 回复文本消息
	public String sendText(String msg, String toUser, BasicModel basicModel) throws Exception;

	// 回复图文消息
	public String sendPictureText(String mediaId, String toUser, BasicModel basicModel) throws Exception;
	
	// 回复图片消息
	public String sendPicture(String mediaId,String toUser,BasicModel basicModel)throws Exception;

	// 转发消息到客服
	public String transferToCustomerService(String toUser, BasicModel basicModel) throws Exception;

	// 处理文本消息
	public Keywords textProcess(String msg, String msgId, BasicModel basicModel) throws Exception;
}
