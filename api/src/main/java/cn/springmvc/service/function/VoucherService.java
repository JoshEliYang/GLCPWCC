package cn.springmvc.service.function;

import java.util.ArrayList;
import java.util.List;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.VoucheModel;

public interface VoucherService {
	
	/**
	 * 剩余优惠券查询
	 * @return
	 * @author summ
	 */
	public List<VoucheModel> getVouvher();
	
	public List<String> getVoucherCode(List<String> promotionIdList, int voucherNum);
}
