package com.zenith.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@TableName("tb_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item implements Serializable {
    private Long id;
    private String name;
}
