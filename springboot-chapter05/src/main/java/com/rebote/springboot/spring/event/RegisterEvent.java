package com.rebote.springboot.spring.event;

import com.rebote.springboot.spring.model.User;
import org.springframework.context.ApplicationEvent;

public class RegisterEvent extends ApplicationEvent {
    public RegisterEvent(User user) {
        super(user);
    }

    public User getUser(){
        return (User) getSource();
    }
}
