package com.qs.quartz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.Page;
import com.qs.quartz.entity.TaskScheduler;
import com.qs.quartz.utils.JsonResult;
import org.quartz.SchedulerException;

import java.text.ParseException;
import java.util.List;

public interface TaskSchedulerService extends IService<TaskScheduler> {

    /**
     * 获取所有的任务列表
     */
    List<TaskScheduler> getAllTaskList();

    /**
     * 将任务添加到scheduler
     */
    void addJob(TaskScheduler taskScheduler) throws SchedulerException, ParseException;

    Page<TaskScheduler> getTaskSchedulerList(Page<TaskScheduler> page, TaskScheduler taskScheduler);

    JsonResult saveOrUpdateJob(TaskScheduler taskScheduler);

    JsonResult batchDeleteByJobIds(List<String> jobIdList);

}
