package cn.springmvc.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.alibaba.fastjson.JSON;

@Aspect
public class ServiceAspect {

	@Pointcut("execution(* cn.springmvc.service.*Service*.*(..))")
	private void aopController() {
	}

	@After("aopController()")
	public void afterService(JoinPoint joinPoint) {
		System.out.println("after service ... OK");
	}

	@Before("aopController()")
	public void beforeService(JoinPoint joinPoint) {
		String out = "before service ";
		Object[] args = joinPoint.getArgs();
		for (Object arg : args) {
			out += arg.toString() + "(" + arg.getClass().getName() + ") ";
		}
		System.out.println(out);
	}

	@AfterReturning(pointcut = "aopController()", returning = "retVal")
	public void atferReturning(Object retVal) {
		String out = "atfer service returned";
		String outDat = JSON.toJSONString(retVal);

		System.out.println(out + " " + outDat);
	}

	@AfterThrowing(pointcut = "aopController()", throwing = "e")
	public void atferThrowing(Exception e) {
		String out = "Exception Occured\r\n";
		out += "\t" + e.getMessage() + "\r\n";
		StackTraceElement[] ste = e.getStackTrace();
		for (StackTraceElement st : ste) {
			out += "\t\t" + st.toString() + "\r\n";
		}
		System.out.println(out);
	}
}
