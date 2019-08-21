# 自定义启动banner

启动banner可以更改，有两种方式，一种是txt文件，一种是图片

## txt文件

+ 1、在classpath下，添加banner.txt文件，启动的时候，会打印banner.txt里面的内容
+ 2、在配置文件中通过 spring.banner.location 可以指定banner.txt的路径
+ 3、对于非UTF-8格式的文件，可以设置编码，默认情况是UTF-8
+ 4、spring.banner.location设置了启动banner，优先于直接在classpath下，添加banner.txt

## 图片文件
SpringBoot 在启动的时候，会自动将图片转化成ASCII文本，并打印出来

+ 1、在classpath下，添加banner.gif,banner.jpg或者banner.png
+ 2、在配置文件中通过 spring.banner.image.location 可以指定banner图片文件

## Banner中可用变量

 | 变量        | 描述    |
 | --------   | :-----   |
 | ${application.version}                  | 应用的版本号,定义在 MANIFEST.MF中. 例如： Implementation-Version: 1.0 将会打印 1.0.|  
 | ${application.formatted-version}        | 应用的版本号格式, as declared in MANIFEST.MF and formatted for display (surrounded with brackets and prefixed with v). For example (v1.0).|  
 | ${spring-boot.version}                  | 您使用的 Spring Boot 版本。例如 2.1.1.RELEASE.。| 
 | ${spring-boot.formatted-version}        | 您使用的 Spring Boot 版本格式化之后显示（用括号括起来，以 v 为前缀）。例如 (v2.1.1.RELEASE)。| 
 | ${Ansi.NAME} (or ${AnsiColor.NAME}, ${AnsiBackground.NAME}, ${AnsiStyle.NAME})        | 其中 NAME 是 ANSI 转义码的名称。有关详细信息，请参阅 AnsiPropertySource。|
 | ${application.title}                    | 您的应用标题，声明在 MANIFEST.MF 中，例如 Implementation-Title: MyApp 打印为 MyApp。|     
 
 # 自定义 SpringApplication
 
 
 
 # 应用程序事件与监听器
 
 ## Spring的事件发布机制
 
 事件机制使用监听者模式。事件机制相关的核心类有四个：

 + ApplicationEvent：Spring中的事件基类
 
```java
 public abstract class ApplicationEvent extends EventObject {
     /**
      * 创建一个事件，需要指定事件源
      */
     public ApplicationEvent(Object source) {
         super(source);
         this.timestamp = System.currentTimeMillis();
     }
 }
```

 + ApplicationEventPublisher: 发布事件这，调用广播发布事件

```java
public interface ApplicationEventPublisher {
    /**发布事件*/
    default void publishEvent(ApplicationEvent event) {
        publishEvent((Object) event);
    }
    void publishEvent(Object event);
}
```
 
 + ApplicationEventMulticaster: 广播，持有观察者集合，可向集合内的观察者通知事件
 
 ```java
public interface ApplicationEventMulticaster {
    /**
     * 添加监听者（观察者）
     */
    void addApplicationListener(ApplicationListener<?> listener);

    /**
     * 删除监听者（观察者）
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * 向所有监听者发布事件
     */
    void multicastEvent(ApplicationEvent event);
}
 ```
 
 + ApplicationListener: 观察者，接受对应事件后，执行逻辑。
 
  ```java
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {
    /**
     * 接收事件后，执行相应逻辑
     */
    void onApplicationEvent(E event);
}
  ```
  
事件发布者ApplicationEventPublisher持有广播ApplicationEventMulticaster，广播负责添加观察者，以及向所有观察者广播事件。

一个事件ApplicationEvent可以通过发布者ApplicationEventPublisher发布后，会调用广播ApplicationEventMulticaster通知所有观察者，

观察者ApplicationListener收到通知后执行相关操作。

### 示例1

  ```java
public class MainTest {

    public static void main(String[] args) {
        //构建广播器
        ApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster();
        //广播添加监听器
        multicaster.addApplicationListener(new RegisterListener1());
        multicaster.addApplicationListener(new RegisterListener2());

        //构建事件发布者
        ApplicationEventPublisher eventPublicsher = new RegiserEventPublisher(multicaster);

        //构建注册事件
        User user = new User("jack", "18782252509", "jack_email@163.com");
        System.out.println("用户注册……");
        RegisterEvent registerEvent = new RegisterEvent(user);

        //发布注册事件
        eventPublicsher.publishEvent(registerEvent);
    }

}

  ```
  
 ## SpringBoot应用程序事件
 
 除了常见的Spring Framework事件，比如 ContextRefreshedEvent，SpringApplication 还会发送其他应用程序事件。
 
 当您运行应用时，应用程序事件将按照以下顺序发送：
 
 + 1.在开始应用开始运行但还没有进行任何处理时（除了注册监听器和初始化器[initializer]），将发送 ApplicationStartingEvent。
 + 2.当 Environment 被上下文使用，但是在上下文创建之前，将发送 ApplicationEnvironmentPreparedEvent。
 + 3.在开始刷新之前，bean 定义被加载之后发送 ApplicationPreparedEvent。
 + 4.在上下文刷新之后且所有的应用和命令行运行器（command-line runner）被调用之前发送 ApplicationStartedEvent。
 + 5.在应用程序和命令行运行器（command-line runner）被调用之后，将发出 ApplicationReadyEvent，该事件用于通知应用已经准备处理请求。
 + 6.如果启动时发生异常，将发送 ApplicationFailedEvent。
 
 试着以创建这些事件的监听者,并使用@Bean注入，看看是否每个监听者，都可以收到相应的事件通知


+ 监听 ApplicationStartingEvent事件
 
 ```java
package com.rebote.springboot.listener;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartingEventListener implements ApplicationListener<ApplicationStartingEvent> {
    public void onApplicationEvent(ApplicationStartingEvent applicationStartingEvent) {
        System.out.println("收到applicationStartingEvent通知");
    }
}
```

+ 监听 ApplicationEnvironmentPreparedEvent事件
 
 ```java
package com.rebote.springboot.listener;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationEnvironmentPreparedEventListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent applicationEnvironmentPreparedEvent) {
        System.out.println("收到applicationEnvironmentPreparedEvent通知");
    }
}
```

+ 监听 ApplicationPreparedEvent 事件
 
 ```java
package com.rebote.springboot.listener;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationEnvironmentPreparedEventListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent applicationEnvironmentPreparedEvent) {
        System.out.println("收到applicationEnvironmentPreparedEvent通知");
    }
}
```

+ 监听 ApplicationStartedEvent 事件
 
 ```java
package com.rebote.springboot.listener;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        System.out.println("收到ApplicationStartedEvent通知");
    }
}

```

+ 监听 ApplicationReadyEvent 事件
 
 ```java
package com.rebote.springboot.listener;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationReadyEventEventListener implements ApplicationListener<ApplicationReadyEvent> {
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        System.out.println("收到applicationReadyEvent通知");
    }
}


```

最后，只收到ApplicationStartedEvent和ApplicationReadyEvent通知，如下图：
