package cn.springmvc.service.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
	
	/**
	 * 处理文本消息
	 * return msg(from wechart.porperties)
	 */
	public String textProcess(String msg, String msgId) throws Exception{
		logger.error(" message text >>> \n"+"msgId:"+msgId+"\n"+msg);
		
		/**
		 * 获得关键字列表
		 */
		Iterator iter=Consts.KEY_WORDS.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry<String, String> entry=(Entry<String, String>) iter.next();
			String key = entry.getKey();
			
			if(msg.indexOf(key)>=0){
				return Consts.KEY_WORDS.get(key);
			}
		}
		
		return null;
	}
	
	public static void main(String args[]) throws Exception{
		MessageServiceImpl test=new MessageServiceImpl();
		System.out.println(test.sendText("test OK", "123456789"));
	}
}
