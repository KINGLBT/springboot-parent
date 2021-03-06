# SpringBoot configuration 注解使用

SpringBoot支持基于Java的配置，也支持XML配置，官方建议使用基于Java的配置，通常建议主配置源为 @Configuration 类。通常，一个很好的选择是将定义了 main 方法的类作为 @Configuration

## configuration类介绍

从Spring3.0，@Configuration用于定义配置类，可替换xml配置文件，被注解的类内部包含有一个或多个被@Bean注解的方法，这些方法将会被AnnotationConfigApplicationContext或AnnotationConfigWebApplicationContext类进行扫描，
并用于构建bean定义，初始化Spring容器。

@Configuration标注在类上，相当于把该类作为spring的xml配置文件中的<beans>，作用为：配置spring容器(应用上下文)
@Configuration标注的类里面，会有一个或多个@Bean注解的方法，相当于XML配置文件中的<bean>

@Configuration注解的配置类有如下要求：
+ @Configuration不可以是final类型；
+ @Configuration不可以是匿名类；
+ 嵌套的configuration必须是静态类。


##  导入额外的配置类
不需要将所有的@Configuration内容放入一个类中，可以通过以下两种方式导入
+ @Import注解可以导入其他配置类，
+ 还可以使用@ComponentScan注解自动扫描所有的Spring组件，包括@Configuration类


##  导入XML配置

在一些条件下，必须使用基于XML的配置文件，SpringBoot官方依旧建议使用 @Configuration 类。然后在@Configuration类上面可以使用 @ImportResource 注解来加载 XML 配置文件。


##  示例

###  @Configuration配置spring并启动spring容器

创建一个@Configuration配置类

```java
@Configuration
public class TestConfiguration {
  
    public TestConfiguration(){
        System.out.println("----init TestConfiguration---");
    }
  
}  
```

加载配置类并启动
```java
public class TestMain {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestConfiguration.class);
    }

}
```

###  @Configuration启动容器+@Bean注册Bean

创建Bean类,TestBean

```java
public class TestBean {

    private String userName;
    private String password;
    private String email;

    public void sayHello() {
        System.out.println("TestBean sayHello...");
    }

    public String toString() {
        return "username:" + this.userName + ",url:" + this.email + ",password:" + this.password;
    }

}
```

在@Configuration类中注册bean对象
@Bean标注在方法上(返回某个实例的方法)，等价于spring的xml配置文件中的<bean>，作用为：注册bean对象

```java
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
```

加载配置类并启动容器，获取注入进来的bean，并调用方法

```java
public class TestMain {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestConfiguration.class);

        TestBean testBean = (TestBean) context.getBean("testBean");
        testBean.sayHello();
    }

}
```

注： 
(1)、@Bean注解在返回实例的方法上，如果未通过@Bean指定bean的名称，则默认与标注的方法名相同； 
(2)、@Bean注解默认作用域为单例singleton作用域，可通过@Scope(“prototype”)设置为原型作用域； 
(3)、既然@Bean的作用是注册bean对象，那么完全可以使用@Component、@Controller、@Service、@Ripository等注解注册bean，当然需要配置@ComponentScan注解进行自动扫描。

###  @Bean的作用于以及初始化方法以及销毁方法



```java
public class TestBean {

    private String userName;
    private String password;
    private String email;

    public void sayHello() {
        System.out.println("TestBean sayHello...");
    }

    public String toString() {
        return "username:" + this.userName + ",url:" + this.email + ",password:" + this.password;
    }

    public void start() {
        System.out.println("TestBean 初始化。。。");
    }

    public void cleanUp() {
        System.out.println("TestBean 销毁。。。");
    }

}
```

```java
@Configuration
public class TestConfiguration {

    public TestConfiguration(){
        System.out.println("----init TestConfiguration---");
    }

    @Bean(name = "testBean",initMethod = "start",destroyMethod = "cleanUp")
    @Scope("prototype")
    public TestBean testBean(){
        return new TestBean();
    }

}

```  

  

###  @Configuration启动容器+@Component注册Bean（@Controller,@Service,@Mapper同理）

Spring中，可以使用@Component、@Controller、@Service、@Ripository等注解注册bean，当然需要配置@ComponentScan注解进行自动扫描。

创建Bean类，并使用@Component注解

```java
@Component
public class TestComponentBean {

    private String userName;
    private String password;
    private String email;

    public void sayHello() {
        System.out.println("TestComponentBean sayHello...");
    }

    public String toString() {
        return "username:" + this.userName + ",url:" + this.email + ",password:" + this.password;
    }

    public void start() {
        System.out.println("TestComponentBean 初始化。。。");
    }

    public void cleanUp() {
        System.out.println("TestComponentBean 销毁。。。");
    }
}
```

