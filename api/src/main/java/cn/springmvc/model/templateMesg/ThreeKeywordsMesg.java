package cn.springmvc.model.templateMesg;

/**
 * 
 * @author johnson
 *
 */
public class ThreeKeywordsMesg {
	String openid;
	String first;
	String keyword1;
	String keyword2;
	String keyword3;
	String remark;
	String url;

	public static String[] getFields() {
		return new String[] { "openid", "first", "keyword1", "keyword2", "keyword3", "remark", "url" };
	}

	@Override
	public String toString() {
		return "ThreeKeywordsMesg [openid=" + openid + ", first=" + first + ", keyword1=" + keyword1 + ", keyword2="
				+ keyword2 + ", keyword3=" + keyword3 + ", remark=" + remark + ", url=" + url + "]";
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

}
