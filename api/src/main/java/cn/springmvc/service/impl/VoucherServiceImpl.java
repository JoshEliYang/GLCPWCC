package cn.springmvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.daoLvdi.VoucherDao;
import cn.springmvc.model.BasicModel;
import cn.springmvc.model.VoucheModel;
import cn.springmvc.service.VoucherService;

@Service
public class VoucherServiceImpl implements VoucherService {
	@Autowired
	VoucherDao voucherDao;
	
	public List<VoucheModel> getVouvher() {
		// TODO Auto-generated method stub
		return voucherDao.getVoucher();
	}

}
