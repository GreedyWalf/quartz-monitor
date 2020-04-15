package com.qs.quartz.quartz;

import com.qs.quartz.entity.TaskSchedule;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 计划任务执行处 无状态
 */
public class QuartzJobFactory implements Job {
    public final Logger log = LoggerFactory.getLogger(this.getClass());

    public void execute(JobExecutionContext context) throws JobExecutionException {
        TaskSchedule taskSchedule = (TaskSchedule) context.getMergedJobDataMap().get("taskSchedule");
        TaskUtils.invokMethod(taskSchedule);
    }
}