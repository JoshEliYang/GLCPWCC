package cn.springmvc.controller.report;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.TagDat;
import cn.springmvc.model.SubCounter.SubscribeCount;
import cn.springmvc.model.SubCounter.SubscribeCountByTags;
import cn.springmvc.model.SubCounter.SubscribeCountQuery;
import cn.springmvc.service.manage.TagService;
import cn.springmvc.service.wechat.SubscribeTableService;

import com.springmvc.utils.ExcelUtil;
import com.springmvc.utils.HttpUtils;

@Scope("prototype")
@Controller
@RequestMapping("/subscribers")
public class SubscribeTableController {

	@Autowired
	private SubscribeTableService subscribeTableService;

	@Autowired
	private TagService tagService;

	Logger logger = Logger.getLogger(SubscribeTableController.class);

	/**
	 * query subscribe info
	 * 
	 * @param queryDat
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public Map<String, Object> getSubscribeInfo(
			@RequestBody SubscribeCountQuery queryDat,
			HttpServletRequest request) {
		BasicModel basic = (BasicModel) request.getAttribute("BasicModel");

		try {
			List<SubscribeCountByTags> result = getSubscribeCountByTags(
					queryDat, basic);
			return HttpUtils.generateResponse("0", "查询成功", result);
		} catch (Exception e) {
			e.printStackTrace();
			return HttpUtils.generateResponse("1", "查询失败", null);
		}
	}

	/**
	 * export to excel
	 * 
	 * @param response
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/{startDate}/{endDate}", method = RequestMethod.GET)
	public void exportTest(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String startDate,
			@PathVariable String endDate) throws Exception {
		BasicModel basic = (BasicModel) request.getAttribute("BasicModel");

		SubscribeCountQuery queryDat = new SubscribeCountQuery();
		queryDat.setStartDate(startDate);
		queryDat.setEndDate(endDate);

		List<SubscribeCountByTags> result = getSubscribeCountByTags(queryDat,
				basic);

		String fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(
				new Date()).toString();

		response.reset();
		response.setContentType("application/vnd.ms-excel"); // 改成输出excel文件
		response.setHeader("Content-disposition", "attachment; filename="
				+ fileName + ".xls");

		ExcelUtil excelUtil = ExcelUtil.getInstance();
		HSSFWorkbook wb = excelUtil.exportSubscribeCount(result, queryDat);

		OutputStream os = response.getOutputStream();
		wb.write(os);

	}

	/**
	 * get subscribe count by tags
	 * 
	 * @param queryDat
	 * @param basic
	 * @return
	 * @throws Exception
	 */
	private List<SubscribeCountByTags> getSubscribeCountByTags(
			SubscribeCountQuery queryDat, BasicModel basic) throws Exception {

		List<List<SubscribeCount>> countList;
		List<SubscribeCountByTags> result = new ArrayList<SubscribeCountByTags>();
		countList = subscribeTableService.get(queryDat);
		List<TagDat> tags = tagService.getTags(basic);

		int i = 0, j = 0, m = 0, n = 0;

		for (i = 0; i < countList.size(); i++) {
			List<SubscribeCount> subItem = countList.get(i);

			for (n = 0; n < subItem.size(); n++) {

				for (j = 0; j < tags.size(); j++) {
					TagDat tag = tags.get(j);
					if (subItem.get(n).getTagId() == tag.getId()) {
						subItem.get(n).setTagName(tag.getName());
						break;
					}
				}

				if (j >= tags.size()) {
					subItem.get(n).setTagId(-1);
					subItem.get(n).setTagName("其他");
				}
			}
		}

		tags.add(new TagDat() {
			{
				this.setId(-1);
				this.setName("其他");
			}
		});
		for (i = 0; i < tags.size(); i++) {
			SubscribeCountByTags count = new SubscribeCountByTags();
			count.setTagId(tags.get(i).getId());
			count.setTagName(tags.get(i).getName());
			ArrayList<Integer> subNumber = new ArrayList<Integer>();
			for (j = 0; j < countList.size(); j++) {
				List<SubscribeCount> subItem = countList.get(j);
				for (m = 0; m < subItem.size(); m++) {
					SubscribeCount countItem = subItem.get(m);
					if (tags.get(i).getId() == countItem.getTagId()) {
						subNumber.add(countItem.getSubscribe());
						break;
					}
				}
				if (m >= subItem.size()) {
					subNumber.add(0);
				}
			}
			count.setCounts(subNumber);
			result.add(count);
		}
		return result;
	}

}
