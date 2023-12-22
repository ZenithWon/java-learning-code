package com.zenith.example.service;

import com.zenith.example.spi.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class LoggerService {
    private static final LoggerService SERVICE = new LoggerService();
    private final List<Logger> loggerList;
    private final Logger logger;
    private LoggerService(){
        ServiceLoader<Logger> load = ServiceLoader.load(Logger.class);
        List<Logger> list = new ArrayList<>();
        for(Logger logger:load){
            list.add(logger);
        }
        loggerList=list;
        if (!list.isEmpty()) {
            // Logger 只取一个
            logger = list.get(0);
        } else {
            logger = null;
        }
    }

    public static LoggerService getService(){
        return SERVICE;
    }

    public void info(String str){
        if(logger==null){
            System.out.println("There is no logger!");
        }else{
            for(Logger logger:loggerList){
                logger.info(str);
            }
        }
    }

    public void debug(String str){
        if(logger==null){
            System.out.println("There is no logger!");
        }else{
            for(Logger logger:loggerList){
                logger.debug(str);
            }
        }
    }
}
