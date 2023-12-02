package com.zenith.controller;

import com.zenith.common.R;
import com.zenith.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    RedisService redisService;

    @GetMapping("/cacheThrough/nullSolution/{id}")
    public R cacheThroughNullSolution(@PathVariable Long id){
        return redisService.cacheThroughNullSolution(id);
    }

    @GetMapping("/cacheThrough/bloomSolution/{id}")
    public R cacheThroughBloomSolution(@PathVariable Long id){
        return redisService.cacheThroughBloomSolution(id);
    }

    @GetMapping("/hitThrough/mutexSolution/{id}")
    public R hitThroughMutexSolution(@PathVariable Long id){
        return redisService.hitThroughMutexSolution(id);
    }

    @GetMapping("/hitThrough/logicalTtlSolution/{id}")
    public R hitThroughMLogicalTtlSolution(@PathVariable Long id){
        return redisService.hitThroughMLogicalTtlSolution(id);
    }

    @GetMapping("/doubleWrite/lock/read/{id}")
    public R doubleWriteLockRead(@PathVariable Long id){
        return redisService.doubleWriteLockRead(id);
    }

    @PutMapping("/doubleWrite/lock/write/{id}")
    public R doubleWriteLockWrite(@PathVariable Long id){
        return redisService.doubleWriteLockWrite(id);
    }





}
