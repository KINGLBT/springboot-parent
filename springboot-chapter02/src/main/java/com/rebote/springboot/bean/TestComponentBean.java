package com.rebote.springboot.bean;

import org.springframework.stereotype.Component;

/**
 * @Auther: Da Shuai
 * @Date: 2019/7/19 15:24
 * @Description:
 * @Company: zytech
 * @Email: 1043489207@qq.com
 */
@Component
public class TestComponentBean {

    private String userName;
    private String password;
    private String email;

    public void sayHello() {
        System.out.println("TestComponentBean sayHello...");
    }

    public String toString() {
        return "username:" + this.userName + ",url:" + this.email + ",password:" + this.password;
    }

    public void start() {
        System.out.println("TestComponentBean 初始化。。。");
    }

    public void cleanUp() {
        System.out.println("TestComponentBean 销毁。。。");
    }
}
