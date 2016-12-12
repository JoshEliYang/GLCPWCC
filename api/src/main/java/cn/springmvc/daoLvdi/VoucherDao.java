package cn.springmvc.daoLvdi;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.springmvc.model.FilterModel;
import cn.springmvc.model.OrderModel;
import cn.springmvc.model.UserParamModel;

/**
 * 
 * @author xz
 *
 */
public interface VoucherDao {

	public List<UserParamModel> getUser(@Param("filter") FilterModel filter,
			@Param("OrderModel") OrderModel order,@Param("offset")int offset,@Param("count")int count);

	public String getUserCount(@Param("filter") FilterModel filter);

	public List<UserParamModel> getBindingCount(@Param("filter") FilterModel filter);


}
