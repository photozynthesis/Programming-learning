# Spring Framework

[TOC]

## 1. Spring 概述

### 1.1 Spring 概述

- Spring是分层的 Java SE/EE应用 full-stack 轻量级开源框架，以 **IoC（Inverse Of Control： 反转控制）**和 **AOP（Aspect Oriented Programming：面向切面编程）**为内核。
- 提供了展现层 Spring MVC 和持久层 Spring JDBC 以及业务层事务管理等众多的企业级应用技术。
- 还能整合开源世界众多 著名的第三方框架和类库，逐渐成为使用最多的Java EE 企业应用开源框架。

### 1.2 Spring 的优势

- **方便解耦，简化开发** 

  通过 Spring提供的 IoC容器，可以将对象间的依赖关系交由 Spring进行控制，避免硬编码所造 成的过度程序耦合。

- **AOP 编程支持** 

  通过 Spring的 AOP 功能，方便进行面向切面的编程，许多不容易用传统OOP 实现的功能可以通过 AOP 轻松应付。 

- **声明式事务的支持 ** 

  通过声明式方式灵活的进行事务的管理， 提高开发效率和质量。 

- **方便程序的测试 ** 

  可以用非容器依赖的编程方式进行几乎所有的测试工作。 

- **方便集成各种优秀框架 ** 

  Spring可以降低各种框架的使用难度，提供了对各种优秀框架（Struts、Hibernate、Hessian、Quartz 等）的直接支持。 

- **降低 JavaEE API的使用难度 ** 

  Spring对 JavaEE API（如 JDBC、JavaMail、远程调用等）进行了薄薄的封装层，使这些 API 的 使用难度大为降低。 



## 2. IOC：概述

### 2.1 IOC 的概述

IOC（Inversion Of Control），即**控制反转**。

- 把创建对象的权力交给框架，是框架的重要特征。
- 以此来降低程序（类）之间的耦合。
- 包括 DI（依赖注入）和 依赖查找。

### 2.2 ''耦合''的概述

- 耦合性(Coupling)，也叫耦合度，是对模块间关联程度的度量。
- 耦合性的强弱取决于多种因素，如模块间接口的复杂性、调用模块的方式等。
- 对象之间的**耦合越高**，**维护成本越高**。因此对象的设计应使类和构件之间的耦合最小。



## 3. IOC：XML实现

### 3.1. 准备工作

- **导入包**：

  - 下载：

    https://repo.spring.io/libs-release-local/org/springframework/spring/

  - Maven：

    ```xml
    <!-- https://mvnrepository.com/artifact/org.springframework/spring-context -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>5.1.1.RELEASE</version>
    </dependency>
    ```

### 3.2 XML 中的配置

#### 3.2.1 创建 xml 文件

- 在 src/resources根路径 下创建**任意名称**的 xml 文件。

- 在xml中添加头部与约束。约束可以在官网 ctrl + F 搜索 `configuration`或`xml`查找，示例如下：

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://www.springframework.org/schema/beans
          http://www.springframework.org/schema/beans/spring-beans.xsd">
  	...
  </beans>
  ```

#### 3.2.2 bean 标签及其属性

bean 标签位于 Spring 容器 xml 文件的根标签`beans`下，用于配置对象来让 Spring 创建。

- **重要属性**：

  - id：设置 bean 在容器中的唯一标识。
  - class：类的完整类名，用于反射创建对象。
  - scope：指定对象的作用范围/单例多例等。可取值：
    - **singleton**：单例对象，默认值。
    - **prototype**：多例对象。
    - request：WEB项目中，将创建的 Bean 存入 request 域中。
    - session：WEB项目中，将创建的 Bean 存入 session 域中。
    - global session：应用于 Portlet 环境的 WEB 项目中，将创建的对象存入多台服务器的共享域中。若不是 Portlet 环境则相当于 session 。
  - init-method：指定初始化之后的回调方法，不能有返回值。
  - destroy-method：指定销毁之前的回调方法，不能有返回值。

- **示例**：

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://www.springframework.org/schema/beans
          http://www.springframework.org/schema/beans/spring-beans.xsd">
  	<bean id="userDao" class="io.github.photozynthesis.dao.UserDao" scope="prototype" />
  </beans>
  ```

#### 3.2.3 Spring 容器中 Bean 的生命周期

