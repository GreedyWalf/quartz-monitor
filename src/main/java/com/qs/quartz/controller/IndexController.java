package com.qs.quartz.controller;

import com.qs.quartz.entity.User;
import com.qs.quartz.service.TaskScheduleService;
import com.qs.quartz.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

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
