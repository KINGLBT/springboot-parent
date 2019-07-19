package com.rebote.springboot.bean;

/**
 * @Auther: Da Shuai
 * @Date: 2019/7/19 14:47
 * @Description:
 * @Company: zytech
 * @Email: 1043489207@qq.com
 */
public class TestBeanTwo {

    private String userName;
    private String password;
    private String email;

    public void sayHello() {
        System.out.println("TestBeanTwo sayHello...");
    }

    public String toString() {
        return "username:" + this.userName + ",url:" + this.email + ",password:" + this.password;
    }

    public void start() {
        System.out.println("TestBeanTwo 初始化。。。");
    }

    public void cleanUp() {
        System.out.println("TestBeanTwo 销毁。。。");
    }

}
