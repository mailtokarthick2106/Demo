package com.stackroute.keepnote.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/* Annotate this class with @Aspect and @Component */
@Aspect
@Component
public class LoggingAspect {
	/*
	 * Write loggers for each of the methods of Category controller, any particular method
	 * will have all the four aspectJ annotation
	 * (@Before, @After, @AfterReturning, @AfterThrowing).
	 */
	@Before("execution(* com.stackroute.keepnote.controller..*(..))")
	public void logBefore(JoinPoint joinPoint) {

		System.out.println("logBefore() is running!");
		System.out.println("hijacked : " + joinPoint.getSignature().getName());
		System.out.println("******");
	}
	@After("execution(* com.stackroute.keepnote.controller..*(..))")
	public void logAfter(JoinPoint joinPoint) {

		System.out.println("logAfter() is running!");
		System.out.println("hijacked : " + joinPoint.getSignature().getName());
		System.out.println("******");

	}
	@AfterReturning(
		      pointcut = "execution(* com.stackroute.keepnote.controller..*(..))",
		      returning= "result")
		   public void logAfterReturning(JoinPoint joinPoint, Object result) {

			System.out.println("logAfterReturning() is running!");
			System.out.println("hijacked : " + joinPoint.getSignature().getName());
			System.out.println("Method returned value is : " + result);
			System.out.println("******");

		   }
	 @AfterThrowing(
		      pointcut = "execution(* com.stackroute.keepnote.exception..*(..))",
		      throwing= "error")
		    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {

			System.out.println("logAfterThrowing() is running!");
			System.out.println("hijacked : " + joinPoint.getSignature().getName());
			System.out.println("Exception : " + error);
			System.out.println("******");

		    }

}
