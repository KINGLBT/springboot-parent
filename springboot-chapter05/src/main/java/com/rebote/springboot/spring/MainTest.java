package com.rebote.springboot.spring;

import com.rebote.springboot.spring.event.RegisterEvent;
import com.rebote.springboot.spring.listener.RegisterListener1;
import com.rebote.springboot.spring.listener.RegisterListener2;
import com.rebote.springboot.spring.model.User;
import com.rebote.springboot.spring.publisher.RegiserEventPublisher;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;

public class MainTest {

    public static void main(String[] args) {
        //构建广播器
        ApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster();
        //广播添加监听器
        multicaster.addApplicationListener(new RegisterListener1());
        multicaster.addApplicationListener(new RegisterListener2());

        //构建事件发布者
        ApplicationEventPublisher eventPublicsher = new RegiserEventPublisher(multicaster);

        //构建注册事件
        User user = new User("jack", "18782252509", "jack_email@163.com");
        System.out.println("用户注册……");
        RegisterEvent registerEvent = new RegisterEvent(user);

        //发布注册事件
        eventPublicsher.publishEvent(registerEvent);
    }

}
