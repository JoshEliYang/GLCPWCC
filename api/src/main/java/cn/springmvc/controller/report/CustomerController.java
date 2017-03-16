package cn.springmvc.controller.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.springmvc.utils.HttpUtils;

import cn.springmvc.dao.CustomerDao;
import cn.springmvc.model.BasicModel;
import cn.springmvc.model.TaskRequest;
import cn.springmvc.model.User;
import cn.springmvc.service.mq.ProducerService;
import cn.springmvc.service.mq.task.CustomerService;

@Scope("prototype")
@Controller
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ProducerService mqProducer;
	@Autowired
	private CustomerDao customerDao;

	Logger logger = Logger.getLogger(CustomerController.class);

	@ResponseBody
	@RequestMapping(value = "/select", method = RequestMethod.POST)
	public Map<String, Object> select(HttpServletRequest request) {
		User adminInfo = (User) request.getAttribute("admin");
		BasicModel basicModel = (BasicModel) request.getAttribute("BasicModel");
		String timeStamp = request.getParameter("timestamp");
		try {
			List<Map<String, String>> user_info_list = new ArrayList<Map<String, String>>();

			String parameter = JSON.toJSONString(basicModel);
			TaskRequest taskRequest = new TaskRequest();
			taskRequest.setMethod("CustomerRefreshMessage");
			taskRequest.setAdmin(adminInfo);
			taskRequest.setTaskTimeStamp(timeStamp);
			taskRequest.setParameter(parameter);

			mqProducer.sendToQueue(taskRequest);

			return HttpUtils.generateResponse("0", "success", user_info_list);
		} catch (Exception e) {
			return HttpUtils.generateResponse("1", "failed", null);
		}
	}
}
