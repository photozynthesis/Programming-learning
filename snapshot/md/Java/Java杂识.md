# 一些Java杂识

[TOC]

## 1. BeanUtils

### 1.1 概述

BeanUtils工具由Apache软件基金组织编写，提供给我们使用，主要解决的问题是：把对象的属性数据封装到对象中。

**依赖于commons-loggin.jar，部分操作也会依赖于commons-collections.jar**，建议全部导入。

谷歌搜索即可下载。

JavaBean即标准的Java类，使用BeanUtils需要至少Bean满足以下条件：

- 类必须被public修饰
- 必须提供空参构造器
- 成员变量必须使用private修饰
- 提供公共的setter和getter方法

### 1.2 常用方法

|             方法              |               说明                |
| :---------------------------: | :-------------------------------: |
| populate(Object obj, Map map) | 将Map中的内容填充到传递的JavaBean |
|                               |                                   |
|                               |                                   |

说明：

- populate方法会对bean中存在的相关属性进行封装，若map中的键不存在于bean中，则该键不产生效果。



## 2. 项目设计

### 2.1 MVC架构

- M：

  Model，模型。

  完成具体的业务操作，如：查询数据库，封装对象。

- V：

  View，视图。

  展示数据。

- C：

  Controller，控制器。

  业务层，获取用户的输入、调用模型、将数据交给试图进行展示等。

- 优缺点：

  - 优点：

    1. 耦合性低，方便维护于协作开发。

    2. 重用性高。

  - 缺点：

    1. 使得项目的架构变得复杂，对开发人员的要求较高。

### 2.2 软件设计的三层架构

- 界面层（表示层/Web层）
  - 接收用户参数，封装数据，调用业务逻辑完成处理。
  - 转发JSP页面完成显示。
- 业务逻辑层（Service层）
  - 组合DAO层中简单的方法，形成复杂的业务逻辑。
- 数据访问层（DAO层）
  - Data Access Object
  - 定义了对数据/数据库基本的操作。



## 3. ThreadLocal

### 3.1 概述

- ThreadLocal是一个关于创建线程局部变量的类。
- 通常情况下，我们创建的变量是可以被任何一个线程访问并修改的。而使用ThreadLocal创建的变量只能被当前线程访问，其他线程则无法访问和修改。

### 3.2 使用

- 创建：

  ```java
  ThreadLocal<String> mStringThreadLocal = new ThreadLocal<>();
  ```

- set方法：

  ```java
  mStringThreadLocal.set("droidyue.com");
  ```

- get方法：

  ```java
  mStringThreadLocal.get();
  ```



## 4. spring-security 框架

### 4.1 概述

- spring-security 是 Spring 项目中用于提供安全认证服务的框架。
-  为基于J2EE企业应用软件提供了全面安全服务。

### 4.1 使用准备

- 导入包

  maven：pom.xml

  ```xml
  <!-- Spring Security -->
  <dependency>
      <groupId>org.springframework.security</groupId>
      <artifactId>spring-security-web</artifactId>
      <version>${spring.version}</version>
  </dependency>
  <dependency>
      <groupId>org.springframework.security</groupId>
      <artifactId>spring-security-config</artifactId>
      <version>${spring.version}</version>
  </dependency>
  ```

- 配置 spring-security 过滤器，指定配置文件，开启监听器（启动服务器时读取配置文件）

  web.xml

  ```xml
  <!-- 指示 spring 和 spring-security 配置文件的路径 -->
  <context-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>
          classpath:applicationContext.xml,classpath:spring-security.xml
      </param-value>
  </context-param>
  <!-- spring-security 拦截器，注意 id 不能改动 -->
  <filter>
      <filter-name>springSecurityFilterChain</filter-name>
      <filter-class>
          org.springframework.web.filter.DelegatingFilterProxy
      </filter-class>
  </filter>
  <filter-mapping>
      <filter-name>springSecurityFilterChain</filter-name>
      <url-pattern>/*</url-pattern>
  </filter-mapping>
  <!-- 监听器（可能已配置） -->
  <listener>
      <listener-class>
          org.springframework.web.context.ContextLoaderListener
      </listener-class>
  </listener>
  ```

### 4.2 已知功能示例

#### 4.2.1 登录与注销

