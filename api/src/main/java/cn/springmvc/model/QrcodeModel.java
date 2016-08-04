package cn.springmvc.model;

public class QrcodeModel {
	int id;
	String name;
	String url;
	String ticket;
	long sceneid;

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
	
}
