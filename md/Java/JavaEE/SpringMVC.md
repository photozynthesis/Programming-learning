 # SpringMVC

[TOC]

## 1. SpringMVC 概述

### 1.1 表现层的概述

 表现层，也就是 Web 层，负责接收客户端请求，向客户端响应结果。

表现层又包括**展示层**和**控制层**，控制层负责接收请求，展示层负责结果的展示。

### 1.2 MVC 模型概述

MVC 全称是 Model、View、Controller，即模型、视图、控制器。

- Model：数据模型，一般指 JavaBean 的类，用于数据封装。
- View：展示数据，一般指 JSP、HTML等。
- Controller：用于接收用户的请求，控制整个流程。

### 1.3 SpringMVC 概述

- 是一种基于 **Java 实现的 MVC 设计模型**的请求驱动类型的**轻量级 WEB 框架**。
- Spring MVC 属于 SpringFrameWork 的后续产品，已经融合在 Spring Web Flow 里面。Spring 框架提供 了构建 Web 应用程序的全功能 MVC 模块。
- 使用 Spring 可插入的 MVC 架构，从而在使用Spring进行WEB开发时，可以选择使用Spring的 SpringMVC框架或集成其他MVC开发框架，如Struts1(现在一般不用)，Struts2等。

### 1.4 SpringMVC 与 Struts2 的对比

- **共同点**：
  - 都是基于 MVC 模型编写的表现层框架。
  - 底层都是原始的 Servlet API。
  - 都通过一个核心控制器处理请求。
- **不同点**：
  - SpringMVC 的入口是 Servlet，而 Struts2 的入口是 filter。
  - Spring MVC 是基于方法设计的，而 Struts2 是基于类设计。Spring MVC 因此相比 Struts2 较快。
  - Spring MVC 使用更加简洁,同时还支持 JSR303, 处理 ajax 的请求更方便。
    - 通过 JSR303 注解可以方便地进行校验
  - Struts2 的 OGNL 表达式使页面的开发效率相比 Spring MVC 更高些，但执行效率并没有比 JSTL 提 升，尤其是 struts2 的表单标签，远没有 html 执行效率高。



## 2. 入门：IntelliJ IDEA 参考

### 2.1 创建项目并导入依赖坐标

- 新建 maven 项目，从模板创建，认准 maven-archetype-webapp 。

- pom.xml 配置依赖坐标。简略来说，包含如下内容：

  - spring-context
  - spring-web
  - spring-webmvc
  - servlet-api
  - jsp-api

  样板：

  ```xml
  <!-- 版本锁定 -->
  <properties>
      <spring.version>5.0.2.RELEASE</spring.version>
  </properties>
   
  <dependencies>
      <dependency>
          <groupId>org.springframework</groupId>            						<artifactId>spring-context</artifactId>            						<version>${spring.version}</version>
      </dependency>
      <dependency>
          <groupId>org.springframework</groupId>            						<artifactId>spring-web</artifactId>            							<version>${spring.version}</version>
      </dependency>
      <dependency>
          <groupId>org.springframework</groupId>            						<artifactId>spring-webmvc</artifactId>            						<version>${spring.version}</version>
      </dependency>
      <dependency>
          <groupId>javax.servlet</groupId>            							<artifactId>servlet-api</artifactId>            						<version>2.5</version>
          <scope>provided</scope>
      </dependency>
      <dependency>
          <groupId>javax.servlet.jsp</groupId>            						<artifactId>jsp-api</artifactId>            							<version>2.0</version>
          <scope>provided</scope>
      </dependency>
  </dependencies>
  ```

### 2.2 配置核心控制器 DispatcherServlet

DispatcherServlet 本质上是一个 Servlet，所以现在 在 web.xml 中配置。

简略信息：

- 完整类名为 org.springframework.web.servlet.DispatcherServlet
- 需要传递参数：springmvc 配置文件的位置。
- url-pattern设置为 /

web.xml：

```xml
...
<servlet>
	<servlet-name>dispatcherServlet</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
    	<param-name>contextConfigLocation</param-name>
        <param-value>classpath:springmvc.xml</param-value>
    </init-param>
    <!-- 优先级 -->
    <load-on-startup>1</load-on-startup>
</servlet>
<servlet-mapping>
	<servlet-name>dispatcherServlet</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>
```

