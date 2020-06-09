package com.qs.quartz.controller;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.qs.quartz.entity.TaskExecuteDetail;
import com.qs.quartz.service.TaskExecuteDetailService;
import com.qs.quartz.utils.PageUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@RequestMapping(value = "/")
@Controller
public class TaskDetailController {

    @Resource
    private TaskExecuteDetailService taskExecuteDetailService;


    @RequestMapping(value = "/taskDetailManage")
    public String taskDetailList() {
        return "taskDetailManage";
    }


    @RequestMapping(value = "/taskDetailList")
    @ResponseBody
    public Map<String, Object> getTaskDetailList(Page<TaskExecuteDetail> page, TaskExecuteDetail taskExecuteDetail) {
        Page<TaskExecuteDetail> taskDetailPage = taskExecuteDetailService.getTaskDetailList(page, taskExecuteDetail);
        PageInfo<TaskExecuteDetail> pageInfo = new PageInfo<>(taskDetailPage);
        return PageUtils.wrapPageDataToMap(pageInfo);
    }
}

