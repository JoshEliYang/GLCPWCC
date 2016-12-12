package cn.springmvc.mq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.alibaba.fastjson.JSON;

import cn.springmvc.model.TaskRequest;

/**
 * 
 * @author johnson
 *
 */
public class MqSender {

	public static void sender(TaskRequest task) {
		ConnectionFactory connectionFactory;
		Connection connection = null;
		Session session;
		Destination destination;
		MessageProducer producer;

		MqUtility mqUtil = MqUtility.getInstance();
		connectionFactory = new ActiveMQConnectionFactory(mqUtil.getUserName(), mqUtil.getPassword(),
				mqUtil.getBrokerURL());

		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue("task_queue");
			producer = session.createProducer(destination);
			// producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			TextMessage message = session.createTextMessage(JSON.toJSONString(task));
			producer.send(message);
			session.commit();
		} catch (Exception e) {
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
