package cn.springmvc.model;

public class FilterModel {

	int sex;
	
	String phone;
	
	String idCard;
	
	int ageST;
	
	int ageED;
	
	String amountST;
	
	String amountED;
	
	int orderST;
	
	int orderED;

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
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

	public int getOrderST() {
		return orderST;
	}

	public void setOrderST(int orderST) {
		this.orderST = orderST;
	}

	public int getOrderED() {
		return orderED;
	}

	public void setOrderED(int orderED) {
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

	public FilterModel(int sex, String phone, String idCard, int ageST,
			int ageED, String amountST, String amountED, int orderST,
			int orderED) {
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
