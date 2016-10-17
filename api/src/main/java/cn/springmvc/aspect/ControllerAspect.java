package cn.springmvc.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.springmvc.utils.mongodb.LogFactory;

/**
 * 
 * @author johnson
 *
 */
@Aspect
public class ControllerAspect {

	private LogFactory logFactory;
	private boolean DEBUG_MODE = false;

	public LogFactory getLogFactory() {
		return logFactory;
	}

	public void setLogFactory(LogFactory logFactory) {
		this.logFactory = logFactory;
	}

	public boolean isDEBUG_MODE() {
		return DEBUG_MODE;
	}

	public void setDEBUG_MODE(boolean dEBUG_MODE) {
		DEBUG_MODE = dEBUG_MODE;
	}

	@Pointcut("execution(* cn.springmvc.controller.*.*Controller*.*(..))")
	private void aopController() {
	}

	/**
	 * after controller
	 * 
	 * @param joinPoint
	 */
	@After("aopController()")
	public void afterController(JoinPoint joinPoint) {
		System.out.println(logFactory.getInstance("operate_log").doLog(joinPoint, AspectPosition.Aop_After));
		if (DEBUG_MODE)
			System.out.println(
					"[debug model] " + logFactory.getInstance("debug_log").doLog(joinPoint, AspectPosition.Aop_After));
	}

	/**
	 * before controller
	 * 
	 * @param joinPoint
	 */
	@Before("aopController()")
	public void beforeController(JoinPoint joinPoint) {
		System.out.println(logFactory.getInstance("operate_log").doLog(joinPoint, AspectPosition.Aop_Before));
		if (DEBUG_MODE)
			System.out.println(
					"[debug model] " + logFactory.getInstance("debug_log").doLog(joinPoint, AspectPosition.Aop_Before));
	}

	/**
	 * after controller returning
	 * 
	 * @param joinPoint
	 * @param retVal
	 */
	@AfterReturning(pointcut = "aopController()", returning = "retVal")
	public void returnController(JoinPoint joinPoint, Object retVal) {
		System.out.println(logFactory.getInstance("operate_log").doLog(joinPoint, retVal, AspectPosition.Aop_Return));
		if (DEBUG_MODE)
			System.out.println("[debug model] "
					+ logFactory.getInstance("debug_log").doLog(joinPoint, retVal, AspectPosition.Aop_Return));
	}

	/**
	 * after controller throwing exceptions
	 * 
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(pointcut = "aopController()", throwing = "e")
	public void ControllerThrow(JoinPoint joinPoint, Exception e) {
		System.out.println(logFactory.getInstance("operate_log").doLog(joinPoint, AspectPosition.Aop_Throw));
		if (DEBUG_MODE)
			System.out.println("[debug model] "
					+ logFactory.getInstance("debug_log").doLog(joinPoint, AspectPosition.Aop_Throw, e));
	}

}
