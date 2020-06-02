package com.qs.quartz.mybatis;

import com.qs.quartz.BaseTest;
import com.qs.quartz.service.mapper.TaskScheduleMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestMb extends BaseTest {

    @Autowired
    private TaskScheduleMapper taskScheduleMapper;


    @Test
    public void test(){
        System.out.println("==>>" + taskScheduleMapper);
    }

}
