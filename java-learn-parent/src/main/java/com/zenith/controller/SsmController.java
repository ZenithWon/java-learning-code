package com.zenith.controller;

import com.zenith.aspect.ApiLog;
import com.zenith.common.MyException;
import com.zenith.common.R;
import com.zenith.service.SsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ssm")
public class SsmController {
    @Autowired
    SsmService ssmService;

    @GetMapping("/transaction/fail/1")
    public R transactionFailOne(Integer from, Integer to){
        return ssmService.transactionFailOne(from,to);
    }

    @GetMapping("/mybatis/cacheLevelOne/{id}")
    public R mybatisCacheLevelOne(@PathVariable Long id){
        return ssmService.mybatisCacheLevelOne(id);
    }

    @GetMapping("/mybatis/cacheLevelTwo/{id}")
    public R mybatisCacheLevelTwo(@PathVariable Long id){
        return ssmService.mybatisCacheLevelTwo(id);
    }
}
