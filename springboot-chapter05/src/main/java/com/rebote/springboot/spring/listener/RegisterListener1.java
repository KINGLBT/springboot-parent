package com.rebote.springboot.spring.listener;

import com.rebote.springboot.spring.event.RegisterEvent;
import com.rebote.springboot.spring.model.User;
import org.springframework.context.ApplicationListener;

public class RegisterListener1 implements ApplicationListener<RegisterEvent> {
    public void onApplicationEvent(RegisterEvent registerEvent) {
        User user = registerEvent.getUser();
        System.out.println("用户:"+ user.getUserName()+"注册结束，向手机"+user.getMobile()+"发送短信!");
    }
}
