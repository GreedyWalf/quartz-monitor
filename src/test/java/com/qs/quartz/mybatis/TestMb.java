package com.qs.quartz.mybatis;

import com.qs.quartz.BaseTest;
import com.qs.quartz.entity.TaskSchedule;
import com.qs.quartz.entity.User;
import com.qs.quartz.service.UserService;
import com.qs.quartz.service.mapper.TaskScheduleDetailMapper;
import com.qs.quartz.service.mapper.TaskScheduleMapper;
import com.qs.quartz.service.mapper.UserMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.Date;

public class TestMb extends BaseTest {

    @Resource
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TaskScheduleMapper taskScheduleMapper;

    @Autowired
    private TaskScheduleDetailMapper taskScheduleDetailMapper;

    @Test
    public void test2() {
        User user = userMapper.selectById("1111");
        System.out.println("---->>>user=" + user);
    }


    @Test
    public void test3() {
        User user = userMapper.getUserById("1111");
        System.out.println("---->>>user=" + user);
    }


    @Test
    public void addTask(){
        TaskSchedule taskSchedule = new TaskSchedule();
        taskSchedule.setJobName("测试");
        taskSchedule.setJobGroup("1");
        taskSchedule.setCronExpression("0/10 * * * * ?");
        taskSchedule.setSpringId("myQuartzService");
        taskSchedule.setBeanClass("com.qs.quartz.service.impl.MyQuartzServiceImpl");
        taskSchedule.setMethodName("test");
        taskSchedule.setIsConcurrent("0");
        taskSchedule.setDescription("测试定时任务");
        taskSchedule.setJobStatus("0");
        taskSchedule.setUpdateTime(new Date());
        taskScheduleMapper.insert(taskSchedule);
    }

    @Test
    public void addTask2(){
        TaskSchedule taskSchedule = new TaskSchedule();
        taskSchedule.setJobName("测试");
        taskSchedule.setJobGroup("1");
        taskSchedule.setCronExpression("0/10 * * * * ?");
        taskSchedule.setSpringId("userService");
        taskSchedule.setBeanClass("com.qs.quartz.service.impl.UserServiceImpl");
        taskSchedule.setMethodName("getUserByUserId");
        taskSchedule.setIsConcurrent("0");
        taskSchedule.setDescription("测试下aop切面");
        taskSchedule.setJobStatus("0");
        taskSchedule.setUpdateTime(new Date());
        taskScheduleMapper.insert(taskSchedule);
    }

    @Test
    public void addTask3(){
        TaskSchedule taskSchedule = new TaskSchedule();
        taskSchedule.setJobName("获取全部用户定时任务");
        taskSchedule.setJobGroup("1");
        taskSchedule.setCronExpression("0/10 * * * * ?");
        taskSchedule.setSpringId("userService");
        taskSchedule.setBeanClass("com.qs.quartz.service.impl.UserServiceImpl");
        taskSchedule.setMethodName("getAllUser");
        taskSchedule.setIsConcurrent("0");
        taskSchedule.setDescription("无参数的方法");
        taskSchedule.setJobStatus("0");
        taskSchedule.setUpdateTime(new Date());
        taskScheduleMapper.insert(taskSchedule);
    }


    @Test
    public void test4(){
        User user = userService.getUserByUserId("1111");
        System.out.println(user);
    }
}
