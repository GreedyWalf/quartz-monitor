package com.qs.quartz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qs.quartz.entity.TaskSchedule;

import java.util.List;

public interface TaskScheduleService extends IService<TaskSchedule> {

    TaskSchedule getTaskSchedule(String beanClass, String methodName);

    /**
     * 添加定时任务数据（task_schedule表中添加一条记录）
     */
    void addTask(TaskSchedule taskSchedule);

    List<TaskSchedule> getAllTaskList();
}
