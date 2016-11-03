package cn.springmvc.controller.report;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.utils.HttpUtils;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.SubCounter.SubscribeCountResponse;
import cn.springmvc.model.SubCounter.SubscribeInfoQuery;
import cn.springmvc.service.wechat.SubscribeCountService;

/**
 * 
 * @author johnson
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/subscribe_report")
public class UserSubscribeReportController {

	@Autowired
	private SubscribeCountService subscribeService;

	/**
	 * query subscribe info
	 * 
	 * @param queryDat
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> getSubscribeInfo(@RequestBody SubscribeInfoQuery queryDat,
			HttpServletRequest request) {
		BasicModel basicModel = (BasicModel) request.getAttribute("BasicModel");

		SubscribeCountResponse result = null;
		try {
			result = subscribeService.get(queryDat, basicModel);
			return HttpUtils.generateResponse("0", "用户关注统计信息查询成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			return HttpUtils.generateResponse("1", "用户关注统计信息查询失败", null);
		}
	}
}
