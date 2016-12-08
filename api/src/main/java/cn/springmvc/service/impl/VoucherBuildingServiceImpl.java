package cn.springmvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.daoLvdi.VoucherDao;
import cn.springmvc.model.BasicModel;
import cn.springmvc.model.FilterModel;
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
	

	public List<UserParamModel> getUser(VoucherModel vmodel, BasicModel model) {
		
		
		return voucherDao.getUser(vmodel.getFilter(), vmodel.getOrder(),
				vmodel.getOffset(), vmodel.getCount());
	}


	public String getUserCount(VoucherModel vmodel, BasicModel model) {
		// TODO Auto-generated method stub
		return voucherDao.getUserCount(vmodel.getFilter());
	}

}
