package cn.springmvc.daoMaster;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.springmvc.model.VoucheModel;

public interface VoucherCodeDao {
	/**
	 * 剩余优惠券查询
	 * 
	 * @return
	 * @author summ
	 */
	public List<VoucheModel> getVoucher();

	public List<String> getVoucherCode(@Param("list") List<String> list, @Param("voucherNum") int voucherNum);
}
