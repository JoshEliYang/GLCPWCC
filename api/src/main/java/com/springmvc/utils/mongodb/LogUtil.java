package com.springmvc.utils.mongodb;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.aspectj.lang.JoinPoint;

import cn.springmvc.aspect.AspectPosition;

/**
 * 
 * @author johnson
 *
 */
public interface LogUtil {

	static ExecutorService pool = Executors.newCachedThreadPool();

	public Object doLog(JoinPoint joinPoint, Object retVal, AspectPosition p, Exception e);

	public Object doLog(JoinPoint joinPoint, Object retVal, AspectPosition p);

	public Object doLog(JoinPoint joinPoint, AspectPosition p);

	public Object doLog(JoinPoint joinPoint, AspectPosition p, Exception e);

}
