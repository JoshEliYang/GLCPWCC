package cn.springmvc.model;

public class UserParamModel {

	String customerId;
	
	String openId;
	
	String nickName;
	
	String realName;
	
	String gender;
	
	String iphone;
	
	String idCard;
	
	String age;
	
	int totalAmount;
	
	String orders;
	
	String recentOrder;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIphone() {
		return iphone;
	}

	public void setIphone(String iphone) {
		this.iphone = iphone;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	public String getRecentOrder() {
		return recentOrder;
	}

	public void setRecentOrder(String recentOrder) {
		this.recentOrder = recentOrder;
	}

	@Override
	public String toString() {
		return "UserParamModel [customerId=" + customerId + ", openId="
				+ openId + ", nickName=" + nickName + ", realName=" + realName
				+ ", gender=" + gender + ", iphone=" + iphone + ", idCard="
				+ idCard + ", age=" + age + ", totalAmount=" + totalAmount
				+ ", orders=" + orders + ", recentOrder=" + recentOrder + "]";
	}

	public UserParamModel() {
		super();
	}

	public UserParamModel(String customerId, String openId, String nickName,
			String realName, String gender, String iphone, String idCard,
			String age, int totalAmount, String orders, String recentOrder) {
		super();
		this.customerId = customerId;
		this.openId = openId;
		this.nickName = nickName;
		this.realName = realName;
		this.gender = gender;
		this.iphone = iphone;
		this.idCard = idCard;
		this.age = age;
		this.totalAmount = totalAmount;
		this.orders = orders;
		this.recentOrder = recentOrder;
	}
	
	
}
