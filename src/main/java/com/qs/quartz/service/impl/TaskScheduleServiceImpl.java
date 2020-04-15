package com.qs.quartz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qs.quartz.entity.TaskSchedule;
import com.qs.quartz.service.TaskScheduleService;
import com.qs.quartz.service.mapper.TaskScheduleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("taskScheduleService")
public class TaskScheduleServiceImpl extends ServiceImpl<TaskScheduleMapper, TaskSchedule> implements TaskScheduleService {

    @Autowired
    private TaskScheduleMapper taskScheduleMapper;

    @Transactional
    @Override
    public TaskSchedule getTaskSchedule(String beanClass, String methodName) {
        QueryWrapper<TaskSchedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bean_class", beanClass);
        queryWrapper.eq("method_name", methodName);
        return taskScheduleMapper.selectOne(queryWrapper);
    }

}
