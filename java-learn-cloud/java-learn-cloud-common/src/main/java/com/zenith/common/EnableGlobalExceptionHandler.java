package com.zenith.common;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({GlobalExceptionHandler.class})

//如果AOP不在启动类扫描包内需要在启动类加上这个注解
public @interface EnableGlobalExceptionHandler {
}
