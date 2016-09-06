package cn.springmvc.mq.task;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.springmvc.utils.ExcelUtil;
import com.springmvc.utils.RequestUtil;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.Coupon;
import cn.springmvc.model.TaskResponse;
import cn.springmvc.model.User;
import cn.springmvc.mq.model.TemplateParameter;
import cn.springmvc.service.WechartService;
import cn.springmvc.service.impl.WechartServiceImpl;
import cn.springmvc.websocket.ProgressSocket;

/**
 * 
 * @author johnson
 *
 */
public class TemplateMessageTask implements Runnable {
	User admin;
	TemplateParameter message;
	String taskTimestamp;

	Logger logger = Logger.getLogger(TemplateMessageTask.class);

	public TemplateMessageTask(String taskTimestamp, User admin, TemplateParameter message) {
		this.admin = admin;
		this.message = message;
		this.taskTimestamp = taskTimestamp;
	}

	public void run() {
		try {
			sendMessage("拉取推送列表", 0, 10, true);
			ArrayList<Coupon> couponList = this.getCoupon(message.getFilePath());
			sendMessage("开始推送消息", 0, couponList.size(), true);
			this.pushToUser(couponList, message.getBasicModel(), message.getTemplateId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * get coupon list from excel file
	 * 
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Coupon> getCoupon(String fileName) throws Exception {
		ExcelUtil excelUtil = ExcelUtil.getInstance();
		ArrayList<Coupon> coupons = excelUtil.readExcel(fileName, Coupon.class, Coupon.getFields());

		File file = new File(fileName);
		file.delete();
		return coupons;
	}

	/**
	 * push coupons to user
	 * 
	 * @param coupons
	 * @throws Exception
	 */
	public void pushToUser(ArrayList<Coupon> coupons, BasicModel basicModel, String templateId) throws Exception {
		System.out.println("result:");
		int i = 0;
		int success = 0;
		int failed = 0;
		for (i = 0; i < coupons.size(); i++) {
			WechartService wechatService = new WechartServiceImpl();
			String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
					+ wechatService.getAccessToken(basicModel);
			String data = "{\"touser\":\"" + coupons.get(i).getOpenid() + "\",\"template_id\":\"" + templateId
					+ "\",\"url\":\"" + coupons.get(i).getUrl() + "\",\"data\":{" + "\"first\":{\"value\":\""
					+ coupons.get(i).getFirst() + "\",\"color\":\"#6699FF\"}," + "\"keyword1\":{\"value\":\""
					+ coupons.get(i).getKeyword1() + "\",\"color\":\"#6699FF\"}," + "\"keyword2\":{\"value\":\""
					+ coupons.get(i).getKeyword2() + "\",\"color\":\"#6699FF\"}," + "\"keyword3\":{\"value\":\""
					+ coupons.get(i).getKeyword3() + "\",\"color\":\"#6699FF\"}," + "\"keyword4\":{\"value\":\""
					+ coupons.get(i).getKeyword4() + "\",\"color\":\"#6699FF\"}," + "\"remark\":{\"value\":\""
					+ coupons.get(i).getRemark() + "\",\"color\":\"#6699FF\"}" + "}}";

			sendMessage("推送给用户：" + coupons.get(i).getOpenid(), i, coupons.size(), true);
			String response = RequestUtil.doPost(url, data);
			logger.error("send template message result >>>>>>> " + response);
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
		ProgressSocket.broadcast(taskMessage);
	}

}
