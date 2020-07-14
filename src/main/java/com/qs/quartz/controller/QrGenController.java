package com.qs.quartz.controller;

import com.qs.quartz.utils.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class QrGenController {


    @RequestMapping(value = "/generate")
    @ResponseBody
    public JsonResult generate(String channelId){
        JsonResult jsonResult = new JsonResult();
        System.out.println("1、根据channelId获取到templateId");
        System.out.println("2、根据templateId获取二维码模板");
        System.out.println("3、根据templateId生成二维码");
        Map<String, String> qrcodeData = new HashMap<>();
        qrcodeData.put("qrcode", "HZBK2010109008979089");
        qrcodeData.put("parseUrl","http://localhost:9090/monitor/parse?qrcode=HZBK2010109008979089");
        jsonResult.setData(qrcodeData);
        return jsonResult;
    }
}
