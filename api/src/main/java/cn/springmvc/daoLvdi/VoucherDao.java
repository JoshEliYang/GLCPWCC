package cn.springmvc.daoLvdi;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.springmvc.model.FilterModel;
import cn.springmvc.model.OrderModel;
import cn.springmvc.model.UserParamModel;
import cn.springmvc.model.VoucheModel;

public interface VoucherDao {
	
	/**
	 * 剩余优惠券查询
	 * @return
	 * @author summ
	 */
	public List<VoucheModel> getVoucher();
	
	public List<String> getVoucherCode(@Param("list") List<String> list, @Param("voucherNum") int voucherNum);

	public List<UserParamModel> getUser(@Param("filter") FilterModel filter, @Param("OrderModel") OrderModel order,
			@Param("offset") int offset, @Param("count") int count);

	public String getUserCount(@Param("filter") FilterModel filter);

	public List<UserParamModel> getBindingCount(@Param("filter") FilterModel filter);

}
