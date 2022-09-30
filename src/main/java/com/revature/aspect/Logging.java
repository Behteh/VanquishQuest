package com.revature.aspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@Aspect
@Component
public class Logging {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Pointcut("execution(* com.revature.controller.*.*(..))")
	private void controllerPointcut() {}
	
	@Before(value = "controllerPointcut()")
	public void beforeAdvice(JoinPoint pjp)
	{
		logger.info("{} is being called", pjp);
	}

}
