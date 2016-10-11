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
public class DaoAspect {

	@Pointcut("execution(* cn.springmvc.dao.*Dao*.*(..))")
	private void aopDao() {
	}

	@After("aopDao()")
	public void afterDao(JoinPoint joinPoint) {
		System.out.println("after dao ... test OK");
	}

	@Before("aopDao()")
	public void beforeDao(JoinPoint joinPoint) {
		String out = "before dao ";
		Object[] args = joinPoint.getArgs();
		for (Object arg : args) {
			out += arg.toString() + "(" + arg.getClass().getName() + ") ";
		}
		System.out.println(out);
	}

	@AfterReturning(pointcut = "aopDao()", returning = "retVal")
	public void afterReturning(Object retVal) {
		String out = "atfer dao returned";
		String outDat = JSON.toJSONString(retVal);

		System.out.println(out + " " + outDat);
	}

	@AfterThrowing(pointcut = "aopDao()", throwing = "e")
	public void afterThrowing(Exception e) {
		String out = "Exception Occured\r\n";
		out += "\t" + e.getMessage() + "\r\n";
		StackTraceElement[] ste = e.getStackTrace();
		for (StackTraceElement st : ste) {
			out += "\t\t" + st.toString() + "\r\n";
		}
		System.out.println(out);
	}
}
