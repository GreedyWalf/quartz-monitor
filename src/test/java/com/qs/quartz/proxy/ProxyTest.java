package com.qs.quartz.proxy;

import com.qs.quartz.BaseTest;
import com.qs.quartz.entity.TaskSchedule;
import com.qs.quartz.quartz.AopTargetUtils;
import com.qs.quartz.quartz.SpringUtils;
import com.qs.quartz.service.QuartzService;
import com.qs.quartz.service.TaskScheduleService;
import org.junit.Test;

import javax.annotation.Resource;
import java.lang.reflect.Proxy;
import java.util.List;

public class ProxyTest extends BaseTest {

    @Resource
    private TaskScheduleService taskScheduleService;

    public static void main(String[] args) {
        PasswordService passwordService = new PasswordServiceImpl();
        MyProxy proxyInstance = new MyProxy(passwordService, "");

        PasswordService passwordServiceProxy = (PasswordService) Proxy.newProxyInstance(ProxyTest.class.getClassLoader(),
                new Class[]{PasswordService.class},
                proxyInstance);

        passwordServiceProxy.updatePassword();
    }


    @Test
    public void test() throws Exception {
        List<TaskSchedule> taskScheduleList = taskScheduleService.list();
        for (TaskSchedule taskSchedule : taskScheduleList) {
            String springId = taskSchedule.getSpringId();
            String methodName = taskSchedule.getMethodName();

            Object serviceBean = SpringUtils.getBean(springId);
            Object targetServiceBean = AopTargetUtils.getTarget(serviceBean);
            Class<?>[] interfaces = targetServiceBean.getClass().getInterfaces();
            MyProxy proxyInstance = new MyProxy(targetServiceBean, methodName);
            Object serviceBeanProxy = Proxy.newProxyInstance(ProxyTest.class.getClassLoader(),
                    interfaces,
                    proxyInstance);
            if(serviceBeanProxy instanceof QuartzService){
                ((QuartzService) serviceBeanProxy).test("cccc");
            }
        }
    }
}
