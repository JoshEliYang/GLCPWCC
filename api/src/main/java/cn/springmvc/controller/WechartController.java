package cn.springmvc.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.utils.ShaUtil;
import com.springmvc.utils.XMLUtils;

import cn.springmvc.dao.CustomerDao;
import cn.springmvc.model.BasicModel;
import cn.springmvc.model.Keywords;
import cn.springmvc.model.MsgType;
import cn.springmvc.model.WechartModel;
import cn.springmvc.model.WechatUser;
import cn.springmvc.service.basic.BasicService;
import cn.springmvc.service.function.KeywordsService;
import cn.springmvc.service.manage.UserService;
import cn.springmvc.service.mq.task.CustomerService;
import cn.springmvc.service.wechat.MessageService;
import cn.springmvc.service.wechat.MsgTypeService;
import cn.springmvc.service.wechat.SubscribeCountService;

/**
 * 
 * @author johsnon
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/wechat")
public class WechartController {
	@Autowired
	public MessageService messageService;
	@Autowired
	public UserService userService;
	@Autowired
	public BasicService basicService;
	@Autowired
	private MsgTypeService msgTypeService;
	@Autowired
	private KeywordsService keywordsService;

	@Autowired
	private SubscribeCountService subCountService;

	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerDao customerDao;

	Logger logger = Logger.getLogger(WechartController.class);

	/**
	 * 签名验证
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/{account}", method = RequestMethod.GET)
	public String checkSignature(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String account) {
		BasicModel basicModel = null;
		try {
			basicModel = basicService.getByUrl(account);
			logger.error("get basicService success ->>" + basicModel);
		} catch (Exception e) {
			logger.error("get basicService failed");
		}

		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");

		logger.error("signature->>" + signature);
		logger.error("timestamp->>" + timestamp);
		logger.error("nonce->>" + nonce);
		logger.error("echostr->>" + echostr);

		String[] str = new String[3];
		str[0] = basicModel.getToken();
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
	@RequestMapping(value = "/{account}", method = RequestMethod.POST)
	public void doRequest(HttpServletRequest request, HttpServletResponse response, @RequestBody String inXml,
			@PathVariable String account) {
		BasicModel basicModel = null;
		try {
			basicModel = basicService.getByUrl(account);
			logger.error("get basicService success ->>" + basicModel);
		} catch (Exception e) {
			logger.error("get basicService failed");
		}

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
				Keywords mesgResult = messageService.textProcess(model.getContent(), model.getMsgId(), basicModel);

				if (mesgResult != null) {
					logger.error(
							"msgType = " + mesgResult.getMsgType() + " type name = " + mesgResult.getMsgTypeName());
					MsgType msgType = msgTypeService.getById(mesgResult.getId());

					if ("text".equals(msgTypeService.getById(mesgResult.getMsgType()).getMsgType())) {
						logger.error("in text process");
						response.getOutputStream().write(
								messageService.sendText(mesgResult.getReply(), openId, basicModel).getBytes("UTF-8"));
						return;
					} else if ("news".equals(msgTypeService.getById(mesgResult.getMsgType()).getMsgType())) {
						response.getOutputStream().write(messageService
								.sendPictureText(mesgResult.getReply(), openId, basicModel).getBytes("UTF-8"));
						return;
					} else if ("image".equals(msgTypeService.getById(mesgResult.getMsgType()).getMsgType())) {
						response.getOutputStream().write(messageService
								.sendPicture(mesgResult.getReply(), openId, basicModel).getBytes("UTF-8"));
						return;
					}

				}

				// 转发给客服
				response.getOutputStream()
						.write(messageService.transferToCustomerService(openId, basicModel).getBytes("UTF-8"));
				return;
			}

			if ("image".equalsIgnoreCase(model.getMsgType())) {
				// 图片消息
				// 直接转发给客服
				response.getOutputStream()
						.write(messageService.transferToCustomerService(openId, basicModel).getBytes("UTF-8"));
				return;
			}

			if ("voice".equalsIgnoreCase(model.getMsgType())) {
				// 语言消息
				// 直接转发给客服
				response.getOutputStream()
						.write(messageService.transferToCustomerService(openId, basicModel).getBytes("UTF-8"));
				return;
			}

			if ("video".equalsIgnoreCase(model.getMsgType())) {
				// 视频消息
				// 直接转发给客服
				response.getOutputStream()
						.write(messageService.transferToCustomerService(openId, basicModel).getBytes("UTF-8"));
				return;
			}

			if ("shortvideo".equalsIgnoreCase(model.getMsgType())) {
				// 小视频消息
				// 直接转发给客服
				response.getOutputStream()
						.write(messageService.transferToCustomerService(openId, basicModel).getBytes("UTF-8"));
				return;
			}

			if ("location".equalsIgnoreCase(model.getMsgType())) {
				// 地理位置消息
				// 直接转发给客服
				response.getOutputStream()
						.write(messageService.transferToCustomerService(openId, basicModel).getBytes("UTF-8"));
				return;
			}

			if ("link".equalsIgnoreCase(model.getMsgType())) {
				// 连接消息
				// 直接转发给客服
				response.getOutputStream()
						.write(messageService.transferToCustomerService(openId, basicModel).getBytes("UTF-8"));
				return;
			}

			if ("event".equalsIgnoreCase(model.getMsgType())) {
				if ("subscribe".equalsIgnoreCase(model.getEvent())) {
					// 未关注用户事件
					logger.error("subscribe OK");

					if (model.getEventKey() != "" && model.getEventKey() != null && !model.getEventKey().isEmpty()) {
						// 未关注用户 扫码事件
						String eventKey = model.getEventKey();
						if (eventKey.startsWith("qrscene_")) {
							String scene = eventKey.substring(8);
							logger.error("sence id <<< " + scene);
							userService.moveToGroup(openId, scene, basicModel);
							subCountService.addSubscribe(Integer.parseInt(scene), basicModel);
						}
						logger.error("SCAN 2 OK");
					} else {
						// 用户直接关注事件
						subCountService.addSubscribe(-1, basicModel);
					}

					/**
					 * 新用户关注，加入customer表。 已存在用户，更新customer表。
					 */
					List<String> openIdList = new ArrayList<String>();
					openIdList.add(openId);
					Map<String, Object> customersMap = customerService.getUserInfo(basicModel, openIdList);
					customerService.refreshUserInfo((List<Map<String, Object>>) customersMap.get("user_info_list"));

					/**
					 * 返回关注自动回复内容
					 */
					Keywords keyword = keywordsService.getSubscribe(basicModel);
					if (keyword.getMsgType() == 1) {
						// reply text message
						response.getOutputStream().write(
								messageService.sendText(keyword.getReply(), openId, basicModel).getBytes("UTF-8"));
					} else if (keyword.getMsgType() == 6) {
						// reply news message
						response.getOutputStream().write(messageService
								.sendPictureText(keyword.getReply(), openId, basicModel).getBytes("UTF-8"));
					}
					return;
				} else if ("SCAN".equals(model.getEvent())) {
					// 已关注用户扫码事件
					logger.error("SCAN OK");

					/**
					 * 新用户关注，加入customer表。 已存在用户，更新customer表。
					 */
					List<String> openIdList = new ArrayList<String>();
					openIdList.add(openId);
					Map<String, Object> customersMap = customerService.getUserInfo(basicModel, openIdList);
					customerService.refreshUserInfo((List<Map<String, Object>>) customersMap.get("user_info_list"));

					/**
					 * 返回关注自动回复内容
					 */
					Keywords keyword = keywordsService.getSubscribe(basicModel);
					if (keyword.getMsgType() == 1) {
						// reply text message
						response.getOutputStream().write(
								messageService.sendText(keyword.getReply(), openId, basicModel).getBytes("UTF-8"));
					} else if (keyword.getMsgType() == 6) {
						// reply news message
						response.getOutputStream().write(messageService
								.sendPictureText(keyword.getReply(), openId, basicModel).getBytes("UTF-8"));
					}
					return;

				} else if ("unsubscribe".equals(model.getEvent())) {
					// 取消关注事件
					WechatUser userInfo = userService.getUserInfo(openId, basicModel);
					subCountService.addUnsubscribe(userInfo.getTagid_list(), basicModel);

					customerDao.unscribe(openId, System.currentTimeMillis());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("xml2Object error >>> \n" + e.getMessage());
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