### 2.3 配置 SpringMVC 的核心配置文件

在 classpath（resources）下编写 springmvc.xml。

简略信息：

- beans 标签需要引入 xmlns:beans、xmlns:mvc、xmlns:context 约束。
- 需要配置创建容器时要扫描的包，一般而言此处扫描控制器的包。
- 配置视图解析器，指定类，以及控制器返回结果的前后缀。
- 配置 spring 开启注解 mvc 的支持。

springmvc.xml：

```xml
<?xml version="1.0" encoding="UTF-8"?> <beans xmlns="http://www.springframework.org/schema/beans"    xmlns:mvc="http://www.springframework.org/schema/mvc"    xmlns:context="http://www.springframework.org/schema/context"    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd        http://www.springframework.org/schema/mvc        http://www.springframework.org/schema/mvc/spring-mvc.xsd        http://www.springframework.org/schema/context        http://www.springframework.org/schema/context/spring-context.xsd">
    
	<context:component-scan base-package="io.github.pz.controller" />
    
    <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    	<property name="prefix" value="/WEB-INF/pages/" />
        <property name="suffix" value=".jsp" />
    </bean>
    
    <mvc:annotation-driven />
    
</beans>
```

### 2.4 编写页面和控制器

- index.jsp（主页）：

  ```jsp
  ...
  <a href="${pageContext.request.contextPath}/hello">入门</a>
  ...
  ```

- HelloController（控制器）：

  ```java
  /**
  *	控制器
  */
  @Controller
  public class HelloController {
      @RequestMapping(path="/hello")
      public String toHello() {
          sout("mvc hello");
          return "success";
      }
  }
  ```

- success.jsp（跳转到的页面）：

  ...

### 2.5 说明

- 启动 Tomcat 后，由于配置了 load-on-startup，所以会创建 DispatcherServlet，并读取 springmvc.xml。
- 开启了 mvc 注解以及包扫描，所以 HelloController 被创建。
- index.jsp -> DispatcherServlet -> HelloController@RequestMapping -> ViewResolver -> success.jsp



## 3. Controller：@RequestMapping 和请求参数绑定

### 3.0 @RequestMapping

- 位置：控制器的类声明上或控制器中的方法上。
- 说明：
  - 若写在类声明上，即设置 URL 的一级访问目录，不设置则无一级访问目录。建议设置，利于模块化管理。
  - URL 需要以 `/` 开头。
  - 以下多个属性约束之间是与的关系，若请求不满足约束条件，将返回 4xx。
- 属性：
  - value/path：指定请求的 URL 。
  - method：指定请求的方式。
  - params：限制请求参数的条件，支持简单的表达式。例如
    - params = {"username"}，表示请求参数必须包含 username
    - （机制混乱）params = {"money!100"}，表示请求参数 money 不能为 100
  - headers：用于指定限制请求头的条件。

### 3.1 请求参数绑定：概述

- 请求参数绑定，即将页面**表单**请求的参数映射到**控制器**中的方法参数。

- 可以映射：
  - 基本类型和 String 类型
  - POJO（Bean）类型
  - 数组 & 集合

### 3.2 请求参数绑定：实现

#### 3.2.1 绑定到基本类型/String

- 说明：请求参数的 name 需要跟控制器方法的参数名称相同，区分大小写。

- 若前端传递数组，并绑定到后端数组，使用 `@RequestParam` 注解，并设置 value 为 `前端数组名[]`。

- 示例：

  ...?username=zhangsan&password=123

  ```java
  @Controller
  public class Mycontroller {
      @RequestMapping
      public String directToSuccess(String username, Integer password) {
          sout(username + password);
          return "success";
      }
  }
  ```

#### 3.2.2 绑定到 JavaBean

- 说明：

  - 若一个表单绑定到一个 JavaBean，请求参数的 name 需要跟 JavaBean 的属性名称一致。
  - 若绑定到的 JavaBean 中包含类型为其他 **Bean** 的属性，请求参数的 name 需要写成`引用类型属性名.属性名`。
  - 若绑定到的 JavaBean 中包含**集合**属性，请求参数的 name 需要为如下格式：
    - 单列集合：`（集合）属性名[索引]`，若该集合的元素类型为 Bean，需要写`（集合）属性名[索引].属性名`。
    - 双列集合：`（集合）属性名['key'].value的字段名`。

