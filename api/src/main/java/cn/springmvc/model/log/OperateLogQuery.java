package cn.springmvc.model.log;

public class OperateLogQuery extends LogQuery {
	/**
	 * -256 means all
	 * 
	 * -1 means none
	 */
	int adminId = -256;
	int basicId = -256;

	String action = null;
	String status = null;

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public int getBasicId() {
		return basicId;
	}

	public void setBasicId(int basicId) {
		this.basicId = basicId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "OperateLogQuery [startTime=" + startTime + ", endTime=" + endTime + ", skip=" + skip + ", adminId="
				+ adminId + ", basicId=" + basicId + ", action=" + action + ", status=" + status + "]";
	}

}
