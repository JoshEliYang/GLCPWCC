package cn.springmvc.model.templateMesg;

import cn.springmvc.model.BasicModel;

public class TemplateParameter {
	String filePath;
	BasicModel basicModel;
	String templateId;

	public TemplateParameter() {
		super();
	}

	public TemplateParameter(String filePath, BasicModel basicModel, String templateId) {
		super();
		this.filePath = filePath;
		this.basicModel = basicModel;
		this.templateId = templateId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public BasicModel getBasicModel() {
		return basicModel;
	}

	public void setBasicModel(BasicModel basicModel) {
		this.basicModel = basicModel;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	@Override
	public String toString() {
		return "TemplateParameter [filePath=" + filePath + ", basicModel=" + basicModel + ", templateId=" + templateId
				+ "]";
	}

}
