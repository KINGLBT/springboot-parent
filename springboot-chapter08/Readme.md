# 懒加载设置

## 使用懒加载的优缺点
以前Spring中的bean在应用启动的时候，会初始化。设置懒加载之后，bean在使用的时候，才会进行初始化。

优点是大大缩短了应用启动的时长。但是也带来一些弊端，第一点是： 延迟了bean问题的发现，假如bean有问题，
可能在启动的时候，项目没有问题，第一次使用的时候，才会出现问题。第二点是：如果设置懒加载，最好稍微调整
JVM内存，以防止内存不够。

## 懒加载设置方式

### 通过SpringApplication的setLazyInitialization设置是否懒加载

```java
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(Application.class);
    application.setLazyInitialization(Boolean.TRUE);
    application.run(args);
  }
  
  
}
```

### 通过 SpringApplicationBuilder的lazyInitialization属性设置

```java
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplicationBuilder builder =  new SpringApplicationBuilder();
        builder
            .sources(Application.class)
            .lazyInitialization(Boolean.TRUE)
            .run(args);
  }
}

```

### 通过在配置文件中设置

SpringBoot 2.0 版本以上，支持在配置文件中进行设置,

```yml
spring:
  main:
    lazy-initialization: true
```    


### 部分bean设置懒加载，通过注解@Lazy(true)设置


```java
@Service
@Lazy(false)
public class AnalyzerService2 {

  public AnalyzerService2() {
    System.out.println("我排除设置懒加载");
    throw new WannaStopException();
  }

}

```

# 自定义banner

## 文件方式

### 在resource下放入banner.txt文件或者banner.gif,banner.png,banner.jpg

图片会自动以ASCII展示

### 通过spring.banner.location或者 spring.banner.image.location 属性指定文件位置

## 通过编程方式打印banner

### 实现 Banner 接口

```java
public class SpringBootCustomerBanner implements Banner {

  private static final String[] BANNER = { "", " ZZZZZ Y   Y TTTTTT EEEE  CCC H  H \n"
      + "   Z   Y Y    TT   E    C    H  H \n" + "  Z     Y     TT   EEE  C    HHHH \n"
      + " Z      Y     TT   E    C    H  H \n" + "ZZZZZ   Y     TT   EEEE  CCC H  H \n" };

  private static final String SPRING_BOOT = " :: Spring Boot :: ";

  private static final int STRAP_LINE_SIZE = 42;

  @Override
  public void printBanner(Environment environment, Class<?> sourceClass, PrintStream printStream) {
    for (String line : BANNER) {
      printStream.println(line);
    }
    String version = SpringBootVersion.getVersion();
    version = (version != null) ? " (v" + version + ")" : "";
    StringBuilder padding = new StringBuilder();
    while (padding.length() < STRAP_LINE_SIZE - (version.length() + SPRING_BOOT.length())) {
      padding.append(" ");
    }

    printStream.println(AnsiOutput.toString(AnsiColor.GREEN, SPRING_BOOT, AnsiColor.DEFAULT, padding.toString(),
        AnsiStyle.FAINT, version));
    printStream.println();
  }
}
```

### 设置使用的Banner实现类

```java
@SpringBootApplication
public class Application {

  public static void main(String[] args) {

    SpringApplicationBuilder builder =  new SpringApplicationBuilder();
    builder
        .sources(Application.class)
        .lazyInitialization(Boolean.TRUE)
        .banner(new SpringBootCustomerBanner())  // 通过接口自定义banner
        .run(args);
  }

}
```

## 设置不启用banner

```java
@SpringBootApplication
public class Application {

  public static void main(String[] args) {

    SpringApplicationBuilder builder =  new SpringApplicationBuilder();
    builder
        .sources(Application.class)
        .lazyInitialization(Boolean.TRUE)
        .banner(new SpringBootCustomerBanner())
        .bannerMode(Banner.Mode.OFF)
        .run(args);
  }
}

```

Banner.Mode.OFF : 不启用

Banner.Mode.CONSOLE : 打印到控制台

Banner.Mode.LOG : 打印到日志文件中
