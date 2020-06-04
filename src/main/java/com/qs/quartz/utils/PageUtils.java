package com.qs.quartz.utils;

import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.Map;

public class PageUtils {

    public static Map<String, Object> wrapPageDataToMap(PageInfo pageInfo) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("count", pageInfo.getTotal());
        dataMap.put("data", pageInfo.getList());
        dataMap.put("code", 0);
        return dataMap;
    }
}
