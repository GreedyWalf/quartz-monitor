package com.qs.quartz.utils;

import com.qs.quartz.entity.TaskScheduler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class TaskUtils {

    private static Logger logger = LoggerFactory.getLogger(TaskUtils.class);

    public static void invokeMethod(TaskScheduler taskScheduler) {
        Object object = null;
        Class clazz = null;
        if (StringUtils.isNotBlank(taskScheduler.getSpringId())) {
            object = SpringUtils.getBean(taskScheduler.getSpringId());
        } else if (StringUtils.isNotBlank(taskScheduler.getBeanClass())) {
            try {
                clazz = Class.forName(taskScheduler.getBeanClass());
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
            method = clazz.getDeclaredMethod(taskScheduler.getMethodName());
        } catch (NoSuchMethodException e) {
            logger.error("未获取到任务方法，jobName={}", taskScheduler.getJobName());
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

        logger.info("任务执行结束，jobName={}", taskScheduler.getJobName());
    }
}
