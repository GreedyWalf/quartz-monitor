package com.qs.quartz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName("t_user")
public class User implements Serializable {

    @TableId(type = IdType.AUTO)
    private String userId;

    private String userName;
}
