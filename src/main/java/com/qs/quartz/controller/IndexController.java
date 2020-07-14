package com.qs.quartz.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.http.HttpUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.qs.quartz.entity.TaskScheduler;
import com.qs.quartz.service.TaskSchedulerService;
import com.qs.quartz.utils.JsonResult;
import com.qs.quartz.utils.PageUtils;
import org.apache.commons.io.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.HashMap;
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

    public static final String privateKey = "30820275020100300d06092a864886f70d01010105000482025f3082025b020100028181009dde6d26c3b8db526d1007742059f91ec5e4aa58293ef89c4b70e4c9536b0ca3859f9aa53c9dff9353da0d4842c7648cdb5c60988407c39eec194c2da8772b994976a65ea9c5c6d4eed9e7700e82ca68d418fd03d6697eaf7b437171b38365f1344fa18046f5cb8f842743d7562595713234f85e18130cc51e8e566babfaf0ff02030100010281807f2ba32fdbf078b4b0687e289cbafdc43d53d3a90b28cfea4f9754a59d4e19b34c3be2be82d320035543ccf94dda0374b86c74dfc753ddd0928e5a60595a0e42bab62dab9a8aea1ac0260de23406354031164bedea68c1c7e35e90622c78b31d87437cea174b96d9b69fbc9e51ac148a92eedd624dc77fa6629388d88ef7e191024100dc81b44aabbd52a5e26acfc65b8f208447b3719aff3c83753e68c6d624840d0bde6f42f7765cd176ba2fe4d50f3b0f1395f66acbe76ef426da018ca16920cb19024100b747a40ac9a2bc4349a0446c87196238812c6485fa3020a5240ef232540fcfe1f42a9bca68709a36abfb615395f5b5f4e91758a5986d86df98189130cbfe37d702400c53178fa0dfb911da80dbd21b65f98c4b31a564e3652f77cb203214dfff9d770f5caaa2883411e50fed035e4136acd60c68b479671b157c626cf9be3fd0fc69024052de518d8f1dc581a7088fe7822e37fad46cfe0695d8ace9fe23c3de7da3a89ac18b82654253a76690dc586532a8a65cd607784d675e1e5d7aa7a0fe2f3e028102402a7b0dddbdf28497a1ef5d3bcc714044dcf0ef64d9410df8396d39c2617c62628c61425081d33a100a27ac64147805569de5108a670c8db0a37cefc1e8682ffb";
    public static final String publicKey = "30819f300d06092a864886f70d010101050003818d00308189028181009dde6d26c3b8db526d1007742059f91ec5e4aa58293ef89c4b70e4c9536b0ca3859f9aa53c9dff9353da0d4842c7648cdb5c60988407c39eec194c2da8772b994976a65ea9c5c6d4eed9e7700e82ca68d418fd03d6697eaf7b437171b38365f1344fa18046f5cb8f842743d7562595713234f85e18130cc51e8e566babfaf0ff0203010001";

    @RequestMapping(value = "/getTaskSchedulerList")
    @ResponseBody
    public Map<String, Object> getTaskSchedulerList(Page<TaskScheduler> page, TaskScheduler taskScheduler) {
        Page<TaskScheduler> taskSchedulerPage = taskSchedulerService.getTaskSchedulerList(page, taskScheduler);
        PageInfo<TaskScheduler> pageInfo = new PageInfo<>(taskSchedulerPage);
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


    @RequestMapping(value = "/pauseAndResume")
    @ResponseBody
    public JsonResult pauseAndResume(String jobId, String jobStatus) throws Exception {
        JsonResult jsonResult = new JsonResult();
        taskSchedulerService.pauseAndResume(jobId, jobStatus);
        return jsonResult;
    }


    @RequestMapping(value = "/getTask", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getTask(@RequestParam Map<String, Object> paramMap) {
        //参数解密，完成请求
        Map<String, Object> decryptMap = getDecryptMap(paramMap);
        String jobId = (String) decryptMap.get("jobId");
        TaskScheduler taskScheduler = taskSchedulerService.getTaskById(jobId);
        JsonResult jsonResult = new JsonResult();
        jsonResult.setData(taskScheduler);
        return jsonResult;
    }


    @RequestMapping(value = "/test")
    @ResponseBody
    public JsonResult test() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("jobId", "1267847583883345922");
        //参数加密后，调用请求
        Map<String, Object> encyptMap = getEncryptMap(paramMap);
        String data = HttpUtil.post("http://localhost:9090/monitor/getTask", encyptMap);
        JsonResult jsonResult = new JsonResult();
        jsonResult.setData(data);
        return jsonResult;
    }

    //使用公钥对参数加密
    private Map<String, Object> getEncryptMap(Map<String, Object> paramMap) {
        Map<String, Object> encryptMap = new HashMap<>();
        RSA rsa = new RSA(null, publicKey);
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            String key = entry.getKey();
            String value = (String) entry.getValue();
            String encryptKey = rsa.encryptHex(StrUtil.bytes(key, Charsets.UTF_8), KeyType.PublicKey);
            String encryptValue = rsa.encryptHex(StrUtil.bytes(value, Charsets.UTF_8), KeyType.PublicKey);
            encryptMap.put(encryptKey, encryptValue);
        }

        return encryptMap;
    }

    //使用私钥对参数解密
    private Map<String, Object> getDecryptMap(Map<String, Object> encryptMap) {
        RSA rsa = new RSA(privateKey, null);
        Map<String, Object> decryptMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : encryptMap.entrySet()) {
            String encryptKey = entry.getKey();
            String encryptValue = (String) entry.getValue();
            String key = rsa.decryptStr(encryptKey, KeyType.PrivateKey);
            String value = rsa.decryptStr(encryptValue, KeyType.PrivateKey);
            decryptMap.put(key, value);
        }

        return decryptMap;
    }

}
