package cn.springmvc.service.impl.mq.task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.springmvc.utils.RequestUtil;

import cn.springmvc.model.TaskResponse;
import cn.springmvc.model.User;
import cn.springmvc.model.UserParamModel;
import cn.springmvc.model.templateMesg.ThreeKeywordsMesg;
import cn.springmvc.model.templateMesg.VoucheBindingResult;
import cn.springmvc.model.voucher.BindingMessageModel;
import cn.springmvc.service.mq.task.TicketExpiredSendService;
import cn.springmvc.service.mq.task.VoucheBindingService;
import cn.springmvc.websocket.ProgressSocket;

/**
 * 绑定优惠券服务
 * 
 * @author johnson
 *
 */
@Service
public class VoucheBindingServiceImpl implements VoucheBindingService {
	User admin;
	BindingMessageModel message;
	String taskTimestamp;

	Logger logger = Logger.getLogger(VoucheBindingServiceImpl.class);

	@Resource(name = "voucheBindingResult")
	private VoucheBindingResult resultFolder;

	String successFolder = null;
	String failedFolder = null;

	@Autowired
	private TicketExpiredSendService msgPush;

	public void send(final User admin, final BindingMessageModel message, final String taskTimestamp) {
		this.admin = admin;
		this.message = message;
		this.taskTimestamp = taskTimestamp;

		this.successFolder = this.resultFolder.getSuccessFolder();
		this.failedFolder = this.resultFolder.getFailedFolder();

		try {
			msgPush.setTaskTimestamp(taskTimestamp);
			msgPush.setAdmin(admin);

			sendMessage("绑定优惠券", 0, 10, true);
			List<String> voucherCodeList = message.getVoucherList();
			List<UserParamModel> userList = message.getUserList();

			ArrayList<ThreeKeywordsMesg> words = new ArrayList<ThreeKeywordsMesg>();

			Map<String, Object> jsonStr = new HashMap<String, Object>();
			List<Map<String, String>> bind = new ArrayList<Map<String, String>>();

			List<String> status = new ArrayList<String>();

			for (int i = 0; i < voucherCodeList.size(); i++) {
				ThreeKeywordsMesg treKM = new ThreeKeywordsMesg();
				treKM.setOpenid(message.getUserList().get(i).getOpenId());
				treKM.setFirst(message.getVoucherConfig().getFirst());
				treKM.setKeyword1(message.getVoucherConfig().getKeyword1());
				// treKM.setKeyword2(keyword2 + voucherCodeList.get(i));
				treKM.setKeyword2(message.getVoucherConfig().getKeyword2());
				treKM.setKeyword3(message.getVoucherConfig().getKeyword3());
				treKM.setRemark(message.getVoucherConfig().getRemark());
				treKM.setUrl(message.getVoucherConfig().getUrl());
				words.add(treKM);
				Map<String, String> aa = new HashMap<String, String>();
				aa.put("CUSTOMER_ID", userList.get(i).getCustomerId());
				aa.put("CUSTOMER_CASHVOUCHE_ID", voucherCodeList.get(i));
				bind.add(aa);

				status.add(userList.get(i).toString());

				if (bind.size() == 100) {
					jsonStr.put("method", "bind");
					jsonStr.put("binding", bind);
					String resStr = this.doPost(jsonStr);
					logger.error("response from RZ binding API: " + resStr);

					@SuppressWarnings("unchecked")
					Map<String, Map<String, String>> resData = (Map<String, Map<String, String>>) JSON.parse(resStr);
					String resMsg = resData.get("data").get("msg");
					String resCode = resData.get("data").get("code");
					switch (Integer.parseInt(resCode)) {
					case 0:
						words.clear();
						sendMessage("正在绑定", i, voucherCodeList.size(), true);
						msgPush.pushToUser(words, message.getBasicModel(), message.getTemplateId());
						writeSuccess(status);
						break;
					default:
						sendMessage(resMsg, i, voucherCodeList.size(), true);
						writeFailed(status);
					}
					bind.clear();
					jsonStr.clear();
					status.clear();
				}
			}
			if (bind.isEmpty() == false) {
				jsonStr.put("method", "bind");
				jsonStr.put("binding", bind);
				String resStr = this.doPost(jsonStr);
				logger.error("response from RZ binding API: " + resStr);

				@SuppressWarnings("unchecked")
				Map<String, Map<String, String>> resData = (Map<String, Map<String, String>>) JSON.parse(resStr);
				String resMsg = resData.get("data").get("msg");
				String resCode = resData.get("data").get("code");
				switch (Integer.parseInt(resCode)) {
				case 0:
					sendMessage("正在绑定", voucherCodeList.size(), voucherCodeList.size(), false);
					msgPush.pushToUser(words, message.getBasicModel(), message.getTemplateId());
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

	/**
	 * 输出成功日志
	 * 
	 * @param lines
	 */
	void writeSuccess(List<String> lines) {
		if (successFolder == null) {
			return;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dfTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String now = df.format(new Date());
		String nowTime = dfTime.format(new Date());

		String cache;

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(successFolder + "bindingVoucherSuccess" + now + ".txt"), true);
			for (int i = 0; i < lines.size(); i++) {
				cache = nowTime + ": " + lines.get(i) + "\r\n";
				fos.write(cache.getBytes());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("write file error in VoucheBindingTask >>> " + e.getMessage());
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("close file error in VoucheBindingTask >>> " + e.getMessage());
				}
		}
	}

	/**
	 * 输出失败日志
	 * 
	 * @param lines
	 */
	void writeFailed(List<String> lines) {
		if (failedFolder == null) {
			return;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dfTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String now = df.format(new Date());
		String nowTime = dfTime.format(new Date());

		String cache;

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(failedFolder + "/bindingVoucherFail" + now + ".txt"), true);
			for (int i = 0; i < lines.size(); i++) {
				cache = nowTime + ": " + lines.get(i) + "\r\n";
				fos.write(cache.getBytes());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("write file error in VoucheBindingTask >>> " + e.getMessage());
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("close file error in VoucheBindingTask >>> " + e.getMessage());
				}
		}
	}

}
