package cn.springmvc.service.impl.manage;

import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.springmvc.utils.RequestUtil;

import cn.springmvc.model.BasicModel;
import cn.springmvc.service.manage.TagService;
import cn.springmvc.service.wechat.WechartService;

@Service
public class TagServiceImpl implements TagService {
	@Autowired
	public WechartService service;
	Logger logger = Logger.getLogger(TagServiceImpl.class);

	public Map<String, String> createTag(String jsonStr, BasicModel model) throws Exception {
		String accessToken = service.getAccessToken(model);
		String url = "https://api.weixin.qq.com/cgi-bin/tags/create?access_token=" + accessToken;
		String response = RequestUtil.doPost(url, jsonStr);
		Map<String, String> res = (Map<String, String>) JSON.parse(response);
		return res;
	}

	public Map<String, String> delete(String jsonStr, BasicModel model) throws Exception {
		String accessToken = service.getAccessToken(model);
		String url = "https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=" + accessToken;
		String response = RequestUtil.doPost(url, jsonStr);
		Map<String, String> res = (Map<String, String>) JSON.parse(response);
		return res;
	}

	public Map<String, String> update(String jsonStr, BasicModel model) throws Exception {
		String accessToken = service.getAccessToken(model);
		logger.error("accessToken--" + accessToken);
		String url = "https://api.weixin.qq.com/cgi-bin/tags/update?access_token=" + accessToken;
		String response = RequestUtil.doPost(url, jsonStr);
		logger.error("json response--" + response);
		Map<String, String> res = (Map<String, String>) JSON.parse(response);
		return res;
	}

	public Map<String, String> getAll(BasicModel model) throws Exception {
		String accessToken = service.getAccessToken(model);
		logger.error("accessToken--" + accessToken);
		String url = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token=" + accessToken;
		logger.error("url--" + url);
		String response = RequestUtil.doGet(url);
		logger.error("response--" + response);
		Map<String, String> tags = (Map<String, String>) JSON.parse(response);
		return tags;
	}

	/**
	 * query all tags and do filter
	 * 
	 * @author johnson
	 */
	public String getAll(BasicModel model, String queryDat) throws Exception {
		String accessToken = service.getAccessToken(model);
		logger.error("accessToken--" + accessToken);
		String url = "https://api.weixin.qq.com/cgi-bin/tags/get?access_token=" + accessToken;
		String response = RequestUtil.doGet(url);

		TagList tags = JSON.parseObject(response, TagList.class);
		for (int i = tags.getTags().size() - 1; i >= 0; i--) {
			String name = tags.getTags().get(i).getName();
			String regex = ".*" + queryDat + ".*";
			if (!name.matches(regex)) {
				tags.getTags().remove(i);
			}
		}

		return JSON.toJSONString(tags);
	}

	public Map<String, String> getUserByTag(String jsonStr, BasicModel model) throws Exception {
		String accessToken = service.getAccessToken(model);
		String url = "https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token" + accessToken;
		String response = RequestUtil.doPost(url, jsonStr);
		Map<String, String> res = (Map<String, String>) JSON.parse(response);
		return res;
	}

	public Map<String, String> createTagAndQrcode(String jsonStr, BasicModel model) throws Exception {
		TagService tagService = null;
		Map<String, String> res = tagService.createTag(jsonStr, model);

		return null;
	}

	public class TagDat {
		int id;
		String name;
		int count;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

	}

	public class TagList {
		ArrayList<TagDat> tags;

		public ArrayList<TagDat> getTags() {
			return tags;
		}

		public void setTags(ArrayList<TagDat> tags) {
			this.tags = tags;
		}

	}
}
