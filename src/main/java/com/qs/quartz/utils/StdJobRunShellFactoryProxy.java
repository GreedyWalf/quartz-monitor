package com.qs.quartz.utils;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.core.JobRunShell;
import org.quartz.core.JobRunShellFactory;
import org.quartz.core.SchedulingContext;

public class StdJobRunShellFactoryProxy implements JobRunShellFactory {

    private Scheduler scheduler;

    private SchedulingContext schedCtxt;


    public void initialize(Scheduler scheduler, SchedulingContext schedCtxt) {
        this.scheduler = scheduler;
        this.schedCtxt = schedCtxt;
    }

    public JobRunShell borrowJobRunShell() throws SchedulerException {
        return new MonitorJobRunShell(this, scheduler, schedCtxt);
    }


    public void returnJobRunShell(JobRunShell jobRunShell) {
        jobRunShell.passivate();
    }
}