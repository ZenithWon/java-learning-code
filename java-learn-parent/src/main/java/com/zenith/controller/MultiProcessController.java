package com.zenith.controller;

import com.zenith.common.R;
import com.zenith.service.MultiProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/multiProcess")
public class MultiProcessController {
    @Autowired
    MultiProcessService multiProcessService;

    @GetMapping("/start/thread")
    public R startByThread(){
        return multiProcessService.startByThread();
    }

    @GetMapping("/start/runnable")
    public R startByRunnable(){
        return multiProcessService.startByRunnable();
    }

    @GetMapping("/start/callable")
    public R startByCallable(){
        return multiProcessService.startByCallable();
    }

    @GetMapping("/start/pool")
    public R startByPool(){
        return multiProcessService.startByPool();
    }
}
