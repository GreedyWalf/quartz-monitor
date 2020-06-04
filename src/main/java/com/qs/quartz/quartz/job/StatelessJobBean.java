package com.qs.quartz.quartz.job;


import com.qs.quartz.entity.TaskScheduler;
import com.qs.quartz.utils.TaskUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 无状态job：支持同一个任务并发执行
 * 备注：concurrent标志位true，表示同一个job未执行完成时，当触发时间到了，会新启动一个线程执行job
 */
public class StatelessJobBean extends QuartzJobBean {

    protected static final Log logger = LogFactory.getLog(StatelessJobBean.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        TaskScheduler taskScheduler = (TaskScheduler) jobExecutionContext.getMergedJobDataMap().get("taskScheduler");
        TaskUtils.invokeMethod(taskScheduler);
    }
}
