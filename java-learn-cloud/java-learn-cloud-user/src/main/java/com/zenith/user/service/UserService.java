package com.zenith.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zenith.common.R;
import com.zenith.entity.User;

public interface UserService extends IService<User> {
    R generateData(Integer num);
}
