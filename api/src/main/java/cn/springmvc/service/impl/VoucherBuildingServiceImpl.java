package cn.springmvc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.daoLvdi.VoucherDao;
import cn.springmvc.model.BasicModel;
import cn.springmvc.model.UserParamModel;
import cn.springmvc.model.VoucherModel;
import cn.springmvc.service.VoucherBuildingService;

/**
 * 
 * @author xia0zhu
 *
 */
@Service
public class VoucherBuildingServiceImpl implements VoucherBuildingService {

	@Autowired
	private VoucherDao voucherDao;

	public List<UserParamModel> getUser(VoucherModel vmodel) throws Exception {
		return voucherDao.getUser(vmodel.getFilter(), vmodel.getOrder(),
				vmodel.getOffset(), vmodel.getCount());
	}

	public String getUserCount(VoucherModel vmodel) {
		return voucherDao.getUserCount(vmodel.getFilter());
	}

	public String getBindingCount(VoucherModel vmodel, BasicModel model) {
		List<UserParamModel> list = voucherDao.getBindingCount(vmodel
				.getFilter());
		return null;
	}

	public List<UserParamModel> getCustomerIdByUser(List<String> customerIdList) {

		List<UserParamModel> userparamModl = new ArrayList<UserParamModel>();

		for (int i = 0; i < customerIdList.size(); i++) {

			UserParamModel upm = voucherDao.getCustomerIdByUser(customerIdList
					.get(i));
			userparamModl.add(upm);
		}

		return userparamModl;
	}

}
