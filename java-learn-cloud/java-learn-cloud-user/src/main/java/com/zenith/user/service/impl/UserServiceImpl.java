package com.zenith.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zenith.common.R;
import com.zenith.entity.User;
import com.zenith.user.mapper.UserMapper;
import com.zenith.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public R generateData(Integer num) {
        baseMapper.delete(new LambdaQueryWrapper<User>().le(User::getId,10000L));

        for(int i=1;i<=num;i++){
            User user=new User();
            user.setId((long) i);
            user.setUsername(UUID.randomUUID().toString());
            user.setPassword("123456");
            user.setPhone("12345678910");
            baseMapper.insert(user);
        }

        return R.ok();
    }
}
