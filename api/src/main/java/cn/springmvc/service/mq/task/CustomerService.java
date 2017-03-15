package cn.springmvc.service.mq.task;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.User;

public interface CustomerService {
	public void send(User admin,BasicModel basicModel, String taskTimestamp);
}
