package com.zenith.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zenith.common.R;
import com.zenith.entity.Order;
import com.zenith.entity.User;
import com.zenith.order.feign.UserFeignClient;
import com.zenith.order.mapper.OrderMapper;
import com.zenith.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    UserFeignClient userFeignClient;

    @Override
    public R generateData(Integer num) {
        baseMapper.delete(new LambdaQueryWrapper<Order>().le(Order::getId,10000L));

        for(int i=1;i<=num;i++){
            Order order=new Order();
            order.setId(UUID.randomUUID().toString());
            order.setUserId((long) new Random().nextInt(100));
            baseMapper.insert(order);
        }

        return R.ok();
    }

    @Override
    public R getByIdWithUser(String id) {
        Order order = baseMapper.selectById(id);
        User user= (User) userFeignClient.getUserById(order.getUserId()).getData();
        order.setUser(user);

        return R.ok(order);
    }
}
