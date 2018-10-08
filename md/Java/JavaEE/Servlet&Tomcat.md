# Servlet & Tomcat

[TOC]

## 1. web服务器软件概述

- **服务器与服务器软件的区别**：
  - 服务器：安装了服务器软件的计算机。
  - 服务器软件：接收用户的请求，处理请求并做出响应。
- **常见的Java相关的web服务器软件**：
  - WebLogic：Oracle公司的大型JavaEE服务器，支持所有JavaEE规范，收费。
  - WebSphere：IBM公司的大型JavaEE服务器，支持所有JavaEE规范，收费。
  - JBOSS：JBOSS公司的大型JavaEE服务器，支持所有JavaEE规范，收费。
  - Tomcat：Apache基金组织的中小型JavaEE服务器，支持部分JavaEE规范，如Servlet、JSP，开源、免费。



## 2. Tomcat的安装与访问

### 2.1 Windows下安装/启动步骤

1. 官方网站下载，地址：[http://tomcat.apache.org/](http://tomcat.apache.org/) 
2. 安装与卸载：
   - 安装：解压下载得到的压缩包即可。
   - 卸载：删除目录即可卸载。
3. 启动与关闭：
   - 启动：运行`bin`下的startup.bat或startup.sh
   - 关闭：
     - 运行shutdown.bat或shutdown.sh
     - ctrl + c
     - 点击`x` 

### 2.2 访问

在浏览器输入：

```
// 默认端口号
http://localhost:8080

// 使用80端口号（可以省略端口号）
http://localhost

// 访问webapps目录下的项目
http://localhost/demoProject
```

### 2.3 常见问题

- 启动时窗口直接闪退
  - 检查JAVA_HOME环境变量
- 启动报错：端口号被占用
  - 关闭占用端口号的Process：
    1. `netstat -ano`查看占用端口号的Process ID
    2. 任务管理器找到Process并关闭
  - 修改自身端口号：
    1. 找到conf/server.xml
    2. 打开并找到`Connector`标签，修改端口（推荐修改为80，这样可以省略端口号）。



## 3. Tomcat项目的部署

- **Tomcat目录下**：

  直接将项目文件夹放在webapps目录下即可。

  说明：

  1. 虚拟路径名为**项目文件夹**的名称。
  2. 可以将项目文件夹压缩为**war**包（zip）并放置在webapps目录下，会**自动**解压/删除。

- **其他路径**：

  - 修改`conf/server.xml`文件

    在`Host`标签体中新增：

    ```xml
    <Context docBase="<项目文件夹目录...>" path="/<虚拟路径...>" />
    ```

  - 在`conf/Catalina/localhost`新增任意名称xml文件

    内容：

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <Context docBase="<项目文件夹目录...>" />
    ```

    - xml文件名即为虚拟目录。



## 4. IntelliJ IDEA中使用Tomcat





## 5. Servlet概述/入门/配置

### 5.1 概述

- Servlet是两个单词`server`和`applet`的缩写。
- Servlet是运行在服务器端的小程序。
- Servlet是JavaEE中的一个接口，定义了Java类被浏览器访问的规则。

### 5.2 入门&配置

按照以下流程创建Servlet：

1. 使用IDE新建**JavaEE项目**。

2. 自定义类实现Servlet接口。

3. 实现接口中的方法。

4. 配置Servlet，有如下几种方式：

   - 修改`WEB-INF`目录下的`web.xml`文件：

     ```xml
     <!-- 在根标签中增加如下内容 -->
     <servlet>
         <!-- 自定义的名称 -->
     	<servlet-name>demoName</servlet-name>
         <!-- Servlet主类完整类名 -->
         <servlet-class>io.github.photozynthesis.DemoMainClass</servlet-class>
     </servlet>
     
     <servlet-mapping>
         <!-- 与上方一致 -->
     	<servlet-name>demoName</servlet-name>
         <!-- 虚拟路径 -->
         <url-pattern>/demo</url-pattern>
     </servlet-mapping>
     ```

     说明：

     ​	系统在收到请求后，会解析请求URL，获取访问Servlet的虚拟路径。之后到web.xml

     文件中查找是否有对应的< url-pattern >标签体内容，若有，则找到对应类执行对应方法。

   - 使用注释配置，**需要Servlet 3.0及以上**：

     在主类上加上`@WebServlet`注解：

     ```java
     // urlPatterns是必选属性，所以只定义该属性的时候可以只写一个双引号及中间的内容
     @WebServlet(name="AnnotationServlet" urlPatterns="/demoUrl")
     public class MyServlet implements Servlet{
         ...
     }
     ```



## 6. Servlet生命周期

Servlet的生命周期方法如下：

- 被创建：init()

  - 只执行一次

  - 默认情况下，第一次访问时，Servlet被创建

  - 可以在`web.xml`中指定创建时机：

    ```xml
    <servlet>
    	...
        <!-- 为负数时将在第一次访问时创建，为0或正整数时服务器启动即创建 -->
        <load-on-startup>5</load-on-startup>
    </servlet>
    ```

- 提供服务：service()

  - 每次访问Servlet时，该方法都会被调用

- 被销毁：destroy()

  - **服务器正常关闭**时，会销毁Servlet
  - 在Servlet销毁前执行，一般用于释放资源