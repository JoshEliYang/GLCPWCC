package cn.springmvc.model;

import java.util.ArrayList;
import java.util.List;

public class VoucheModel {
	String promotionName;
	String voucherValue;
	String satisfyLimit;
	String number;
	String startTime;
	String endTime;
	String promotionId;
	int voucherNum;
	List<String> promotionIdList;
	
	public List<String> getPromotionIdList() {
		return promotionIdList;
	}
	public void setPromotionIdList(List<String> promotionIdList) {
		this.promotionIdList = promotionIdList;
	}
	public int getVoucherNum() {
		return voucherNum;
	}
	public void setVoucherNum(int voucherNum) {
		this.voucherNum = voucherNum;
	}
	public String getPromotionId() {
		return promotionId;
	}
	public void setPromotionId(String promotionId) {
		this.promotionId = promotionId;
	}
	public String getPromotionName() {
		return promotionName;
	}
	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}
	public String getVoucherValue() {
		return voucherValue;
	}
	public void setVoucherValue(String voucherValue) {
		this.voucherValue = voucherValue;
	}
	public String getSatisfyLimit() {
		return satisfyLimit;
	}
	public void setSatisfyLimit(String satisfyLimit) {
		this.satisfyLimit = satisfyLimit;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
