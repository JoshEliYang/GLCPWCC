package cn.springmvc.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.TaskRequest;
import cn.springmvc.model.templateMesg.TemplateParameter;
import cn.springmvc.model.voucher.BindingMessageModel;
import cn.springmvc.service.mq.task.CouponMessageSendService;
import cn.springmvc.service.mq.task.CustomerService;
import cn.springmvc.service.mq.task.TicketExpiredSendService;
import cn.springmvc.service.mq.task.VoucheBindingService;

/**
 * 消息队列监听器
 * 
 * @author johnson
 *
 */
public class QueueMessageListener implements MessageListener {

	/* 兑换券消息  --已废弃 */
	@Autowired
	private CouponMessageSendService couponMessage;
	/* 服务券到期消息 */
	@Autowired
	private TicketExpiredSendService ticketExpiredMessage;
	/* 绑定优惠券任务 */
	@Autowired
	private VoucheBindingService voucheBindingService;
	/* 刷新微信用户任务 */
	@Autowired
	private CustomerService customerService;
	
	Logger logger=Logger.getLogger(QueueMessageListener.class);

	public void onMessage(Message message) {
		TextMessage tm = (TextMessage) message;
		try {
			TaskRequest task = JSON.parseObject(tm.getText(), TaskRequest.class);

			if ("SendTemplateMessage".equals(task.getMethod())) {
				/* 兑换券消息  --已废弃 */
				TemplateParameter taskParameter = JSON.parseObject(task.getParameter(), TemplateParameter.class);
				couponMessage.send(task.getTaskTimeStamp(), task.getAdmin(), taskParameter);
			} else if ("TicketExpiredMessage".equals(task.getMethod())) {
				/* 服务券到期消息 */
				TemplateParameter taskParameter = JSON.parseObject(task.getParameter(), TemplateParameter.class);
				ticketExpiredMessage.send(task.getTaskTimeStamp(), task.getAdmin(), taskParameter);
			} else if ("VoucherBindingMessage".equals(task.getMethod())) {
				/* 绑定优惠券任务 */
				BindingMessageModel taskParameter = JSON.parseObject(task.getParameter(), BindingMessageModel.class);
				voucheBindingService.send(task.getAdmin(), taskParameter, task.getTaskTimeStamp());
			} else if ("CustomerRefreshMessage".equals(task.getMethod())) {
				/* 刷新微信关注用户任务 */
				BasicModel basicModel = JSON.parseObject(task.getParameter(), BasicModel.class);
				customerService.send(task.getAdmin(), basicModel, task.getTaskTimeStamp());
			}

		} catch (JMSException e) {
			e.printStackTrace();
			logger.error("error occured in QueuemessageListemer >>>> "+e.getMessage());
		}
	}

}
