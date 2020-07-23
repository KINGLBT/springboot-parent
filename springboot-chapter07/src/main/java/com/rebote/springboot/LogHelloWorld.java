package com.rebote.springboot;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogHelloWorld {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(LogHelloWorld.class);
        logger.debug("Hello world");

        // 打印内部的状态
        LoggerContext lc = (LoggerContext)LoggerFactory.getILoggerFactory();
        StatusPrinter.print(lc);
    }

}