- **单例对象**：
  - 一个应用只存在一个的对象。
  - 与容器共存，仅当容器销毁时才销毁。

- **多例对象**：
  - 每次从容器获取时，都会重新创建对象实例。
  - 由 Java 垃圾回收器管理，一般长时间不使用即被回收。

#### 3.2.4 Spring 容器实例化 Bean 的三种方式

- **构造方法实例化**：

  - bean 标签使用 id 和对应 class 属性。

    - id：唯一标识
    - class：全路径类名

  - 默认使用无参构造方法，没有无参构造方法将会报错；有参相关参考依赖注入。

  - 示例：

    ```xml
    <beans ......>
    	<bean id="userDao" class="io.github.photozynthesis.dao.UserDao" />
    </beans>
    ```

- **普通工厂实例化**：

  - 需要先将工厂交由 spring 管理，再指定 bean 的工厂 id 和 对应方法。

    - id：唯一标识
    - factory-bean：工厂 bean 的 id
    - factory-method：工厂中实例化该对象的方法

  - 示例：

    ```xml
    <beans ......>
    	<bean id="instanceFactory" class="io.github.photozynthesis.factory.InstanceFactory" />
        <bean id="instance" factory-bean="instanceFactory" factory-method="createInstance" />
    </beans>
    ```

- **静态工厂实例化**：

  - 使用静态工厂中的静态方法创建对象，并将对象存入 spring 容器。

    - id：唯一标识
    - class：静态工厂的完整类名
    - factory-method：静态工厂中产生对象的静态方法

  - 示例：

    ```xml
    <beans ......>
    	<bean id="instance" class="io.github.photozynthesis.factory.StaticFactory" factory-method="createInstance" />
    </beans>
    ```



### 4. 容器相关 Java 类

- **常用类/接口体系结构**：

  > |--- interface **BeanFactory** 
  >
  > ​	| --- interface **ApplicationContext** 
  >
  > ​		| --- class **ClassPathXmlApplicationContext** 
  >
  > ​		| --- class **FileSystemXmlApplicationContext** 
  >
  > ​		| --- class **AnnotationConfigApplicationContext** 

- **接口 BeanFactory**：

  - Spring 容器的顶层接口。
  - 需要使用实例时才创建对象。

- **接口 ApplicationContext**：

  - 默认只要一读取配置文件，即创建对象。

- **类 ClassPathXmlApplicationContext**：

  - 从类的根路径下加载配置文件，推荐使用。

- **类 FileSystemXmlApplicationContext**：

  - 可以从磁盘任意位置加载配置文件。

- **类 AnnotationConfigApplicationContext**：

  - 读取注解配置容器的对象（配置 Java 类）。



## 5. DI：概述

- DI（Dependency Injection），即依赖注入，是 IOC 的具体实现。
- 依赖注入是将所需要的依赖传递给将使用的从属对象。
- 此处的依赖注入通过配置的方式，由 spring 完成，并非手动注入。



## 6. DI：实现

### 6.1 通过构造方法注入

- 说明：

  - bean 中需要有 具有对应参数列表的构造方法。

- 使用的子标签及其属性：

  `constructor-arg`：每一个子标签对应一个参数，常用属性如下：

  - 指定给哪个参数赋值，通常是第一个属性：
    - index：指定参数在构造方法参数列表中对应的索引，从 0 开始
    - type：指定参数在构造方法中的数据类型
    - name：指定参数在构造方法中的名称，一般多使用这个
  - 指定该参数所赋的值，通常是第二个属性：
    - value：可以赋基本类型和 String
    - ref：可以赋其他 bean 类型，不过需要在容器配置过 bean，此处输入 bean 的 id

- 示例：

  ```xml
  <beans ......>
  	<bean id="user" class="io.github.photozynthesis.domain.User">
      	<constructor-arg name="name" value="张三" />
          <constructor-arg name="age" value="20" />
          <constructor-arg name="birthday" ref="now" />
      </bean>
      <bean id="now" class="java.util.Date">
      	...
      </bean>
  </beans>
  ```

### 6.2 通过 set 方法注入

- 说明：

  - bean 中需要有对应 set 方法

