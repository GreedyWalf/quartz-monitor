package com.qs.quartz.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qs.quartz.entity.User;

public interface UserMapper extends BaseMapper<User> {

    User getUserById(String userId);
}
