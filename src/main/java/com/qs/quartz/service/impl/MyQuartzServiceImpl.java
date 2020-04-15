package com.qs.quartz.service.impl;

import com.qs.quartz.entity.User;
import com.qs.quartz.service.QuartzService;
import com.qs.quartz.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("myQuartzService")
public class MyQuartzServiceImpl implements QuartzService {

    @Resource
    private UserService userService;

    @Transactional
    @Override
    public void test(String param) {
        User user = new User();
        user.setUserName("bbb");
        userService.updateById(user);
        int i = 1/0;
    }
}
