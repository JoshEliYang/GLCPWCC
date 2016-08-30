package cn.springmvc.model;

/**
 * 
 * @author johnson
 *
 */
public class TaskResponse {
	int adminId;
	String taskTimestamp;
	String task;
	String message;
	boolean isRunning;
	int progress;
	int max;

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public String getTaskTimestamp() {
		return taskTimestamp;
	}

	public void setTaskTimestamp(String taskTimestamp) {
		this.taskTimestamp = taskTimestamp;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public TaskResponse(int adminId, String taskTimestamp, String task, String message, boolean isRunning, int progress,
			int max) {
		super();
		this.adminId = adminId;
		this.taskTimestamp = taskTimestamp;
		this.task = task;
		this.message = message;
		this.isRunning = isRunning;
		this.progress = progress;
		this.max = max;
	}

	public TaskResponse() {
		super();
	}

}
