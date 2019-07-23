# 自定义启动banner

启动banner可以更改，有两种方式，一种是txt文件，一种是图片

## txt文件

1、在classpath下，添加banner.txt文件，启动的时候，会打印banner.txt里面的内容
2、在配置文件中通过 spring.banner.location 可以指定banner.txt的路径
3、对于非UTF-8格式的文件，可以设置编码，默认情况是UTF-8
4、spring.banner.location设置了启动banner，优先于直接在classpath下，添加banner.txt

## 图片文件
SpringBoot 在启动的时候，会自动将图片转化成ASCII文本，并打印出来

1、在classpath下，添加banner.gif,banner.jpg或者banner.png
2、在配置文件中通过 spring.banner.image.location 可以指定banner图片文件

## Banner中可用变量

 | 变量        | 描述    |
    | --------   | -----:   |
    | ${application.version}        | 应用的版本号,定义在 MANIFEST.MF中. 例如： Implementation-Version: 1.0 将会打印 1.0.      |  
    | ${application.formatted-version}        | $1      |  
    | ${spring-boot.version}        | $1      | 
    | ${spring-boot.formatted-version}        | $1      | 
    | ${Ansi.NAME} (or ${AnsiColor.NAME}, ${AnsiBackground.NAME}, ${AnsiStyle.NAME})        | $1      |、
    | ${application.title}        | $1      |     