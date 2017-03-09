package cn.springmvc.service.mq.task;

import java.util.ArrayList;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.User;
import cn.springmvc.model.templateMesg.ThreeKeywordsMesg;
import cn.springmvc.mq.model.TemplateParameter;

public interface TicketExpiredSendService {

	public void setAdmin(User admin);

	public void setMessage(TemplateParameter message);

	public void setTaskTimestamp(String taskTimestamp);

	public void send(String taskTimestamp, User admin, TemplateParameter message);

	public void pushToUser(final ArrayList<ThreeKeywordsMesg> words, final BasicModel basicModel,
			final String templateId) throws Exception;

}
