package cn.springmvc.model.templateMesg;

import java.util.ArrayList;
import java.util.List;

import cn.springmvc.model.BasicModel;

/**
 * 模板消息
 * 
 * @author johnson
 *
 */
public class TemplateParameter {
	String filePath;

	BasicModel basicModel;
	String templateId;
	List<ThreeKeywordsMesg> threeWordsList = new ArrayList<ThreeKeywordsMesg>();
	ArrayList<Coupon> fourWordsList = new ArrayList<Coupon>();

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

	public List<ThreeKeywordsMesg> getThreeWordsList() {
		return threeWordsList;
	}

	public void setThreeWordsList(List<ThreeKeywordsMesg> threeWordsList) {
		this.threeWordsList = threeWordsList;
	}

	public ArrayList<Coupon> getFourWordsList() {
		return fourWordsList;
	}

	public void setFourWordsList(ArrayList<Coupon> fourWordsList) {
		this.fourWordsList = fourWordsList;
	}

	@Override
	public String toString() {
		return "TemplateParameter [filePath=" + filePath + ", basicModel=" + basicModel + ", templateId=" + templateId
				+ ", threeWordsList=" + threeWordsList + ", fourWordsList=" + fourWordsList + "]";
	}

}