- 示例：

  - 结果包含 Bean：

    ...?username=zhangsan&password=123&address.province=anhui&address.city=anqing

    ```java
    @Controller
    public class Mycontroller {
        @RequestMapping
        public String directToSuccess(User user) {
            sout(User);
            return "success";
        }
    }
    ```

  - 结果包含 集合：

    form.html

    ```html
    <form action="account/updateAccount" method="POST">
        用户名：<input type="text" name="username" /><br />
        密码：<input type="password" name="password" /><br />
        账户1名称：<input type="text" name="accounts[0].aname" /><br />
        账户1金额：<input type="text" name="accounts[0].abal" /><br />
        账户2......
    </form>
    ```



## 6. filter 解决乱码

- 概述：

  通过配置 spring-web 提供的字符编码过滤器来实现。

- 处理 POST 请求的乱码：

  web.xml

  ```xml
  <filter>
  	<filter-name>CharacterEncodingFilter</filter-name>
      <filter-class>
      	org.springframework.web.filter.CharacterEncodingFilter
      </filter-class>
      <init-param>
      	<param-name>encoding</param-name>
          <param-value>UTF-8</param-value>
      </init-param>
      <!-- 开启过滤器 -->
      <init-param>
      	<param-name>forceEncoding</param-name>
          <param-value>true</param-value>
      </init-param>
  </filter>
  <filter-mapping>
  	<filter-name>CharacterEncodingFilter</filter-name>
      <!-- 过滤所有请求 -->
      <url-pattern>/*</url-pattern>
  </filter-mapping>
  ```

- 防止静态资源被拦截：

  springmvc.xml

  ```xml
  <!-- location为文件夹路径，mapping为要使用的资源的路径，**表示所有子文件和文件夹 -->
  <mvc:resources location="/css/" mapping="/css/**" />
  <mvc:resources location="/img/" mapping="/img/**" />
  
  <!-- 或者使用如下方式，放过所有静态资源 -->
  <mvc:default-servlet-handler />
  ```

- 处理 GET 请求的乱码：

  修改 Tomcat 的 server.xml 配置文件。

  - 找到 Connector 标签，添加属性`useBodyEncodingForURI="true"`。
  - ajax 请求乱码：添加属性`URIEncoding="UTF-8"`。



## 5. 类型转换器

- 概述：
  - 当页面的请求参数需要绑定到控制器中不同的类型时，例如一个日期字符串需要绑定到一个 Date 类型时，用到转换器。
  - 简要说明：编写转换器类实现 spring 的 Converter<S, T> 接口，并在 spring 的配置文件中进行注册。

- 示例：

  - MyStringToDateConverter.java

    ```java
    package io.github.photozynthesis.converter.MyStringToDateConverter;
    ...
    // Converter 的泛型第一个为源类型，第二个为转成的类型
    public class MyStringToDateConverter implements Converter<String,Date> {
    	@Override
        public Date convertStrToDate (String source) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = format.parse(Source);
                return date;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    ```

  - springmvc.xml

    ```xml
    <!-- 注册到转换服务 -->
    <bean id="converterService" class="org.springframework.context.support.ConversionServiceFactoryBean">
    	<property name="converters">
        	<array>
            	<bean class="io.github.pz.converter.MyStringToDateConverter" />
            </array>
        </property>
    </bean>
    
    <!-- 引用类型转换服务 -->
    <mvc:annotation-driven conversion-service="converterService" />
    ```



## 6. Controller：使用 Servlet API 对象作为参数

- SpringMVC 支持将原始的 Servlet API 对象作为控制器方法的参数。

- 支持如下原始 Servlet API 对象：

  - **HttpServletRequest**
  - **HttpServletResponse**
  - **HttpSession**
  - InputStream
  - OutputStream
  - Reader
  - Writer
  - Locale
  - java.security.Principal

- 示例：

  MyController.java

  ```java
  ...
      @RequestMapping("/testServletAPI")
      public String testServletAPI (HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
      session.setAttribute(...);
      ...
      return "success";
  }
  ```



