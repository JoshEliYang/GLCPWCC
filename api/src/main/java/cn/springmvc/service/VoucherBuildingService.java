package cn.springmvc.service;

import java.util.List;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.UserParamModel;
import cn.springmvc.model.VoucherModel;

public interface VoucherBuildingService {

	public List<UserParamModel> getUser(VoucherModel vmodel) throws Exception;

	public String getUserCount(VoucherModel vmodel);

	public String getBindingCount(VoucherModel vmodel, BasicModel model);

	public List<UserParamModel> getCustomerIdByUser(List<String> customerIdList) ;


}
