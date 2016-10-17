package cn.springmvc.model;

public class QrcodeModel {
	int id;
	long basicId;
	String name;
	String url;
	String ticket;
	long sceneid;

	public long getBasicId() {
		return basicId;
	}

	public void setBasicId(long basicId) {
		this.basicId = basicId;
	}

	public long getSceneid() {
		return sceneid;
	}

	public void setSceneid(long sceneid) {
		this.sceneid = sceneid;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "QrcodeModel [id=" + id + ", basicId=" + basicId + ", name=" + name + ", url=" + url + ", ticket="
				+ ticket + ", sceneid=" + sceneid + "]";
	}

}
