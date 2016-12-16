package cn.springmvc.mq.task;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.springmvc.utils.ExcelUtil;
import com.springmvc.utils.RequestUtil;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.TaskResponse;
import cn.springmvc.model.User;
import cn.springmvc.model.templateMesg.ThreeKeywordsMesg;
import cn.springmvc.mq.model.TemplateParameter;
import cn.springmvc.service.impl.wechat.WechartServiceImpl;
import cn.springmvc.service.wechat.WechartService;
import cn.springmvc.websocket.ProgressSocket;

/**
 * 
 * @author johnson
 *
 */
public class TicketExpiredTask implements Runnable {
	User admin;
	TemplateParameter message;
	String taskTimestamp;

	Logger logger = Logger.getLogger(TemplateMessageTask.class);

	public TicketExpiredTask(){
		super();
	}
	
	public TicketExpiredTask(String taskTimestamp, User admin, TemplateParameter message) {
		this.admin = admin;
		this.message = message;
		this.taskTimestamp = taskTimestamp;
	}

	public void run() {
		sendMessage("拉取推送列表", 0, 10, true);
		ArrayList<ThreeKeywordsMesg> words = null;
		try {
			words = this.getExcel(message.getFilePath());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
					"error occurred when reading excel in TicketExpiredTask >>> " + e.getMessage() + e.getStackTrace());
		}
		sendMessage("开始推送消息", 0, words.size(), true);
		try {
			this.pushToUser(words, message.getBasicModel(), message.getTemplateId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error occurred when pushing message in TicketExpiredTask >>>>> " + e.getMessage()
					+ e.getStackTrace());
		}
	}

	/**
	 * get excel content
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ThreeKeywordsMesg> getExcel(String fileName) throws Exception {
		ExcelUtil excelUtil = ExcelUtil.getInstance();
		ArrayList<ThreeKeywordsMesg> words = excelUtil.readExcel(fileName, ThreeKeywordsMesg.class,
				ThreeKeywordsMesg.getFields());

		File file = new File(fileName);
		file.delete();
		return words;
	}

	public void pushToUser(ArrayList<ThreeKeywordsMesg> words, BasicModel basicModel, String templateId)
			throws Exception {
		System.out.println("result:");
		int i = 0;
		int success = 0;
		int failed = 0;
		for (i = 0; i < words.size(); i++) {
			WechartService wechatService = new WechartServiceImpl();
			String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
					+ wechatService.getAccessToken(basicModel);
			String data = "{\"touser\":\"" + words.get(i).getOpenid() + "\",\"template_id\":\"" + templateId
					+ "\",\"url\":\"" + words.get(i).getUrl() + "\",\"data\":{" + "\"first\":{\"value\":\""
					+ words.get(i).getFirst() + "\",\"color\":\"#6699FF\"}," + "\"keyword1\":{\"value\":\""
					+ words.get(i).getKeyword1() + "\",\"color\":\"#6699FF\"}," + "\"keyword2\":{\"value\":\""
					+ words.get(i).getKeyword2() + "\",\"color\":\"#6699FF\"}," + "\"keyword3\":{\"value\":\""
					+ words.get(i).getKeyword3() + "\",\"color\":\"#6699FF\"}," + "\"remark\":{\"value\":\""
					+ words.get(i).getRemark() + "\",\"color\":\"#6699FF\"}" + "}}";

			sendMessage("推送给用户：" + words.get(i).getOpenid(), i, words.size(), true);
			String response = RequestUtil.doPost(url, data);
			logger.error("send template message result >>>>>>> " + response);
			Map<String, Object> result = (Map<String, Object>) JSON.parse(response);
			if ((Integer) result.get("errcode") == 0) {
				success++;
				sendMessage("推送给用户:" + words.get(i).getOpenid() + " -成功 ", i + 1, words.size(), true);
			} else {
				failed++;
				sendMessage("推送给用户:" + words.get(i).getOpenid() + " -失败 ", i + 1, words.size(), true);
			}
		}
		sendMessage("任务结束 -成功:" + success + " -失败:" + failed, i, words.size(), false);
	}

	private void sendMessage(String message, int progress, int max, boolean isRunning) {
		TaskResponse taskMessage = new TaskResponse(admin.getId(), taskTimestamp, "消息推送任务", message, isRunning,
				progress, max);
		ProgressSocket.broadcast(taskMessage);
	}
}
