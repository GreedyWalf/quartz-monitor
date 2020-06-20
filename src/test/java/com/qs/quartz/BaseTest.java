package com.qs.quartz;

import com.qs.quartz.service.UserService;
import com.qs.quartz.service.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class BaseTest {

    @Resource
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test() throws Exception {
        System.out.println("--->>>userService=" + userService);
        System.out.println("---->>>>userMapper=" + userMapper);
    }



}
