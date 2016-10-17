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
public class ServiceAspect {

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

	@Pointcut("execution(* cn.springmvc.service.*.*Service*.*(..))")
	private void aopController() {
	}

	@After("aopController()")
	public void afterService(JoinPoint joinPoint) {
		if (DEBUG_MODE)
			System.out.println(
					"[debug mode] " + logFactory.getInstance("debug_log").doLog(joinPoint, AspectPosition.Aop_After));
	}

	@Before("aopController()")
	public void beforeService(JoinPoint joinPoint) {
		if (DEBUG_MODE)
			System.out.println(
					"[debug mode] " + logFactory.getInstance("debug_log").doLog(joinPoint, AspectPosition.Aop_Before));
	}

	@AfterReturning(pointcut = "aopController()", returning = "retVal")
	public void atferReturning(JoinPoint joinPoint, Object retVal) {
		if (DEBUG_MODE)
			System.out.println("[debug mode] "
					+ logFactory.getInstance("debug_log").doLog(joinPoint, retVal, AspectPosition.Aop_Return));
	}

	@AfterThrowing(pointcut = "aopController()", throwing = "e")
	public void atferThrowing(JoinPoint joinPoint, Exception e) {
		if (DEBUG_MODE)
			System.out.println("[debug mode] "
					+ logFactory.getInstance("debug_log").doLog(joinPoint, AspectPosition.Aop_Throw, e));
	}

}
