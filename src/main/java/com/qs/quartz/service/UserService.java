package com.qs.quartz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qs.quartz.entity.User;

import java.util.List;

public interface UserService extends IService<User> {

    User getUserByUserId(String userId);

    List<User> getAllUser();

}
