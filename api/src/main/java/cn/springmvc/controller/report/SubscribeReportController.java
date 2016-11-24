package cn.springmvc.controller.report;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
import cn.springmvc.model.SubCounter.SubscribeSetting;
import cn.springmvc.service.manage.TagService;
import cn.springmvc.service.wechat.SubscribeCountService;

/**
 * 
 * @author johnson
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/subscribe_report")
public class SubscribeReportController {

	@Autowired
	private SubscribeCountService subscribeService;

	@Autowired
	private TagService tagService;

	/**
	 * query subscribe info
	 * 
	 * @param queryDat
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> getSubscribeInfo(@RequestBody SubscribeInfoQuery queryDat, HttpServletRequest request) {
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

	/**
	 * query subscribe with default query condition
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public Map<String, Object> getSubscribeInfo(HttpServletRequest request) {
		BasicModel basicModel = (BasicModel) request.getAttribute("BasicModel");

		SubscribeInfoQuery queryDat = new SubscribeInfoQuery();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		queryDat.setEndDate(df.format(now));
		now.setDate(now.getDate() - 6);
		queryDat.setStartDate(df.format(now));
		queryDat.setDateType(3);

		List<SubscribeSetting> queryList = null;
		try {
			queryList = subscribeService.getQueryList(basicModel);
		} catch (Exception e) {
			e.printStackTrace();
			return HttpUtils.generateResponse("1", "用户关注统计信息查询失败", null);
		}
		int[] tags = new int[queryList.size()];
		for (int i = 0; i < queryList.size(); i++) {
			tags[i] = queryList.get(i).getTagId();
		}
		queryDat.setTagList(tags);

		SubscribeCountResponse result = null;
		try {
			result = subscribeService.get(queryDat, basicModel);
			result.setSettingList(queryList);
		} catch (Exception e) {
			e.printStackTrace();
			return HttpUtils.generateResponse("2", "用户关注统计信息查询失败", null);
		}
		
		try {
			/**
			 * set all tags
			 */
			result.setTags(tagService.getTags(basicModel));
			return HttpUtils.generateResponse("3", "用户关注统计信息查询失败", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return HttpUtils.generateResponse("0", "用户关注统计信息查询成功", result);
	}
}
