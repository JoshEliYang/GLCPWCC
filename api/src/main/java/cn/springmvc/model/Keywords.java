package cn.springmvc.model;

/**
 * keywords in autoReply
 * 
 * @author johnson
 *
 */
public class Keywords {
	int id;
	int basicId;
	String value;
	int msgType;
	String reply;
	String remark;
	boolean isSubscribe;
	boolean inUsing;
	String bak1;
	String bak2;
	String msgTypeName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBasicId() {
		return basicId;
	}

	public void setBasicId(int basicId) {
		this.basicId = basicId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isSubscribe() {
		return isSubscribe;
	}

	public void setSubscribe(boolean isSubscribe) {
		this.isSubscribe = isSubscribe;
	}

	public boolean isInUsing() {
		return inUsing;
	}

	public void setInUsing(boolean inUsing) {
		this.inUsing = inUsing;
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

	public String getMsgTypeName() {
		return msgTypeName;
	}

	public void setMsgTypeName(String msgTypeName) {
		this.msgTypeName = msgTypeName;
	}

	@Override
	public String toString() {
		return "Keywords [id=" + id + ", basicId=" + basicId + ", value=" + value + ", msgType=" + msgType + ", reply="
				+ reply + ", remark=" + remark + ", isSubscribe=" + isSubscribe + ", inUsing=" + inUsing + ", bak1="
				+ bak1 + ", bak2=" + bak2 + ", msgTypeName=" + msgTypeName + "]";
	}

}
