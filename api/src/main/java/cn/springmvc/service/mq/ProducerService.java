package cn.springmvc.service.mq;

import cn.springmvc.model.TaskRequest;

/**
 * 将消息发送至消息队列
 * 
 * @author johnson
 *
 */
public interface ProducerService {
	
	/**
	 * 目前仅用于测试
	 * 
	 * @param mesg
	 */
	public void sendMessage(final String mesg);

	/**
	 * 发送TaskRequest
	 * 
	 * @param task
	 */
	public void send(final TaskRequest task);
}
