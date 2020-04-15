package com.qs.quartz.service.impl;

import com.qs.quartz.entity.User;
import com.qs.quartz.service.QuartzService;
import com.qs.quartz.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("myQuartzService")
public class MyQuartzServiceImpl implements QuartzService {

    @Resource
    private UserService userService;

    @Override
    public void test(String param) {
        System.out.println("this is test：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    @Override
    public void getAllUser() {
        List<User> userList = userService.getAllUser();
        System.out.println("===>>>>userList=" + userList);
    }
}
