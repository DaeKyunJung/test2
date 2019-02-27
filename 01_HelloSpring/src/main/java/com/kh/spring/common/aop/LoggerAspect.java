package com.kh.spring.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@Component//빈객체등록
@Aspect//aop객체임
public class LoggerAspect {

	private Logger logger=LoggerFactory.getLogger(LoggerAspect.class);
	
	/*@Pointcut("execution(* com.kh.spring..*(..))")
	public void myAround() {}*/
	@Pointcut("execution(* com.kh.spring.memo..*(..))")
	public void before() {}
	
	@Around("execution(* com.kh.spring..*(..))")	
	public Object loggerAdvice(ProceedingJoinPoint joinPoint) throws Throwable
	{
		/*Object[] objs=joinPoint.getArgs();
		for(Object o : objs)
			logger.debug("매개변수"+o);	*/	
		/*HttpServletRequest request=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		request.getParameterMap();*/
		Signature signature=joinPoint.getSignature();
		
		String type=signature.getDeclaringTypeName();
		String methodName=signature.getName();
		
		String componentName="";
		if(type.indexOf("Controller")>-1)
		{
			componentName="Controller \t : ";
		}
		else if(type.indexOf("Service")>-1)
		{
			componentName="Service \t : ";
		}
		else if(type.indexOf("Dao")>-1)
		{
			componentName="Dao \t : ";
		}
		logger.debug("[Before]"+componentName+type+"."+methodName+"()");
		Object obj=joinPoint.proceed();
		logger.debug("[after]"+componentName+type+"."+methodName+"()");
		return obj;
	}
	
	//before를 적용해보자!
	@Before("before()")
	public void beforeAd(JoinPoint joinPoint) {
		Signature sig=joinPoint.getSignature();
		logger.debug("before"+sig.getDeclaringTypeName()+" : "
		+sig.getName());
	}
	
	
	
}







