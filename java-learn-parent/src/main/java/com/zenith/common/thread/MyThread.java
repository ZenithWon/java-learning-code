package com.zenith.common.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyThread extends Thread{
    @Override
    public void run() {
        log.info("Start new thread by thread:{}",Thread.currentThread().getId());
    }
}
