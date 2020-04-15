package com.qs.quartz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("task_schedule_detail")
public class TaskScheduleDetail implements Serializable {

    @TableId(type = IdType.ID_WORKER_STR)
    private String detailId;

    private String jobId;

    private Date startTime;

    private Date endTime;

    private Long totalExecuteTime;

    private String executeStatus;

    private Date createTime;

}
