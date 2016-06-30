package cn.springmvc.service;

/**
 * 
 * @author johsnon
 *
 */
public interface MessageService {

	// 回复文本消息
	public String sendText(String msg, String toUser) throws Exception;

	// 回复图文消息
	public String sendPictureText(String mediaId,String toUser)throws Exception;
	
	// 转发消息到客服
	public String transferToCustomerService(String toUser)throws Exception;
	
	// 处理文本消息
	public String textProcess(String msg, String msgId) throws Exception;
}
