package com.rebote.springboot.component;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(101)
public class MyCommandLineRunner implements CommandLineRunner {
    public void run(String... args) throws Exception {
        for (String arg:args) {
            System.out.println(arg);
        }
        System.out.println("MyCommandLineRunner,我在 SpringApplication.run() 完成之前调用");
    }
}
