package com.zenith.order.feign;

import com.zenith.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("user-service")
public interface UserFeignClient {
    @GetMapping("/user/{id}")
    public R getUserById(@PathVariable("id") Long id);
}
