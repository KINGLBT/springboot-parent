package com.rebote.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private ApplicationArguments applicationArguments;

    @RequestMapping("/test")
    public String getCustomerInfo(){
        return "zhangsan";
    }

    @RequestMapping("/getRunArguments")
    public void getRunArguments(){
        String[] array = applicationArguments.getSourceArgs();
        for (String arg:array) {
            System.out.println(arg);
        }
    }

}
