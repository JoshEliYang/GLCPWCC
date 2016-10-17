package com.springmvc.utils.mongodb;

import org.aspectj.lang.JoinPoint;

import com.springmvc.utils.mongodb.model.DebugLog;
import com.springmvc.utils.mongodb.model.MongoConfig;

import cn.springmvc.aspect.AspectPosition;

public class DebugLogUtil implements LogUtil {

	private MongoConfig mongoConfig;

	public DebugLogUtil(MongoConfig mongoConfig) {
		this.mongoConfig = mongoConfig;
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
			debugLog = new DebugLog(className, methodName, argList, "开始", "", "");
			log(debugLog);
			break;
		case Aop_After:
			debugLog = new DebugLog(className, methodName, argList, "结束", "", "");
			log(debugLog);
			break;
		case Aop_Return:
			debugLog = new DebugLog(className, methodName, argList, "返回结果", retVal.toString(), "");
			log(debugLog);
			break;
		case Aop_Throw:
			String errorMesg = e.getMessage();
			for (StackTraceElement item : e.getStackTrace()) {
				errorMesg += "\r\n" + item.toString();
			}
			debugLog = new DebugLog(className, methodName, argList, "异常", "", errorMesg);
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
					mongoUtil = new MongoDBUtil(mongoConfig);
					mongoUtil.setup();
					mongoUtil.insert("debug_log", log);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (mongoUtil != null)
						mongoUtil.destory();
				}
			}
		};
		pool.execute(logThread);
	}

}
