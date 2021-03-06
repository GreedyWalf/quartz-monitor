package com.qs.quartz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("task_schedule")
public class TaskSchedule implements Serializable {

    @TableId(type = IdType.ID_WORKER_STR)
    private String jobId;

    private String jobName;

    private String jobGroup;

    private String cronExpression;

    private String springId;

    private String beanClass;

    private String methodName;

    //0-未启动，1-已启动
    private String jobStatus;

    //是否等待上个任务完成（0-等待，1-不等待）
    private String isConcurrent;

    private String description;

    private Date createTime;

    private Date updateTime;
}
