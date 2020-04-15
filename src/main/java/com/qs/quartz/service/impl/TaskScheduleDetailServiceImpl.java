package com.qs.quartz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qs.quartz.entity.TaskScheduleDetail;
import com.qs.quartz.service.TaskScheduleDetailService;
import com.qs.quartz.service.mapper.TaskScheduleDetailMapper;
import org.springframework.stereotype.Service;

@Service("taskScheduleDetailService")
public class TaskScheduleDetailServiceImpl extends ServiceImpl<TaskScheduleDetailMapper, TaskScheduleDetail> implements TaskScheduleDetailService {

}
