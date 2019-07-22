package com.rebote.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: Da Shuai
 * @Date: 2019/7/22 14:07
 * @Description:
 * @Company: zytech
 * @Email: 1043489207@qq.com
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @GetMapping("/info")
    public String getCustomerInfo(){
        System.out.println("1111111111");
        return "hello";
    }

    @GetMapping(value = "/test")
    public ModelAndView test(HttpServletRequest req) {
        // UserEntity userEntity = getCurrentUser(req);
        System.out.println("fdasfdasfd");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/show.html");
        return mv;
    }

}
