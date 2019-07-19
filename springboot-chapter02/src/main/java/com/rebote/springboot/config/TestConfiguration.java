package com.rebote.springboot.config;

import com.rebote.springboot.bean.TestBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: Da Shuai
 * @Date: 2019/7/19 14:39
 * @Description:
 * @Company: zytech
 * @Email: 1043489207@qq.com
 */
@Configuration
public class TestConfiguration {

    public TestConfiguration(){
        System.out.println("----init TestConfiguration---");
    }

    @Bean
    public TestBean testBean(){
        return new TestBean();
    }

}
