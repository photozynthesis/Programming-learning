# MyBatis

[TOC]

## 1. MyBatis 概述

- MyBatis是一个可以**自定义SQL、存储过程和高级映射**的持久层框架。
- MyBatis 摒除了大部分的JDBC代码、手工设置参数和结果集重获。**MyBatis只使用简单的XML和注解来配置和映射基本数据类型、Map 接口和POJO 到数据库记录**。



## 2. MyBatis 入门

MyBatis简单的查询案例：

1. maven添加MyBatis依赖：

   ```xml
   <!-- 版本号及最新信息可在官网查询 -->
   <dependency>
       <groupId>org.mybatis</groupId>
       <artifactId>mybatis</artifactId>
       <version>3.4.6</version>
   </dependency>
   ```

2. 在数据库创建表，并插入数据；

3. 创建JavaBean（实现Serializable）；

4. 编写Dao（持久层接口）：

   ```java
   public interface UserDao {
       /**
       *	查询并返回所有用户的List
       */
       List<User> getAll();
   }
   ```

5. 在Resources根目录（src/main/java）创建核心配置文件`SqlMapConfig.xml`，内容如下：

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <!DOCTYPE configuration     PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-config.dtd">
   <configuration>
       <!-- 配置 mybatis 的环境 -->
       <environments default="mysql">
           <!-- 配置 mysql 的环境 -->
           <environment id="mysql">
               <!-- 配置事务的类型 -->
               <transactionManager type="JDBC">
               </transactionManager>
               <!-- 配置连接数据库的信息：用的是数据源(连接池) -->
               <dataSource type="POOLED">
                   <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                   <property name="url" value="jdbc:mysql://localhost:3306/test?serverTimezone=CST"/>
                   <property name="username" value="root"/>
                   <property name="password" value="rootzxcvbnm"/>
               </dataSource>
           </environment>
       </environments>
       <!-- 告知 mybatis 映射配置的位置 -->
       <mappers>
           <mapper resource="io/github/photozynthesis/dao/UserDao.xml"/>
       </mappers>
   </configuration>
   ```

   说明：

   - 每添加一个Mapper，需要在`mappers`标签下添加一条`mapper`。

6. 在Dao接口的同目录下，创建同名的xml文件（mapper）。在Resources文件夹中创建时，注意需要创建相同的目录结构。

   UserDao.xml：

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <!DOCTYPE mapper
           PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
   <mapper namespace="io.github.photozynthesis.dao.UserDao">
       <!-- 配置查询所有操作 -->
       <select id="getAll" resultType="io.github.photozynthesis.domain.User">
           select * from user where id = #{id}
       </select>
   </mapper>
   ```

   说明：

   - `mapper`标签的属性`namespace`的值需要为Dao接口的完整包路径。
   - `select`标签的属性`id`的值需要为与Dao中相同的方法名。
   - `select`标签的属性`resultType`的值需要为查询结果JavaBean的完整包路径，若查询结果为List等，会自动封装。
   - 可以使用`#{参数}`作为SQL占位符，参数名需要为方法传递的形参名。

7. 进行测试。

   测试分为多个步骤：

   ```java
   import io.github.photozynthesis.dao.UserDao;
   import io.github.photozynthesis.domain.User;
   import org.apache.ibatis.io.Resources;
   import org.apache.ibatis.session.SqlSession;
   import org.apache.ibatis.session.SqlSessionFactory;
   import org.apache.ibatis.session.SqlSessionFactoryBuilder;
   
   import java.io.IOException;
   import java.io.InputStream;
   import java.util.List;
   
   public class Test {
       public static void main(String[] args) {
           // 1.读取配置文件
           InputStream is = null;
           try {
               is = Resources.getResourceAsStream("SqlMapConfig.xml");
           } catch (IOException e) {
               e.printStackTrace();
               System.exit(1);
           }
   
           // 2.创建SqlSessionFactoryBuilder对象
           SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
   
           // 3.使用构建者创建SqlSessionFactory
           SqlSessionFactory factory = builder.build(is);
   
           // 4.生产SqlSession
           SqlSession session = factory.openSession();
   
           // 5.使用SqlSession创建Dao的代理对象
           UserDao userDao = session.getMapper(UserDao.class);
   
           // 6.使用代理对象执行查询所有的方法
           List<User> list = userDao.getAll();
           for (User user : list) {
               System.out.println(user);
           }
   
           // 7.释放资源
           session.close();
           try {
               is.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
   }
   ```



## 3. SqlMapConfig.xml 配置文件

SqlMapConfig.xml是MyBatis的核心配置文件，包含很多框架基本的配置信息。

放在Resources根目录（/src/main/java下）。

### 3.1 内容与顺序

```
-properties（属性）
	--property
-settings（全局配置参数）
	--setting 
-typeAliases（类型别名）
	--typeAliase
	--package
-typeHandlers（类型处理器）
-objectFactory（对象工厂）
-plugins（插件） 
-environments（环境集合属性对象）  
	--environment（环境子属性对象）   
		---transactionManager（事务管理）   
		---dataSource（数据源） 
-mappers（映射器）  
	--mapper  
	--package
```

具体可以参考http://mybatis.org/dtd/mybatis-3-config.dtd约束文件。

### 3.2 properties

- **概述**：

  配置name - value键值对，用以在xml中可以使用`${name}`来表示value。

- **编写方式**：

  - 方式一：在properties中写property，编写name和value属性。

    ```xml
    <!-- SqlMapConfig.xml -->
    <properties>
    	<property name="jdbc.driver" value="com.mysql.cj.jdbc.driver" />
        <property ... />
    </properties>
    ```

  - 方式二：编写外部properties文件，通过properties的url属性引入。

    ```properties
    # db.properties
    jdbc.driver=com.mysql.cj.jdbc.driver
    ...
    ```

    ```xml
    <!-- SqlMapConfig.xml -->
    <properties url="file:///D:/.../db.properties"></properties>
    ```

- **使用**：

  在任意位置`"${name}"`即可。

  ```xml
  <environments>
  	<environment>
      	<dataSource type="POOLED">
          	<property name="driver" value="${jdbc.driver}" />
              <property ... />
          </dataSource>
      </environment>
  </environments>
  ```

### 3.3 typeAliases

- **概述**：

  可以给类自定义别名。

  别名定义后，直接使用即可。

- **编写**：

  - 定义单个类的别名

    ```xml
    <typeAliases>
    	<typeAlias alias="user" type="io.github.photozynthesis.domain.User" />
        <typeAlias ... />
    </typeAliases>
    ```

  - 批量定义一整个包下所有类的别名（别名为类名，不区分大小写）

    ```xml
    <typeAliases>
    	<package name="io.github.photozynthesis.domain" />
        <package ... />
    </typeAliases>
    ```

### 3.4 mappers

- **概述**：

  定义映射。

- **编写**：

  - 指定相对于classpath的资源（xml）

    ```xml
    <mappers>
    	<mapper resource="io/github/photozynthesis/dao/UserDao.xml" />
    </mappers>
    ```

  - 指定mapper接口的类路径

    ```xml
    <mappers>
    	<mapper class="io.github.photozynthesis.dao.UserDao" />
    </mappers>
    ```

    说明：需要接口与映射文件名称相同，且放在同一个目录中。

  - 注册指定包下所有接口

    ```xml
    <mappers>
    	<package name="io.github.photozynthesis.dao" />
    </mappers>
    ```

    说明：需要接口与映射文件名称相同，且放在同一个目录中。

