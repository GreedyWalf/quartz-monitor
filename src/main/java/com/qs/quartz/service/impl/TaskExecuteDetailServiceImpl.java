package com.qs.quartz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qs.quartz.entity.TaskExecuteDetail;
import com.qs.quartz.service.TaskExecuteDetailService;
import com.qs.quartz.service.mapper.TaskExecuteDetailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("taskExecuteDetailService")
public class TaskExecuteDetailServiceImpl extends ServiceImpl<TaskExecuteDetailMapper, TaskExecuteDetail>
        implements TaskExecuteDetailService {

    private static final Logger logger = LoggerFactory.getLogger(TaskExecuteDetailServiceImpl.class);

    @Autowired
    private TaskExecuteDetailMapper taskExecuteDetailMapper;

    @Transactional
    @Override
    public Page<TaskExecuteDetail> getTaskDetailList(Page<TaskExecuteDetail> page, TaskExecuteDetail taskExecuteDetail) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return taskExecuteDetailMapper.getTaskDetailList(taskExecuteDetail);
    }
}
