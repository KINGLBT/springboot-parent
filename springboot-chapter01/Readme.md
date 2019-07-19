# SpringBoot 使用

## SpringBoot 2.2.0 环境要求
+  JDK8以上
+  Spring Framework 5.2.0及以上
+  构建工具 Maven3.3以上、Gradle5.X版本

## SpringBoot自带继承

```xml
<parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.0.BUILD-SNAPSHOT</version>
</parent>
``` 


## SpringBoot项目使用自己的继承

```xml
<dependencyManagement>
        <dependencies>
            <!-- Override Spring Data release train provided by Spring Boot
            <dependency>
                <groupId>org.springframework.data</groupId>
                <artifactId>spring-data-releasetrain</artifactId>
                <version>Fowler-SR2</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            -->
            <dependency>
                <!-- Import dependency management from Spring Boot -->
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>2.1.6.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
</dependencyManagement>
``` 

## SpringBoot对于非RELEASE版本的，需要额外添加仓库

```xml
<repositories>
        <repository>
            <id>spring-snapshots</id>
            <url>https://repo.spring.io/snapshot</url>
            <snapshots><enabled>true</enabled></snapshots>
        </repository>
        <repository>
            <id>spring-milestones</id>
            <url>https://repo.spring.io/milestone</url>
        </repository>
</repositories>
<pluginRepositories>
        <pluginRepository>
            <id>spring-snapshots</id>
            <url>https://repo.spring.io/snapshot</url>
        </pluginRepository>
        <pluginRepository>
            <id>spring-milestones</id>
            <url>https://repo.spring.io/milestone</url>
        </pluginRepository>
</pluginRepositories>
``` 


## SpringBoot运行方式

+ run as
+ mvn spring-boot:run
+ jar -jar 可执行jar包

## SpringBoot 可执行jar包，必须添加以下依赖，否则会报找不到主类

```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
``` 
