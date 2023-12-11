package com.zenith.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_order")
public class Order {
    private String id;
    private Long userId;

    @TableField(exist = false)
    private User user;
}
