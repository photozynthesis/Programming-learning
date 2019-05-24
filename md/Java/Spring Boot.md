# Spring Boot

[TOC]

## 1. Spring Boot 概述

### 1.1 概述

- Spring Boot 不是对 Spring 框架在功能上的增强，而是提供了一种快速使用 Spring 的方式。
- 一定程度上解决了 Spring 配置文件过多的问题。
- 提供了多个依赖间兼容性问题的解决方案。

### 1.2 Spring Boot 核心功能

- 起步依赖：将具备某种功能的包的坐标打包到一起，并提供一些默认的功能。
- 自动配置：在程序运行时，自动决定用哪个配置。



## 2. Spring Boot 快速入门

1. **创建普通 maven 工程**。

   略。

2. **使工程继承 Spring Boot 的起步依赖工程**。

   - spring-boot-starter-parent

   ```xml
   <parent>
   	<groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-parent</artifactId>
       <version>2.0.1.RELEASE</version> 
   </parent>
   ```

3. **引入对应功能的启动依赖**，如这里引入 web 启动依赖。

   - [demo] spring-boot-starter-web

   ```xml
   <dependencies>
   	<dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-web</artifactId> 
       </dependency>
   </dependencies>
   ```

4. **编写 Spring Boot 引导类**。

   ```java
   @SpringBootApplication
   public class MySpringBootApplication {
       public static void main(String[] args) {
           SpringApplication.run(MySpringBootApplication.class);
       }
   }
   ```

5. **[demo] 编写控制器，并测试**。

   - 编写 DemoController 。

   ```java
   @Controller
   public class DemoController {
       @RequestMapping("/demo")
       @ResponseBody
       public String demo() {
           return "hello";
       }
   }
   ```

   - 运行 MySpringBootApplication 中的 main 方法，并根据控制台提示通过浏览器访问给定路径。



## 3. Spring Boot 配置文件

### 3.1 概述

- Spring Boot 是基于约定的，意味着很多配置都有默认值。
- 可以使用配置文件，来更改默认配置。
- 配置文件中的配置还可以在 java 文件中使用。
- 参考配置地址：https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html#common-application-properties

### 3.2 可用的配置方式及文件类型

- 常用的配置方式（按照优先级）：

  - 命令行参数

    ```shell
    java -jar xxx.jar --spring.datasource.name=root --spring.datasource.password=root
    ```

  - ./config 下的配置文件

  - ./ 下的配置文件

  - classpath:/config 下的配置文件

  - classpath:/ 下的配置文件

- 可用的配置文件类型：

  - yml 配置文件：application.yml
  - properties 配置文件：application.properties

### 3.3 存在多个配置文件时的问题

- 多个配置文件中不冲突的配置项会合并生效。
- 有冲突的配置项，按照上述优先级进行覆盖。
- yml 配置文件与 properties 配置文件中配置项的冲突，yml 的优先覆盖 properties 的。
- 更多可以参考源码。

### 3.4 yml 配置文件的基本语法

- yml/yaml（YAML Aint Markup Language）

- 格式：key: value

  ```yaml
  name: zhangsan
  ```

- 对象/Map 数据：

  ```yaml
  # 对应 properties 文件为如下格式
  # person.name = zhangsan
  # person.age = 20
  person:
    name: zhangsan
    age: 20
  ```

- 数组/集合 数据：

  ```yaml
  # 注意空格不能少
  city:
    - beijing
    - nanjing
    - xiamen
    
  # 集合中存储了对象/Map
  student:
    - name: zhangsan
      age: 20
    - name: lisi
      age: 22
  ```



## 4. Spring Boot 整合 MyBatis

### 4.1 一般步骤

1. **引入启动依赖及数据库驱动坐标**

   - mybatis-spring-boot-starter（由 MyBatis 提供）
   - mysql-connector-java

   ```xml
   <dependency>
   	<groupId>org.mybatis.spring.boot</groupId>
       <artifactId>mybatis-spring-boot-starter</artifactId>
       <version>1.1.1</version>
   </dependency>
   <dependency>
   	<groupId>mysql</groupId>
       <artifactId>mysql-connector-java</artifactId>
   </dependency>
   ```

