package cn.springmvc.model;

import java.util.List;

public class VoucherModel {

	int offset;
	int count;
	
	int customerCount;
	
	int voucherValue;
	int satisfyLimit;

	FilterModel filter;
	OrderModel order;
	
	List<String> promotionIds;
	
	List<String> users;
	
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
	public int getVoucherValue() {
		return voucherValue;
	}
	public void setVoucherValue(int voucherValue) {
		this.voucherValue = voucherValue;
	}
	public int getSatisfyLimit() {
		return satisfyLimit;
	}
	public void setSatisfyLimit(int satisfyLimit) {
		this.satisfyLimit = satisfyLimit;
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
	public int getCustomerCount() {
		return customerCount;
	}
	public void setCustomerCount(int customerCount) {
		this.customerCount = customerCount;
	}
	public List<String> getPromotionIds() {
		return promotionIds;
	}
	public void setPromotionIds(List<String> promotionIds) {
		this.promotionIds = promotionIds;
	}
	public List<String> getUsers() {
		return users;
	}
	public void setUsers(List<String> users) {
		this.users = users;
	}
	@Override
	public String toString() {
		return "VoucherModel [offset=" + offset + ", count=" + count
				+ ", customerCount=" + customerCount + ", voucherValue="
				+ voucherValue + ", satisfyLimit=" + satisfyLimit + ", filter="
				+ filter + ", order=" + order + ", promotionIds="
				+ promotionIds + ", users=" + users + "]";
	}
	

}
