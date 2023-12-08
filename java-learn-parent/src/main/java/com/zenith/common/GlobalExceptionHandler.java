package com.zenith.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MyException.class)
    public R handlerMyException(MyException e){
        log.error("System throw a exception: {}",e.getMsg());
        e.printStackTrace();
        return R.error(e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public R handlerUnKnownException(Exception e){
        e.printStackTrace();
        return R.error("System error!");
    }
}
