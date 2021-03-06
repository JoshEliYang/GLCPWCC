package cn.springmvc.service.impl.mq.task;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.springmvc.utils.ExcelUtil;
import com.springmvc.utils.RequestUtil;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.TaskResponse;
import cn.springmvc.model.User;
import cn.springmvc.model.templateMesg.Coupon;
import cn.springmvc.model.templateMesg.TemplateMessageData;
import cn.springmvc.model.templateMesg.TemplateParameter;
import cn.springmvc.service.impl.wechat.WechartServiceImpl;
import cn.springmvc.service.mq.ProducerService;
import cn.springmvc.service.mq.task.CouponMessageSendService;
import cn.springmvc.service.wechat.WechartService;

/**
 * 处理兑换券模板消息
 * 
 * 已废弃
 * 
 * @author johnson
 *
 */
@Service
public class CouponMessageSendServiceImpl implements CouponMessageSendService {

	User admin;
	TemplateParameter message;
	String taskTimestamp;

	@Autowired
	private ProducerService mqProducer;

	Logger logger = Logger.getLogger(CouponMessageSendServiceImpl.class);

	public void send(String taskTimestamp, User admin, TemplateParameter message) {
		this.admin = admin;
		this.message = message;
		this.taskTimestamp = taskTimestamp;

		try {
			sendMessage("拉取推送列表", 0, 10, true);
			ArrayList<Coupon> couponList = message.getFourWordsList();
			sendMessage("开始推送消息", 0, couponList.size(), true);
			this.pushToUser(couponList, message.getBasicModel(), message.getTemplateId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("send template message error >>>>>>> " + e.getMessage());
		}
	}

	/**
	 * get coupon list from excel file
	 * 
	 * @return
	 * @throws Exception
	 */
	public static ArrayList<Coupon> getCoupon(String fileName) throws Exception {
		ExcelUtil excelUtil = ExcelUtil.getInstance();
		ArrayList<Coupon> coupons = excelUtil.readExcel(fileName, Coupon.class, Coupon.getFields());

		File file = new File(fileName);
		file.delete();
		return coupons;
	}

	int i = 0;

	/**
	 * push coupons to user
	 * 
	 * @param coupons
	 * @throws Exception
	 */
	public void pushToUser(final ArrayList<Coupon> coupons, final BasicModel basicModel, final String templateId)
			throws Exception {
		System.out.println("result:");
		int success = 0;
		int failed = 0;
		for (i = 0; i < coupons.size(); i++) {
			WechartService wechatService = new WechartServiceImpl();
			String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
					+ wechatService.getAccessToken(basicModel);

			TemplateMessageData templateData = new TemplateMessageData() {
				{
					this.setTemplate_id(templateId);
					this.setTouser(coupons.get(i).getOpenid());
					this.setUrl(coupons.get(i).getUrl());
					this.data = new HashMap<String, Map<String, String>>();
					data.put("first", new HashMap<String, String>() {
						private static final long serialVersionUID = 1L;
						{
							this.put("value", coupons.get(i).getFirst());
							this.put("color", "#6699FF");
						}
					});
					data.put("keyword1", new HashMap<String, String>() {
						private static final long serialVersionUID = 1L;
						{
							this.put("value", coupons.get(i).getKeyword1());
							this.put("color", "#6699FF");
						}
					});
					data.put("keyword2", new HashMap<String, String>() {
						private static final long serialVersionUID = 1L;
						{
							this.put("value", coupons.get(i).getKeyword2());
							this.put("color", "#6699FF");
						}
					});
					data.put("keyword3", new HashMap<String, String>() {
						private static final long serialVersionUID = 1L;
						{
							this.put("value", coupons.get(i).getKeyword3());
							this.put("color", "#6699FF");
						}
					});
					data.put("keyword4", new HashMap<String, String>() {
						private static final long serialVersionUID = 1L;
						{
							this.put("value", coupons.get(i).getKeyword4());
							this.put("color", "#6699FF");
						}
					});
					data.put("remark", new HashMap<String, String>() {
						private static final long serialVersionUID = 1L;
						{
							this.put("value", coupons.get(i).getRemark());
							this.put("color", "#6699FF");
						}
					});
				}
			};
			String data = JSON.toJSONString(templateData);

			sendMessage("推送给用户：" + coupons.get(i).getOpenid(), i, coupons.size(), true);
			String response = RequestUtil.doPost(url, data);
			logger.error("send template message result >>>>>>> " + response);

			@SuppressWarnings("unchecked")
			Map<String, Object> result = (Map<String, Object>) JSON.parse(response);
			if ((Integer) result.get("errcode") == 0) {
				success++;
				sendMessage("推送给用户:" + coupons.get(i).getOpenid() + " -成功 ", i + 1, coupons.size(), true);
			} else {
				failed++;
				sendMessage("推送给用户:" + coupons.get(i).getOpenid() + " -失败 ", i + 1, coupons.size(), true);
			}
		}
		sendMessage("任务结束 -成功:" + success + " -失败:" + failed, i, coupons.size(), false);
	}

	private void sendMessage(String message, int progress, int max, boolean isRunning) {
		TaskResponse taskMessage = new TaskResponse(admin.getId(), taskTimestamp, "消息推送任务", message, isRunning,
				progress, max);
		mqProducer.sendToBroadcast(taskMessage);
	}

}