- 使用的子标签及其属性：

  `property`：

  - 指定给哪个参数赋值，通常是第一个属性：
    - name：类中 set 方法后面的部分，如`setName()` -> `name` 
  - 指定该参数所赋的值，通常是第二个属性：
    - value：可以赋基本类型和 String
    - ref：可以赋其他 bean 类型，不过需要在容器配置过 bean，此处输入 bean 的 id

- 示例：

  ```xml
  <beans ......>
  	<bean id="user" class="io.github.photozynthesis.domain.User">
      	<property name="name" value="张三" />
          <property name="age" value="20" />
          <property name="birthday" ref="now" />
      </bean>
      <bean id="now" class="java.util.Date">
      	...
      </bean>
  </beans>
  ```

### 6.3 *通过 p名称空间 注入

- 说明：

  - 不常用
  - 本质上也是通过 set 方法注入，只是将内容的位置由 bean 的子标签转移到了 bean 的属性。
  - beans 标签需要加一条约束。

- 示例：

  ```xml
  <!-- 添加的约束为第二条 -->
  <beans xmlns="http://www.springframework.org/schema/beans" 					   xmlns:p="http://www.springframework.org/schema/p"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation=" http://www.springframework.org/schema/beans            http://www.springframework.org/schema/beans/spring-beans.xsd">
  	<bean id="user" class="io.github.photozynthesis.domain.User" p:name="zhangsan" p:age="20" p:birthday-ref="now" />
      <bean id="now" class="java.util.Date" />
  </beans>
  ```

### 6.4 注入数组与集合

- 说明：

  - 针对 bean 的属性为数组或集合的情况
  - 此处介绍：
    - List结构：array，list，set
    - Map结构：map，entry，props，prop
  - 相同结构的对应标签可以互换，具体见下方示例。

- 示例：

  已知存在 bean 类，其中有数组、单列集合、双列集合等五个属性。

  xml：

  ```xml
  <beans ......>
  	<bean id="myCollections" class="io.github.photozynthesis.domain.MyCollections">
          <!-- 注入数组，标签已替换 -->
      	<property name="myStrArray">
          	<set>
              	<value>AAA</value>
                  <value>BBB</value>
                  <value>CCC</value>
              </set>
          </property>
          <!-- 注入List集合，标签已替换 -->
      	<property name="myList">
          	<array>
              	<value>AAA</value>
                  <value>BBB</value>
                  <value>CCC</value>
              </array>
          </property>
          <!-- 注入Set集合，标签已替换 -->
      	<property name="mySet">
          	<list>
              	<value>AAA</value>
                  <value>BBB</value>
                  <value>CCC</value>
              </list>
          </property>
          <!-- 注入Map集合 -->
          <property name="myMap">
          	<map>
              	<entry key="key1">
                  	<value>value1</value>
                  </entry>
                  <entry key="key2" value="value2" />
              </map>
          </property>
          <!-- 注入Properties数据 -->
          <property name="myProp">
              <prop key="key1" value="value1" />
          	<prop key="key2" value="value2" />
          </property>
      </bean>
  </beans>
  ```



## 7. IOC/DI：注解实现

### 7.1 准备工作

- **导入包**：	

  在xml配置的基础上，需要导入`spring-aop`包，不过一般导入`spring-context`即已自动导入。

- **创建 xml 配置文件**：

  若要使用注解配置，相比于纯 xml 配置需要在 beans 标签上多加一条约束，新加的约束是 context：

  > <beans xmlns="http://www.springframework.org/schema/beans" 
  >
  > **xmlns:context="http://www.springframework.org/schema/context"** 
  >
  > xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
  >
  > xsi:schemaLocation="http://www.springframework.org/schema/beans 
  >
  > http://www.springframework.org/schema/beans/spring-beans.xsd 
  >
  > **http://www.springframework.org/schema/context** 
  >
  > **http://www.springframework.org/schema/context/spring-context.xsd**">
  > ​	...
  > </beans>

  加粗部分为新加入部分。

### 7.2 创建对象的标签

- **@Component**：
  - 位置：bean 类的声明上
  - 说明：通用的标签，用于配置一个 bean 以加入 spring 容器
  - 属性：value -> 指定 bean 的 id，若不指定则为首字母小写的类名
- **@Controller & @Service & @Repository**：
  - 都是 `@Component` 的衍生注解，使用方法和功能一模一样。
  - 更加语义化，增加辨识度。

