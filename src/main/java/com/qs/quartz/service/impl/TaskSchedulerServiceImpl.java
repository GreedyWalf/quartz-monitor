package com.qs.quartz.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qs.quartz.entity.TaskScheduler;
import com.qs.quartz.enums.JobConcurrentStateEnum;
import com.qs.quartz.enums.JobStatusEnum;
import com.qs.quartz.quartz.job.StatefulJobBean;
import com.qs.quartz.quartz.job.StatelessJobBean;
import com.qs.quartz.service.TaskSchedulerService;
import com.qs.quartz.service.mapper.TaskSchedulerMapper;
import com.qs.quartz.utils.JsonResult;
import com.qs.quartz.utils.JsonStatus;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

@Service("taskSchedulerService")
public class TaskSchedulerServiceImpl extends ServiceImpl<TaskSchedulerMapper, TaskScheduler> implements TaskSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(TaskSchedulerServiceImpl.class);

    @Autowired
    private TaskSchedulerMapper taskSchedulerMapper;

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;


    @Transactional
    @Override
    public List<TaskScheduler> getAllTaskList() {
        return taskSchedulerMapper.selectList(null);
    }

    @Override
    public void addJob(TaskScheduler taskScheduler) throws SchedulerException, ParseException {
        if (taskScheduler == null) {
            return;
        }

        String jobStatus = taskScheduler.getJobStatus();
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.resumeAll();
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(taskScheduler.getJobName(), taskScheduler.getJobGroup());
        //不存在，则创建一个
        if (trigger == null) {
            String concurrentFlag = taskScheduler.getIsConcurrent();
            Class jobClass = null;
            if (JobConcurrentStateEnum.CONCURRENT_TRUE.getCode().equals(concurrentFlag)) {
                jobClass = StatelessJobBean.class;
            } else {
                jobClass = StatefulJobBean.class;
            }

            JobDetail jobDetail = new JobDetail(taskScheduler.getJobName(), taskScheduler.getJobGroup(), jobClass);
            jobDetail.getJobDataMap().put("taskScheduler", taskScheduler);
            trigger = new CronTrigger(taskScheduler.getJobName(), taskScheduler.getJobGroup(), taskScheduler.getCronExpression());
            //判断任务状态
            if (JobStatusEnum.STATUS_PAUSE.getCode().equals(jobStatus)) {
                scheduler.pauseJob(taskScheduler.getJobName(), taskScheduler.getJobGroup());
            } else {
                scheduler.scheduleJob(jobDetail, trigger);
            }
        } else {  //存在，则更新cronExpression
            trigger.setJobName(taskScheduler.getJobName());
            trigger.setJobGroup(taskScheduler.getJobGroup());
            trigger.setCronExpression(taskScheduler.getCronExpression());
            scheduler.scheduleJob(trigger);
        }
    }

    @Override
    @Transactional
    public Page<TaskScheduler> getTaskSchedulerrList(Page<TaskScheduler> page, TaskScheduler taskScheduler) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return taskSchedulerMapper.getTaskSchedulerrList(taskScheduler);
    }


    @Transactional
    @Override
    public JsonResult saveOrUpdateJob(TaskScheduler taskScheduler) {
        JsonResult jsonResult = new JsonResult();
        String jobId = taskScheduler.getJobId();
        if (StringUtils.isBlank(jobId)) {
            this.save(taskScheduler);
        } else {
            TaskScheduler taskSchedulerFromDB = this.getById(jobId);
            if (taskSchedulerFromDB == null) {
                logger.error("未获取到taskScheduler，jobId={}", jobId);
                jsonResult.setStatus(JsonStatus.ERROR);
                jsonResult.setMsg("根据jobId未获取到定时任务配置");
                return jsonResult;
            }

            this.updateById(taskScheduler);
        }

        try {
            this.addJob(taskScheduler);
        } catch (Exception e) {
            e.printStackTrace();
        }

        jsonResult.setStatus(JsonStatus.SUCCESS);
        return jsonResult;
    }

    @Transactional
    @Override
    public JsonResult batchDeleteByJobIds(String[] jobIds) {
        JsonResult jsonResult = new JsonResult();
        List<String> jobIdList = Arrays.asList(jobIds);
        QueryWrapper<TaskScheduler> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("job_id", jobIdList);
        List<TaskScheduler> taskSchedulerList = taskSchedulerMapper.selectList(queryWrapper);
        if (CollUtil.isEmpty(taskSchedulerList)) {
            jsonResult.setStatus(JsonStatus.ERROR);
            jsonResult.setMsg("未获取到定时任务配置");
            return jsonResult;
        }

        boolean isDelete = removeByIds(jobIdList);
        if (BooleanUtils.isFalse(isDelete)) {
            jsonResult.setMsg("删除失败");
            return jsonResult;
        }

        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        for (TaskScheduler taskScheduler : taskSchedulerList) {
            try {
                scheduler.deleteJob(taskScheduler.getJobName(), taskScheduler.getJobGroup());
            } catch (SchedulerException e) {
                logger.error("scheduler中删除任务出现异常，jobName={}，jobGroup={}", taskScheduler.getJobName(), taskScheduler.getJobGroup());
                e.printStackTrace();
            }
        }

        jsonResult.setStatus(JsonStatus.SUCCESS);
        return jsonResult;
    }
}
