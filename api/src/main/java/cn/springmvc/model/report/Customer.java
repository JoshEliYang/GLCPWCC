package cn.springmvc.model.report;

public class Customer {
	String openid;
	int subscribe;
	String nickname;
	int sex;
	String city;
	String province;
	String country;
	String headimgurl;
	long subscribe_time;
	long unsubscribe_time;
	String unionid;
	String reamark;
	int groupid;
	int[] tagid_list;
	
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public int getSubscribe() {
		return subscribe;
	}
	public void setSubscribe(int subscribe) {
		this.subscribe = subscribe;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public long getSubscribe_time() {
		return subscribe_time;
	}
	public void setSubscribe_time(long subscribe_time) {
		this.subscribe_time = subscribe_time;
	}
	public long getUnsubscribe_time() {
		return unsubscribe_time;
	}
	public void setUnsubscribe_time(long unsubscribe_time) {
		this.unsubscribe_time = unsubscribe_time;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	public String getReamark() {
		return reamark;
	}
	public void setReamark(String reamark) {
		this.reamark = reamark;
	}
	public int getGroupid() {
		return groupid;
	}
	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}
	public int[] getTagid_list() {
		return tagid_list;
	}
	public void setTagid_list(int[] tagid_list) {
		this.tagid_list = tagid_list;
	}
}
