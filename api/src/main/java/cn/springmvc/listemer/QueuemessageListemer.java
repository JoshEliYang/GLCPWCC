package cn.springmvc.listemer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

import cn.springmvc.model.TaskRequest;
import cn.springmvc.model.templateMesg.TemplateParameter;
import cn.springmvc.model.voucher.BindingMessageModel;
import cn.springmvc.service.mq.task.CouponMessageSendService;
import cn.springmvc.service.mq.task.TicketExpiredSendService;
import cn.springmvc.service.mq.task.VoucheBindingService;

public class QueuemessageListemer implements MessageListener {

	@Autowired
	private CouponMessageSendService couponMessage;
	@Autowired
	private TicketExpiredSendService ticketExpiredMessage;
	@Autowired
	private VoucheBindingService voucheBindingService;

	public void onMessage(Message message) {
		TextMessage tm = (TextMessage) message;
		try {
			System.out.println("queue listened: " + tm.getText());

			TaskRequest task = JSON.parseObject(tm.getText(), TaskRequest.class);

			if ("SendTemplateMessage".equals(task.getMethod())) {
				TemplateParameter taskParameter = JSON.parseObject(task.getParameter(), TemplateParameter.class);
				couponMessage.send(task.getTaskTimeStamp(), task.getAdmin(), taskParameter);
			} else if ("TicketExpiredMessage".equals(task.getMethod())) {
				TemplateParameter taskParameter = JSON.parseObject(task.getParameter(), TemplateParameter.class);
				ticketExpiredMessage.send(task.getTaskTimeStamp(), task.getAdmin(), taskParameter);
			} else if ("VoucherBindingMessage".equals(task.getMethod())) {
				BindingMessageModel taskParameter = JSON.parseObject(task.getParameter(), BindingMessageModel.class);
				voucheBindingService.send(task.getAdmin(), taskParameter, task.getTaskTimeStamp());
				// Thread VoucherBindingThread = new Thread(
				// new VoucheBindingTask(task.getAdmin(), taskParameter,
				// task.getTaskTimeStamp()));
				// VoucherBindingThread.start();
			}

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
