package cn.springmvc.dao;

import org.apache.ibatis.annotations.Param;

import cn.springmvc.model.report.Customer;

public interface CustomerDao {

	public Customer select(String openid);

	public void update(Customer customer);

	public void insert(Customer customer);

	public void unscribe(@Param("openid") String openId, @Param("timestamp") long timestamp);
}
