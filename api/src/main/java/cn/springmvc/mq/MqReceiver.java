package cn.springmvc.mq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

import cn.springmvc.model.TaskRequest;
import cn.springmvc.mq.model.TemplateParameter;
import cn.springmvc.mq.task.TemplateMessageTask;

/**
 * 
 * @author johnson
 *
 */
public class MqReceiver implements Runnable {

	// ConnectionFactory ：连接工厂，JMS 用它创建连接
	ConnectionFactory connectionFactory;
	// Connection ：JMS 客户端到JMS Provider 的连接
	Connection connection = null;
	// Session： 一个发送或接收消息的线程
	Session session;
	// Destination ：消息的目的地;消息发送给谁.
	Destination destination;
	// 消费者，消息接收者
	MessageConsumer consumer;

	Logger logger = Logger.getLogger(MqReceiver.class);

	public void run() {
		MqUtility mqUtil = MqUtility.getInstance();
		connectionFactory = new ActiveMQConnectionFactory(mqUtil.getUserName(), mqUtil.getPassword(),
				mqUtil.getBrokerURL());

		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue("task_queue");
			consumer = session.createConsumer(destination);
			while (true) {
				TextMessage message = (TextMessage) consumer.receive(100000);
				if (null != message) {
					logger.error("get mq message >>> " + message.getText());
					TaskRequest task = JSON.parseObject(message.getText(), TaskRequest.class);

					/**
					 * do send template message task
					 */
					if ("SendTemplateMessage".equals(task.getMethod())) {
						TemplateParameter taskParameter = JSON.parseObject(task.getParameter(),
								TemplateParameter.class);
						Thread templateMessageThread = new Thread(
								new TemplateMessageTask(task.getTaskTimeStamp(), task.getAdmin(), taskParameter));
						templateMessageThread.start();
					}
				}
			}
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != connection)
					connection.close();
			} catch (Throwable ignore) {
				ignore.printStackTrace();
			}
		}

	}

}
