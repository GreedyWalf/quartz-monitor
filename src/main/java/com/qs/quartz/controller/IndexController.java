package com.qs.quartz.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.qs.quartz.entity.TaskScheduler;
import com.qs.quartz.service.TaskSchedulerService;
import com.qs.quartz.utils.JsonResult;
import com.qs.quartz.utils.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Resource
    private TaskSchedulerService taskSchedulerService;

    @RequestMapping(value = {"/", "/index"})
    public String index() {
        return "/index";
    }

    @RequestMapping(value = "/taskManage")
    public String taskManage() {
        return "/taskManage";
    }

    @RequestMapping(value = "/getTaskSchedulerList")
    @ResponseBody
    public Map<String, Object> getTaskSchedulerList(Page<TaskScheduler> page, TaskScheduler taskScheduler) {
        Page<TaskScheduler> deptsPage = taskSchedulerService.getTaskSchedulerList(page, taskScheduler);
        PageInfo<TaskScheduler> pageInfo = new PageInfo<>(deptsPage);
        return PageUtils.wrapPageDataToMap(pageInfo);
    }


    @RequestMapping(value = "/showTaskForm")
    public String showTaskForm(HttpServletRequest request) {
        String jobId = request.getParameter("jobId");
        if (StringUtils.isNotBlank(jobId)) {
            TaskScheduler taskScheduler = taskSchedulerService.getById(jobId);
            request.setAttribute("taskScheduler", taskScheduler);
        }

        return "taskForm";
    }


    @RequestMapping(value = "/saveOrUpdateJob")
    @ResponseBody
    public JsonResult saveOrUpdateJob(TaskScheduler taskScheduler) {
        return taskSchedulerService.saveOrUpdateJob(taskScheduler);
    }


    @RequestMapping(value = "/delete")
    @ResponseBody
    public JsonResult delete(String jobIds) {
        List<String> jobIdList = Arrays.asList(jobIds.split(","));
        return taskSchedulerService.batchDeleteByJobIds(jobIdList);
    }

}
