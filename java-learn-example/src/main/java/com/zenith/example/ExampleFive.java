package com.zenith.example;

import com.zenith.example.service.LoggerService;

/**
 * @author ZenithWon
 * @date 2023/12/22
 * @description SPI ServiceLoader使用案例
 */

public class ExampleFive {
    public static void main(String[] args) {
        LoggerService service = LoggerService.getService();
        service.info("hello world!");
    }
}
