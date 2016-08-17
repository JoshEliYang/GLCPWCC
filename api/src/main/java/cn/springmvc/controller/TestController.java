package cn.springmvc.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

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

import cn.springmvc.model.BasicModel;
import cn.springmvc.service.WechartService;

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

	Logger logger = Logger.getLogger(TestController.class);

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
