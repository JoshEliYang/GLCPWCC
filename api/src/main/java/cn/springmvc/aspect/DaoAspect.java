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
public class DaoAspect {

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

	@Pointcut("execution(* cn.springmvc.dao.*Dao*.*(..))")
	private void aopDao() {
	}

	@After("aopDao()")
	public void afterDao(JoinPoint joinPoint) {
		if (DEBUG_MODE)
			System.out.println(
					"[debug mode] " + logFactory.getInstance("debug_log").doLog(joinPoint, AspectPosition.Aop_After));
	}

	@Before("aopDao()")
	public void beforeDao(JoinPoint joinPoint) {
		if (DEBUG_MODE)
			System.out.println(
					"[debug mode] " + logFactory.getInstance("debug_log").doLog(joinPoint, AspectPosition.Aop_Before));
	}

	@AfterReturning(pointcut = "aopDao()", returning = "retVal")
	public void afterReturning(JoinPoint joinPoint, Object retVal) {
		if (DEBUG_MODE)
			System.out.println("[debug mode] "
					+ logFactory.getInstance("debug_log").doLog(joinPoint, retVal, AspectPosition.Aop_Return));
	}

	@AfterThrowing(pointcut = "aopDao()", throwing = "e")
	public void afterThrowing(JoinPoint joinPoint, Exception e) {
		if (DEBUG_MODE)
			System.out.println("[debug mode] "
					+ logFactory.getInstance("debug_log").doLog(joinPoint, AspectPosition.Aop_Throw, e));
	}
}
