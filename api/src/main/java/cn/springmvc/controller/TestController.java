package cn.springmvc.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.utils.HttpUtils;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.TaskResponse;
import cn.springmvc.model.report.Customer;
import cn.springmvc.service.mq.ProducerService;
import cn.springmvc.service.mq.task.CustomerService;
import cn.springmvc.service.wechat.WechartService;

/**
 * 
 * @author johsnon
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	public WechartService service;

	@Resource(name = "taskQueueDestination")
	private Destination testQueueDestination;

	@Autowired
	private ProducerService producerService;

	@Autowired
	private CustomerService customerService;

	Logger logger = Logger.getLogger(TestController.class);

	@ResponseBody
	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	public String customerTest(HttpServletRequest request) throws Exception {
		BasicModel basicModel = (BasicModel) request.getAttribute("BasicModel");
		String openId = "oVR24s7e0AlKzSgJLJdGT4Gbpto4";

		List<String> openIdList = new ArrayList<String>();
		openIdList.add(openId);
		Map<String, Object> customersMap = customerService.getUserInfo(basicModel, openIdList);
		logger.error("getted customers map");
		List<Map<String, Object>> customerList = (List<Map<String, Object>>) customersMap.get("user_info_list");
		logger.error("try to refresh customer");
		customerService.refreshUserInfo(customerList);
		logger.error("new customer subscribe by scan qrcode>>> ");

		return "success";
	}

	/**
	 * just use to test message queue
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/mq", method = RequestMethod.GET)
	public Map<String, Object> mqtest() {
		producerService.sendToBroadcast(new TaskResponse() {
			{
				this.setAdminId(2);
				this.setProgress(50);
				this.setMax(100);
				this.setRunning(true);
				this.setTask("test task");
				this.setMessage("still testing");
				this.setTaskTimestamp("xxxxx");
			}
		});
		return HttpUtils.generateResponse("0", "test success", null);
	}

	/**
	 * token test
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/token", method = RequestMethod.GET)
	public String test(HttpServletRequest request) {
		BasicModel basicModel = (BasicModel) request.getAttribute("BasicModel");

		String token = null;
		try {
			token = service.getAccessToken(basicModel);
		} catch (Exception e) {
			e.printStackTrace();
			// logger.error(e.toString());
		}
		return token;
	}

	/**
	 * 
	 * @param url
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "/image", method = RequestMethod.POST)
	public void getimage(@RequestBody String url, HttpServletResponse response) {
		InputStream inputStream = null;
		HttpURLConnection httpURLConnection = null;
		try {
			URL urlx = new URL(url);
			if (urlx != null) {
				httpURLConnection = (HttpURLConnection) urlx.openConnection();
				httpURLConnection.setConnectTimeout(3000);
				httpURLConnection.setRequestMethod("GET");
				httpURLConnection.setRequestProperty("Referer", "");
				int responseCode = httpURLConnection.getResponseCode();
				if (responseCode == 200) {
					inputStream = httpURLConnection.getInputStream();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			response.setHeader("Content-Type", "image/jpeg");
			response.getOutputStream().write(readInputStream(inputStream));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@ResponseBody
	@RequestMapping(value = "/wechatimage", method = RequestMethod.GET)
	public void getwechatImage(HttpServletRequest request, HttpServletResponse response) {
		String url = request.getParameter("url");
		url = url.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
		try {
			String urlStr = URLDecoder.decode(url, "UTF-8");
			url = URLDecoder.decode(url, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		InputStream inputStream = null;
		HttpURLConnection httpURLConnection = null;
		try {
			URL urlx = new URL(url);
			if (urlx != null) {
				httpURLConnection = (HttpURLConnection) urlx.openConnection();
				httpURLConnection.setConnectTimeout(3000);
				httpURLConnection.setRequestMethod("GET");
				httpURLConnection.setRequestProperty("Referer", "");
				int responseCode = httpURLConnection.getResponseCode();
				if (responseCode == 200) {
					inputStream = httpURLConnection.getInputStream();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			response.setHeader("Content-Type", "image/jpeg");
			response.getOutputStream().write(readInputStream(inputStream));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[2048];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}
}
