package cn.springmvc.model.log;

/**
 * 
 * @author johnson
 *
 */
public class DebugLogQuery extends LogQuery {
	String status = null;
	String layer = null;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}

	@Override
	public String toString() {
		return "DebugLogQuery [status=" + status + ", layer=" + layer + ", startTime=" + startTime + ", endTime="
				+ endTime + ", skip=" + skip + "]";
	}

}
