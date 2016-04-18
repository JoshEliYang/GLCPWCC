package cn.springmvc.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.utils.ShaUtil;
import com.springmvc.utils.XMLUtils;

import cn.springmvc.Consts;
import cn.springmvc.KeyWords;
import cn.springmvc.model.WechartModel;
import cn.springmvc.service.MessageService;
import cn.springmvc.service.UserService;

/**
 * 
 * @author johsnon
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/wechart")
public class WechartController {
	@Autowired
	public MessageService messageService;
	@Autowired
	public UserService userService;

	Logger logger = Logger.getLogger(WechartController.class);

	/**
	 * 签名验证
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public String checkSignature(HttpServletRequest request, HttpServletResponse response) {

		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");

		logger.error("signature->>" + signature);
		logger.error("timestamp->>" + timestamp);
		logger.error("nonce->>" + nonce);
		logger.error("echostr->>" + echostr);

		String[] str = new String[3];
		str[0] = Consts.TOKEN;
		str[1] = timestamp;
		str[2] = nonce;
		Arrays.sort(str);

		String plainCode = str[0] + str[1] + str[2];
		try {
			String cipher = ShaUtil.encryptSHA(plainCode);
			logger.error("plain code->>" + plainCode);
			logger.error("SHA-1 cipher-<<" + cipher);

			if (cipher.equals(signature)) {
				logger.error("check OK");
			} else {
				logger.error("check error");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("exception occured");
		}

		return echostr;
	}

	/**
	 * 微信请求
	 * 
	 * @param request
	 * @param response
	 * @param inXml
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public void doRequest(HttpServletRequest request, HttpServletResponse response, @RequestBody String inXml) {

		logger.error("xml >>> \n" + inXml);

		WechartModel model = null;
		String openId = null;

		try {
			model = XMLUtils.xml2Object(WechartModel.class, inXml);
			if (model == null) {
				logger.error("xml2Object error with null result");
				// return "success";
				response.getOutputStream().write("success".getBytes("UTF-8"));
				return;
			}

			// 获得用户openID
			openId = model.getFromUserName();

			if ("text".equalsIgnoreCase(model.getMsgType())) {
				// 处理文本消息
				String mesgResult = messageService.textProcess(model.getContent(), model.getMsgId());
				if (mesgResult != null) {
					if (mesgResult.startsWith("media_id_")) {
						mesgResult = mesgResult.substring(9);
						response.getOutputStream()
								.write(messageService.sendPictureText(mesgResult, openId).getBytes("UTF-8"));
						return;
					}
					if (mesgResult.startsWith("text_")) {
						mesgResult = mesgResult.substring(5);
						response.getOutputStream().write(messageService.sendText(mesgResult, openId).getBytes("UTF-8"));
						return;
					}
				}
			}

			if ("event".equalsIgnoreCase(model.getMsgType())) {
				if ("subscribe".equalsIgnoreCase(model.getEvent())) {
					// 未关注用户事件
					logger.error("subscribe OK");

					if (model.getEventKey() != "") {
						// 为关注用户 扫码事件
						String eventKey = model.getEventKey();
						if (eventKey.startsWith("qrscene_")) {
							String scene = eventKey.substring(8);
							logger.error("sence id <<< " + scene);
							userService.moveToGroup(openId, scene);
						}
						logger.error("SCAN 2 OK");
					}

					String subscribeReply=KeyWords.getInstance().REPLY_SUBSCRIBE;
					if(subscribeReply.startsWith("text_"))
						subscribeReply=subscribeReply.substring(5);
					if(subscribeReply.startsWith("media_id_"))
						subscribeReply=subscribeReply.substring(9);
					
					response.getOutputStream().write(
							messageService.sendText(subscribeReply, openId).getBytes("UTF-8"));
					return;
				} else if ("SCAN".equals(model.getEvent())) {
					// 已关注用户扫码事件
					logger.error("SCAN OK");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("xml2Object error >>> \n" + e.getLocalizedMessage());
		}

		// return "success";
		try {
			response.getOutputStream().write("success".getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
