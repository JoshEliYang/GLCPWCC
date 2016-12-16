package cn.springmvc.model;

/**
 * 
 * @author johnson
 *
 */
public class TaskRequest {
	String method; //VoucherBindingMessage
	String taskTimeStamp; //""
	User admin;
	/**
	 * need to convert parameter object to string
	 */
	String parameter;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getTaskTimeStamp() {
		return taskTimeStamp;
	}

	public void setTaskTimeStamp(String taskTimeStamp) {
		this.taskTimeStamp = taskTimeStamp;
	}

	public User getAdmin() {
		return admin;
	}

	public void setAdmin(User admin) {
		this.admin = admin;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public TaskRequest(String method, String taskTimeStamp, User admin, String parameter) {
		super();
		this.method = method;
		this.taskTimeStamp = taskTimeStamp;
		this.admin = admin;
		this.parameter = parameter;
	}

	public TaskRequest() {
		super();
	}

	@Override
	public String toString() {
		return "TaskRequest [method=" + method + ", taskTimeStamp=" + taskTimeStamp + ", admin=" + admin
				+ ", parameter=" + parameter + "]";
	}

}
