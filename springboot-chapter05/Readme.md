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

![Image text](https://github.com/KINGLBT/springboot-parent/blob/master/image/chapter05/05-1.png)

为什么收不到其他消息呢？

因为有些事件，是在ApplicationContext创建之前触发的，而上面的监听器，都是通过@Bean自动注册监听器。也就是消息的触发，发生在监听器
注册之前了，在监听器还没有注册成功之前，这些消息就触发了，监听器肯定收不到消息。

SpringBoot中，提供了一些解决方法：

+ 1.通过 SpringApplication.addListeners(​...) 或者 SpringApplicationBuilder.listeners(...​) 方法注册它们

 ```java
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        //SpringApplication.run(Application.class,args);
        SpringApplication app = new SpringApplication(Application.class);
        //app.setBannerMode(Banner.Mode.OFF);
        // 添加监听器,必须放在run之前
        app.addListeners(new ApplicationPreparedEventListener(),new ApplicationEnvironmentPreparedEventListener(),new ApplicationStartingEventListener());
        app.run(args);
    }

}
```

如下所示，事件都打印出来了：

 ```java
"C:\Program Files\Java\jdk1.8.0_111\bin\java.exe" -XX:TieredStopAtLevel=1 -noverify -Dspring.output.ansi.enabled=always -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=63260 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Djava.rmi.server.hostname=localhost -Dspring.liveBeansView.mbeanDomain -Dspring.application.admin.enabled=true "-javaagent:D:\JAVA-SoftWare\IntelliJ IDEA 2018.2.5\lib\idea_rt.jar=63261:D:\JAVA-SoftWare\IntelliJ IDEA 2018.2.5\bin" -Dfile.encoding=UTF-8 -classpath "C:\Program Files\Java\jdk1.8.0_111\jre\lib\charsets.jar;C:\Program Files\Java\jdk1.8.0_111\jre\lib\deploy.jar;C:\Program Files\Java\jdk1.8.0_111\jre\lib\ext\access-bridge-64.jar;C:\Program Files\Java\jdk1.8.0_111\jre\lib\ext\cldrdata.jar;C:\Program Files\Java\jdk1.8.0_111\jre\lib\ext\dnsns.jar;C:\Program Files\Java\jdk1.8.0_111\jre\lib\ext\jaccess.jar;C:\Program Files\Java\jdk1.8.0_111\jre\lib\ext\jfxrt.jar;C:\Program Files\Java\jdk1.8.0_111\jre\lib\ext\localedata.jar;C:\Program Files\Java\jdk1.8.0_111\jre\lib\ext\nashorn.jar;C:\Program Files\Java\jdk1.8.0_111\jre\lib\ext\sunec.jar;C:\Program Files\Java\jdk1.8.0_111\jre\lib\ext\sunjce_provider.jar;C:\Program Files\Java\jdk1.8.0_111\jre\lib\ext\sunmscapi.jar;C:\Program Files\Java\jdk1.8.0_111\jre\lib\ext\sunpkcs11.jar;C:\Program Files\Java\jdk1.8.0_111\jre\lib\ext\zipfs.jar;C:\Program Files\Java\jdk1.8.0_111\jre\lib\javaws.jar;C:\Program Files\Java\jdk1.8.0_111\jre\lib\jce.jar;C:\Program Files\Java\jdk1.8.0_111\jre\lib\jfr.jar;C:\Program Files\Java\jdk1.8.0_111\jre\lib\jfxswt.jar;C:\Program Files\Java\jdk1.8.0_111\jre\lib\jsse.jar;C:\Program Files\Java\jdk1.8.0_111\jre\lib\management-agent.jar;C:\Program Files\Java\jdk1.8.0_111\jre\lib\plugin.jar;C:\Program Files\Java\jdk1.8.0_111\jre\lib\resources.jar;C:\Program Files\Java\jdk1.8.0_111\jre\lib\rt.jar;D:\IdeaProjects\springboot-parent\springboot-chapter05\target\classes;D:\JAVA-SoftWare\repo\org\springframework\boot\spring-boot-starter\2.1.6.RELEASE\spring-boot-starter-2.1.6.RELEASE.jar;D:\JAVA-SoftWare\repo\org\springframework\boot\spring-boot\2.1.6.RELEASE\spring-boot-2.1.6.RELEASE.jar;D:\JAVA-SoftWare\repo\org\springframework\spring-context\5.1.8.RELEASE\spring-context-5.1.8.RELEASE.jar;D:\JAVA-SoftWare\repo\org\springframework\boot\spring-boot-autoconfigure\2.1.6.RELEASE\spring-boot-autoconfigure-2.1.6.RELEASE.jar;D:\JAVA-SoftWare\repo\org\springframework\boot\spring-boot-starter-logging\2.1.6.RELEASE\spring-boot-starter-logging-2.1.6.RELEASE.jar;D:\JAVA-SoftWare\repo\ch\qos\logback\logback-classic\1.2.3\logback-classic-1.2.3.jar;D:\JAVA-SoftWare\repo\ch\qos\logback\logback-core\1.2.3\logback-core-1.2.3.jar;D:\JAVA-SoftWare\repo\org\apache\logging\log4j\log4j-to-slf4j\2.11.2\log4j-to-slf4j-2.11.2.jar;D:\JAVA-SoftWare\repo\org\apache\logging\log4j\log4j-api\2.11.2\log4j-api-2.11.2.jar;D:\JAVA-SoftWare\repo\org\slf4j\jul-to-slf4j\1.7.26\jul-to-slf4j-1.7.26.jar;D:\JAVA-SoftWare\repo\javax\annotation\javax.annotation-api\1.3.2\javax.annotation-api-1.3.2.jar;D:\JAVA-SoftWare\repo\org\springframework\spring-core\5.1.8.RELEASE\spring-core-5.1.8.RELEASE.jar;D:\JAVA-SoftWare\repo\org\springframework\spring-jcl\5.1.8.RELEASE\spring-jcl-5.1.8.RELEASE.jar;D:\JAVA-SoftWare\repo\org\yaml\snakeyaml\1.23\snakeyaml-1.23.jar;D:\JAVA-SoftWare\repo\org\slf4j\slf4j-api\1.7.26\slf4j-api-1.7.26.jar;D:\JAVA-SoftWare\repo\org\springframework\boot\spring-boot-starter-web\2.1.6.RELEASE\spring-boot-starter-web-2.1.6.RELEASE.jar;D:\JAVA-SoftWare\repo\org\springframework\boot\spring-boot-starter-json\2.1.6.RELEASE\spring-boot-starter-json-2.1.6.RELEASE.jar;D:\JAVA-SoftWare\repo\com\fasterxml\jackson\core\jackson-databind\2.9.9\jackson-databind-2.9.9.jar;D:\JAVA-SoftWare\repo\com\fasterxml\jackson\core\jackson-annotations\2.9.0\jackson-annotations-2.9.0.jar;D:\JAVA-SoftWare\repo\com\fasterxml\jackson\core\jackson-core\2.9.9\jackson-core-2.9.9.jar;D:\JAVA-SoftWare\repo\com\fasterxml\jackson\datatype\jackson-datatype-jdk8\2.9.9\jackson-datatype-jdk8-2.9.9.jar;D:\JAVA-SoftWare\repo\com\fasterxml\jackson\datatype\jackson-datatype-jsr310\2.9.9\jackson-datatype-jsr310-2.9.9.jar;D:\JAVA-SoftWare\repo\com\fasterxml\jackson\module\jackson-module-parameter-names\2.9.9\jackson-module-parameter-names-2.9.9.jar;D:\JAVA-SoftWare\repo\org\springframework\boot\spring-boot-starter-tomcat\2.1.6.RELEASE\spring-boot-starter-tomcat-2.1.6.RELEASE.jar;D:\JAVA-SoftWare\repo\org\apache\tomcat\embed\tomcat-embed-core\9.0.21\tomcat-embed-core-9.0.21.jar;D:\JAVA-SoftWare\repo\org\apache\tomcat\embed\tomcat-embed-el\9.0.21\tomcat-embed-el-9.0.21.jar;D:\JAVA-SoftWare\repo\org\apache\tomcat\embed\tomcat-embed-websocket\9.0.21\tomcat-embed-websocket-9.0.21.jar;D:\JAVA-SoftWare\repo\org\hibernate\validator\hibernate-validator\6.0.17.Final\hibernate-validator-6.0.17.Final.jar;D:\JAVA-SoftWare\repo\javax\validation\validation-api\2.0.1.Final\validation-api-2.0.1.Final.jar;D:\JAVA-SoftWare\repo\org\jboss\logging\jboss-logging\3.3.2.Final\jboss-logging-3.3.2.Final.jar;D:\JAVA-SoftWare\repo\com\fasterxml\classmate\1.4.0\classmate-1.4.0.jar;D:\JAVA-SoftWare\repo\org\springframework\spring-web\5.1.8.RELEASE\spring-web-5.1.8.RELEASE.jar;D:\JAVA-SoftWare\repo\org\springframework\spring-beans\5.1.8.RELEASE\spring-beans-5.1.8.RELEASE.jar;D:\JAVA-SoftWare\repo\org\springframework\spring-webmvc\5.1.8.RELEASE\spring-webmvc-5.1.8.RELEASE.jar;D:\JAVA-SoftWare\repo\org\springframework\spring-aop\5.1.8.RELEASE\spring-aop-5.1.8.RELEASE.jar;D:\JAVA-SoftWare\repo\org\springframework\spring-expression\5.1.8.RELEASE\spring-expression-5.1.8.RELEASE.jar" com.rebote.springboot.Application
收到applicationStartingEvent通知
收到applicationEnvironmentPreparedEvent通知
                            _ooOoo_
                           o8888888o
                           88" . "88
                           (| -_- |)
                            O\ = /O
                        ____/`---'\____
                      .   ' \\| |// `.
                       / \\||| : |||// \
                     / _||||| -:- |||||- \
                       | | \\\ - /// | |
                     | \_| ''\---/'' | |
                      \ .-\__ `-` ___/-. /
                   ___`. .' /--.--\ `. . __
                ."" '< `.___\_<|>_/___.' >'"".
               | | : `- \`.;`\ _ /`;.`/ - ` : | |
                 \ \ `-. \_ __\ /__ _/ .-` / /
         ======`-.____`-.___\_____/___.-`____.-'======
                            `=---='
            ::2.1.6.RELEASE
         .............................................
                  佛祖镇楼                  BUG辟易
          佛曰:
                  写字楼里写字间，写字间里程序员；
                  程序人员写程序，又拿程序换酒钱。
                  酒醒只在网上坐，酒醉还来网下眠；
                  酒醉酒醒日复日，网上网下年复年。
                  但愿老死电脑间，不愿鞠躬老板前；
                  奔驰宝马贵者趣，公交自行程序员。
                  别人笑我忒疯癫，我笑自己命太贱；
                  不见满街漂亮妹，哪个归得程序员？

2019-08-22 10:47:44.879  INFO 7356 --- [           main] com.rebote.springboot.Application        : Starting Application on DESKTOP-OBF2T7O with PID 7356 (D:\IdeaProjects\springboot-parent\springboot-chapter05\target\classes started by lmluo in D:\IdeaProjects\springboot-parent)
2019-08-22 10:47:44.881  INFO 7356 --- [           main] com.rebote.springboot.Application        : No active profile set, falling back to default profiles: default
收到ApplicationPreparedEvent通知
2019-08-22 10:47:45.524  INFO 7356 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
2019-08-22 10:47:45.539  INFO 7356 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2019-08-22 10:47:45.539  INFO 7356 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.21]
2019-08-22 10:47:45.617  INFO 7356 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2019-08-22 10:47:45.617  INFO 7356 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 700 ms
2019-08-22 10:47:45.739  INFO 7356 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2019-08-22 10:47:45.854  INFO 7356 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2019-08-22 10:47:45.855  INFO 7356 --- [           main] com.rebote.springboot.Application        : Started Application in 1.224 seconds (JVM running for 2.27)
收到ApplicationStartedEvent通知
收到applicationReadyEvent通知
```

+ 2.在配置文件中指定

如果您希望无论应用使用何种创建方式都能自动注册这些监听器，您都可以将 META-INF/spring.factories 文件添加到项目中，
并使用 org.springframework.context.ApplicationListener 属性键指向您的监听器

 ```java
org.springframework.context.ApplicationListener=\
  com.rebote.springboot.listener.ApplicationEnvironmentPreparedEventListener,\
  com.rebote.springboot.listener.ApplicationPreparedEventListener,\
  com.rebote.springboot.listener.ApplicationStartingEventListener
```


 # web环境
 
 SpringApplication 试图为您创建正确类型的 ApplicationContext。确定 WebApplicationType 的算法非常简单：
 
 + 如果存在 Spring MVC，则使用 AnnotationConfigServletWebServerApplicationContext
 
 + 如果 Spring MVC 不存在且存在 Spring WebFlux，则使用 AnnotationConfigReactiveWebServerApplicationContext
 
 +否则，使用 AnnotationConfigApplicationContext
 
 这意味着如果您在同一个应用程序中使用了 Spring MVC 和 Spring WebFlux 中的新 WebClient，默认情况下将使用 Spring MVC。您可以通过调用 setWebApplicationType(WebApplicationType) 修改默认行为。
 
 也可以调用 setApplicationContextClass(...) 来完全控制 ApplicationContext 类型。
 
  # 访问应用程序参数
  
  如何在应用程序中访问SpringApplication.run(​...)传入进来的参数？
  
  + 1.注入 org.springframework.boot.ApplicationArguments
   
   ```java
  @RestController
  public class CustomerController {
  
      @Autowired
      private ApplicationArguments applicationArguments;
  
      @RequestMapping("/getRunArguments")
      public void getRunArguments(){
          String[] array = applicationArguments.getSourceArgs();
          for (String arg:array) {
              System.out.println(arg);
          }
      }
  
  }
  ```
  
  # CommandLineRunner 和 ApplicationRunner 使用
 
 如果您需要在 SpringApplication 启动时运行一些代码，可以实现 ApplicationRunner 或者 CommandLineRunner 接口。这两个接口的
 工作方式是一样的，都提供了一个单独的 run 方法，它将在 SpringApplication.run(​...) 完成之前调用。
  
 允许实现多个ApplicationRunner 或者 CommandLineRunner 接口，可以通过order指定执行顺序
  
```java
 package com.rebote.springboot.component;
 
 import org.springframework.boot.ApplicationArguments;
 import org.springframework.boot.ApplicationRunner;
 import org.springframework.core.annotation.Order;
 import org.springframework.stereotype.Component;
 
 @Component
 @Order(103)
 public class MyApplicationRunner implements ApplicationRunner {
     public void run(ApplicationArguments args) throws Exception {
         for (String arg:args.getSourceArgs()) {
             System.out.println(arg);
         }
         System.out.println("MyApplicationRunner,我在 SpringApplication.run() 完成之前调用");
     }
 }

```
    
```java
package com.rebote.springboot.component;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(60)
public class MyApplicationRunner2 implements ApplicationRunner {
    public void run(ApplicationArguments args) throws Exception {
        for (String arg:args.getSourceArgs()) {
            System.out.println(arg);
        }
        System.out.println("MyApplicationRunner2,我在 SpringApplication.run() 完成之前调用");
    }
}

```
      
```java
package com.rebote.springboot.component;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(101)
public class MyCommandLineRunner implements CommandLineRunner {
    public void run(String... args) throws Exception {
        for (String arg:args) {
            System.out.println(arg);
        }
        System.out.println("MyCommandLineRunner,我在 SpringApplication.run() 完成之前调用");
    }
}
```
  