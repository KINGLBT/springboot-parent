# SpringBoot Auto-configuration 注解使用

SpringBoot 会根据你添加的jar包依赖，自动配置Spring应用
使用SpringBoot的 Auto-configuration 需要在configuration其中一个类中，添加@EnableAutoConfiguration 或者 @SpringBootApplication
，但是官方推荐在主的configuration类上添加注解

## configuration类介绍

