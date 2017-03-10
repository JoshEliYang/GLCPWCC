package cn.springmvc.service.mq.task;

import cn.springmvc.model.User;
import cn.springmvc.model.templateMesg.TemplateParameter;

/**
 * 处理兑换券模板消息
 * 
 * 已废弃
 * 
 * @author johnson
 *
 */
public interface CouponMessageSendService {

	public void send(String taskTimestamp, User admin, TemplateParameter message);
}