## 7. Controller：常用注解

### 7.1 @RequestParam

- **作用**：

  用表单**请求参数**中指定名称的参数给**控制器的形参**赋值。

- **说明**：

  - 注解位置：控制器方法的形参之前（类型而非变量名之前）。
  - 属性：
    - value：请求参数名
    - required：请求参数是否必须包含该参数，默认为 true，表示不提供将会报错。
  - 特别说明：
    - 若请求参数为数组，请求参数名需要加上 `[]`。

- **示例**：

  MyController.java

  ```java
  ...
      @RequestMapping("/test")
      public String test (@RequestParam(value="name") String username), ...) {
      	sout(username);
      	...
          return "success";
  	}
  ```

### 7.2 RequestBody

- **作用**：

  用于获取 **POST** 方法请求的**请求体内容**，用以给**控制器的形参**赋值。

- **说明**：

  - 注解位置：控制器方法的形参之前（类型而非变量名之前）。
  - 属性：
    - required：是否必须有请求体，默认为true。为 true 时候 GET 方式会报错，而为 false 时会得到一个 null 值。

- **示例**：

  index.jsp

  ```jsp
  <form action="${pageContext.request.contextPath}/test/test2" method="POST">
      用户名：<input type="text" name="username">
      密码：<input type="password" name="password">
  </form>
  ```

  MyController.java

  ```java
  ...
      @RequestMapping("/test2")
      public String test (@RequestBody(required=true) String body) {
      	sout(body); // username=demo&password=demo
      	...
          return "success";
  	}
  ```

### 7.3 REST 风格 URL 和 @PathVariable

- **REST 风格 URL 概述**：

  - REST（Representational State Transfer），是一种网络系统设计风格，没有明确标准。
  - restful 特点：结构清晰、符合标准、易于理解、扩展方便。
  - **概括来说就是提交请求时，不使用 `?` + 参数的格式，而是使用统一的 url，通过提交方式的不同来处理不同的任务**。
  - 例如：/account/1    ->   GET：查询；DELETE：删除；PUT：更新；POST：新增。

- **@PathVariable**：

  - **位置**：

    位于 Controller 的形参之前。

  - **说明**：

    用于在 url 中定义占位符，并绑定到 Controller 的形参中。

    需要在 @RequestMapping 的 path 属性中定义占位符。

  - **属性**：

    - value/name：指定 url 中的占位符，给形参赋值。
    - required：是否必须提供占位符。

  - 示例：

    xx.jsp

    ```jsp
    <a href=".../demoPath/100">demo</a>
    ```

    MyController.java

    ```java
    @RequestMapping("/demoPath/{id}")
    public String demo(@PathVariable("id") Integer id) {
        sout(id);
        return "success";
    }
    ```

### 7.4 HiddenHttpMethodFilter 概述与示例

- **概述**：

  - 浏览器 form 表单只支持 GET 与 POST 请求，而 DELETE、PUT 等 method 并不支持。

  - Spring3.0 添加了一个过滤器，可以**将浏览器请求改为指定的请求方式**，发送给我们的控制器方法，使得支持 GET、POST、PUT 与 DELETE 请求。

- **使用步骤与示例**：

  - 在 web.xml 中配置该过滤器。

    web.xml

    ```xml
    ...
    <filter>  
        <filter-name>HiddenHttpMethodFilter</filter-name>  
        <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>  
    </filter>  
    <filter-mapping>  
        <filter-name>HiddenHttpMethodFilter</filter-name>  
        <servlet-name>spring</servlet-name>  
    </filter-mapping>
    <servlet>
        <servlet-name>spring</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:dispatcher.xml</param-value>
        </init-param>
    </servlet>
    <servlet-mapping>
        <servlet-name>spring</servlet-name>
        <url-pattern>*.html</url-pattern>
    </servlet-mapping>
    ...
    ```

  - 页面中的请求使用 POST，并使用隐藏域，定义 name="_method" 和 value="PUT..."

    ```html
    <form action="..." method="POST">
        <input ...>
        <input type="hidden" name="_method" value="DELETE">
    </form>
    ```

  - 在控制器的 @RequestMapping 中添加属性 method=RequestMethod.DELETE

    ```java
    @RequestMapping(value = "/test/{id}", method = RequestMethod.DELETE)
    public String demo(@PathVariable("id") Integer id) {
        sout(id);
        return "success";
    }
    ```

