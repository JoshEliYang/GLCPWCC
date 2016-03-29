package cn.springmvc.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.utils.ShaUtil;
import com.springmvc.utils.XMLUtils;

import cn.springmvc.model.WechartModel;

/**
 * 
 * @author johsnon
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/wechart")
public class WechartController {
	static String token="hKKl48yK4Jolq4dbX5R9RYE8YzYkNndb";
	
	Logger logger=Logger.getLogger(WechartController.class);
	
	/**
	 * 签名验证
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public String checkSignature(HttpServletRequest request, 
			HttpServletResponse response) {
		
		String signature=request.getParameter("signature");
		String timestamp=request.getParameter("timestamp");
		String nonce=request.getParameter("nonce");
		String echostr=request.getParameter("echostr");
		
		logger.error("signature->>"+signature);
		logger.error("timestamp->>"+timestamp);
		logger.error("nonce->>"+nonce);
		logger.error("echostr->>"+echostr);
		
		String[] str=new String[3];
		str[0]=token;
		str[1]=timestamp;
		str[2]=nonce;
		Arrays.sort(str);
		
		String plainCode=str[0]+str[1]+str[2];
		try {
			String cipher=ShaUtil.encryptSHA(plainCode);
			logger.error("plain code->>"+plainCode);
			logger.error("SHA-1 cipher-<<"+cipher);
			
			if(cipher.equals(signature)){
				logger.error("check OK");
			}else{
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
	 * @param request
	 * @param response
	 * @param inXml
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public String doRequest(HttpServletRequest request, 
			HttpServletResponse response,@RequestBody String inXml) {
		
		logger.error("xml >>> \n"+inXml);
		
		WechartModel model=null;
		String openId=null;
		
		try {
			model=XMLUtils.xml2Object(WechartModel.class, inXml);
			if(model==null){
				logger.error("xml2Object error with null result");
				return "success";
			}
			
			openId = model.getFromUserName();
			if("event".equalsIgnoreCase(model.getMsgType())){
				if("subscribe".equalsIgnoreCase(model.getEvent())){
					// 未关注用户事件
					logger.error("subscribe OK");
					
					if(model.getEventKey()!=""){
						// 为关注用户 扫码事件
						logger.error("SCAN 2 OK");
					}
					
				}else if("SCAN".equals(model.getEvent())){
					// 已关注用户扫码事件
					logger.error("SCAN OK");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("xml2Object error >>> \n" +e.getLocalizedMessage());
		}
		
		return "success";
	}
}
