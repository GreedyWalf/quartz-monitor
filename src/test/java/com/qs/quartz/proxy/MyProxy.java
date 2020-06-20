package com.qs.quartz.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyProxy implements InvocationHandler {

    private Object target;

    private String methodName;

    public MyProxy(Object target, String methodName) {
        this.target = target;
        this.methodName = methodName;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        System.out.println("==>>执行方法：" + method.getName());
        if (method.getName().equals(methodName)) {
            System.out.println("--->>测试1。。。");
            result = method.invoke(target, args);
            System.out.println("--->>测试2。。。");

        }

        return result;
    }

}
