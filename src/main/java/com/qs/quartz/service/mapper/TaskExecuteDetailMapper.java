package com.qs.quartz.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.pagehelper.Page;
import com.qs.quartz.entity.TaskExecuteDetail;
import org.apache.ibatis.annotations.Param;

public interface TaskExecuteDetailMapper extends BaseMapper<TaskExecuteDetail> {


    Page<TaskExecuteDetail> getTaskDetailList(@Param("taskExecuteDetail") TaskExecuteDetail taskExecuteDetail);
}
