package com.zenith.example;

import com.zenith.example.domain.Item;

/**
 * @author ZenithWon
 * @date 2023/12/20
 * @description 类成员变量初始化问题
 */

public class ExampleOne {
    public static void main(String[] args) {
        Item item = new Item();
        System.out.println(item.toString());
    }
}
