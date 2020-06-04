package com.qs.quartz.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.qs.quartz.entity.TaskScheduler;
import com.qs.quartz.entity.TaskScheduler;
import com.qs.quartz.service.TaskSchedulerService;
import com.qs.quartz.utils.JsonResult;
import com.qs.quartz.utils.PageUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
    public String taskManage(){
        return "/taskManage";
    }

    @RequestMapping(value = "/getTaskSchedulerrList")
    @ResponseBody
    public Map<String, Object> getTaskSchedulerrList(Page<TaskScheduler> page, TaskScheduler taskScheduler) {
        Page<TaskScheduler> deptsPage = taskSchedulerService.getTaskSchedulerrList(page, taskScheduler);
        PageInfo<TaskScheduler> pageInfo = new PageInfo<>(deptsPage);
        return PageUtils.wrapPageDataToMap(pageInfo);
    }


    /**
     * 动态添加或修改定时任务
     *
     * @param taskScheduler 表单定时任务配置
     * @return 返回新增或更新结果
     * @author qinyupeng
     * @since 2020-06-03 22:38:20
     */
    @RequestMapping(value = "/addJob")
    @ResponseBody
    public JsonResult saveOrUpdateJob(TaskScheduler taskScheduler) {
        return taskSchedulerService.saveOrUpdateJob(taskScheduler);
    }


    /**
     * 动态删除定时任务
     *
     * @param jobIds 定时任务id集合
     * @return 返回删除操作结果
     * @author qinyupeng
     * @since 2020-06-03 22:43:50
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public JsonResult delete(String[] jobIds) {
        return taskSchedulerService.batchDeleteByJobIds(jobIds);
    }

}
