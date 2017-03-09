package cn.springmvc.service.mq.task;

import cn.springmvc.model.User;
import cn.springmvc.model.voucher.BindingMessageModel;

public interface VoucheBindingService {
	public void send(User admin, BindingMessageModel message, String taskTimestamp);
}