- 配置文件：

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:security="http://www.springframework.org/schema/security"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://www.springframework.org/schema/beans
              http://www.springframework.org/schema/beans/spring-beans.xsd
              http://www.springframework.org/schema/security
              http://www.springframework.org/schema/security/spring-security.xsd">
  
      <!-- 配置不过滤的资源，可以是物理路径，也可以是虚拟路径 -->
      <security:http security="none" pattern="/css/**" />
      <security:http security="none" pattern="/plugins/**" />
      <security:http security="none" pattern="/img/**" />
      <security:http security="none" pattern="/admin/login" />
      <security:http security="none" pattern="/admin/login_failure" />
  
      <!-- 是否使用提供的登录页面 - 是否使用 SPEL 表达式 -->
      <security:http auto-config="true" use-expressions="false">
          <!-- 对哪些 url 进行权限控制，哪些角色可以访问（可用逗号分隔） -->
          <security:intercept-url pattern="/**" access="ROLE_USER" />
          <!-- 指定登录相关信息，如登陆页面，登录检查 url，成功 url，失败 url 等 -->
          <security:form-login login-page="/admin/login" 
                               login-processing-url="/login" 
                               default-target-url="/admin/main" 
                               authentication-failure-url="/admin/login_failure" 
                               username-parameter="username" 
                               password-parameter="password"/>
          <!-- 指定登出相关信息，如是否销毁 session、登出处理 url、登出成功页面等 -->
          <security:logout invalidate-session="true" 
                           logout-url="/logout" 
                           logout-success-url="/admin/login" />
          <!-- 防御 CSRF（跨站请求伪造） -->
          <security:csrf disabled="true" />
      </security:http>
  
      <security:authentication-manager>
          <!-- 指定 service，具体下文说明 -->
          <security:authentication-provider user-service-ref="userService">
              <!--<security:user-service>-->
                  <!--<security:user name="user" password="{noop}user" authorities="ROLE_USER" />-->
                  <!--<security:user name="admin" password="{noop}admin" authorities="ROLE_ADMIN" />-->
              <!--</security:user-service>-->
          </security:authentication-provider>
      </security:authentication-manager>
  
  </beans>
  ```

  **说明**：

  - 对于登录、登出的处理 url（login-processing-url、logout-url），无需创建控制器。只需将前端页面的表单提交地址设置为对应值即可。
  - 对于登录的 username-parameter 和 password-parameter，其作用是指定表单中对应用户名和密码的表单参数名。
  - 若用户密码未经过加密，需要在 password 的值前拼上 `{noop}`。

- 定义上文配置文件中提到的 userService（用户服务）。

  **说明**：

  - 实现提供的 UserDetailsService 接口，重写通过用户名加载 User 的方法。
  - 上述方法返回 UserDetails，是提供的接口。可以使用提供的实现类 User。
  - 可以自行通过 dao 从数据库查询出用户的相关信息，定义一个 User，将数据存入。

  **示例**：

  UserService.java（附带了角色查询）

  ```java
  @Service("userService")
  public class UserService implements UserDetailsService {
  
      @Autowired
      UserInfoDao userInfoDao;
  
      /**
       * 用于 Spring-Security 处理登录
       *
       * @param username
       * @return Spring 提供的 UserDetails 实现类 User，需要包含用户名、密码、状态信息、权限集合等。
       * @throws UsernameNotFoundException
       */
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          // 通过 username 查询出 UserInfo
          UserInfo userInfo = new UserInfo();
          userInfo.setUsername(username);
          userInfo = userInfoDao.findUserByUsername(userInfo);
          List<Role> list = userInfo.getRoles();
  
          // 通过结果获取角色列表
          List<SimpleGrantedAuthority> authorities = getAuthoritiesByRoles(list);
  
          // 创建 String-Security 提供的 User，并赋予查询出的参数
          User user = new User(userInfo.getUsername(), "{noop}" + userInfo.getPassword(), userInfo.getStatus() == 1 ? true : false, true, true, true, authorities);
          return user;
      }
  
      /**
       * 将用户的角色列表中的角色名存入 authority 列表
       * @param list
       * @return
       */
      public List<SimpleGrantedAuthority> getAuthoritiesByRoles(List<Role> list) {
          List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
          for (Role role : list) {
              authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
          }
          return authorities;
      }
  
  
  }
  ```

#### 4.2.2 使用 BCrypt 对密码进行加密

- **BCrypt 概述**：

  BCrypt 是一种不可逆的加密算法。

  用户表的密码通常使用 MD5 等不可逆算法加密后存储，为防止彩虹表破解更会先使用 一个特定的字符串（如域名）加密，然后再使用一个随机的 salt（盐值）加密。

  特定字符串是程序代码中固定的，salt 是每个密码单独随机，一般给用户表加一个字段单独存储，比 较麻烦。

  BCrypt 算法将 salt 随机并混入最终加密后的密码，验证时也无需单独提供之前的 salt，从而无需单独处理 salt 问题。 

- **对密码进行加密存储**：

  - 创建 BCryptPasswordEncoder 类对象（spring-security）；
  - 调用 encode 方法加密一个 CharSequence（String、StringBuffer 等的接口），返回 String；
  - 存储加密后的字符串。

  ```java
  BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
  seller.setPassword(encoder.encode(seller.getPassword()));
  sellerService.add(seller);
  ```

- **在 spring-security 中配置加密登录**：

  - 配置 BcryptPasswordEncoder 的 bean；
  - 在 authentication-manager 的 authentication-provider 中添加 password-encoder，引用刚配置的 bean。

  ```xml
  <!-- 加密类 bean -->
  <bean id="bCryptPasswordEncoder" class="org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder" />
  <!-- 认证管理器 -->
  <authentication-manager>
      <authentication-provider user-service-ref="userDetailsService">
          <password-encoder ref="bCryptPasswordEncoder" />
      </authentication-provider>
  </authentication-manager>
  ```

#### 4.2.3 获取用户的登录名

编写控制器，通过以下方法获取。

```java
String loginName = SecurityContextHolder.getContext().getAuthentication().getName();
```



## 5. Lombok

- 简化 POJO 类的插件包，可以通过简单的注解来消除 getter/setter、构造方法、toString 等。
- maven 导入依赖后，使用 @Data、@ToString 等注解即完成操作。



## 6. Swagger 接口开发

### 6.1 接口开发与 Swagger 概述

- Swagger 是描述和记录 Rest Api 的一个规范，指定了 Rest 服务接口的格式，包含 url、资源、方法等。
- 可以将 Swagger2 方便的集成进 Spring 工程中，根据代码方便地生成接口文档，并提供 html 访问。

### 6.2 整合到 spring boot 工程

- 创建 spring boot 工程。

  略。

- 引入 swagger2 的依赖。

  pom.xml

  ```xml
  <dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger2</artifactId>
      <version>2.9.2</version>
  </dependency>
  <dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger-ui</artifactId>
      <version>2.9.2</version>
  </dependency>
  ```

- 创建 swagger 配置类，并进行相关配置。

  SwaggerConfig.java（名称可更改）

  ```java
  // 此处注解不能少
  @Configuration
  @EnableSwagger2
  public class SwaggerConfig {
      
      /**
      * 在此指定要扫描的 controller 包，并加载自行编写的 ApiInfo
      */
      @Bean
      public Docket createDocket() {
          return new Docket(DocumentationType.SWAGGER_2)
              .apiInfo(getApiInfo())
              .select()
              .apis(RequestHandlerSelectors.basePackage("io.controller"))
              .paths(PathSelectors.any())
              .build();
      }
      
      // 自行编写的 ApiInfo
      private ApiInfo getApiInfo() {
          return new ApiInfoBuilder()
              .title("在此设置标题")
              .description("在此设置描述")
              .version("在此设置版本")
              .build();
      }
  }
  ```

  注意：**要使 spring 扫描当前类**，否则无法获取 Docket。可以在 spring boot 启动类上加上 ComponentScan 注解并使用 basePackageClasses 属性。

- 编写接口类（interface），添加相关注解（下文详细说明）。

  略。

- 编写 controller，实现接口类。

- 启动 spring 工程，访问 http://localhost:port/swagger-ui.html 即可查看接口文档。

  说明：html 文档的端口由 spring web 工程的端口决定。可以更改 server.port 属性来更改。

### 6.3 编写接口类

常用注解如下：

#### 6.3.1 类上的注解

- Api
  - 在 api 接口声明上，表示是一个接口 interface。
  - 常用参数：
    - description - 接口类的描述
- ApiModel
  - 在后台模型类声明上，表示是一个后端 model。

#### 6.3.2 方法上的注解

- ApiOperation
  - 在接口类的方法上，进行接口的描述。
  - 常用参数：
    - value - 描述
- ApiImplicitParams
  - 在接口类的方法上，描述接口的参数集合
  - 参数：多个 ApiImplicitParam 注解
- ApiImplicitParam
  - 在接口类的方法上或 ApiImplicitParams 注解中，描述单个参数。
  - 常用参数：
    - name - 接收参数名
    - value - 接收参数的描述
    - required - 是否必填
    - defaultValue - 默认值
    - dataType - 参数的数据类型
    - dataType - 查询参数类型，常用：
      - path - 以地址的形式提供参数（参数包含在 url 中）
      - query - url 中后跟参数完成自动映射赋值
      - body - 以流的形式提交
      - header - 请求头中提交
      - form - 以 form 表单的形式提交
- ApiResponses
  - 在接口类的方法上，描述该接口的各个响应码状态码及对应信息。包含多个 ApiResponse 注解。
- ApiResponse
  - 常用参数：
    - code - 状态码
    - message - 描述信息
- ApiIgnore
  - 表示忽略此 api，不显示文档。

#### 6.3.3 成员变量上的注解

- ApiModelProperty
  - 在模型类的成员变量上，对模型属性进行描述。