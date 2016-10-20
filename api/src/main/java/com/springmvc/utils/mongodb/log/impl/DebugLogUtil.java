package com.springmvc.utils.mongodb.log.impl;

import org.aspectj.lang.JoinPoint;

import com.springmvc.utils.mongodb.MongoDBUtil;
import com.springmvc.utils.mongodb.log.LogUtil;
import com.springmvc.utils.mongodb.model.DebugLog;
import com.springmvc.utils.mongodb.model.MongoConfig;

import cn.springmvc.aspect.AspectPosition;

public class DebugLogUtil implements LogUtil {

	private MongoConfig mongoConfig;
	private String layer;

	public DebugLogUtil(MongoConfig mongoConfig, String layer) {
		this.mongoConfig = mongoConfig;
		this.layer = layer;
	}

	public Object doLog(JoinPoint joinPoint, Object retVal, AspectPosition p, Exception e) {
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();

		String argList = "";
		Object[] args = joinPoint.getArgs();
		for (Object arg : args) {
			argList += arg.toString() + "(" + arg.getClass().getName() + ");";
		}

		DebugLog debugLog = null;
		switch (p) {
		case Aop_Before:
			debugLog = new DebugLog(className, methodName, argList, "开始", "", "", layer);
			log(debugLog);
			break;
		case Aop_After:
			debugLog = new DebugLog(className, methodName, argList, "结束", "", "", layer);
			log(debugLog);
			break;
		case Aop_Return:
			if (retVal != null)
				debugLog = new DebugLog(className, methodName, argList, "返回结果", retVal.toString(), "", layer);
			else
				debugLog = new DebugLog(className, methodName, argList, "返回结果", "", "", layer);
			log(debugLog);
			break;
		case Aop_Throw:
			String errorMesg = e.getMessage();
			for (StackTraceElement item : e.getStackTrace()) {
				errorMesg += "\r\n" + item.toString();
			}
			debugLog = new DebugLog(className, methodName, argList, "异常", "", errorMesg, layer);
			log(debugLog);
			break;
		}
		return debugLog;
	}

	public Object doLog(JoinPoint joinPoint, Object retVal, AspectPosition p) {
		return doLog(joinPoint, retVal, p, null);
	}

	public Object doLog(JoinPoint joinPoint, AspectPosition p) {
		return doLog(joinPoint, "", p, null);
	}

	public Object doLog(JoinPoint joinPoint, AspectPosition p, Exception e) {
		return doLog(joinPoint, "", p, e);
	}

	DebugLog log;

	/**
	 * write into mongoDB server
	 * 
	 * @param debugLog
	 */
	private void log(DebugLog debugLog) {
		log = debugLog;
		Thread logThread = new Thread() {
			public void run() {
				MongoDBUtil mongoUtil = null;
				try {
					mongoUtil = MongoDBUtil.getInstance(mongoConfig);
					// mongoUtil.setup();
					mongoUtil.insert("debug_log", log);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					// if (mongoUtil != null)
					// mongoUtil.destory();
				}
			}
		};
		pool.execute(logThread);
	}

}
