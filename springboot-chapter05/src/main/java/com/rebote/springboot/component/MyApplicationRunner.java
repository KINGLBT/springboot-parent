package com.rebote.springboot.component;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(103)
public class MyApplicationRunner implements ApplicationRunner {
    public void run(ApplicationArguments args) throws Exception {
        for (String arg:args.getSourceArgs()) {
            System.out.println(arg);
        }
        System.out.println("MyApplicationRunner,我在 SpringApplication.run() 完成之前调用");
    }
}
