package cn.springmvc.model.log;

/**
 * 
 * @author johnson
 *
 */
public class LogQuery {
	String startTime;
	String endTime;
	int skip = 0;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getSkip() {
		return skip;
	}

	public void setSkip(int skip) {
		this.skip = skip;
	}

	@Override
	public String toString() {
		return "LogQuery [startTime=" + startTime + ", endTime=" + endTime + ", skip=" + skip + "]";
	}

}
