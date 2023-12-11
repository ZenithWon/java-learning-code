package com.zenith.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zenith.common.R;
import com.zenith.entity.Order;

public interface OrderService extends IService<Order> {
    R generateData(Integer num);

    R getByIdWithUser(String id);
}
