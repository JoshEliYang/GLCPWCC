package cn.springmvc.service.impl.mq;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.springmvc.model.TaskRequest;
import cn.springmvc.model.TaskResponse;
import cn.springmvc.model.templateMesg.Coupon;
import cn.springmvc.model.templateMesg.TemplateParameter;
import cn.springmvc.model.templateMesg.ThreeKeywordsMesg;
import cn.springmvc.service.impl.mq.task.CouponMessageSendServiceImpl;
import cn.springmvc.service.impl.mq.task.TicketExpiredSendServiceImpl;
import cn.springmvc.service.mq.ProducerService;
import jxl.common.Logger;

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

	@Resource(name = "topicTemplate")
	private JmsTemplate topicTemplate;

	Logger logger = Logger.getLogger(ProducerServiceImpl.class);

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
	public void sendToQueue(final TaskRequest task) {
		jsmTemplate.send(new MessageCreator() {
			public Message createMessage(Session arg0) throws JMSException {
				TemplateParameter templateTask = JSON.parseObject(task.getParameter(), TemplateParameter.class);

				if (task.getMethod().equals("SendTemplateMessage")) {
					try {
						ArrayList<Coupon> couponList = CouponMessageSendServiceImpl
								.getCoupon(templateTask.getFilePath());
						templateTask.setFourWordsList(couponList);
						task.setParameter(JSON.toJSONString(templateTask));
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("error occurred when reading excel in template message >>>>>>> " + e.getMessage());
					}

				} else if (task.getMethod().equals("TicketExpiredMessage")) {
					try {
						ArrayList<ThreeKeywordsMesg> words = TicketExpiredSendServiceImpl
								.getExcel(templateTask.getFilePath());
						templateTask.setThreeWordsList(words);
						task.setParameter(JSON.toJSONString(templateTask));
					} catch (Exception e) {
						e.printStackTrace();
						logger.error("error occurred when reading excel in TicketExpiredTask >>> " + e.getMessage()
								+ e.getStackTrace());
					}
				}
				return arg0.createTextMessage(JSON.toJSONString(task));
			}
		});
	}

	/**
	 * 发送到WebSocket广播队列
	 */
	public void sendToBroadcast(final TaskResponse task) {
		topicTemplate.send(new MessageCreator() {
			public Message createMessage(Session arg0) throws JMSException {
				return arg0.createTextMessage(JSON.toJSONString(task));
			}
		});
	}

}
