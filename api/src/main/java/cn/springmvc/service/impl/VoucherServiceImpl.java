package cn.springmvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.daoMaster.VoucherCodeDao;
import cn.springmvc.model.VoucheModel;
import cn.springmvc.service.VoucherService;

@Service
public class VoucherServiceImpl implements VoucherService {
	@Autowired
	VoucherCodeDao voucherDao;

	/**
	 * 剩余优惠券查询
	 * 
	 * @return
	 * @author summ
	 */
	public List<VoucheModel> getVouvher() {
		return voucherDao.getVoucher();
	}

	/**
	 * 获取券码
	 * 
	 * @author summ
	 * @return
	 */
	public List<String> getVoucherCode(List<String> promotionIdList, int voucherNum) {
		System.out.println(promotionIdList);
		return voucherDao.getVoucherCode(promotionIdList, voucherNum);
	}

}
