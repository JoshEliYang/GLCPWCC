package cn.springmvc.service.impl;

import java.util.ArrayList;
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
	
	/**
	 * 剩余优惠券查询
	 * @return
	 * @author summ
	 */
	public List<VoucheModel> getVouvher() {
		// TODO Auto-generated method stub
		return voucherDao.getVoucher();
	}

	/**
	 * 获取券码
	 * @author summ
	 * @return 
	 */
	public List<String> getVoucherCode(List<String> promotionIdList, int voucherNum) {
		// TODO Auto-generated method stub
		System.out.println(promotionIdList);
		return voucherDao.getVoucherCode(promotionIdList, voucherNum);
	}

}
