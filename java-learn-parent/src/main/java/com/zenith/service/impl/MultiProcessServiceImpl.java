package com.zenith.service.impl;

import com.zenith.common.R;
import com.zenith.common.thread.MyCallable;
import com.zenith.common.thread.MyRunnable;
import com.zenith.common.thread.MyThread;
import com.zenith.service.MultiProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

@Service
@Slf4j
public class MultiProcessServiceImpl implements MultiProcessService {
    @Override
    public R startByThread() {
        MyThread t1=new MyThread();
        MyThread t2=new MyThread();

        t1.start();
        t2.start();

        return R.atte();
    }

    @Override
    public R startByRunnable() {
        MyRunnable runnable=new MyRunnable();
        Thread t1=new Thread(runnable);
        Thread t2=new Thread(runnable);

        t1.start();
        t2.start();
        return R.atte();
    }

    @Override
    public R startByPool() {
        ExecutorService threadPool= Executors.newFixedThreadPool(10);
        for(int i=0;i<5;i++){
            threadPool.submit(new MyThread());
        }
        return R.atte();
    }

    @Override
    public R startByCallable() {
        MyCallable callable = new MyCallable();

        FutureTask<String> task=new FutureTask<>(callable);

        Thread t1=new Thread(task);
        Thread t2=new Thread(task);
        t1.start();
        t2.start();

        try {
            log.info(task.get());
        }catch (Exception e){
            throw new RuntimeException();
        }
        return R.atte();
    }
}
