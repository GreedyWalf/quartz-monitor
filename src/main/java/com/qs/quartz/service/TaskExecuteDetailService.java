package com.qs.quartz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.Page;
import com.qs.quartz.entity.TaskExecuteDetail;
import com.qs.quartz.entity.TaskScheduler;
import com.qs.quartz.utils.JsonResult;
import org.quartz.SchedulerException;

import java.text.ParseException;
import java.util.List;

public interface TaskExecuteDetailService extends IService<TaskExecuteDetail> {


    Page<TaskExecuteDetail> getTaskDetailList(Page<TaskExecuteDetail> page, TaskExecuteDetail taskExecuteDetail);
}
