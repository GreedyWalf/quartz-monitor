package com.qs.quartz.enums;

/**
 * 任务执行明细状态
 *
 * @author qinyupeng
 * @since 2020-06-02 22:22:45
 */
public enum TaskDetailStatusEnum {

    STATUS_EXECUTE("0", "执行中"),
    STATUS_EXECUTE_SUCCESS("1", "执行成功"),
    STATUS_EXECUTE_FAILED("1", "执行失败");


    private String code;

    private String desc;

    TaskDetailStatusEnum(String code, String desc) {
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
