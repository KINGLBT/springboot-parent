package com.rebote.springboot.spring.publisher;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ApplicationEventMulticaster;

public class RegiserEventPublisher implements ApplicationEventPublisher {

    private ApplicationEventMulticaster applicationEventMulticaster;

    public RegiserEventPublisher(ApplicationEventMulticaster applicationEventMulticaster) {
        this.applicationEventMulticaster = applicationEventMulticaster;
    }

    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
    }

    public void publishEvent(Object o) {
        publishEvent(0);
    }

    public ApplicationEventMulticaster getApplicationEventMulticaster() {
        return applicationEventMulticaster;
    }

    public void setApplicationEventMulticaster(ApplicationEventMulticaster applicationEventMulticaster) {
        this.applicationEventMulticaster = applicationEventMulticaster;
    }
}