### 7.3 注入数据的标签

- **说明**：
  - 只使用注解注入属性，可以不写 set 方法。其实甚至连构造方法都不需要，底层是暴力反射。
- **@Autowired**：
  - 位置：bean 的成员变量上。
  - 说明：只能注入 bean 类型。
  - 注入规则：
    - 首先自动按照**类型**注入，容器中只有一个对应类型的 bean 时有效
    - 容器中有多个匹配类型的 bean 时，找到 id 与当前变量名一致的 bean 来注入
    - 若上一条没找到，报错
  - 属性：无
- **@Qualifier**：
  - 位置：bean 的成员变量上。
  - 说明：按照类型+id 给成员变量注入值，需要和`@Autowired`一起使用。只能注入 bean 类型。
  - 属性：
    - value（默认）：bean 的 id
- **@Resource**：
  - 位置：bean 的成员变量上。
  - 说明：只能注入 bean 类型，直接按照 id 注入。
  - 属性：
    - name：bean 的 id
- **@Value**：
  - 位置：bean 的成员变量上。
  - 说明：只能注入基本类型和 String 。
  - 属性：
    - value（默认）：值。

### 7.4 生命周期与作用范围相关标签

- **@PostConstruct**：
  - 位置：bean 的成员方法上。
  - 说明：指定 bean 的初始化方法，在实例化完成后执行。
  - 属性：无。
- **@PreDestroy**：
  - 位置：bean 的成员方法上。
  - 说明：指定 bean 的销毁方法，在即将销毁之前执行。
  - 属性：无。
- **@Scope**：
  - 位置：bean 的类声明上。
  - 说明：指定 bean 的单例与多例、作用范围。
  - 属性：
    - value（默认）：可取值如下：
      - singleton
      - prototype
      - request
      - session
      - globalsession



## 8. IOC：配置类+注解实现

### 8.1 创建配置类 & 获取容器

配置类就是通过一个 Java 类来替代 xml 配置文件。

- **创建配置类**：

  建议新建 config 包，并且配置类以 "config" 结尾。

  使用如下注释，该类就成了配置类：

  - **@Configuration**：
    - 位置：配置类的类声明
    - 说明：指定当前类为 spring 容器配置类
    - 属性：无

  ```java
  @Configuration
  public class BeansConfig{
      
  }
  ```

- **获取配置类容器对象**：

  获取配置类使用 ApplicationContext 的子类：**AnnotationConfigApplicationContext** 。

  相对的，获取 xml 文件的容器对象使用的是 ClassPathXmlApplicationContext 。

  ```java
  public class Test{
      ApplicationContext factory = new AnnotationConfigApplicationContext(BeansConfig.class);
      public void test() {
          User user = (User)factory.getBean("id");
          ...
      }
  }
  ```

### 8.2 包扫描

指定 spring 容器初始化时扫描的 java 包，扫描出其中通过注解配置的 bean。

效果同于 xml 中的：

```xml
<context:component-scan base-package="io.github.photozynthesis.domain" />
```

使用如下注解：

- **@ComponentScan**：
  - 位置：配置类的类声明。
  - 说明：指定 spring 容器初始化时扫描的 java 包，扫描出其中通过注解配置的 bean。
  - 属性：
    - value/basePackages（互相别名）：要扫描的完整包路径。

示例：

```java
@Configuration
@ComponentScan("io.github.photozynthesis.domain")
public class BeansConfig{
    
}
```

### 8.3 任意方法定义 bean

使用如下注解：

- **@Bean**：
  - 位置：配置类中的方法上。
  - 说明：表明使用此方法创建一个对象（对象为返回值），并放入 spring 容器。
  - 属性：value/name（互相别名了一波）：bean 的 id 。

示例：

```java
@Configuration
public class BeansConfig{
    @Bean("now")
    public Date getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse("2018-08-08");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
```

### 8.4 引入外部 properties 文件

引入外部 properties 文件后，可以通过表达式来动态获取 properties 中的内容，避免将代码写死。

使用如下注解来引入 properties 文件：

- **@PropertySource**：
  - 位置：类的方法声明上。
  - 说明：引入 properties 文件。
  - 属性：
    - value[]：指定 properties 文件位置。若文件在 classpath 中，还需要写上 classpath ，具体见下方示例。

