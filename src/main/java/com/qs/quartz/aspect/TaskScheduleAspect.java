package com.qs.quartz.aspect;


import com.qs.quartz.entity.TaskSchedule;
import com.qs.quartz.entity.TaskScheduleDetail;
import com.qs.quartz.service.TaskScheduleDetailService;
import com.qs.quartz.service.TaskScheduleService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 定时任务切面
 */
@Component
@Aspect
public class TaskScheduleAspect {

    private static final Logger logger = LoggerFactory.getLogger(TaskScheduleAspect.class);

    @Resource
    private TaskScheduleService taskScheduleService;

    @Resource
    private TaskScheduleDetailService taskScheduleDetailService;

    @Pointcut("execution(* com.qs.quartz.service.UserService.*(..))")
    public void proxyAspect() {

    }

    @Around("proxyAspect()")
    public Object doInvoke(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object target = joinPoint.getTarget();
        //获取执行的类全名
        String className = target.getClass().getName();
        //获取执行的目标方法名
        String methodName = joinPoint.getSignature().getName();
        //执行方法的参数
        Object[] params = joinPoint.getArgs();
        //执行方法的参数类型
        Class[] paramTypes = methodSignature.getParameterTypes();

        TaskScheduleDetail taskScheduleDetail = null;
        TaskSchedule taskSchedule = taskScheduleService.getTaskSchedule(className, methodName);
        if (taskSchedule == null) {
            logger.error("系统定时任务表未配置，beanClass={}，method={}", className, methodName);
        } else {
            taskScheduleDetail = new TaskScheduleDetail();
            taskScheduleDetail.setJobId(taskSchedule.getJobId());
        }

        if (taskScheduleDetail != null) {
            //任务开始执行时间
            taskScheduleDetail.setStartTime(new Date());
            //创建时间
            taskScheduleDetail.setCreateTime(new Date());
        }


        long startTime = System.currentTimeMillis();
        //未开始
        String executeStatus = "0";
        Object result = null;
        try {
            //真正执行的目标方法
            result = joinPoint.proceed();
        } catch (Exception e) {
            //出现异常
            executeStatus = "2";
            logger.error("类：{}，方法：{}，参数：{}，定时任务执行出现异常：{}", className, methodName, params, e.getMessage());
        }

        try {
            long endTime = System.currentTimeMillis();
            if (taskScheduleDetail != null) {
                taskScheduleDetail.setTotalExecuteTime((startTime - endTime) / 1000);
                taskScheduleDetail.setEndTime(new Date());
                taskScheduleDetail.setExecuteStatus(executeStatus.equals("0") ? "1" : executeStatus);
                taskScheduleDetailService.save(taskScheduleDetail);
            }
        } catch (Exception e) {
            logger.error("保存定时任务执行明细数据出现异常：{}", e.getMessage());
        }

        return result;
    }
}