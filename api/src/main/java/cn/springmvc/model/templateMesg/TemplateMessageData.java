package cn.springmvc.model.templateMesg;

import java.util.Map;

public class TemplateMessageData {
	String touser = "OPENID";
	String template_id;
	String url;
	Map<String, Map<String, String>> data;

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, Map<String, String>> getData() {
		return data;
	}

	public void setData(Map<String, Map<String, String>> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "TemplateMessageData [touser=" + touser + ", template_id=" + template_id + ", url=" + url + ", data="
				+ data + "]";
	}

}
