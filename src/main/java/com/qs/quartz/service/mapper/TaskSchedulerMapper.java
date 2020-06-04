package com.qs.quartz.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.qs.quartz.entity.TaskScheduler;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TaskSchedulerMapper extends BaseMapper<TaskScheduler> {


    Page<TaskScheduler> getTaskSchedulerrList(@Param("taskScheduler") TaskScheduler taskScheduler);

}
