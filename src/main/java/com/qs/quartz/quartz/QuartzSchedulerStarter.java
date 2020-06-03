package com.qs.quartz.quartz;

import com.qs.quartz.entity.TaskSchedule;
import com.qs.quartz.service.TaskScheduleService;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;

/**
 * 定时任务启动类
 *
 * @author qinyupeng
 * @since 2020-06-02 22:23:12
 */
@Component
public class QuartzSchedulerStarter {

    @Resource
    private TaskScheduleService taskScheduleService;


    /**
     * 注入bean时执行，加载task_schedule表中配置的所有的定时任务
     */
    @PostConstruct
    public void init() throws SchedulerException, ParseException {
        List<TaskSchedule> taskScheduleList = taskScheduleService.getAllTaskList();
        for (TaskSchedule taskSchedule : taskScheduleList) {
            //将任务添加到scheduler执行
            taskScheduleService.addJob(taskSchedule);
        }
    }


}
