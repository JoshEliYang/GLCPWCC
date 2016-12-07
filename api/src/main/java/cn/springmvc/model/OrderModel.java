package cn.springmvc.model;

public class OrderModel {

	String orderBy;
	
	String sort;

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	@Override
	public String toString() {
		return "OrderModel [orderBy=" + orderBy + ", sort=" + sort + "]";
	}

	public OrderModel() {
		super();
	}

	public OrderModel(String orderBy, String sort) {
		super();
		this.orderBy = orderBy;
		this.sort = sort;
	}
	
	
	
}
