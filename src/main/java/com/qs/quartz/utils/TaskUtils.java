package com.qs.quartz.utils;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.qs.quartz.entity.TaskExecuteDetail;
import com.qs.quartz.entity.TaskScheduler;
import com.qs.quartz.enums.TaskDetailStatusEnum;
import com.qs.quartz.service.TaskExecuteDetailService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

@Component
public class TaskUtils {

    private static TaskExecuteDetailService taskExecuteDetailService = SpringUtils.getBean("taskExecuteDetailService");

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
            Date startDate = new Date();
            String detailId = IdWorker.getIdStr();
            String jobId = taskScheduler.getJobId();

            //保存执行明细（todo 可以改造为异步保存执行明细）
            logger.info("开始插入任务执行明细记录，jobId={}", jobId);
            insertExecuteDetail(startDate, detailId, jobId);

            try {
                method.invoke(object);
            } catch (Exception e) {
                e.printStackTrace();
            }

            logger.info("开始更新任务执行明细结束，jobId={}", jobId);
            updateExecuteDetail(startDate, detailId);
        }

        logger.info("任务执行结束，jobName={}", taskScheduler.getJobName());
    }

    private static void insertExecuteDetail(Date startDate, String detailId, String jobId) {
        TaskExecuteDetail detail = new TaskExecuteDetail();
        detail.setDetailId(detailId);
        detail.setJobId(jobId);
        detail.setStartTime(DateUtil.format(startDate, "yyyy-MM-dd HH:mm:ss.SSS"));
        detail.setThreadId(Thread.currentThread().getName() + "-" + Thread.currentThread().getId());
        detail.setStatus(TaskDetailStatusEnum.STATUS_EXECUTE.getCode());
        detail.setCreateTime(startDate);
        taskExecuteDetailService.save(detail);
    }

    private static void updateExecuteDetail(Date startDate, String detailId) {
        Date endDate = new Date();
        long totalTime = endDate.getTime() - startDate.getTime();
        TaskExecuteDetail taskExecuteDetail = taskExecuteDetailService.getById(detailId);
        taskExecuteDetail.setEndTime(DateUtil.format(endDate, "yyyy-MM-dd HH:mm:ss.SSS"));
        taskExecuteDetail.setTotalTime(totalTime);
        taskExecuteDetail.setStatus(TaskDetailStatusEnum.STATUS_EXECUTE_SUCCESS.getCode());
        taskExecuteDetailService.updateById(taskExecuteDetail);
    }
}
