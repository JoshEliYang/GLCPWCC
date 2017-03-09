package cn.springmvc.service.mq;

import cn.springmvc.model.TaskRequest;

public interface ProducerService {
	public void sendMessage(final String mesg);

	public void send(final TaskRequest task);
}
