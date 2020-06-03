package com.qs.quartz.quartz.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component("myJobListener")
public class MyJobListener implements JobListener {

    private static final Logger logger = LoggerFactory.getLogger(MyJobListener.class);

    @Override
    public String getName() {
        String name = getClass().getSimpleName();
        logger.info(" listener name is:" + name);
        return name;
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        String jobName = context.getJobDetail().getKey().getName();
        logger.info(jobName + " is going to be executed");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        String jobName = context.getJobDetail().getKey().getName();
        logger.info(jobName + " was vetoed and not executed");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        String jobName = context.getJobDetail().getKey().getName();
        logger.info(jobName + " was executed");
    }
}
