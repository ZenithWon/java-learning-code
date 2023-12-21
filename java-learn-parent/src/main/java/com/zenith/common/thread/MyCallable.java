package com.zenith.common.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

@Slf4j
public class MyCallable implements Callable<String> {
    @Override
    public String call() {
        log.info("Start new thread by callable:{}",Thread.currentThread().getId());
        return  "ok";
    }
}
