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
			sendMessage("绑定优惠券", 0, 10, true);
			List<String> voucherCodeList = message.getVoucherList();
			List<UserParamModel> userList = message.getUserList();

			Map<String, Object> jsonStr = new HashMap<String, Object>();
			List<Map<String, String>> bind = new ArrayList<Map<String, String>>();

			for (int i = 0; i < voucherCodeList.size(); i++) {
				Map<String, String> aa = new HashMap<String, String>();
				aa.put("customer_id", userList.get(i).getCustomerId());
				aa.put("customer_cashvouche_id", voucherCodeList.get(i));
				bind.add(aa);
				if (bind.size() == 100) {
					jsonStr.put("method", "bind");
					jsonStr.put("binding", bind);
					this.doPost(jsonStr);
					bind.clear();
					jsonStr.clear();
					sendMessage("正在绑定", i, voucherCodeList.size(), true);
				}
			}
			if (bind.isEmpty() == false) {
				jsonStr.put("method", "bind");
				jsonStr.put("binding", bind);
				this.doPost(jsonStr);
				bind.clear();
				jsonStr.clear();
				sendMessage("绑定成功", voucherCodeList.size(), voucherCodeList.size(), false);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Map<String, Object> genarateJson(int times, int rest) {
		List<String> voucherCodeList = message.getVoucherList();
		List<UserParamModel> userList = message.getUserList();

		Map<String, Object> jsonStr = new HashMap<String, Object>();
		List<Map<String, String>> bind = new ArrayList<Map<String, String>>();
		for (int i = 100 * times; i < 100 * (times + 1); i++) {
			Map<String, String> aa = new HashMap<String, String>();
			aa.put("customer_id", userList.get(i).getCustomerId());
			aa.put("customer_cashvouche_id", voucherCodeList.get(i));
			bind.add(aa);
		}
		jsonStr.put("binding", bind);
		return jsonStr;
	}

	public String doPost(Map<String, Object> json) throws ClientProtocolException, IOException {
		return RequestUtil.doPost("http://app.rongzer.com/g.manage/LvdiCustomsVoucheBinding/CustomsVoucheBinding.htm",
				JSON.toJSONString(json));
	}

	private void sendMessage(String message, int progress, int max, boolean isRunning) {
		TaskResponse taskMessage = new TaskResponse(admin.getId(), taskTimestamp, "优惠券绑定任务", message, isRunning,
				progress, max);
		ProgressSocket.broadcast(taskMessage);
	}

}
