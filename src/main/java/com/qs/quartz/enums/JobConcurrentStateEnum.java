package com.qs.quartz.enums;

/**
 * 任务并发标志枚举
 *
 * @author qinyupeng
 * @since 2020-06-02 22:22:45
 */
public enum JobConcurrentStateEnum {

    CONCURRENT_FALSE("0", "有状态"),
    CONCURRENT_TRUE("1", "无状态");


    private String code;

    private String desc;

    JobConcurrentStateEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
