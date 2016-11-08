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
public class DaoAspect {

	private LogFactory logFactory;

	public LogFactory getLogFactory() {
		return logFactory;
	}

	public void setLogFactory(LogFactory logFactory) {
		this.logFactory = logFactory;
	}

	@Pointcut("execution(* cn.springmvc.dao.*Dao*.*(..))")
	private void aopDao() {
	}

	@After("aopDao()")
	public void afterDao(JoinPoint joinPoint) {
		if (Consts.DEBUG_MODE)
			System.out.println("[debug mode] "
					+ logFactory.getInstance("debug_log", "dao").doLog(joinPoint, AspectPosition.Aop_After));
	}

	@Before("aopDao()")
	public void beforeDao(JoinPoint joinPoint) {
		if (Consts.DEBUG_MODE)
			System.out.println("[debug mode] "
					+ logFactory.getInstance("debug_log", "dao").doLog(joinPoint, AspectPosition.Aop_Before));
	}

	@AfterReturning(pointcut = "aopDao()", returning = "retVal")
	public void afterReturning(JoinPoint joinPoint, Object retVal) {
		if (Consts.DEBUG_MODE)
			System.out.println("[debug mode] "
					+ logFactory.getInstance("debug_log", "dao").doLog(joinPoint, retVal, AspectPosition.Aop_Return));
	}

	@AfterThrowing(pointcut = "aopDao()", throwing = "e")
	public void afterThrowing(JoinPoint joinPoint, Exception e) {
		if (Consts.DEBUG_MODE)
			System.out.println("[debug mode] "
					+ logFactory.getInstance("debug_log", "dao").doLog(joinPoint, AspectPosition.Aop_Throw, e));
		System.out.println(logFactory.getInstance("error_log").doLog(joinPoint, AspectPosition.Aop_Throw, e));
	}
}
