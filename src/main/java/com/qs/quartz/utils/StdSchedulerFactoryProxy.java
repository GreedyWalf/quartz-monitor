package com.qs.quartz.utils;

import org.quartz.Scheduler;
import org.quartz.SchedulerConfigException;
import org.quartz.core.JobRunShellFactory;
import org.quartz.core.QuartzScheduler;
import org.quartz.core.QuartzSchedulerResources;
import org.quartz.core.SchedulingContext;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;

public class StdSchedulerFactoryProxy extends StdSchedulerFactory {

    @Override
    protected Scheduler instantiate(QuartzSchedulerResources rsrcs, QuartzScheduler qs) {
        SchedulingContext schedCtxt = new SchedulingContext();
        schedCtxt.setInstanceId(rsrcs.getInstanceId());
        Scheduler scheduler = new StdScheduler(qs, schedCtxt);

        try {
            JobRunShellFactory jobFactory = new StdJobRunShellFactoryProxy();
            jobFactory.initialize(scheduler, schedCtxt);
            rsrcs.setJobRunShellFactory(jobFactory);
        } catch (SchedulerConfigException e) {
            System.out.println("====>>>> 初始化MonitorStdJobRunShellFactory出错");
            e.printStackTrace();
        }
        return scheduler;
    }
}
