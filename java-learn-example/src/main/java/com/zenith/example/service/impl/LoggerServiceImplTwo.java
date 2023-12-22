package com.zenith.example.service.impl;

import com.zenith.example.spi.Logger;

public class LoggerServiceImplTwo implements Logger {
    @Override
    public void info(String str) {
        System.out.println("The second implementation execute info method: "+str);
    }

    @Override
    public void debug(String str) {
        System.out.println("The second implementation execute debug method: "+str);
    }
}
