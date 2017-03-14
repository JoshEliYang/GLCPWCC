package cn.springmvc.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

import cn.springmvc.model.TaskResponse;
import cn.springmvc.websocket.ProgressSocket;

/**
 * webSocket 广播队列监听器
 * 
 * @author johnson
 *
 */
public class TopicMessageListener implements MessageListener {

	Logger logger = Logger.getLogger(TopicMessageListener.class);

	public void onMessage(Message message) {
		TextMessage tm = (TextMessage) message;
		try {
			TaskResponse task = JSON.parseObject(tm.getText(), TaskResponse.class);
			/* 通过WebSocet广播 */
			ProgressSocket.broadcast(task);
		} catch (JMSException e) {
			e.printStackTrace();
			logger.error("error occurred in TopicMessageListener >>> " + e.getMessage());
		}
	}

}
