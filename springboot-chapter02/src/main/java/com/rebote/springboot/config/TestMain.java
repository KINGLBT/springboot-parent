package com.rebote.springboot.config;

import com.rebote.springboot.bean.TestBean;
import com.rebote.springboot.bean.TestBeanTwo;
import com.rebote.springboot.bean.TestComponentBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Auther: Da Shuai
 * @Date: 2019/7/19 14:40
 * @Description:
 * @Company: zytech
 * @Email: 1043489207@qq.com
 */
public class TestMain {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestConfiguration.class);

        TestBean testBean = (TestBean) context.getBean("testBean");
        testBean.sayHello();

        TestBean testBean2 = (TestBean) context.getBean("testBean");
        testBean.sayHello();

        TestComponentBean componentBean = (TestComponentBean) context.getBean("testComponentBean");
        componentBean.sayHello();

        TestBeanTwo testBeanTwo = (TestBeanTwo) context.getBean("testBeanTwo");
        testBeanTwo.sayHello();
    }

}
