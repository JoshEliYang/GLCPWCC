package cn.springmvc.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.springmvc.Consts;
import cn.springmvc.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

	Logger logger=Logger.getLogger(MessageServiceImpl.class);
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
		logger.error("generate xml >>> "+xml);
		return new String(xml.getBytes(),"UTF-8");
	}
	
	public static void main(String args[]) throws Exception{
		MessageServiceImpl test=new MessageServiceImpl();
		System.out.println(test.sendText("test OK", "123456789"));
	}
}
