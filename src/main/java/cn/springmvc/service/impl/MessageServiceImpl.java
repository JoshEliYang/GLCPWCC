package cn.springmvc.service.impl;

import org.springframework.stereotype.Service;

import cn.springmvc.Consts;
import cn.springmvc.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

	
	// 回复文本消息
	public String sendText(String msg, String toUser) throws Exception {
		String xml="<xml>"
				+ "<ToUserName><![CDATA["+toUser+"]]></ToUserName>"
				+ "<FromUserName><![CDATA["+Consts.WECHART_ACCOUNT+"]]></FromUserName>"
				+ "<CreateTime>"+( System.currentTimeMillis() / 1000)+"</CreateTime>"
				+ "<MsgType><![CDATA[text]]></MsgType>"
				+ "<Content><![CDATA["+msg+"]]></Content>"
				+ "</xml>";
//		System.out.println(xml);
		return xml;
	}
	
	public static void main(String args[]) throws Exception{
		MessageServiceImpl test=new MessageServiceImpl();
		test.sendText("test OK", "123456789");
	}
}
