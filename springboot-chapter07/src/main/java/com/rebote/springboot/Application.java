package com.rebote.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        //SpringApplication.run(Application.class,args);
        SpringApplication app = new SpringApplication(Application.class);
        //app.setBannerMode(Banner.Mode.OFF);
        // 添加监听器,必须放在run之前
        //放大手动阀fdsaa
        //app.addListeners(new ApplicationPreparedEventListener(),new ApplicationEnvironmentPreparedEventListener(),new ApplicationStartingEventListener());
        app.run(args);
    }

}
