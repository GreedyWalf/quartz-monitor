package com.qs.quartz.utils;

import org.springframework.stereotype.Component;

// 目标类
@Component
@FruitAspect
public class Apple {

    public void eat() {
        System.out.println("---->>>>Apple.eat method invoked.");
    }
}
