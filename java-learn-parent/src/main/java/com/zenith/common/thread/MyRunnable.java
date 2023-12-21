package com.zenith.common.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyRunnable implements Runnable {
    @Override
    public void run() {
        log.info("Start new thread by runnable:{}",Thread.currentThread().getId());
    }
}
