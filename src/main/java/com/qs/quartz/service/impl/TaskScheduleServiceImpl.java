package com.qs.quartz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qs.quartz.entity.TaskSchedule;
import com.qs.quartz.quartz.QuartzJobFactory;
import com.qs.quartz.quartz.QuartzJobFactoryDisallowConcurrentExecution;
import com.qs.quartz.service.TaskScheduleService;
import com.qs.quartz.service.mapper.TaskScheduleMapper;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service("taskScheduleService")
public class TaskScheduleServiceImpl extends ServiceImpl<TaskScheduleMapper, TaskSchedule> implements TaskScheduleService {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    private TaskScheduleMapper taskScheduleMapper;

    /**
     * 初始化时，获取所有定时任务，并加入执行
     */
    @PostConstruct
    public void init() {
        List<TaskSchedule> allTaskList = getAllTaskList();
        for(TaskSchedule taskSchedule : allTaskList){
            try {
                addJob(taskSchedule);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
    }


    //添加任务
    public void addJob(TaskSchedule taskSchedule) throws SchedulerException {
        //todo 任务不存在，或者任务已执行（0-STATUS_NOT_RUNNING,1-STATUS_RUNNING）
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        String jobName = taskSchedule.getJobName();
        String jobGroup = taskSchedule.getJobGroup();
        String cronExpression = taskSchedule.getCronExpression();

        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        //不存在任务，创建一个
        if (trigger == null) {
            Class clazz = null;
            String isConcurrent = taskSchedule.getIsConcurrent();
            if ("1".equals(isConcurrent)) {
                clazz = QuartzJobFactory.class;
            } else {
                clazz = QuartzJobFactoryDisallowConcurrentExecution.class;
            }

            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(jobName, jobGroup).build();
            jobDetail.getJobDataMap().put("taskSchedule", taskSchedule);
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup).withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            // Trigger已存在，那么更新相应的定时设置
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        }
    }

    @Transactional
    @Override
    public TaskSchedule getTaskSchedule(String beanClass, String methodName) {
        QueryWrapper<TaskSchedule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bean_class", beanClass);
        queryWrapper.eq("method_name", methodName);
        return taskScheduleMapper.selectOne(queryWrapper);
    }

    @Override
    public void addTask(TaskSchedule taskSchedule) {
        taskScheduleMapper.insert(taskSchedule);
    }

    @Override
    public List<TaskSchedule> getAllTaskList() {
        QueryWrapper<TaskSchedule> queryWrapper = new QueryWrapper<>();
        return taskScheduleMapper.selectList(queryWrapper);
    }
}
