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
 
 