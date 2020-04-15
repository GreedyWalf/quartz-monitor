package com.qs.quartz.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAspect {

    private static final Logger logger = LoggerFactory.getLogger(MyAspect.class);

    @Pointcut("execution(* com.qs.quartz.service.QuartzService.*(..))")
    public void proxyAspect() {

    }


    @Around(value = "proxyAspect()")
    public Object doInvoke(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("执行之前。。。");
        Object result = joinPoint.proceed();
        logger.info("执行之后。。。");
        return result;
    }

}
