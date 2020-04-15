package com.qs.quartz.controller;

import com.qs.quartz.entity.TaskSchedule;
import com.qs.quartz.entity.User;
import com.qs.quartz.quartz.SpringUtils;
import com.qs.quartz.quartz.TaskUtils;
import com.qs.quartz.service.TaskScheduleService;
import com.qs.quartz.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Date;

@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Resource
    private UserService userService;

    @Resource
    private TaskScheduleService taskScheduleService;

    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/getUser")
    @ResponseBody
    public User getUser() {
        String userId = "1111";
        User user = userService.getUserByUserId(userId);
        return user;
    }

    /**
     * 测试通过反射调用，执行定时任务方法
     */
    @RequestMapping(value = "/testInvoke")
    public void test() {
        TaskSchedule taskSchedule = taskScheduleService.getTaskSchedule("com.qs.quartz.service.impl.UserServiceImpl",
                "getAllUser");
        TaskUtils.invokMethod(taskSchedule);
    }

    @RequestMapping(value = "/addTask")
    public void addTask(){
        TaskSchedule taskSchedule = new TaskSchedule();
        taskSchedule.setJobName("获取全部用户定时任务");
        taskSchedule.setJobGroup("1");
        taskSchedule.setBeanClass("com.qs.quartz.service.impl.MyQuartzServiceImpl");
        taskSchedule.setMethodName("getAllUser");
        taskSchedule.setSpringId("myQuartzService");
        taskSchedule.setDescription("测试定时任务添加");
        String cronExpression = "0/10 * * * * ?";
        try {
            CronScheduleBuilder.cronSchedule(cronExpression);
        } catch (Exception e) {
            logger.error("cron表达式有误，不能够被解析！");
        }

        taskSchedule.setCronExpression(cronExpression);
        taskSchedule.setJobStatus("0");
        taskSchedule.setIsConcurrent("1");
        taskSchedule.setCreateTime(new Date());

        Object obj = null;
        String springId = taskSchedule.getSpringId();
        if(StringUtils.isNotBlank(springId)){
            obj = SpringUtils.getBean(springId);
        }else{
            try {
                Class clazz = Class.forName(taskSchedule.getBeanClass());
                obj = clazz.newInstance();
            } catch (Exception e) {
                logger.error("根据beanClass，反射转换为对象出现异常：{}", e.getMessage());
            }
        }

        if(obj == null){
            logger.error("未获取到目标类");
            return;
        }

        Class<?> clazz = obj.getClass();
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(taskSchedule.getMethodName());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        if(method == null){
            logger.error("未获取到目标方法");
            return;
        }

        taskScheduleService.addTask(taskSchedule);
    }
}
