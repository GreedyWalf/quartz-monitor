package com.qs.quartz.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAspect2 {

    @Around("@within(com.qs.quartz.utils.FruitAspect)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("--------->>>>this is before around advice");
        Object result = pjp.proceed();
        System.out.println("----------->>>>this is after around advice");
        return result;
    }
}
