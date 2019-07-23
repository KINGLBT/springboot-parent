package com.rebote.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    @RequestMapping("/test")
    public String getCustomerInfo(){
        return "zhangsan";
    }

}
