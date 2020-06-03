package com.qs.quartz.quartz.job;

import com.qs.quartz.entity.TaskSchedule;
import com.qs.quartz.utils.TaskUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 有状态的job：不支持同一个任务并发执行
 * 备注：concurrent标志位false，所谓有状态指的是，在到达触发时间点执行job时，会等待同一个任务执行完成后，在下一个触发时间点触发
 */
public class StatefulJobBean extends QuartzJobBean implements StatefulJob {

    private static final Logger logger = LoggerFactory.getLogger(StatefulJobBean.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        TaskSchedule taskSchedule = (TaskSchedule) jobExecutionContext.getMergedJobDataMap().get("taskSchedule");
        TaskUtils.invokeMethod(taskSchedule);
    }
}
