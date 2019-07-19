package com.rebote.springboot.config;

import com.rebote.springboot.bean.TestBean;
import com.rebote.springboot.bean.TestBeanTwo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @Auther: Da Shuai
 * @Date: 2019/7/19 15:34
 * @Description:
 * @Company: zytech
 * @Email: 1043489207@qq.com
 */
@Configuration
public class TestConfigurationTwo {

    public TestConfigurationTwo(){
        System.out.println("--- init TestConfigurationTwo ----");
    }

    @Bean(name = "testBeanTwo",initMethod = "start",destroyMethod = "cleanUp")
    public TestBeanTwo testBeanTwo(){
        return new TestBeanTwo();
    }

}
