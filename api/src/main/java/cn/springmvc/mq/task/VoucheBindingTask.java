package cn.springmvc.mq.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.springmvc.utils.RequestUtil;

import cn.springmvc.model.TaskResponse;
import cn.springmvc.model.User;
import cn.springmvc.model.UserParamModel;
import cn.springmvc.model.templateMesg.ThreeKeywordsMesg;
import cn.springmvc.model.voucher.BindingMessageModel;
import cn.springmvc.websocket.ProgressSocket;

public class VoucheBindingTask implements Runnable {
	User admin;
	BindingMessageModel message;
	String taskTimestamp;

	Logger logger = Logger.getLogger(VoucheBindingTask.class);

	public VoucheBindingTask(User admin, BindingMessageModel message, String taskTimestamp) {
		this.admin = admin;
		this.message = message;
		this.taskTimestamp = taskTimestamp;
	}

	public void run() {
		try {
			TicketExpiredTask msgPush = new TicketExpiredTask();
			msgPush.taskTimestamp = taskTimestamp;
			msgPush.admin = admin;

			sendMessage("绑定优惠券", 0, 10, true);
			List<String> voucherCodeList = message.getVoucherList();
			List<UserParamModel> userList = message.getUserList();

			ArrayList<ThreeKeywordsMesg> words = new ArrayList<ThreeKeywordsMesg>();

			Map<String, Object> jsonStr = new HashMap<String, Object>();
			List<Map<String, String>> bind = new ArrayList<Map<String, String>>();

			for (int i = 0; i < voucherCodeList.size(); i++) {
				ThreeKeywordsMesg treKM = new ThreeKeywordsMesg();
				treKM.setOpenid(message.getUserList().get(i).getOpenId());
				treKM.setFirst("您有优惠券即将到期");
				treKM.setKeyword1("优惠券到期提醒");
				treKM.setKeyword2("券码:" + voucherCodeList.get(i));
				treKM.setKeyword3("近期到期");
				treKM.setRemark("感谢您的支持");
				treKM.setUrl("http://g-super.glcp.com.cn/gapp/hy/coupons/index.htm");
				words.add(treKM);
				Map<String, String> aa = new HashMap<String, String>();
				aa.put("CUSTOMER_ID", userList.get(i).getCustomerId());
				aa.put("CUSTOMER_CASHVOUCHE_ID", voucherCodeList.get(i));
				bind.add(aa);
				if (bind.size() == 100) {
					jsonStr.put("method", "bind");
					jsonStr.put("binding", bind);
					String resStr = this.doPost(jsonStr);
					logger.error("response from RZ binding API: " + resStr);

					Map<String, Map<String, String>> resData = (Map<String, Map<String, String>>) JSON.parse(resStr);
					String resMsg = resData.get("data").get("msg");
					String resCode = resData.get("data").get("code");
					switch (Integer.parseInt(resCode)) {
					case 0:
						msgPush.pushToUser(words, message.getBasicModel(), message.getTemplateId());
						words.clear();
						sendMessage("正在绑定", i, voucherCodeList.size(), true);
						break;
					default:
						sendMessage(resMsg, i, voucherCodeList.size(), true);
					}
					bind.clear();
					jsonStr.clear();
				}
			}
			if (bind.isEmpty() == false) {
				jsonStr.put("method", "bind");
				jsonStr.put("binding", bind);
				String resStr = this.doPost(jsonStr);
				logger.error("response from RZ binding API: " + resStr);

				Map<String, Map<String, String>> resData = (Map<String, Map<String, String>>) JSON.parse(resStr);
				String resMsg = resData.get("data").get("msg");
				String resCode = resData.get("data").get("code");
				switch (Integer.parseInt(resCode)) {
				case 0:
					msgPush.pushToUser(words, message.getBasicModel(), message.getTemplateId());
					sendMessage("正在绑定", voucherCodeList.size(), voucherCodeList.size(), false);
					break;
				default:
					sendMessage(resMsg, voucherCodeList.size(), voucherCodeList.size(), false);
				}
				bind.clear();
				jsonStr.clear();
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error occurred in VoucheBindingTask >>> " + e.getMessage() + e.getStackTrace());
		}

	}

	public Map<String, Object> genarateJson(int times, int rest) {
		return null;
	}

	public String doPost(Map<String, Object> json) throws ClientProtocolException, IOException {
		String resStr = RequestUtil.doPost(
				"http://g-super.glcp.com.cn/g.manage/LvdiCustomsVoucheBinding/CustomsVoucheBinding.htm",
				JSON.toJSONString(json));
		logger.error("request data in VoucheBindingTask: " + JSON.toJSONString(json));
		return resStr;
	}

	private void sendMessage(String message, int progress, int max, boolean isRunning) {
		TaskResponse taskMessage = new TaskResponse(admin.getId(), taskTimestamp, "优惠券绑定任务", message, isRunning,
				progress, max);
		ProgressSocket.broadcast(taskMessage);
	}

}
