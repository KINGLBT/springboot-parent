package com.rebote.springboot.bean;

/**
 * @Auther: Da Shuai
 * @Date: 2019/7/19 16:02
 * @Description:
 * @Company: zytech
 * @Email: 1043489207@qq.com
 */
public class TestBeanXml {

    private String userName;
    private String password;
    private String email;

    public void sayHello() {
        System.out.println("TestBeanXml sayHello...");
    }

    public String toString() {
        return "username:" + this.userName + ",url:" + this.email + ",password:" + this.password;
    }

    public void start() {
        System.out.println("TestBeanXml 初始化。。。");
    }

    public void cleanUp() {
        System.out.println("TestBeanXml 销毁。。。");
    }

}
