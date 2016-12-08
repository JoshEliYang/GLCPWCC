package cn.springmvc.model;

public class VoucherModel {

	int offset;

	int count;

	FilterModel filter;

	OrderModel order;

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public FilterModel getFilter() {
		return filter;
	}

	public void setFilter(FilterModel filter) {
		this.filter = filter;
	}

	public OrderModel getOrder() {
		return order;
	}

	public void setOrder(OrderModel order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "VoucherModel [offset=" + offset + ", count=" + count
				+ ", filter=" + filter + ", order=" + order + "]";
	}

}
