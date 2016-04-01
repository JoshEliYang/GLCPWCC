package cn.springmvc.service;

/**
 * 
 * @author johsnon
 *
 */
public interface MessageService {
	
	// 回复文本消息
	public String sendText(String msg,String toUser)throws Exception;
}