### 7.5 @RequestHeader

- **说明**：

  用于获取请求的消息头。

- **位置**：

  Controller 方法的形参之前。

- **属性**：

  - value：消息头名称。
  - required：是否必须有此消息头。

- 示例：

  MyController.java

  ```java
  @RequestMapping("/demo")
  public String demo(@RequestHeader(value="Accept-Language", required=false) String header_language) {
      sout(header_language);
      return "success";
  }
  ```

### 7.6 @CookieValue

- **说明**：

  将指定 cookie 名称的值传入 Controller 的参数。

- **位置**：

  Controller 方法的形参之前。

- **属性**：

  - value：cookie 的名称。
  - required：是否必须有此 cookie。

- 示例：

  MyController.java

  ```java
  @RequestMapping("/demo")
  public String demo(@CookieValue(value="JSESSIONID", required=false) String cookieValue) {
      sout(cookieValue);
      return "success";
  }
  ```

### 7.7 @ModelAttribute

- **说明**：

  - 于 SpringMVC 4.3 之后加入。
  - 一般用于给表单中没有没有传递的 Bean 中的字段赋值。
  - **出现在 Controller 中的非 @RequestMapping 方法上**：
    - 表示该方法在控制器方法执行之前执行（多个将按照方法名排序执行）。
    - **可以传入表单中的参数，（处理后）直接 return 表单的目标 Bean 对象：这样不用做其他操作控制器就能接收到该 Bean 对象**。
    - **可以在传入表单参数的同时传入一个 `Map(String, Bean)` ，（处理后）将 Bean 存入该 Map（自定义键）：这样第一该方法无需返回值，第二在控制器的方法形参上写上 `@ModelAttribute(value = "key")` 注解就可以导入之前存入的 Bean**。
  - **出现在 Controller 控制器方法形参上**：
    - 用于传入之前处理的数据，属性 value 可以为 POJO，也可以为 Map 中的 key。
    - 没什么多余的可以说了。

- **位置**：

  - Controller 中的非 @RequestMapping 方法上
  - Controller 中 @RequestMapping 方法的形参之前（需要已定义返回值为 null 并传入了 Map 的 @ModelAttribute 方法，用于指定 Map 中的 key 来给参数赋值）

- **属性**：

  - 我说过了。

- **示例**：

  - **没有返回值**：

    page.html

    - 只传入了 username

    MyController.java

    ```java
    @Controller
    public class MyController {
        
        @ModelAttribute
        public void getModel(String username, Map<String, User> map) {
            // 根据 username 查询出一个 User，对没传的字段赋值
            User user = getUserByName(username);
            // 存入 map
            map.put("user", user);
        }
        
        @RequestMapping("/demo")
        public String demo(@ModelAttribute("user") User user) {
            ...
            return "success";
        }
    }
    ```

  - **有返回值**：

    page.html

    - 只传入了 username

    MyController.java

    ```java
    @Controller
    public class MyController {
        
        @ModelAttribute
        public User getUser(String username) {
            // 根据 username 查询出一个 User，对没传的字段赋值
            User user = getUserByName(username);
            // 返回
            return user;
        }
        
        @RequestMapping("/demo")
        public String demo(User user) {
            ...
            return "success";
        }
    }
    ```

### 7.8 @SessionAttributes

- **说明**：
  - 使用 @SessionAttributes 可以方便的管理 session，用于在多个请求之间共享某个模型属性数据。
  - 在 @SessionAttributes 中配置的 attributes，会在控制器将其存入 Model 时自动存入 session，也就是可以通过 Model 对象，也可以通过 ModelAndView 对象使用。
  - 属性 value 和 type 没有任何关系。
- **位置**：

  - 控制器类定义上。
- **属性**：
  - value/name[]：任何符合这些 key 的 attribute在存入 Model 时，都会存一份到 session 。
  - type[]：任何符合这些类型的 attribute 在存入 Model 时，都会存一份到 session 。

