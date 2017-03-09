package cn.springmvc.service.mq.task;

import cn.springmvc.model.User;
import cn.springmvc.model.templateMesg.TemplateParameter;

public interface CouponMessageSendService {

	public void send(String taskTimestamp, User admin, TemplateParameter message);
}
