package com.qs.quartz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qs.quartz.entity.TaskSchedule;
import com.qs.quartz.enums.JobConcurrentStateEnum;
import com.qs.quartz.enums.JobStatusEnum;
import com.qs.quartz.quartz.job.StatefulJobBean;
import com.qs.quartz.quartz.job.StatelessJobBean;
import com.qs.quartz.service.TaskScheduleService;
import com.qs.quartz.service.mapper.TaskScheduleMapper;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

@Service("taskScheduleService")
public class TaskScheduleServiceImpl extends ServiceImpl<TaskScheduleMapper, TaskSchedule> implements TaskScheduleService {

    private static final Logger logger = LoggerFactory.getLogger(TaskScheduleServiceImpl.class);

    @Autowired
    private TaskScheduleMapper taskScheduleMapper;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;


    @Transactional
    @Override
    public List<TaskSchedule> getAllTaskList() {
        return taskScheduleMapper.selectList(null);
    }

    @Override
    public void addJob(TaskSchedule job) throws SchedulerException, ParseException {
        if (job == null) {
            return;
        }

        //已经处于运行中的任务，无需添加
        if (JobStatusEnum.STATUS_RUNNABLE.getCode().equals(job.getJobStatus())) {
            logger.info("当前任务已经处于运行中，jobId={}", job.getJobId());
            return;
        }

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        Trigger trigger = scheduler.getTrigger(job.getJobName(), job.getJobGroup());
        //不存在，则创建一个
        if (trigger == null) {
            String concurrentFlag = job.getIsConcurrent();
            Class jobClass = null;
            if(JobConcurrentStateEnum.CONCURRENT_TRUE.getCode().equals(concurrentFlag)){
                jobClass = StatelessJobBean.class;
            }else{
                jobClass = StatefulJobBean.class;
            }

            JobDetail jobDetail = new JobDetail(job.getJobName(), job.getJobGroup(), jobClass);
            jobDetail.getJobDataMap().put("taskSchedule", job);
            trigger = new CronTrigger(job.getJobName(), job.getJobGroup(), job.getCronExpression());
            scheduler.scheduleJob(jobDetail, trigger);
        } else {  //存在，则更新cronExpression
            trigger = new CronTrigger(job.getJobName(), job.getJobGroup(), job.getCronExpression());
            scheduler.scheduleJob(trigger);
        }
    }
}
