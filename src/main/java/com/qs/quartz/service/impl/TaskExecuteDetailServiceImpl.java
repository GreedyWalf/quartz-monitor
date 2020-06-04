package com.qs.quartz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qs.quartz.entity.TaskExecuteDetail;
import com.qs.quartz.service.TaskExecuteDetailService;
import com.qs.quartz.service.mapper.TaskExecuteDetailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("taskExecuteDetailService")
public class TaskExecuteDetailServiceImpl extends ServiceImpl<TaskExecuteDetailMapper, TaskExecuteDetail> implements TaskExecuteDetailService {

    private static final Logger logger = LoggerFactory.getLogger(TaskExecuteDetailServiceImpl.class);


}
