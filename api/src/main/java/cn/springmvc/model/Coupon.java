package cn.springmvc.model;

public class Coupon {
	String openid;
	/**
	 * title
	 */
	String first;
	/**
	 * 兑换券名称
	 */
	String keyword1;
	/**
	 * 兑换券数量
	 */
	String keyword2;
	/**
	 * 兑换码
	 */
	String keyword3;
	/**
	 * 有效期
	 */
	String keyword4;
	/**
	 * 附加消息
	 */
	String remark;
	String url;

	public static String[] getFields() {
		return new String[] { "openid", "first", "keyword1", "keyword2", "keyword3", "keyword4", "remark", "url" };
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getKeyword1() {
		return keyword1;
	}

	public void setKeyword1(String keyword1) {
		this.keyword1 = keyword1;
	}

	public String getKeyword2() {
		return keyword2;
	}

	public void setKeyword2(String keyword2) {
		this.keyword2 = keyword2;
	}

	public String getKeyword3() {
		return keyword3;
	}

	public void setKeyword3(String keyword3) {
		this.keyword3 = keyword3;
	}

	public String getKeyword4() {
		return keyword4;
	}

	public void setKeyword4(String keyword4) {
		this.keyword4 = keyword4;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return this.getOpenid() + "\t" + this.getFirst() + "\t" + this.getKeyword1() + "\t" + this.getKeyword2() + "\t"
				+ this.getKeyword3() + "\t" + this.getKeyword4() + "\t" + this.getRemark() + "\t" + this.getUrl();
	}

}
