package com.qs.quartz.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("task_execute_detail")
public class TaskExecuteDetail {

    private String detailId;

    private String jobId;

    private String threadId;

    private String startTime;

    private String endTime;

    private Long totalTime;

    //0-执行中，1-执行成功，2-执行失败
    private String status;

    private Date createTime;
}
