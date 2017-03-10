package cn.springmvc.service.impl.mq;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.springmvc.model.TaskRequest;
import cn.springmvc.service.mq.ProducerService;

/**
 * 将消息发送至消息队列
 * 
 * @author johnson
 *
 */
@Service
public class ProducerServiceImpl implements ProducerService {

	@Resource(name = "jmsTemplate")
	private JmsTemplate jsmTemplate;

	/**
	 * 目前仅用于测试
	 */
	public void sendMessage(final String mesg) {
		System.out.println("send mesg: " + mesg);
		jsmTemplate.send(new MessageCreator() {
			public Message createMessage(Session arg0) throws JMSException {
				return arg0.createTextMessage(mesg);
			}
		});
	}

	/**
	 * 发送TaskRequest
	 */
	public void send(final TaskRequest task) {
		jsmTemplate.send(new MessageCreator() {
			public Message createMessage(Session arg0) throws JMSException {
				return arg0.createTextMessage(JSON.toJSONString(task));
			}
		});
	}

}
