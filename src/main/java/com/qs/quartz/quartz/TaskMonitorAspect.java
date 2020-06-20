package com.qs.quartz.quartz;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

//@Component
//@Aspect
public class TaskMonitorAspect {

    @Around("@annotation(com.qs.quartz.annotation.QuartzMethodAspect)")
    public Object doInvoke(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("开始切面");
        Object result = joinPoint.proceed();
        System.out.println("结束切面");
        return result;
    }

}
