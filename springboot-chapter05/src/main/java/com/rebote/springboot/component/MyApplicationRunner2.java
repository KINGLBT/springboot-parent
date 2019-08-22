package com.rebote.springboot.component;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(60)
public class MyApplicationRunner2 implements ApplicationRunner {
    public void run(ApplicationArguments args) throws Exception {
        for (String arg:args.getSourceArgs()) {
            System.out.println(arg);
        }
        System.out.println("MyApplicationRunner2,我在 SpringApplication.run() 完成之前调用");
    }
}
