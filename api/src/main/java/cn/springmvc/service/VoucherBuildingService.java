package cn.springmvc.service;

import java.util.List;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.User;
import cn.springmvc.model.UserParamModel;
import cn.springmvc.model.VoucherModel;
import cn.springmvc.model.voucher.VoucherMessageModel;

public interface VoucherBuildingService {

	public List<UserParamModel> getUser(VoucherModel vmodel) throws Exception;

	public String getUserCount(VoucherModel vmodel);

	public String getBindingCount(VoucherModel vmodel, BasicModel model);

	public List<UserParamModel> getCustomerIdByUser(List<String> customerIdList, String timestamp, User admin);

	/**
	 * get the configuration of voucher message model
	 * 
	 * @return
	 * @throws Exception
	 */
	public VoucherMessageModel getVoucherConfig() throws Exception;

	/**
	 * set the configuration of voucher message model
	 * 
	 * @param voucherConfig
	 * @throws Exception
	 */
	public void setVoucherConfig(VoucherMessageModel voucherConfig) throws Exception;
}
