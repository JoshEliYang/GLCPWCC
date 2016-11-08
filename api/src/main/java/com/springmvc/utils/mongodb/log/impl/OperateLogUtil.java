package com.springmvc.utils.mongodb.log.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.springmvc.utils.mongodb.MongoDBUtil;
import com.springmvc.utils.mongodb.log.LogUtil;
import com.springmvc.utils.mongodb.model.MongoConfig;
import com.springmvc.utils.mongodb.model.OperateLog;

import cn.springmvc.aspect.AspectPosition;
import cn.springmvc.model.BasicModel;
import cn.springmvc.model.User;

/**
 * 
 * @author johnson
 *
 */
public class OperateLogUtil implements LogUtil {

	private MongoConfig mongoConfig;

	OperateLog log;

	private Properties operateMapper;

	/**
	 * debug mode (set in spring-mvc.xml)
	 */
	private boolean DEBUG_MODE = false;

	public OperateLogUtil(MongoConfig mongoConfig, boolean DEBUG_MODE, Properties operateMapper) {
		this.mongoConfig = mongoConfig;
		this.DEBUG_MODE = DEBUG_MODE;
		this.operateMapper = operateMapper;
	}

	private static Map<String, String> actionType = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;

		{
			put(".*add.*", "添加");
			put(".*insert.*", "添加");

			put(".*delete.*", "删除");
			put(".*clear.*", "删除");

			put(".*update.*", "更新");
			put(".*edit.*", "更新");
			put(".*change.*", "更新");
			put(".*set.*","更新");

			put(".*query.*", "查询");
			put(".*get.*", "查询");
		}
	};

	public Object doLog(JoinPoint joinPoint, Object retVal, AspectPosition p, Exception e) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();

		String className = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		String target = getTarge(className);

		String argList = "";
		Object[] args = joinPoint.getArgs();
		for (Object arg : args) {
			argList += arg.toString() + "(" + arg.getClass().getName() + ");";
		}

		User admin = (User) request.getAttribute("admin");
		BasicModel basicModel = (BasicModel) request.getAttribute("BasicModel");

		String action = getActionType(methodName);
		OperateLog log = null;
		switch (p) {
		case Aop_Before:
			if (DEBUG_MODE) {
				log = new OperateLog(admin, basicModel, className, methodName, argList, action, target, "开始", "");
				log(log);
			}
			break;
		case Aop_Return:
			if (retVal != null)
				log = new OperateLog(admin, basicModel, className, methodName, argList, action, target, "返回结果",
						retVal.toString());
			else
				log = new OperateLog(admin, basicModel, className, methodName, argList, action, target, "返回结果", "");
			log(log);
			break;
		case Aop_After:
			if (DEBUG_MODE) {
				log = new OperateLog(admin, basicModel, className, methodName, argList, action, target, "结束", "");
				log(log);
			}
			break;
		case Aop_Throw:
			log = new OperateLog(admin, basicModel, className, methodName, argList, action, target, "异常", "");
			log(log);
			break;
		}
		return log;
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

	/**
	 * get action type by mehtodName
	 * 
	 * @param methodName
	 * @return
	 */
	private String getActionType(String methodName) {
		Iterator<Entry<String, String>> it = actionType.entrySet().iterator();
		while (it.hasNext()) {
			methodName.toLowerCase();
			Entry<String, String> item = it.next();
			if (methodName.matches(item.getKey())) {
				return item.getValue();
			}
		}
		return "其他";
	}

	/**
	 * get target name by className
	 * 
	 * @param className
	 * @return
	 */
	private String getTarge(String className) {
		Iterator<Entry<Object, Object>> it = operateMapper.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Object, Object> entry = (Entry<Object, Object>) it.next();
			String key = (String) entry.getKey();
			if (className.toLowerCase().matches(".*" + key.toLowerCase() + ".*")) {
				return (String) entry.getValue();
			}
		}
		return className;
	}

	/**
	 * write into MongoDB
	 * 
	 * @param logDat
	 */
	private void log(OperateLog logDat) {
		log = logDat;
		Thread operateThread = new Thread() {
			public void run() {
				MongoDBUtil mongoUtil = null;
				try {
					mongoUtil = MongoDBUtil.getInstance(mongoConfig);
					// mongoUtil.setup();
					mongoUtil.insert("operate_log", log);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					// if (mongoUtil != null)
					// mongoUtil.destory();
				}
			}
		};
		pool.execute(operateThread);
	}
}
