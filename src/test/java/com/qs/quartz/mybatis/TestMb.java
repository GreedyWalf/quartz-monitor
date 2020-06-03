package com.qs.quartz.mybatis;

import com.qs.quartz.BaseTest;
import com.qs.quartz.entity.TaskSchedule;
import com.qs.quartz.enums.JobConcurrentStateEnum;
import com.qs.quartz.enums.JobStatusEnum;
import com.qs.quartz.service.mapper.TaskScheduleMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class TestMb extends BaseTest {

    @Autowired
    private TaskScheduleMapper taskScheduleMapper;


    @Test
    public void test(){
        System.out.println("==>>" + taskScheduleMapper);
    }


    @Test
    public void addTask(){
        TaskSchedule taskSchedule = new TaskSchedule();
        taskSchedule.setJobName("test2");
        taskSchedule.setJobGroup("test2Group");
        taskSchedule.setJobStatus(JobStatusEnum.STATUS_NOT_START.getCode());
        taskSchedule.setIsConcurrent(JobConcurrentStateEnum.CONCURRENT_TRUE.getCode());
        taskSchedule.setCronExpression("0/2 * * * * ?");
        taskSchedule.setSpringId("myQuartzService");
        taskSchedule.setBeanClass("com.qs.quartz.service.impl.MyQuartzServiceImpl");
        taskSchedule.setMethodName("test");
        taskSchedule.setDescription("2s执行一次");
        taskSchedule.setCreateTime(new Date());
        taskScheduleMapper.insert(taskSchedule);
    }

}
