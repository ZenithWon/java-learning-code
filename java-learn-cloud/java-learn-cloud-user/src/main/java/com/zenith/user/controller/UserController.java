package com.zenith.user.controller;

import com.zenith.common.R;
import com.zenith.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/generate/{num}")
    public R generateItemData(@PathVariable Integer num){
        return userService.generateData(num);
    }

    @GetMapping("/{id}")
    public R getById(@PathVariable Long id){
        return R.ok(userService.getById(id));
    }

}
