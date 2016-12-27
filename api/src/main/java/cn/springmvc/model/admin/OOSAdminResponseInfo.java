package cn.springmvc.model.admin;

/**
 * 
 * @author johnson
 *
 */
public class OOSAdminResponseInfo {
	String code;
	String msg;
	OOSAdminData data;

	@Override
	public String toString() {
		return "OOSAdminResponseInfo [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public OOSAdminData getData() {
		return data;
	}

	public void setData(OOSAdminData data) {
		this.data = data;
	}

}
