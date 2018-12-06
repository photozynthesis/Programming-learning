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
          <security:form-login login-page="/admin/login" login-processing-url="/login"
                               default-target-url="/admin/main" authentication-failure-url="/admin/login_failure"
                              username-parameter="username" password-parameter="password"/>
          <!-- 指定登出相关信息，如是否销毁 session、登出处理 url、登出成功页面等 -->
          <security:logout invalidate-session="true" logout-url="/logout" logout-success-url="/admin/login" />
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