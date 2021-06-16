package com.example.graalvmdemo.aspect;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggableAspect {

	private static final Logger logger = LoggerFactory.getLogger(LoggableAspect.class);
	 
    @Around("demoMethod()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("I'm working!");
        return joinPoint.proceed();
    }

    @Pointcut("execution(* com.example.graalvmdemo.rest.PersonController.demo(..))")
    private void demoMethod(){}
	
}
