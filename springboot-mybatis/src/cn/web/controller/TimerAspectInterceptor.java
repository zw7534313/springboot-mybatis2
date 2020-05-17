package cn.web.controller;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//使用@Aspect实现面向切面编程：如controller方法调用的耗时
@Aspect
@Component
public class TimerAspectInterceptor {

	private Logger logger = LoggerFactory.getLogger(TimerAspectInterceptor.class);

	@Pointcut("execution(* cn.web.controller.UserController..*(..))")
	public void controllerMethodPointCut() {

	}
	
	 //匹配那些有指定注解的连接点@Logable(带用这个注解的所有方法作为连接点)
	 @Pointcut("@annotation(cn.web.controller.Logable)")
	 public void multiMethodPointCut() {
	
	 }

	/*@Before(value="controllerMethodPointCut()")
	public void before2(JoinPoint point) {
		Object[] args = point.getArgs();
		logger.info("before..args={}", args.length);
	}
	@Before(value = "beforePointcut(param)", argNames = "param")  
	public void beforeAdvice(String param) {  
          System.out.println("===========before advice param:" + param);  
	} 

	@After("controllerMethodPointCut()")
	public void after() {
		logger.info("after..");
	}

	@AfterReturning(value = "controllerMethodPointCut()")
	public void afterReturn() {
		logger.info("afterReturn..");
	}
	*/
	@AfterThrowing(value = "controllerMethodPointCut()")
	public void afterThrow() {
		logger.info("afterThrow..");
	}
	
	 //@Around(value = "controllerMethodPointCut() || multiMethodPointCut()")
	//@Around(value = "controllerMethodPointCut()")
	@Around("multiMethodPointCut()") //环饶通知如果原有抛出异常，会调用afterThrow()
	public void aroundControllerMethod(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();
		//回调目标的原有方法
		joinPoint.proceed();
		logger.info("op=look method={} cost={}", joinPoint.getSignature().getName(),
				System.currentTimeMillis() - start);
	}
	
}