- 示例：

  MyController.java

  ```java
  @Controller
  @SessionAttributes(value={"name"}, type={Integer.class})
  public class MyController {
      @RequestMapping("/demo")
      public String demo(Model model) {
          model.addAttribute("name", "zhangsan");
          return "success";
      }
  }
  ```



## 8. Controller：使用不同返回值

控制器方法的返回值可以设置为 String、void 或是 ModelAndView，也可以通过引入  jackson 包来返回 Bean 类型以响应 ajax 请求。具体如下：

### 8.1 String

- 概述

  返回 String ,即返回逻辑视图名，最终通过视图解析器解析为物理视图地址。

- 示例

  MyController.java

  ```java
  @RequestMapping("/demo")
  public String demo() {
      return "success";
  }
  ```

### 8.2 void

- 概述

  返回 void，即不通过视图解析器进行解析。此时一般使用 Servlet API 的参数进行请求转发、重定向等。

- 示例

  - 进行请求转发

    MyController.java

    ```java
    @RequestMapping("/demo")
    public void demo(HttpServletRequest req, HttpServletResponse resp) {
        req.getRequestDispatcher("/pages/success.jsp").forward(req, resp);
    }
    ```

  - 进行重定向

    MyController.java

    ```java
    @RequestMapping("/demo")
    public void demo(HttpServletRequest req, HttpServletResponse resp) {
        resp.sendRedirect("/pages/success.jsp");
    }
    ```

### 8.3 ModelAndView

- 概述

  - ModelAndView 是 Spring MVC 提供的对象，可以作为 Controller 的返回值。
  - 同时包含了模型信息（attributes）和视图信息，可以：
    - 通过 `addObject(String,Object)` 来向 requestScope 中添加附加信息，在 jsp 中可以直接通过 EL 表达式获取。
    - 通过 `setViewName(@Nullable String)` 来设置逻辑视图，交由视图解析器处理。

- 示例

  MyController.java

  ```java
  @RequestMapping("/demo")
  public ModelAndView demo() {
      ModelAndView mav = new ModelAndView();
      mav.addObject("username", "张三");
      mav.setViewName("success");
      return mav;
  }
  ```

  success.jsp

  ```jsp
  <p>
      ${requestScope.username}，你好
  </p>
  ```

### 8.4 返回 Bean 响应 ajax 请求

- 概述
  - 该注解位于 Controller 方法的 Bean 返回值之前，注解之后 Spring MVC 会通过 HttpMessageConverter 转换为 json/xml 等。
  - Spring MVC 默认用 MappingJacksonHttpMessageConverter 实现来转成 json ，所以**此处加入 jackson 的三个包（jackson-annotations、jackson-databind、jackson-core）**，具体见前面的笔记。
  - 导入的 jackson 版本需要高于 2.7.0 。

- 示例

  - page.jsp

    ```jsp
    <script>
        $(function() {
            $("#test").click(function() {
                $.ajax({
                    type:"post",
                    url:"${pageContext.request.contextPath}/demo",
                    contentType:"application/json;charset=utf-8",
                    data:"{'id':1,'name':'test','money':999.0}",
                    dataType:"json",
                    success:function(data) {
                        alert(data);
                    }
                })
            })
        })
    </script>
    ```

  - MyController.java

    ```java
    @RequestMapping("/demo")
    public @ResponseBody Account demo(@RequestBody Account account) {
        return account;
    }
    ```



## 9. Controller：转发与重定向

可以在返回 String 时在前面加上 `forward:` 或 `redirect:` 来定义请求转发与重定向。

### 9.1 请求转发

- 说明

  - 若直接返回逻辑视图，默认就是请求转发的方式。
  - 在返回的 String 前面加上 `forward:` 来定义请求转发。
  - 如果使用了 `forward:` 路径必须返回 url，不能写逻辑视图，例如 `forward:/pages/success.jsp`。

- 示例

  MyController.java

  ```java
  @RequestMapping("/demo")
  public String demo() {
      return "forward:/pages/success.jsp";
  }
  ```

### 9.2 重定向

- 说明

  - 在返回的 String 前面加上 `redirect:` 就是重定向。
  - 与请求转发相同，不能写逻辑视图，需要写 url。
  - 若重定向到 jsp 页面，jsp 页面不能在 WEB-INF 目录中，否则找不到。

