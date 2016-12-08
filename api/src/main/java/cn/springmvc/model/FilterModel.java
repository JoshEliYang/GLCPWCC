package cn.springmvc.model;

public class FilterModel {

	String sex;
	
	String phone;
	
	String idCard;
	
	int ageST=-1;
	
	int ageED=-1;
	
	String amountST;
	
	String amountED;
	
	String orderST;
	
	String orderED;

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public int getAgeST() {
		return ageST;
	}

	public void setAgeST(int ageST) {
		this.ageST = ageST;
	}

	public int getAgeED() {
		return ageED;
	}

	public void setAgeED(int ageED) {
		this.ageED = ageED;
	}

	public String getAmountST() {
		return amountST;
	}

	public void setAmountST(String amountST) {
		this.amountST = amountST;
	}

	public String getAmountED() {
		return amountED;
	}

	public void setAmountED(String amountED) {
		this.amountED = amountED;
	}

	public String getOrderST() {
		return orderST;
	}

	public void setOrderST(String orderST) {
		this.orderST = orderST;
	}

	public String getOrderED() {
		return orderED;
	}

	public void setOrderED(String orderED) {
		this.orderED = orderED;
	}

	@Override
	public String toString() {
		return "FilterModel [sex=" + sex + ", phone=" + phone + ", idCard="
				+ idCard + ", ageST=" + ageST + ", ageED=" + ageED
				+ ", amountST=" + amountST + ", amountED=" + amountED
				+ ", orderST=" + orderST + ", orderED=" + orderED + "]";
	}

	public FilterModel() {
		super();
	}

	public FilterModel(String sex, String phone, String idCard, int ageST,
			int ageED, String amountST, String amountED, String orderST,
			String orderED) {
		super();
		this.sex = sex;
		this.phone = phone;
		this.idCard = idCard;
		this.ageST = ageST;
		this.ageED = ageED;
		this.amountST = amountST;
		this.amountED = amountED;
		this.orderST = orderST;
		this.orderED = orderED;
	}

	
	
	
	
}
