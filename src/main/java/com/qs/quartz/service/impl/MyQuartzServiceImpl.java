package com.qs.quartz.service.impl;

import com.qs.quartz.service.MyQuartzService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service(value = "myQuartzService")
public class MyQuartzServiceImpl implements MyQuartzService {

    @Override
    public void test() {
        System.out.println("线程：" + Thread.currentThread().getName()+ "，开始执行："
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("--->>线程："+Thread.currentThread().getName() +"，结束执行："
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    @Override
    public void test2() {
        System.out.println("--->>执行test2方法，开始时间："
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    @Override
    public void test3() {
        System.out.println("--->>执行test3方法，开始时间："
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