示例：

jdbc.properties：

```properties
jdbc.url = jdbc:mysql://localhost:3306/test?useSSL=false&ServerTimezone=UTC
```

BeansConfig.java：

```java
@Configuration
@PropertySource({"classpath:jdbc.properties"})
public class BeansConfig {
    @Value("${jdbc.url}")
    private String url;
    
    @Bean("url")
    public String getUrl() {
        return url;
    }
}
```

### 8.5 引入其他配置类

在一个配置类中引入其他配置类后，其他配置类就不用写`@Configuration`注解了（写也可以）。

利于配置类的管理。

- **@Import**：
  - 位置：配置类的类声明。
  - 说明：引入其他配置类。
  - 属性：value[]：指定导入的配置类的 class 字节码文件。

示例：

```java
@Configuration
@Import({UserBeansConfig.class, AccountBeansConfig.class})
public class CoreBeansConfig {
    
}

public class UserBeansConfig {
    @Bean("id")
    ...
}

public class AccountBeansConfig {
    @Bean("aid")
    ...
}
```








## #. 组件：Spring JDBC Template

### #.1 概述

Spring框架对JDBC的简单封装，提供一个JdbcTemplate对象来简化JDBC开发。

### #.2 Maven添加依赖

1. 在pom.xml中按照如下格式添加：

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    ...
    <dependencies>
    	<dependency>
        	<groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>4.3.6.RELEASE</version>
        </dependency>
    </dependencies>
</project>
```

2. Maven -> Update Project，刷新项目以下载依赖，若下载失败，在`C:/user/<username...>/.m2/`下找到并删除下到一半的玩意，并重新刷新项目。
3. 下载速度慢可以换Alibaba源或挂梯子。

### #.3 使用步骤

1. 导入jar包

2. 根据DataSource创建JDBCTemplate对象

   ```java
   JdbcTemplate template = new JdbcTemplate(ds);
   ```

3. 调用JdbcTemplate的方法来进行CUID操作，常用方法如下：

   - **update** (String sql, args...)，执行DML语句（增删改数据）

     - 若sql使用`?`作占位符，可使用args来传递参数，底层将使用PreparedStatement
   - **queryForMap** (String sql, args...)，查询并将结果封装为Map集合

     - 封装为Map时，查询的结果集只能有一条。
     - 返回的Map表示这一个查询出来的条目，键为字段名，值为对应值
     - 若sql使用`?`作占位符，可使用args来传递参数，底层将使用PreparedStatement
   - **queryForList** (String sql, args...)，查询并将结果封装为List集合

     - 将每一条记录封装为一个Map集合，再将Map集合装载到List集合中。
     - 返回的List集合中存储的是`org.springframework.util.LinkedCaseInsensitiveMap`对象
     - 若sql使用`?`作占位符，可使用args来传递参数，底层将使用PreparedStatement
   - **query** (String sql, RowMapper)，查询并将结果封装为JavaBean对象

     - 使用RowMapper的实现类，可以实现数据到JavaBean的自动封装

     - 使用`new BeanPropertyRowMapper<Type...> (<Type.class...>)`封装JavaBean

     - JavaBean的各字段名需要与查询结果的各字段名相同才会封装

     - 使用举例：

       ```java
       import ...;
       
       public class JdbcTemplateTest {
           @Test
           public void test() {
               // Get DataSource and construct a JdbcTemplate instance
               C3p0JDBCUtils utils = new C3p0JDBCUtils();
       		DataSource dataSource = utils.getDataSource();
               JdbcTemplate template = new JdbcTemplate(dataSource);
       		
               // Construct a RowMapper instance using JavaBean's class
       		RowMapper<Emp> rm = new BeanPropertyRowMapper<>(Emp.class);
       		
               // Execute query and get a list of JavaBean instances
       		List<Emp> list = template.query("select * from emp", rm);
       		
               // output the list
       		System.out.println(list);
           }
       }
       
       // JavaBean
       class Emp {
           int id;
           String name;
           String job;
           String manager_id;
           
           ...
       }
       ```
   - **queryForObject** (String sql, class, args...)，查询并将结果封装为对象

     - 一般用于聚合函数查询，查询普通条目会报错
     - 例如：查询`select count(*) from emp`，将返回long值