2. **配置文件添加数据库连接信息**

   application.yml

   ```yaml
   # DB config
   spring:
     datasource:
       driverClassName: com.mysql.cj.jdbc.driver
       url: jabc:mysql://localhost:3306/test?...
       username: root
       password: root
   ```

3. **配置 MyBatis 相关信息**

   application.yml

   ```yaml
   # spring mybatis
   mybatis:
     # pojo 别名扫描
     type-aliases-package: io.github.photozynthesis.pojo
     # 映射器扫描
     mapper-locations: classpath:io.github.photozynthesis/*Mapper.xml
   ```

4. **开始 MyBatis**

   略。

### 4.2 重要说明

- 在 mybatis-spring 中，sqlSession 由 template 控制。每次访问数据时，spring 会申请一个新的 sqlSession，所以此处**一级缓存失效**。



## 5. Spring Boot 整合 Junit

1. **添加启动依赖**

   - spring-boot-starter-test

2. **开始测试**

   ```java
   @Runwith(SpringRunner.class)
   @SpringBootTest(classes = MySpringBootApplication.class)
   public class Test {
       ...
   }
   ```

   - 需要指定启动类
   - @Runwith 用 SpringRunner 或之前的 SpringJunit4ClassRunner 皆可。



## 6. Spring Boot 整合 Spring Data JPA

1. **添加启动依赖及数据库驱动**

   - spring-boot-starter-data-jpa
   - mysql-connector-java

2. **添加配置**

   application.yaml

   ```yaml
   spring:
     # DB config
     datasource:
       driverClassName: com.mysql.cj.jdbc.driver
       url: jabc:mysql://localhost:3306/test?...
       username: root
       password: root
     # JPA config
     jpa:
       database: MySQL
       show-sql: true
       generate-ddl: true
       hibernate:
         ddl-auto: update
         naming_strategy: org.hibernate.cfg.ImprovedNamingStrategy
   ```

3. **开始使用**

   略。



## 7. Spring Boot 整合 Spring Data Redis

1. **添加启动依赖**

   - spring-boot-starter-data-redis

2. **配置连接信息**

   ```yaml
   # Redis config
   spring:
     redis:
       host: localhost
       port: 6379
   ```

3. **开始使用**

   ```java
   @RunWith(...)
   @SpringBootTest(...)
   public class Test {
       @Autowired
       private RedisTemplate<String, String> redisTemplate;
   }
   ```



## 8. Spring Boot 开启热部署

### 8.1 概述

- 修改代码后，不需要手动重新启动工程 代码即可生效。

### 8.2 使用步骤

- 引入依赖
  - spring-boot-devtools

- IntelliJ IDEA 设置
  - settings - Compiler - Build poject automatically
  - shift + ctrl + alt + / - Registry - compiler.automake.allow.when.app.running



## 9. Spring Boot 部分源码解析

### 9.1 起步依赖

- 起步依赖 spring-boot-starter-parent 继承了父工程 spring-boot-dependencies 。
- Spring-boot-dependencies 的 pom 文件中定义了大量包的依赖，确定了其版本。

### 9.2 spring-boot-starter-web

- 该工程的 pom 定义了大量跟 web 工程有关的依赖，确定了依赖版本。

### 9.3 自动配置

- @SpringBootApplication 注解的源码中包含 @SpringBootConfiguration、@EnableAutoConfiguration、@ComponentScan 注解。
- @SpringBootConfiguration 包含了 @Configuration，表名使用该注解的类为一个 Spring 配置类。
- @EnableAutoConfiguration 中导入了 AutoConfigurationImportSelector 类，该类的代码中定义了从 META-INF/spring.factories 文件中读取指定类名称的列表。

- META-INF/spring.factories 文件中的类中通过 @EnableConfigurationProperties 引入了 xxxProperties 配置类。
- xxxProperties 配置类中的 prefix 和 字段与之前的配置项相匹配。

