package com.rebote.springboot;

import com.rebote.springboot.listener.ApplicationEnvironmentPreparedEventListener;
import com.rebote.springboot.listener.ApplicationPreparedEventListener;
import com.rebote.springboot.listener.ApplicationStartingEventListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        //SpringApplication.run(Application.class,args);
        SpringApplication app = new SpringApplication(Application.class);
        //app.setBannerMode(Banner.Mode.OFF);
        // 添加监听器,必须放在run之前
        //app.addListeners(new ApplicationPreparedEventListener(),new ApplicationEnvironmentPreparedEventListener(),new ApplicationStartingEventListener());
        app.run(args);
    }

}
