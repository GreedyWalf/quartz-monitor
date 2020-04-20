package com.qs.quartz.utils;

import com.qs.quartz.quartz.SpringUtils;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.core.JobRunShell;
import org.quartz.core.JobRunShellFactory;
import org.quartz.core.SchedulingContext;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;

public class MonitorJobRunShell extends JobRunShell {


    public MonitorJobRunShell(JobRunShellFactory jobRunShellFactory, Scheduler scheduler, SchedulingContext schdCtxt) {
        super(jobRunShellFactory, scheduler, schdCtxt);
    }

    @Override
    protected void begin() throws SchedulerException {
        jec.getJobDetail().getJobDataMap();
        MethodInvokingJobDetailFactoryBean jobDetailFactoryBean = (MethodInvokingJobDetailFactoryBean) jec.getJobDetail().getJobDataMap().get("methodInvoker");
//        String beanClass = jobDetailFactoryBean.getTargetClass().getName();
//        SpringUtils.getBean(beanClass).equals(SpringUtils.getBean("myQuartzService"));
        jobDetailFactoryBean.getTargetObject().equals(SpringUtils.getBean("myQuartzService"));
        String targetMethod = jobDetailFactoryBean.getTargetMethod();
        super.begin();
    }

    @Override
    protected void complete(boolean successfulExecution) throws SchedulerException {
        long jobRunTime = jec.getJobRunTime();
//        jec.getScheduler().shutdown();
//        try {
//            super.complete(successfulExecution);
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//            throw e;
//        }
    }

    @Override
    public void run() {
        super.run();
    }
}
