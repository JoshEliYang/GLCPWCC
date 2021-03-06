package cn.springmvc.model.voucher;

import java.util.List;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.UserParamModel;

/**
 * 
 * @author johnson
 *
 */
public class BindingMessageModel {
	BasicModel basicModel;
	String templateId;
	List<UserParamModel> userList;
	List<String> voucherList;
	VoucherMessageModel voucherConfig;

	public BindingMessageModel() {
		super();
	}

	public BindingMessageModel(BasicModel basicModel, String templateId, List<UserParamModel> userList,
			List<String> voucherList, VoucherMessageModel voucherConfig) {
		super();
		this.basicModel = basicModel;
		this.templateId = templateId;
		this.userList = userList;
		this.voucherList = voucherList;
		this.voucherConfig = voucherConfig;
	}

	@Override
	public String toString() {
		return "BindingMessageModel [basicModel=" + basicModel + ", templateId=" + templateId + ", userList=" + userList
				+ ", voucherList=" + voucherList + ", voucherConfig=" + voucherConfig + "]";
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

	public List<UserParamModel> getUserList() {
		return userList;
	}

	public void setUserList(List<UserParamModel> userList) {
		this.userList = userList;
	}

	public List<String> getVoucherList() {
		return voucherList;
	}

	public void setVoucherList(List<String> voucherList) {
		this.voucherList = voucherList;
	}

	public VoucherMessageModel getVoucherConfig() {
		return voucherConfig;
	}

	public void setVoucherConfig(VoucherMessageModel voucherConfig) {
		this.voucherConfig = voucherConfig;
	}

}
