package cn.springmvc.model.templateMesg;

/**
 * 优惠券绑定模块，绑定成功、失败时保存的路径
 * 
 * @author johnson
 *
 */
public class VoucheBindingResult {
	String successFolder = null;
	String failedFolder = null;

	public String getSuccessFolder() {
		return successFolder;
	}

	public void setSuccessFolder(String successFolder) {
		this.successFolder = successFolder;
	}

	public String getFailedFolder() {
		return failedFolder;
	}

	public void setFailedFolder(String failedFolder) {
		this.failedFolder = failedFolder;
	}

	@Override
	public String toString() {
		return "VoucheBindingResult [successFolder=" + successFolder + ", failedFolder=" + failedFolder + "]";
	}

}
