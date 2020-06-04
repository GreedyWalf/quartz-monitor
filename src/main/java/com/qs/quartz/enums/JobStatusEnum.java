package com.qs.quartz.enums;

/**
 * 任务状态枚举
 *
 * @author qinyupeng
 * @since 2020-06-02 22:22:45
 */
public enum JobStatusEnum {

    STATUS_RUNNABLE("0", "运行中"),
    STATUS_PAUSE("1", "已暂停");

    private String code;

    private String desc;

    JobStatusEnum(String code, String desc) {
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
