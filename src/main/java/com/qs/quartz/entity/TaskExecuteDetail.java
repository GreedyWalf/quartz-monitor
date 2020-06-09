package com.qs.quartz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("task_execute_detail")
public class TaskExecuteDetail {

    @TableId(type = IdType.ID_WORKER_STR)
    private String detailId;

    //任务id
    private String jobId;

    //线程id
    private String threadId;

    //开始时间
    private String startTime;

    //结束时间
    private String endTime;

    //执行耗时
    private Long totalTime;

    //0-执行中，1-执行成功，2-执行失败
    private String status;

    private Date createTime;


    /* 非持久化字段 */

    //任务名称
    @TableField(exist = false)
    private String jobName;
}
