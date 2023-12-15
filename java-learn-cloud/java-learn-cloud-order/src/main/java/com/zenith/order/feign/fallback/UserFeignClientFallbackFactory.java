package com.zenith.order.feign.fallback;

import com.zenith.common.R;
import com.zenith.entity.User;
import com.zenith.order.feign.UserFeignClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

//Using fallback factory can catch exception info
@Component
public class UserFeignClientFallbackFactory implements FallbackFactory<UserFeignClient> {
    @Override
    public UserFeignClient create(Throwable throwable) {
        return new UserFeignClient() {
            @Override
            public R getUserById(Long id) {
                User user=new User();
                user.setId(999999L);
                user.setUsername("default user (Fallback)");
                return R.ok(user);
            }
        };
    }
}
