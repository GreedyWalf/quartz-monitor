package com.qs.quartz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qs.quartz.entity.TaskSchedule;
import org.quartz.SchedulerException;

import java.text.ParseException;
import java.util.List;

public interface TaskScheduleService extends IService<TaskSchedule> {

    /**
     * 获取所有的任务列表
     */
    List<TaskSchedule> getAllTaskList();

    /**
     * 添加任务
     */
    void addJob(TaskSchedule taskSchedule) throws SchedulerException, ParseException;
}
