package cn.springmvc.dao;

import cn.springmvc.model.report.Customer;

public interface CustomerDao {
	
	public Customer select(String openid);
	
	public void update(Customer customer); 
	
	public void insert(Customer customer);
}
