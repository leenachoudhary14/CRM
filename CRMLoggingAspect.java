package com.luv2code.springdemo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {

	//setup logger
	private Logger myLogger = 
			              Logger.getLogger(getClass().getName());
	
	//set up pointcut declaration
	@Pointcut("execution(* com.luv2code.springdemo.controller.*.*(..))")
	private void forControllerPackage() {}
	
	//do same for service and dao package
	@Pointcut("execution(* com.luv2code.springdemo.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* com.luv2code.springdemo.dao.*.*(..))")
	private void forDAOPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDAOPackage()")
	private void forAddFlow() {}
	
	//add @Before advice
	@Before("forAddFlow()")
	public void before(JoinPoint theJoinPoint) {
	
		//display method we are calling
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("====>>> in @Before calling method: " + theMethod);
		
		//display the argument of the method
		
		//get the argument
		Object args[] = theJoinPoint.getArgs();
		
		//loop thru and display args
		for(Object tempArg : args) {
			myLogger.info("====>> arguments: " + tempArg);
		}
	}
	
	//add @AfterReturning advice
	@AfterReturning(
			     pointcut="forAddFlow()" ,
			     returning="theResult")
	public void afterReturning(JoinPoint theJoinPoint, Object theResult) {
		
		//display method we are returning from
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("====>>> in @AfterReturning: from method: " + theMethod);
		
		//display data return
		myLogger.info("====>> result: " + theResult);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