- 示例：

  MyController.java

  ```java
  @RequestMapping("/demo")
  public String demo() {
      return "redirect:/pages/success.jsp";
  }
  ```



## 10. 文件上传

- **概述**

  - 需要导入 `commons-fileupload` 和 `commons-io` 两个包。
  - 需要配置文件解析器，具体见下文。
  - 页面中的表单需要方式为 POST、添加属性 `enctype="multipart/form-data"`，以及类型为 `file` 的 input 标签。
  - 控制器中需要设法控制文件不重复，并最终使用 `uploadFile.transferTo(newFile)` 来完成上传。

- **具体**

  - 导入包

    略。

  - 配置文件解析器

    springmvc.xml

    ```xml
    <!-- id固定，不能改动 -->
    <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
    	<!-- 设置最大上传大小，单位：Byte -->
        <property name="maxUploadSize">
        	<value>1048576</value>
        </property>
    </bean>
    ```

  - 页面

    demo.html

    ```html
    <form action="/fileUpload" method="POST" enctype="multipart/form-data">
        文件：<input type="file" name="uploadedFile" /><br>
        <input type="submit" value="上传" />
    </form>
    ```

  - 控制器

    FileUploadController.java

    ```java
    @Controller
    public class FileUploadController {
        @RequestMapping("/fileUpload")
        public String fileUpload(MultipartFile uploadedFile, HttpServletRequest req) throws Exception {
            // 1. 获取上传文件的文件名
            String srcFileName = uploadedFile.getOriginalFilename();
            // 2. 定义文件放置位置的 basePath
            String basePath = "/usr/filesUploaded";
            // 3. 判断 basePath 是否存在，不存在就创建目录
            File basePathFile = new File(basePath);
            if(!basePathFile.exists()) {
                basePathFile.mkdirs();
            }
            // 4. 拼接成新文件
            File targetFile = new File(basePath, srcFileName);
            // 5. 调用 MultipartFile 接口中的方法，进行复制
            uploadedFile.transferTo(targetFile);
            // 6. 页面跳转
            return "success";
        }
    }
    ```

- **跨服务器文件上传概述**

  - 负责文件存储的 Tomcat 服务器需要添加如下 jar 包：

    - commons-fileupload
    - commons-io
    - jersey-client
    - jersey-core

  - 需要设置文件解析器，见上文，略。

  - 需要编辑 web.xml。

  - 主服务器中控制器的核心代码：

    ```java
    ...
        public static final String FILE_SERVER_URL = "http://localhost:9090/.../uploads";
    	@RequestMapping("/demo")
    	...
            Client client = CLient.create();
    		WebResource resource = client.resource(FILE_SERVER_URL + fileName);
    		String result = resource.put(String.class, uploadFile.getBytes());
    ```



## 11. Spring MVC：异常处理

**概述**：

- Dao、Service 等层抛出的异常，最终会传到 Controller，之后到达前端控制器 DispatcherServlet，被交由异常处理器处理。
- Spring MVC 提供了简单的异常处理器实现：SimpleMappingExceptionResolver，对异常进行统一的处理（例如统一跳转到指定页面等）。
- 使用 Spring MVC，也可以自定义异常处理器。

### 11.1 使用自带的异常处理器

springmvc.xml

```xml
<!-- springmvc提供的简单异常处理器 -->
<bean class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">
     <!-- 定义默认的异常处理页面 -->
    <property name="defaultErrorView" value="/WEB-INF/jsp/error.jsp"/>
    <!-- 定义异常处理页面用来获取异常信息的变量名，也可不定义，默认名为exception --> 
    <property name="exceptionAttribute" value="ex"/>
    <!-- 定义需要特殊处理的异常，这是重要点 --> 
    <property name="exceptionMappings">
        <props>
            <prop key="ssm.exception.CustomException">/WEB-INF/jsp/custom_error.jsp</prop>
        </props>
        <!-- 还可以定义其他的自定义异常 -->
    </property>
</bean>
```

### 11.2 自定义异常处理器

参考步骤：

- **编写错误页面**

  error.html：略。

- **编写自定义异常类**

  MyException.java

  ```java
  public class MyException extends Exception {
      private String message;
      public MyException(String message) {
          this.message = message;
      }
      public String getMessage() {
          return message;
      }
  }
  ```

