package cn.springmvc.mq.task;

import com.alibaba.fastjson.JSON;

import cn.springmvc.model.User;
import cn.springmvc.mq.model.TemplateParameter;

public class VoucheBindingTask implements Runnable {
	User admin;
	TemplateParameter message;
	String taskTimestamp;
	
	public VoucheBindingTask(User admin, TemplateParameter message, String taskTimestamp){
		this.admin = admin;
		this.message = message;
		this.taskTimestamp = taskTimestamp;
	}

	public void run() {
		// TODO Auto-generated method stub
		
		
	}
	
	public String genarateJson(){
		return null;
	}
	
}
