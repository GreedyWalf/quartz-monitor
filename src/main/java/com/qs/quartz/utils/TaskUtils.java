package com.qs.quartz.utils;

import com.qs.quartz.entity.TaskSchedule;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class TaskUtils {

    private static Logger logger = LoggerFactory.getLogger(TaskUtils.class);

    public static void invokeMethod(TaskSchedule taskSchedule) {
        Object object = null;
        Class clazz = null;
        if (StringUtils.isNotBlank(taskSchedule.getSpringId())) {
            object = SpringUtils.getBean(taskSchedule.getSpringId());
        } else if (StringUtils.isNotBlank(taskSchedule.getBeanClass())) {
            try {
                clazz = Class.forName(taskSchedule.getBeanClass());
                object = clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (object == null) {
            return;
        }

        clazz = object.getClass();
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(taskSchedule.getMethodName());
        } catch (NoSuchMethodException e) {
            logger.error("未获取到任务方法，jobName={}", taskSchedule.getJobName());
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        if (method != null) {
            try {
                method.invoke(object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        logger.info("任务执行结束，jobName={}", taskSchedule.getJobName());
    }
}
