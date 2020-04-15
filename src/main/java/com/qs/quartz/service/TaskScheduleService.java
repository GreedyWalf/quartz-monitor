package com.qs.quartz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qs.quartz.entity.TaskSchedule;

public interface TaskScheduleService extends IService<TaskSchedule> {

    TaskSchedule getTaskSchedule(String beanClass, String methodName);

}
