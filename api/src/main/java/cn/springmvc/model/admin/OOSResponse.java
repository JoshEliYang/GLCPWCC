package cn.springmvc.model.admin;

import java.util.List;

/**
 * 
 * @author johnson
 *
 */
public class OOSResponse {

	String code;
	String msg;
	List<OOSAdmin> data;

	@Override
	public String toString() {
		return "OOSResponse [code=" + code + ", msg=" + msg + ", admin=" + data + "]";
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

	public List<OOSAdmin> getData() {
		return data;
	}

	public void setData(List<OOSAdmin> data) {
		this.data = data;
	}

}
