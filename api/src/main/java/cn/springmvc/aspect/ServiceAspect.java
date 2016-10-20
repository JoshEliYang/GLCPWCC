package cn.springmvc.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.springmvc.utils.mongodb.log.LogFactory;

import cn.springmvc.Consts;

/**
 * 
 * @author johnson
 *
 */
@Aspect
public class ServiceAspect {

	private LogFactory logFactory;

	public LogFactory getLogFactory() {
		return logFactory;
	}

	public void setLogFactory(LogFactory logFactory) {
		this.logFactory = logFactory;
	}

	@Pointcut("execution(* cn.springmvc.service.*.*Service*.*(..))")
	private void aopController() {
	}

	@After("aopController()")
	public void afterService(JoinPoint joinPoint) {
		System.out.println("after service");
		if (Consts.DEBUG_MODE)
			System.out.println("[debug mode] "
					+ logFactory.getInstance("debug_log", "service").doLog(joinPoint, AspectPosition.Aop_After));
	}

	@Before("aopController()")
	public void beforeService(JoinPoint joinPoint) {
		System.out.println("before service");
		if (Consts.DEBUG_MODE)
			System.out.println("[debug mode] "
					+ logFactory.getInstance("debug_log", "service").doLog(joinPoint, AspectPosition.Aop_Before));
	}

	@AfterReturning(pointcut = "aopController()", returning = "retVal")
	public void atferReturning(JoinPoint joinPoint, Object retVal) {
		System.out.println("returning service");
		if (Consts.DEBUG_MODE)
			System.out.println("[debug mode] " + logFactory.getInstance("debug_log", "service").doLog(joinPoint, retVal,
					AspectPosition.Aop_Return));
	}

	@AfterThrowing(pointcut = "aopController()", throwing = "e")
	public void atferThrowing(JoinPoint joinPoint, Exception e) {
		System.out.println("throwing service");
		if (Consts.DEBUG_MODE)
			System.out.println("[debug mode] "
					+ logFactory.getInstance("debug_log", "service").doLog(joinPoint, AspectPosition.Aop_Throw, e));
		System.out.println(logFactory.getInstance("error_log").doLog(joinPoint, AspectPosition.Aop_Throw, e));
	}

}
