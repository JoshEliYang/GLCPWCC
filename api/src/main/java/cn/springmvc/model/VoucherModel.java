package cn.springmvc.model;

import java.util.List;

public class VoucherModel {

	List<FilterModel> filter;
	
	List<OrderModel> order;

	public List<FilterModel> getFilter() {
		return filter;
	}

	public void setFilter(List<FilterModel> filter) {
		this.filter = filter;
	}

	public List<OrderModel> getOrder() {
		return order;
	}

	public void setOrder(List<OrderModel> order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "VoucherModel [filter=" + filter + ", order=" + order + "]";
	}

	public VoucherModel() {
		super();
	}

	public VoucherModel(List<FilterModel> filter, List<OrderModel> order) {
		super();
		this.filter = filter;
		this.order = order;
	}

	
	
}
