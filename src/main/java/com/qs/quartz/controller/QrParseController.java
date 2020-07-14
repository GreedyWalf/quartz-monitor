package com.qs.quartz.controller;

import com.qs.quartz.utils.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class QrParseController {

    @RequestMapping(value = "/parse")
    public String parse(String qrcode) {
        System.out.println("1、解密qrcode");
        System.out.println("2、根据qrcode获取到二维码记录");
        System.out.println("3、校验渠道、校验黑名单、校验白名单、校验有效期");
        System.out.println("4、获取业务数据");
        System.out.println("5、完成跳转");
        return "redirect:https://www.baidu.com";
    }


    @RequestMapping(value = "/parseJson")
    @ResponseBody
    public JsonResult parseJson(String qrcode) {
        JsonResult jsonResult = new JsonResult();
        System.out.println("1、解密qrcode");
        System.out.println("2、根据qrcode获取到二维码记录");
        System.out.println("3、校验渠道、校验黑名单、校验白名单、校验有效期");
        System.out.println("4、获取业务数据");
        System.out.println("5、完成跳转");
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("shareFlag", "1");
        resultMap.put("HZBank", "HZBank");
        return jsonResult;
    }


}
