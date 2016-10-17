package cn.springmvc.model;

/**
 * message type
 * 
 * @author johnson
 *
 */
public class MsgType {
	int id;
	String msgType;
	String remark;
	String requireDat;
	String bak1;
	String bak2;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRequireDat() {
		return requireDat;
	}

	public void setRequireDat(String requireDat) {
		this.requireDat = requireDat;
	}

	public String getBak1() {
		return bak1;
	}

	public void setBak1(String bak1) {
		this.bak1 = bak1;
	}

	public String getBak2() {
		return bak2;
	}

	public void setBak2(String bak2) {
		this.bak2 = bak2;
	}

	@Override
	public String toString() {
		return "MsgType [id=" + id + ", msgType=" + msgType + ", remark=" + remark + ", requireDat=" + requireDat
				+ ", bak1=" + bak1 + ", bak2=" + bak2 + "]";
	}

}
