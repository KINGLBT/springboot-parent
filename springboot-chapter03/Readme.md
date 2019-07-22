# @SpringBootApplication注解使用
@SpringBootApplication 注解相当于使用 @Configuration、@EnableAutoConfiguration 和 
@ComponentScan 及他们的默认属性


# SpringBoot Auto-configuration 注解使用

SpringBoot 会根据你添加的jar包依赖，自动配置Spring应用
使用SpringBoot的 Auto-configuration 需要在configuration其中一个类中，添加@EnableAutoConfiguration 或者 @SpringBootApplication
，但是官方推荐在主的configuration类上添加注解

# Developer Tools
Spring Boot 包含了一套工具，可以使应用开发体验更加愉快。spring-boot-devtools 模块可包含在任何项目中，
以提供额外的开发时（development-time）功能。要启用 devtools 支持，只需要将模块依赖添加到您的构建配置中即可：

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
``` 

## 注意
1、在IDEA里面，加完之后，可能发现不起作用，IDEA需要开启自动编译

   File-->Setting-->Build,Exception....-->Compoler-->Build project automatically 勾选

2、将 Maven 的依赖标记为可选或者在 Gradle 中使用 compileOnly 是防止您的项目被其他模块使用时 devtools 被应用到其它模块的最佳方法。

Maven依赖可传递，在引入spring-boot-devtools的时候，使用optional，可以防止项目被其他模块引用时候，spring-boot-devtools被应用到其他模块

3、当运行完全打包的应用时，开发者工具将会自动禁用。java -jar 方式启动，开发者工具将会自动禁用

## Property 默认值

Spring Boot 中支持的一些库使用了缓存来提高性能，换成在生产环境中非常有效，但是在开发过程中，会造成无法及时看到刚刚的更改。

我们知道，这些缓存通常可以在application.properties文件中配置。例如，Thymeleaf 提供了 spring.thymeleaf.cache 属性。但是，一般情况下，

无需去手动设置这些缓存为false,spring-boot-devtools 会自动应用合适的开发时（development-time）配置。

### spring-boot-devtools默认应用的缓存是什么？
参考https://github.com/spring-projects/spring-boot/blob/v2.1.6.RELEASE/spring-boot-project/spring-boot-devtools/src/main/java/org/springframework/boot/devtools/env/DevToolsPropertyDefaultsPostProcessor.java

```java
Map<String, Object> properties = new HashMap<>();
properties.put("spring.thymeleaf.cache", "false");
properties.put("spring.freemarker.cache", "false");
properties.put("spring.groovy.template.cache", "false");
properties.put("spring.mustache.cache", "false");
properties.put("server.servlet.session.persistent", "true");
properties.put("spring.h2.console.enabled", "true");
properties.put("spring.resources.cache.period", "0");
properties.put("spring.resources.chain.cache", "false");
properties.put("spring.template.provider.cache", "false");
properties.put("spring.mvc.log-resolved-exception", "true");
properties.put("server.error.include-stacktrace", "ALWAYS");
properties.put("server.servlet.jsp.init-parameters.development", "true");
properties.put("spring.reactor.stacktrace-mode.enabled", "true");
``` 


### 注意

 在使用spring-boot-devtools时，会自动将缓存配置为开发时配置，如果您不希望应用属性默认值，则可以在application.properties
中将spring.devtools.add-properties设置为false。


### 示例：spring.thymeleaf.cache 在加入开发者工具前后，缓存是否生效

从上面可以知道，thymeleaf默认情况是开启的，引入spring-boot-devtools之后，会将spring.thymeleaf.cache置为false


1、引入thymeleaf依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
``` 

2、创建controller以及show.html页面

```java
@GetMapping(value = "/test")
public ModelAndView test(HttpServletRequest req) {
    // UserEntity userEntity = getCurrentUser(req);
       ModelAndView mv = new ModelAndView();
       mv.setViewName("/show.html");
       return mv;
    }
``` 

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <table>
        <tr>
            <td>用户</td>
            <td>密码</td>
        </tr>
    </table>
</body>
</html>
``` 

3、application.properties配置文件中，引入thymeleaf配置

```json
spring.thymeleaf.mode=HTML
``` 

4、运行并修改 <td>用户</td> 为 <td>用户123</td>

发现页面由 用户 变成 用户123，说明引入spring-boot-devtools之后，会将spring.thymeleaf.cache置为false

5、禁用spring-boot-devtools的默认配置

在配置文件中添加以下内容

```json
spring.devtools.add-properties=false
``` 

6、运行并修改 <td>用户123</td> 为 <td>用户1234</td>
发现页面无变化，还是 用户123，说明spring-boot-devtools默认默认开发时间段缓存被禁用，使用thymeleaf的缓存



## 自动重启

使用spring-boot-devtools的应用程序会在类路径上的文件发生更改时自动重新启动。在IDE中工作时，这可能是一个有用的功能，因为它为代码更改提供了非常快速的反馈循环。
默认情况下，将监视类路径上指向文件夹的任何条目的更改。但是，某些资源（如静态资源和视图模板）无需重新启动应用程序。SpringBoot中，可以设置哪些资源变更，不重新启动

## Excluding Resources

某些资源在更改时不一定需要触发重启。例如，可以就地编辑Thymeleaf模板。默认情况下，更改/ META-INF / maven，/ META-INF / resources，
/ resources，/ static，/ public或/ templates中的资源不会触发重新启动，但会触发实时重新加载。如果要自定义这些排除项，
可以使用spring.devtools.restart.exclude属性。例如，要仅排除/ static和/ public，您需要设置以下属性：
spring.devtools.restart.exclude=static/**,public/**


### 示例：除了public目录下的文件修改不重启，其他都重启

1、在application.properties配置文件中添加以下内容
```json
spring.devtools.restart.exclude=public/**
``` 

2、修改static下的test.js，看是否重启


## 监听其他额外的路径

从上面可以知道，使用spring-boot-devtools的应用程序会在类路径上的文件发生更改时自动重新启动。那么对不在类路径中的文件进行更改时，
怎么才能重新启动或重新加载应用程序
```json
# 指定public目录下文件修改，都重启
spring.devtools.restart.additional-paths=public/**
# 排除public，其他都重启
spring.devtools.restart.exclude=public/**
``` 














