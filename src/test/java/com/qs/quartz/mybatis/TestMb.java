package com.qs.quartz.mybatis;

import com.github.pagehelper.Page;
import com.qs.quartz.BaseTest;
import com.qs.quartz.entity.TaskScheduler;
import com.qs.quartz.enums.JobConcurrentStateEnum;
import com.qs.quartz.enums.JobStatusEnum;
import com.qs.quartz.service.mapper.TaskSchedulerMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class TestMb extends BaseTest {

    @Autowired
    private TaskSchedulerMapper taskSchedulerMapper;


    @Test
    public void test(){
        System.out.println("==>>" + taskSchedulerMapper);
    }


    @Test
    public void addTask(){
        TaskScheduler taskScheduler = new TaskScheduler();
        taskScheduler.setJobName("test2");
        taskScheduler.setJobGroup("test2Group");
        taskScheduler.setJobStatus(JobStatusEnum.STATUS_RUNNABLE.getCode());
        taskScheduler.setIsConcurrent(JobConcurrentStateEnum.CONCURRENT_TRUE.getCode());
        taskScheduler.setCronExpression("0/5 * * * * ?");
        taskScheduler.setSpringId("myQuartzService");
        taskScheduler.setBeanClass("com.qs.quartz.service.impl.MyQuartzServiceImpl");
        taskScheduler.setMethodName("test2");
        taskScheduler.setDescription("5s执行一次");
        taskScheduler.setCreateTime(new Date());
        taskSchedulerMapper.insert(taskScheduler);
    }


    @Test
    public void test3(){
        Page<TaskScheduler> page = taskSchedulerMapper.getTaskSchedulerList(new TaskScheduler());
        System.out.println("===>>" + page);
    }
}
