package cn.springmvc.service.impl.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.dao.BasicDao;
import cn.springmvc.model.BasicModel;
import cn.springmvc.model.TagDat;
import cn.springmvc.service.manage.TagService;
import cn.springmvc.service.wechat.WechartService;

import com.alibaba.fastjson.JSON;
import com.springmvc.utils.RequestUtil;

@Service
public class TagServiceImpl implements TagService {
	@Autowired
	private BasicDao basicDao;
	@Autowired
	public WechartService service;

	Logger logger = Logger.getLogger(TagServiceImpl.class);

	public Map<String, String> createTag(String jsonStr, BasicModel model)
			throws Exception {
		String accessToken = service.getAccessToken(model);
		String url = "https://api.weixin.qq.com/cgi-bin/tags/create?access_token="
				+ accessToken;
		String response = RequestUtil.doPost(url, jsonStr);
		Map<String, String> res = (Map<String, String>) JSON.parse(response);
		return res;
	}

	public Map<String, String> delete(String jsonStr, BasicModel model)
			throws Exception {
		String accessToken = service.getAccessToken(model);
		String url = "https://api.weixin.qq.com/cgi-bin/tags/delete?access_token="
				+ accessToken;
		String response = RequestUtil.doPost(url, jsonStr);
		Map<String, String> res = (Map<String, String>) JSON.parse(response);
		return res;
	}

	public Map<String, String> update(String jsonStr, BasicModel model)
			throws Exception {
		String accessToken = service.getAccessToken(model);
		logger.error("accessToken--" + accessToken);
		String url = "https://api.weixin.qq.com/cgi-bin/tags/update?access_token="
				+ accessToken;
		String response = RequestUtil.doPost(url, jsonStr);
		logger.error("json response--" + response);
		Map<String, String> res = (Map<String, String>) JSON.parse(response);
		return res;
	}

	public Map<String, String> getAll(BasicModel model) throws Exception {
		String accessToken = service.getAccessToken(model);
		logger.error("accessToken--" + accessToken);
		String url = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token="
				+ accessToken;
		logger.error("url--" + url);
		String response = RequestUtil.doGet(url);
		logger.error("response--" + response);
		Map<String, String> tags = (Map<String, String>) JSON.parse(response);
		return tags;
	}

	/**
	 * get all tags
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<TagDat> getTags(BasicModel model) throws Exception {
		String accessToken = service.getAccessToken(model);
		String url = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token="
				+ accessToken;
		String response = RequestUtil.doGet(url);

		@SuppressWarnings("unchecked")
		Map<String, List<Map<String, Object>>> res = (Map<String, List<Map<String, Object>>>) JSON
				.parse(response);
		List<Map<String, Object>> tags = res.get("tags");

		List<TagDat> tagDataList = new ArrayList<TagDat>();
		for (int i = 0; i < tags.size(); i++) {
			Map<String, Object> tag = tags.get(i);

			TagDat datItem = new TagDat();
			datItem.setId((Integer) tag.get("id"));
			datItem.setName((String) tag.get("name"));
			datItem.setCount((Integer) tag.get("count"));

			tagDataList.add(datItem);
		}

		return tagDataList;
	}

	/**
	 * query all tags and do filter
	 * 
	 * @author johnson
	 */
	public String getAll(BasicModel model, String queryDat) throws Exception {
		String accessToken = service.getAccessToken(model);
		logger.error("accessToken--" + accessToken);
		String url = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token="
				+ accessToken;
		String response = RequestUtil.doGet(url);

		System.out.println(response);

		Map<String, List<Map<String, String>>> res = (Map<String, List<Map<String, String>>>) JSON
				.parse(response);
		List<Map<String, String>> tags = res.get("tags");

		for (int i = tags.size() - 1; i >= 0; i--) {
			String name = tags.get(i).get("name");
			String tagId = String.valueOf(tags.get(i).get("id"));
			String regex = ".*" + queryDat + ".*";
			if (!name.matches(regex) && !tagId.matches(regex)) {
				tags.remove(i);
			}
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("tags", tags);
		return JSON.toJSONString(result);
	}

	public Map<String, String> getUserByTag(String jsonStr, BasicModel model)
			throws Exception {
		String accessToken = service.getAccessToken(model);
		String url = "https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token"
				+ accessToken;
		String response = RequestUtil.doPost(url, jsonStr);
		Map<String, String> res = (Map<String, String>) JSON.parse(response);
		return res;
	}

	public Map<String, String> createTagAndQrcode(String jsonStr,
			BasicModel model) throws Exception {
		TagService tagService = null;
		Map<String, String> res = tagService.createTag(jsonStr, model);

		return null;
	}

	// public class TagDat {
	// int id;
	// String name;
	// int count;
	//
	// public int getId() {
	// return id;
	// }
	//
	// public void setId(int id) {
	// this.id = id;
	// }
	//
	// public String getName() {
	// return name;
	// }
	//
	// public void setName(String name) {
	// this.name = name;
	// }
	//
	// public int getCount() {
	// return count;
	// }
	//
	// public void setCount(int count) {
	// this.count = count;
	// }
	//
	// }

	public class TagList {
		List<TagDat> tags;

		public List<TagDat> getTags() {
			return tags;
		}

		public void setTags(List<TagDat> tags) {
			this.tags = tags;
		}

	}

	public String addTag(cn.springmvc.model.TagList tl) {
		// TODO Auto-generated method stub

		return String.valueOf(basicDao.addTag(tl));
	}

	public String deleteTag(cn.springmvc.model.TagList tl) {
		// TODO Auto-generated method stub

		return String.valueOf(basicDao.deleteTag(tl));
	}

}