在主配置类上添加自动扫描@ComponentScan

```java
@Configuration
@ComponentScan("com.rebote.springboot.bean")
public class TestConfiguration {

    public TestConfiguration(){
        System.out.println("----init TestConfiguration---");
    }

    @Bean(name = "testBean",initMethod = "start",destroyMethod = "cleanUp")
    @Scope("prototype")
    public TestBean testBean(){
        return new TestBean();
    }

}
```    

加载配置类，启用容器，获取自动注册的bean，并调用
```java
public class TestMain {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(TestConfiguration.class);

        TestBean testBean = (TestBean) context.getBean("testBean");
        testBean.sayHello();

        TestBean testBean2 = (TestBean) context.getBean("testBean");
        testBean.sayHello();

        TestComponentBean componentBean = (TestComponentBean) context.getBean("testComponentBean");
        componentBean.sayHello();

    }

} 
```  


###  在@configuration中引入其他配置类（在TestConfiguration中引入TestConfigurationTwo）

创建TestBeanTwo,TestConfigurationTwo

```java
public class TestBeanTwo {

    private String userName;
    private String password;
    private String email;

    public void sayHello() {
        System.out.println("TestBeanTwo sayHello...");
    }

    public String toString() {
        return "username:" + this.userName + ",url:" + this.email + ",password:" + this.password;
    }

    public void start() {
        System.out.println("TestBeanTwo 初始化。。。");
    }

    public void cleanUp() {
        System.out.println("TestBeanTwo 销毁。。。");
    }

}
```  

```java
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
```  

使用Import 引入其他配置类

@Import(TestConfigurationTwo.class)

```java
@Configuration
@ComponentScan("com.rebote.springboot.bean")
@Import(TestConfigurationTwo.class)
public class TestConfiguration {

    public TestConfiguration(){
        System.out.println("----init TestConfiguration---");
    }

    @Bean(name = "testBean",initMethod = "start",destroyMethod = "cleanUp")
    @Scope("prototype")
    public TestBean testBean(){
        return new TestBean();
    }

}
``` 

加载配置类，启用容器，获取自动注册的bean，并调用

```java
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
``` 

###  在@configuration中引入spring的xml配置文件

创建TestBeanXML
```java
public class TestBeanXml {

    private String userName;
    private String password;
    private String email;

    public void sayHello() {
        System.out.println("TestBeanXml sayHello...");
    }

    public String toString() {
        return "username:" + this.userName + ",url:" + this.email + ",password:" + this.password;
    }

    public void start() {
        System.out.println("TestBeanXml 初始化。。。");
    }

    public void cleanUp() {
        System.out.println("TestBeanXml 销毁。。。");
    }

}
``` 

创建applicationContext-configuration.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context" xmlns:jdbc="http://www.springframework.org/schema/jdbc"
       xmlns:jee="http://www.springframework.org/schema/jee" xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:util="http://www.springframework.org/schema/util" xmlns:task="http://www.springframework.org/schema/task" xsi:schemaLocation="
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd
        http://www.springframework.org/schema/jdbc http://www.springframework.org/schema/jdbc/spring-jdbc-4.0.xsd
        http://www.springframework.org/schema/jee http://www.springframework.org/schema/jee/spring-jee-4.0.xsd
        http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.0.xsd
        http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util-4.0.xsd
        http://www.springframework.org/schema/task http://www.springframework.org/schema/task/spring-task-4.0.xsd" default-lazy-init="false">
    <bean id="testBeanXml" class="com.rebote.springboot.bean.TestBeanXml"></bean>
</beans>
``` 


使用ImportResource 引入XML配置类
@ImportResource("classpath:applicationContext-configuration.xml")
```java
@Configuration
@ComponentScan("com.rebote.springboot.bean")
@Import(TestConfigurationTwo.class)
@ImportResource("classpath:applicationContext-configuration.xml")
public class TestConfiguration {

    public TestConfiguration(){
        System.out.println("----init TestConfiguration---");
    }

    @Bean(name = "testBean",initMethod = "start",destroyMethod = "cleanUp")
    @Scope("prototype")
    public TestBean testBean(){
        return new TestBean();
    }

}
``` 

加载配置类，启用容器，获取自动注册的bean，并调用
```java
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

        TestBeanXml testBeanXml = (TestBeanXml) context.getBean("testBeanXml");
        testBeanXml.sayHello();
    }

}
``` 