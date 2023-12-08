package com.zenith.aspect;

import com.zenith.common.MyException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
public class LogAspect {
    @Pointcut("execution(* com.zenith.controller.*.* (..))")
    public void pt(){
    }

    @Around("pt()")
    public Object handlerController(ProceedingJoinPoint joinPoint){
        Object res=null;
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String serverName = request.getServerName();
        String pathInfo = request.getServletPath();
        String method = request.getMethod();
        log.debug("From server: {}, request =>[{} {}]",serverName,method,pathInfo);

        try {
            long begin = System.currentTimeMillis();
            res=joinPoint.proceed();
            long end=System.currentTimeMillis();
            log.debug("[{} {}] request successfully, runtime {} ms",method,pathInfo, end-begin);
            } catch (Throwable throwable) {
            log.error("[{} {}] request failed!",method,pathInfo);
            if(throwable instanceof MyException){
                throw new MyException(((MyException) throwable).getMsg());
            }else {
                throw new RuntimeException();
            }
        }
        return res;
    }
}