- **自定义异常处理器 及 配置**

  - 自定义异常处理器参考

    MyExceptionResolver.java

    ```java
    public class MyExceptionResolver implements HandlerExceptionResolver {
        @Override
        public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception e) {
            // 1. 打印异常信息
            e.printStackTrace();
            // 2. 创建自定义的异常
            MyException expt = null;
            // 3. 判断抛出的异常是否为自定义异常，否则赋予通用信息
            if(e instanceof MyException {
                expt = (MyException)e;
            }) else {
                expt = new MyException("系统发生了通用错误！");
            }
            // 4. 将异常中的信息赋予 ModelAndView，同时也赋予其错误页面逻辑视图
            ModelAndView mav = new ModelAndView();
            mav.addObject("message", expt.getMessage());
            mav.setViewName("error");
            // 5. 结束
            return mav;
        }
    }
    ```

  - 配置异常处理器

    springmvc.xml

    ```xml
    <bean id="handlerExceptionResolver" class="io.github.pz.exceptionResolvers.MyExceptionResolver" />
    ```


## 12. Spring MVC：拦截器

### 12.1 拦截器概述

- Spring MVC 的拦截器类似于 Servlet 中的 Filter，不过它只能做 Filter 能做的部分事情，而 Filter 可以做一切拦截器能做的事情。
- 拦截器用于对处理器进行预处理和后处理。
- 与 Filter 类似，拦截器也存在拦截器链，去程和回程都会按照往返的顺序来执行。
- 与 Filter 的区别：
  - Filter 是 Servlet 规范的一部分，一般 Web 工程都可以使用；而拦截器只有配置了 Spring MVC 框架的工程才能使用。
  - 过滤器在配置了 `/*` 之后，会对所有要访问的资源进行拦截；而**拦截器只会拦截对控制器的访问**，对于 jsp、html、js、css、img 等静态资源不拦截。

### 12.2 拦截器执行流程

- **拦截器链**

  > 客户端 --- MyHandlerInterceptor1 --- MyHandlerInterceptor2 --- ... --- Controller

- **定义拦截器** 

  自定义类实现 HandlerInterceptor，并重写其中三个方法。

  MyHandlerInterceptor.java

  ```java
  public class MyHandlerInterceptor implements HandlerInterceptor {
      
      @Override
      public boolean preHandler(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
          ...
          return true;
      }
      
      @Override
      public void postHandle(HttpServletRequest req, HttpServletResponse resp, Object handler, ModelAndView mav) throws Exception {
          ...
      }
      
      @Override
      public void afterCompletion(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception ex) throws Exception {
          ...
      }
      
  }
  ```

- **拦截器中的三个方法及执行时机**

  - **boolean preHandle()** 

    - 调用时机：

      去程，按照 springmvc.xml 配置文件中配置的顺序调用。

      若上游的拦截器返回了 false（没有放行），就不调用了。

    - 如何放行：

      返回 true 即放行，false 拦截。

      放行后执行下游拦截器中的 preHandle() 或执行控制器；拦截后执行上游拦截器中的 afterCompletion() 或直接向客户端返回结果。

    - 作用示例：

      访问验证之类。

  - **void postHandle()** 

    - 调用时机：

      Controller 执行完之后。

      按拦截器在配置文件中定义顺序的逆序调用。

    - 作用：

      在该方法中对用户请求 request 进行处理。

  - **void afterCompletion()** 

    - 调用时机：

      DispatcherServlet 完全处理完请求之后调用。

      即所有的 postHandle() 执行完后。

      按拦截器在配置文件中定义顺序的逆序调用。

    - 作用：

      资源清理之类。

### 12.3 配置拦截器

springmvc.xml

```xml
<mvc:interceptors>
	<mvc:interceptor>
        <!-- 拦截的路径 -->
    	<mvc:mapping path="/**" />
        <!-- 排除的路径 -->
        <mvc:exclude-mapping path="..." />
        <bean id="myHandlerInterceptor" class="io.github.pz.web.interceptor.MyHandlerInterceptor" />
    </mvc:interceptor>
    <mvc:interceptor>
    	...
    </mvc:interceptor>
    ...
</mvc:interceptors>
```

