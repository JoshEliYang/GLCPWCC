package cn.springmvc.controller.function;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.springmvc.utils.HttpUtils;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.TaskRequest;
import cn.springmvc.model.User;
import cn.springmvc.model.templateMesg.TemplateMessage;
import cn.springmvc.model.templateMesg.TemplateParameter;
import cn.springmvc.service.function.TemplateMessageService;
import cn.springmvc.service.mq.ProducerService;

/**
 * 推送模板消息
 * 
 * @author johnson
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/message")
public class TemplateMessageController {
	@Autowired
	private TemplateMessageService templateService;

	@Autowired
	private ProducerService mqProducer;

	Logger logger = Logger.getLogger(TemplateMessageController.class);

	/**
	 * upload message file and push to user
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = ("/upload/{methodName}"), method = RequestMethod.POST)
	public void fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request,
			HttpServletResponse response, @PathVariable String methodName) throws IOException {

		User admin = (User) request.getAttribute("admin");
		BasicModel basicModel = (BasicModel) request.getAttribute("BasicModel");
		String templateId = request.getParameter("templateId");
		String taskTimestamp = request.getParameter("timestamp");

		System.out.println("file upload");
		String filePath = null;
		// 判断文件是否为空
		if (!file.isEmpty()) {
			try {
				Pattern pattern = Pattern.compile("\\.[a-z|A-Z]+$");
				Matcher matcher = pattern.matcher(file.getOriginalFilename());
				if (matcher.find()) {
					// 文件保存路径
					// filePath = "G:/test/" + System.currentTimeMillis() +
					// matcher.group(0);
					filePath = "/opt/data/source/uploaded/" + System.currentTimeMillis() + matcher.group(0);
				} else {
					logger.error("file upload error >>>>> get file type by regex error!");
					response.getOutputStream().print("{\"code\":\"2\"}");
					return;
				}

				// 转存文件
				file.transferTo(new File(filePath));
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("file upload error >>>>> occured error when upload file!");
				response.getOutputStream().print("{\"code\":\"1\"}");
				return;
			}
		} else {
			logger.error("file upload error >>>>> no file!");
			response.getOutputStream().print("{\"code\":\"2\"}");
			return;
		}

		// response.setContentType("application/jsonp");
		response.getOutputStream().print("{\"code\":\"0\",\"data\":\"" + filePath + "\"}");

		TemplateParameter templateTask = new TemplateParameter(filePath, basicModel, templateId);
		TaskRequest task = new TaskRequest(methodName, taskTimestamp, admin, JSON.toJSONString(templateTask));
		// MqSender.sender(task);
		mqProducer.send(task);
	}

	/**
	 * get all template message
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/templates", method = RequestMethod.GET)
	public Map<String, Object> getTemplate(HttpServletRequest request, HttpServletResponse response) {
		BasicModel basicModel = (BasicModel) request.getAttribute("BasicModel");

		List<TemplateMessage> results = null;
		try {
			results = templateService.getTemplate(basicModel);
			logger.error("get all template message success >>> \n" + results);
			return HttpUtils.generateResponse("0", "模板消息查询成功", results);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("get all template message failed >>> \n" + null);
			return HttpUtils.generateResponse("1", "模板消息查询失败", null);
		}
	}

}
