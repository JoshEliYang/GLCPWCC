package com.springmvc.utils.mongodb.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.User;

/**
 * 
 * @author johnson
 *
 */
public class OperateLog {
	String dateTime;
	int adminId;
	/**
	 * admin's real name
	 */
	String adminName;
	int basicId;
	String basicRemark;
	String className;
	String methodName;
	String argList;
	/**
	 * action:[add,delete,update,query...]
	 */
	String action;
	/**
	 * the object which was treated
	 */
	String target;
	/**
	 * before/after/return action
	 */
	String status;
	String retrunDat;

	/**
	 * structure with all fields
	 * 
	 * @param admin
	 * @param basicModel
	 * @param className
	 * @param methodName
	 * @param argList
	 * @param action
	 * @param retrunDat
	 */
	public OperateLog(User admin, BasicModel basicModel, String className, String methodName, String argList,
			String action, String target, String status, String retrunDat) {
		super();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		this.dateTime = df.format(new Date());
		if (admin != null) {
			this.adminId = admin.getId();
			this.adminName = admin.getRealname();
		} else {
			this.adminId = -1;
			this.adminName = "";
		}
		if (basicModel != null) {
			this.basicId = basicModel.getId();
			this.basicRemark = basicModel.getRemark();
		} else {
			this.basicId = -1;
			this.basicRemark = "";
		}
		this.className = className;
		this.methodName = methodName;
		this.argList = argList;
		this.action = action;
		this.target = target;
		this.status = status;
		this.retrunDat = retrunDat;
	}

	/**
	 * structured without returnDat
	 * 
	 * @param admin
	 * @param basicModel
	 * @param className
	 * @param methodName
	 * @param argList
	 * @param action
	 */
	public OperateLog(User admin, BasicModel basicModel, String className, String methodName, String argList,
			String action, String target, String status) {
		super();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		this.dateTime = df.format(new Date());
		if (admin != null) {
			this.adminId = admin.getId();
			this.adminName = admin.getRealname();
		} else {
			this.adminId = -1;
			this.adminName = "";
		}
		if (basicModel != null) {
			this.basicId = basicModel.getId();
			this.basicRemark = basicModel.getRemark();
		} else {
			this.basicId = -1;
			this.basicRemark = "";
		}
		this.className = className;
		this.methodName = methodName;
		this.argList = argList;
		this.action = action;
		this.target = target;
		this.status = status;
	}

	public OperateLog() {
		super();
	}

	@Override
	public String toString() {
		return dateTime + " " + adminName + action + target + " 状态:" + status + " (用户ID:" + adminId + ", basicId="
				+ basicId + ", basicRemark=" + basicRemark + ", 类名=" + className + ", 方法名=" + methodName + ", 参数列表="
				+ argList + ", 返回值=" + retrunDat + ")";
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public int getBasicId() {
		return basicId;
	}

	public void setBasicId(int basicId) {
		this.basicId = basicId;
	}

	public String getBasicRemark() {
		return basicRemark;
	}

	public void setBasicRemark(String basicRemark) {
		this.basicRemark = basicRemark;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getArgList() {
		return argList;
	}

	public void setArgList(String argList) {
		this.argList = argList;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRetrunDat() {
		return retrunDat;
	}

	public void setRetrunDat(String retrunDat) {
		this.retrunDat = retrunDat;
	}

}
