package com.qs.quartz.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyAspect {

    private static final Logger logger = LoggerFactory.getLogger(MyAspect.class);


    public Object handler(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("执行之前。。。");
        System.out.println("执行之后。。。");
        Object result = joinPoint.proceed();
        return result;
    }
}
