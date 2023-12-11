package com.zenith.order.controller;

import com.zenith.common.R;
import com.zenith.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/generate/{num}")
    public R generateItemData(@PathVariable Integer num){
        return orderService.generateData(num);
    }

    @GetMapping("/{id}")
    public R getByIdWithUser(@PathVariable String id){
        return orderService.getByIdWithUser(id);
    }
}
