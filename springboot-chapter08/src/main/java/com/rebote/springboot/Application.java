package com.rebote.springboot;

import com.rebote.springboot.banner.SpringBootCustomerBanner;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @ClassName: Application
 * @description:
 * @author: luomeng
 * @time: 2020/7/16 20:38
 */
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    //SpringApplication.run(Application.class, args);

    /*SpringApplication application = new SpringApplication(Application.class);
    application.setLazyInitialization(Boolean.TRUE);
    application.run(args);*/

    SpringApplicationBuilder builder =  new SpringApplicationBuilder();
    builder
        .sources(Application.class)
        .lazyInitialization(Boolean.TRUE)
        .banner(new SpringBootCustomerBanner())
        .bannerMode(Banner.Mode.OFF)
        .run(args);
  }

}
