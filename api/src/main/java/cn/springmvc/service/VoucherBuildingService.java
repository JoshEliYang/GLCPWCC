package cn.springmvc.service;

import java.util.List;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.UserParamModel;
import cn.springmvc.model.VoucherModel;


public interface VoucherBuildingService {

	List<UserParamModel> getUser(VoucherModel vmodel, BasicModel model);
	
}
