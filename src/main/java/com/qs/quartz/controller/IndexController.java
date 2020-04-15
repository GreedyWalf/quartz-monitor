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
}
