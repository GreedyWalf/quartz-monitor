package com.qs.quartz.quartz;

import com.qs.quartz.entity.TaskSchedule;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class TaskUtils {

    private final static Logger logger = LoggerFactory.getLogger(TaskUtils.class);

    /**
     * 通过反射调用scheduleJob中定义的方法
     */
    public static void invokMethod(TaskSchedule scheduleJob) {
        Class clazz = null;
        Object object = null;
        String springId = scheduleJob.getSpringId();
        String beanClass = scheduleJob.getBeanClass();
        if (StringUtils.isNotBlank(springId)) {
            object = SpringUtils.getBean(springId);
        } else {
            if (StringUtils.isNotBlank(beanClass)) {
                try {
                    clazz = Class.forName(beanClass);
                    object = clazz.newInstance();
                } catch (Exception e) {
                    logger.error("使用beanClass反射获取实例时出现异常：{}", e.getMessage());
                }
            }
        }

        //任务名称
        String jobName = scheduleJob.getJobName();
        if (object == null) {
            logger.error("任务名称：[{}]--未启动成功，请检查配置", jobName);
            return;
        }

        clazz = object.getClass();
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(scheduleJob.getMethodName());
        } catch (Exception e) {
            logger.error("任务名称：[{}]--未启动成功，请检查配置", jobName);
        }

        if (method != null) {
            try {
                method.invoke(SpringUtils.getBean(clazz));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        logger.info("任务名称：[{}]--启动成功", jobName);
    }
}