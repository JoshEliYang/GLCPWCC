package cn.springmvc.aspect;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.springmvc.utils.mongodb.LogUtil;
import com.springmvc.utils.mongodb.model.OperateLog;

import cn.springmvc.model.BasicModel;
import cn.springmvc.model.User;

/**
 * 
 * @author johnson
 *
 */
@Aspect
public class ControllerAspect {
	private enum Position {
		Aop_Before, Aop_After, Aop_Return, Aop_Throw
	}

	private static Map<String, String> actionType = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;

		{
			put(".*add.*", "添加");
			put(".*insert.*", "添加");

			put(".*delete.*", "删除");

			put(".*update.*", "更新");
			put(".*edit.*", "更新");

			put(".*query.*", "查询");
			put(".*get.*", "查询");
		}
	};

	private LogUtil logUtil;

	public LogUtil getLogUtil() {
		return logUtil;
	}

	public void setLogUtil(LogUtil logUtil) {
		this.logUtil = logUtil;
	}

	@Pointcut("execution(* cn.springmvc.controller.*.*Controller*.*(..))")
	private void aopController() {
	}

	@After("aopController()")
	public void afterController(JoinPoint joinPoint) {
		System.out.println("[after controller]" + doLog(joinPoint, Position.Aop_After));
	}

	@Before("aopController()")
	public void beforeController(JoinPoint joinPoint) {
		System.out.println("[before contorller]" + doLog(joinPoint, Position.Aop_Before));
	}

	@AfterReturning(pointcut = "aopController()", returning = "retVal")
	public void returnController(JoinPoint joinPoint, Object retVal) {
		System.out.println("[return contorller]" + doLog(joinPoint, retVal, Position.Aop_Return));
	}

	@AfterThrowing(pointcut = "aopController()", throwing = "e")
	public void ControllerThrow(JoinPoint joinPoint, Exception e) {
		String out = "Exception Occured in " + joinPoint.getTarget().getClass().getName() + " \r\n";
		out += "\t" + e.getMessage() + "\r\n";
		StackTraceElement[] ste = e.getStackTrace();
		for (StackTraceElement st : ste) {
			out += "\t\t" + st.toString() + "\r\n";
		}
		System.out.println(out);
	}

	private OperateLog doLog(JoinPoint joinPoint, Object retVal, Position p) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();

		String argList = "";
		Object[] args = joinPoint.getArgs();
		for (Object arg : args) {
			argList += arg.toString() + "(" + arg.getClass().getName() + ");";
		}

		User admin = (User) request.getAttribute("admin");
		BasicModel basicModel = (BasicModel) request.getAttribute("BasicModel");

		String action = getActionType(methodName);
		String status = null;
		if (p == Position.Aop_Before) {
			status = "开始";
		} else if (p == Position.Aop_Return) {
			status = "返回结果";
		} else if (p == Position.Aop_After) {
			status = "结束";
		}

		OperateLog log = null;
		if (retVal != null)
			log = new OperateLog(admin, basicModel, className, methodName, argList, action, status, retVal.toString());
		else
			log = new OperateLog(admin, basicModel, className, methodName, argList, action, status, "");
		logUtil.operateLog(log);

		return log;
	}

	private OperateLog doLog(JoinPoint joinPoint, Position p) {
		return doLog(joinPoint, null, p);
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
}
