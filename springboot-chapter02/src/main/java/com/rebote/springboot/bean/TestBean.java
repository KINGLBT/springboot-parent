package com.rebote.springboot.bean;

/**
 * @Auther: Da Shuai
 * @Date: 2019/7/19 14:47
 * @Description:
 * @Company: zytech
 * @Email: 1043489207@qq.com
 */
public class TestBean {

    private String userName;
    private String password;
    private String email;

    public void sayHello() {
        System.out.println("TestBean sayHello...");
    }

    public String toString() {
        return "username:" + this.userName + ",url:" + this.email + ",password:" + this.password;
    }

}
