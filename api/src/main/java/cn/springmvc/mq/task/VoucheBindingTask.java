package cn.springmvc.mq.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

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

	public VoucheBindingTask(User admin, BindingMessageModel message, String taskTimestamp) {
		this.admin = admin;
		this.message = message;
		this.taskTimestamp = taskTimestamp;
	}

	public void run() {
		try {
			TicketExpiredTask msgPush = new TicketExpiredTask();
			
			sendMessage("绑定优惠券", 0, 10, true);
			List<String> voucherCodeList = message.getVoucherList();
			List<UserParamModel> userList = message.getUserList();

			ArrayList<ThreeKeywordsMesg> words = new ArrayList<ThreeKeywordsMesg>();
			ThreeKeywordsMesg treKM = new ThreeKeywordsMesg();
			
			Map<String, Object> jsonStr = new HashMap<String, Object>();
			List<Map<String, String>> bind = new ArrayList<Map<String, String>>();

			for (int i = 0; i < voucherCodeList.size(); i++) {
				treKM.setOpenid(message.getUserList().get(i).getOpenId());
				treKM.setFirst("您有优惠券即将到期");
				treKM.setKeyword1("优惠券到期提醒");
				treKM.setKeyword1("券码:" + voucherCodeList.get(i));
				treKM.setKeyword1("近期到期");
				treKM.setRemark("感谢您的支持");
				treKM.setUrl("http://g-super.glcp.com.cn/gapp/index.htm");
				words.add(treKM);
				Map<String, String> aa = new HashMap<String, String>();
				aa.put("customer_id", userList.get(i).getCustomerId());
				aa.put("customer_cashvouche_id", voucherCodeList.get(i));
				bind.add(aa);
				if (bind.size() == 100) {
					jsonStr.put("method", "bind");
					jsonStr.put("binding", bind);
					String resStr = this.doPost(jsonStr);
					Map<String, Object> resMap = JSON.parseObject(resStr);
					String resMsg = (String) resMap.get("msg");
					int resCode = (Integer) resMap.get("code");
					switch (resCode) {
					 case 0:
						 msgPush.pushToUser(words, message.getBasicModel(), message.getTemplateId());
						 words.clear();
						 
						 sendMessage("正在绑定", i, voucherCodeList.size(), true);
					 case 2:sendMessage("用户不存在" + resMsg, i, voucherCodeList.size(), true);
					 case 3:sendMessage("优惠券不存在或已被领用" + resMsg, i, voucherCodeList.size(), true);
					 case 4:sendMessage("未知错误", i, voucherCodeList.size(), true);
					}
					bind.clear();
					jsonStr.clear();
				}
			}
			if (bind.isEmpty() == false) {
				jsonStr.put("method", "bind");
				jsonStr.put("binding", bind);
				String resStr = this.doPost(jsonStr);
				Map<String, Object> resMap = JSON.parseObject(resStr);
				String resMsg = (String) resMap.get("msg");
				int resCode = (Integer) resMap.get("code");
				switch (resCode) {
				 case 0:
					 msgPush.pushToUser(words, message.getBasicModel(), message.getTemplateId());
					 sendMessage("正在绑定", voucherCodeList.size(), voucherCodeList.size(), false);
				 case 2:sendMessage("用户不存在" + resMsg, voucherCodeList.size(), voucherCodeList.size(), false);
				 case 3:sendMessage("优惠券不存在或已被领用" + resMsg, voucherCodeList.size(), voucherCodeList.size(), false);
				 case 4:sendMessage("未知错误", voucherCodeList.size(), voucherCodeList.size(), false);
				}
				bind.clear();
				jsonStr.clear();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Map<String, Object> genarateJson(int times, int rest) {
		return null;
	}

	public String doPost(Map<String, Object> json) throws ClientProtocolException, IOException {
		String resStr =  RequestUtil.doPost("http://app.rongzer.com/g.manage/LvdiCustomsVoucheBinding/CustomsVoucheBinding.htm",
				JSON.toJSONString(json));
		return resStr;
	}

	private void sendMessage(String message, int progress, int max, boolean isRunning) {
		TaskResponse taskMessage = new TaskResponse(admin.getId(), taskTimestamp, "优惠券绑定任务", message, isRunning,
				progress, max);
		ProgressSocket.broadcast(taskMessage);
	}

}
