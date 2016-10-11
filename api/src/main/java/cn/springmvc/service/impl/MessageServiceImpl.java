package cn.springmvc.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.dao.KeywordsDao;
import cn.springmvc.model.BasicModel;
import cn.springmvc.model.Keywords;
import cn.springmvc.service.MediaService;
import cn.springmvc.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {
	@Autowired
	public MediaService mediaService;

	@Autowired
	private KeywordsDao keywordsDao;

	Logger logger = Logger.getLogger(MessageServiceImpl.class);

	// 回复文本消息
	public String sendText(String msg, String toUser, BasicModel basicModel) throws Exception {
		String xml = "<xml>" + "<ToUserName><![CDATA[" + toUser + "]]></ToUserName>"
		// +
		// "<FromUserName><![CDATA["+Consts.WECHART_ACCOUNT+"]]></FromUserName>"
				+ "<FromUserName><![CDATA[" + basicModel.getWechatAccount() + "]]></FromUserName>" + "<CreateTime>"
				+ (System.currentTimeMillis() / 1000) + "</CreateTime>" + "<MsgType><![CDATA[text]]></MsgType>"
				+ "<Content><![CDATA[" + msg + "]]></Content>" + "</xml>";
		// System.out.println(xml);
		logger.error("generate xml >>> " + xml);
		return new String(xml.getBytes(), "UTF-8");
	}

	// 回复图文消息
	public String sendPictureText(String mediaId, String toUser, BasicModel basicModel) throws Exception {
		Map<String, List<Map<String, String>>> mapx = mediaService.getNews(mediaId, basicModel);
		List<Map<String, String>> list = mapx.get("news_item");

		String xml = "<xml>" + "<ToUserName><![CDATA[" + toUser + "]]></ToUserName>"
		// +
		// "<FromUserName><![CDATA["+Consts.WECHART_ACCOUNT+"]]></FromUserName>"
				+ "<FromUserName><![CDATA[" + basicModel.getWechatAccount() + "]]></FromUserName>" + "<CreateTime>"
				+ (System.currentTimeMillis() / 1000) + "</CreateTime>" + "<MsgType><![CDATA[news]]></MsgType>"
				+ "<ArticleCount>" + list.size() + "</ArticleCount>" + "<Articles>";

		for (int i = 0; i < list.size(); i++) {
			Map<String, String> newsItem = list.get(i);

			xml += "<item>" + "<Title><![CDATA[" + newsItem.get("title") + "]]></Title> " + "<Description><![CDATA["
					+ newsItem.get("digest") + "]]></Description>" + "<PicUrl><![CDATA[" + newsItem.get("thumb_url")
					+ "]]></PicUrl>" + "<Url><![CDATA[" + newsItem.get("url") + "]]></Url>" + "</item>";
		}

		xml += "</Articles>" + "</xml>";

		logger.error("generate xml >>> " + xml);
		return new String(xml.getBytes(), "UTF-8");
	}
	
	
	public String sendPicture(String mediaId,String toUser,BasicModel basicModel)throws Exception{
		String xml="<xml>"
				+ "<ToUserName><![CDATA["+toUser+"]]></ToUserName>"
				+ "<FromUserName><![CDATA["+basicModel.getWechatAccount()+"]]></FromUserName>"
				+ "<CreateTime>"+(System.currentTimeMillis() / 1000)+"</CreateTime>"
				+ "<MsgType><![CDATA[image]]></MsgType>"
				+ "<Image>"
				+ "<MediaId><![CDATA["+mediaId+"]]></MediaId>"
				+ "</Image>"
				+ "</xml>";
		
		logger.error("generate xml >>> " + xml);
		return new String(xml.getBytes(), "UTF-8");
	}

	// 转发消息到客服
	public String transferToCustomerService(String toUser, BasicModel basicModel) throws Exception {
		String xml = "<xml>" + "<ToUserName><![CDATA[" + toUser + "]]></ToUserName>"
		// +
		// "<FromUserName><![CDATA["+Consts.WECHART_ACCOUNT+"]]></FromUserName>"
				+ "<FromUserName><![CDATA[" + basicModel.getWechatAccount() + "]]></FromUserName>" + "<CreateTime>"
				+ (System.currentTimeMillis() / 1000) + "</CreateTime>"
				+ "<MsgType><![CDATA[transfer_customer_service]]></MsgType>" + "</xml>";

		logger.error("generate transfer xml >>> " + xml);
		return new String(xml.getBytes(), "UTF-8");
	}

	/**
	 * 处理文本消息 return msg(from wechart.porperties)
	 */
	public Keywords textProcess(String msg, String msgId, BasicModel basicModel) throws Exception {
		logger.error(" message text >>> \n" + "msgId:" + msgId + "\n" + msg);

		List<Keywords> keywordsList = keywordsDao.getAllExceptUnused(basicModel.getId());
		for (int i = 0; i < keywordsList.size(); i++) {
			if (msg.indexOf(keywordsList.get(i).getValue()) >= 0) {
				return keywordsList.get(i);
			}
		}

		// /**
		// * 获得关键字列表
		// */
		// Iterator<Entry<String,String>>
		// iter=KeyWords.getInstance().WORDS.entrySet().iterator();
		// while(iter.hasNext()){
		// Map.Entry<String, String> entry=(Entry<String, String>) iter.next();
		// String key = entry.getKey();
		// String value=entry.getValue();
		//
		// if(msg.indexOf(key)>=0){
		// return null;
		// }
		// }

		return null;
	}

}
