# JDBC

## 目录

[TOC]

## 1. JDBC概述

- **概述：**

  JDBC（Java Database Connectivity） API，Java数据库连接，是一个Java编程中用于与数据库连接的API。

  JDBC是一套规范，提供一整套接口，允许一种可移植的访问底层数据库的API ，定义了操作所有关系型数据库的规则。

  各个数据库厂商会实现这套接口，导入厂商提供的实现后，调用JDBC提供的接口实际会调用厂商的实现来操作数据库。

  JDBC库包括数据库使用相关的功能：

  - 连接到数据库
  - 创建SQL或MySQL语句
  - 在数据库中执行SQL或MySQL查询
  - 查看和修改结果记录

- **使用步骤：**

  1. 导入驱动jar包
  2. 注册驱动
  3. 获取数据库连接对象
  4. 定义sql
  5. 获取执行sql语句的对象statement
  6. 执行sql，接收返回结果
  7. 处理结果
  8. 释放资源

- **简单的样板：**

  ```java
  package io.github.photozynthesis;
  
  import java.sql.Connection;
  import java.sql.DriverManager;
  import java.sql.ResultSet;
  import java.sql.SQLException;
  import java.sql.Statement;
  
  //import com.mysql.jdbc.Driver;
  
  public class Test {
  
  	public static String USER = "root";
  	public static String PASSWORD = "rootzxcvbnm";
  
  	public static void main(String[] args) {
  
  		Connection connection = null;
  		Statement statement = null;
  
  		try {
  			// 加载驱动
  			// Class.forName("com.mysql.cj.jdbc.Driver");
  
  			// 连接数据库
  			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=UTC",
  					USER, PASSWORD);
  
  			// 执行查询
  			statement = connection.createStatement();
  			String sql = "select * from emp;";
  			ResultSet rs = statement.executeQuery(sql);
  
  			// 输出结果
  			while (rs.next()) {
  				int id = rs.getInt("id");
  				String name = rs.getString("name");
  				String job = rs.getString("job");
  				double salary = rs.getDouble("salary");
  
  				System.out.println(id + "===" + name + "===" + job + "===" + salary);
  			}
  
  			// 释放资源
  			rs.close();
  			statement.close();
  			connection.close();
  
  		} catch (SQLException e) {
  			e.printStackTrace();
  		}
  	}
  }
  ```



## 2. JDBC中常用对象

### 2.1 DriverManager

**概述**：

​	class DriverManager，驱动管理器对象

**功能**：

|                           重要方法                           |                             说明                             |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
|        static void **registerDriver** (Driver driver)        | 注册给定的驱动程序，一般使用Class.forName("com.mysql.jdbc.Driver")，各版本可能不同 |
| static Connection **getConnection** (String url, String user, String password) |               获取数据库连接，参数说明见下方。               |

**相关说明**：

1. getConnection方法参数的说明：

   - String url：指定连接的路径，MySQL的语法如下：

     jdbc:mysql://<ip...>:<port...>/<databaseName...>

   - String user：数据库用户名

   - String password：数据库该用户的密码

### 2.2 Connection

**概述**：

​	interface Connection，数据库连接对象。

**功能**：

|                      重要方法                       |         说明         |
| :-------------------------------------------------: | :------------------: |
|          Statement **createStatement** ()           |  获取执行sql的对象   |
| PreparedStatement **prepareStatement** (String sql) |  获取执行sql的对象   |
|     void **setAutoCommit** (boolean autoCommit)     | 设置是否自动提交事务 |
|                 void **commit** ()                  |       提交事务       |
|                void **rollback** ()                 |       回滚事务       |

### 2.3 Statement

**概述**：

​	interface Statement，执行SQL语句的对象。

**功能**：

|                重要方法                 |                             说明                             |
| :-------------------------------------: | :----------------------------------------------------------: |
|    boolean **execute** (String sql)     |                     可以执行任意SQL语句                      |
|   int **executeUpdate** (String sql)    | 执行DML（增删改表中的数据）和DDL（增删改表结构）语句，返回值为受影响的行数 |
| ResultSet **executeQuery** (String sql) |                     执行DQL（查询）语句                      |

**相关说明**：

1. executeUpdate (String sql) 的返回值可用于判断语句是否执行成功。

### 2.4 ResultSet

**概述**：

​	interface ResultSet，结果集对象。

**功能**：

|                方法                 |                    说明                     |
| :---------------------------------: | :-----------------------------------------: |
|         boolean **next** ()         |              游标向下移动一行               |
| xxx **getXxx** (String columnLabel) |        根据字段名获取指定类型的数据         |
|  xxx **getXxx** (int columnIndex)   | 根据列的编号获取指定类型的数据，编号从1开始 |

### 2.5 PreparedStatement

**概述**：

​	interface PreparedStatement，预编译的SQL语句对象。

​	该对象SQL的参数使用`?`作为占位符，可以解决SQL注入问题。

**使用步骤**：

1. ...

2. 获取connection

3. 定义SQL语句，使用`?`作为参数占位符，如：

   ```sql
   SELECT * FROM user WHERE username = ? and password = ?;
   ```

4. 根据connection得到PreparedStatement对象

5. 给`?`赋值：

   ```
   pstmt.setString(1, <username...>);
   pstmt.setString(2, <password...>);
   ```

6. 执行查询

7. ...

**特殊方法**：

|                   方法                    |              说明              |
| :---------------------------------------: | :----------------------------: |
| void **setXxx** (int paramIndex, Xxx xxx) | 根据编号给`?`赋值，编号从1开始 |



## 3. 数据库连接池

### 3.1 连接池技术概述

**概述**：

​	数据库连接池是一个存放数据库连接的容器（集合）。

​	系统初始化后，创建容器并申请一些连接对象放入集合。当需要连接时，从池子中获取连接对象；使用完毕后，将连接对象归还给连接池。

**好处**：

- 节省系统资源
- 访问高效

**数据库连接池的使用**：

- Java提供了一个标准接口：javax.sql.DataSource，方法如下：

|              方法               |                             说明                             |
| :-----------------------------: | :----------------------------------------------------------: |
| Connection **getConnection** () |           获取一个到此DataSource表示的数据源的连接           |
|       Connection.close ()       | 不是DataSource中的方法，而若该Connection是从连接池获取的，该close方法不会关闭连接而是归还连接到连接池 |

​	一般数据库厂商会提供接口的实现。

- 常用的实现：
  - C3P0，数据库连接池技术，Hibernate和Spring都在使用
  - Druid，Alibaba提供的数据库连接池技术

### 3.2 C3P0连接池的使用

1. 导入jar包：

   - c3p0-0.9.5.2.jar
   - mchange-commons-java-0.2.12.jar

   以上内容可从 [https://sourceforge.net/projects/c3p0/](https://sourceforge.net/projects/c3p0/) 下载。

2. 写配置文件：

   在src目录下创建c3p0-config.xml文件，**注意需要将文件添加到.classpath**。
   内容如下：

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <c3p0-config>
       <default-config> 
           <property name="jdbcUrl">jdbc:mysql://localhost:3306/test?allowPublicKeyRetrieval=true&amp;useSSL=false&amp;serverTimezone=UTC</property>
           <property name="driverClass">com.mysql.jdbc.Driver</property>
           <property name="user">root</property>
           <property name="password">123</property> 
           
   　　     <!--当连接池中的连接耗尽的时候c3p0一次同时获取的连接数。Default: 3 -->
           <property name="acquireIncrement">3</property>
           
   　　     <!-- 初始化数据库连接池时连接的数量 -->
           <property name="initialPoolSize">10</property>
           
           <!-- 数据库连接池中的最小的数据库连接数 -->
           <property name="minPoolSize">2</property>
           
           <!-- 数据库连接池中的最大的数据库连接数 -->
           <property name="maxPoolSize">10</property>
       </default-config>
   </c3p0-config>
   ```

   更多内容详见官网 [https://www.mchange.com/projects/c3p0/](https://www.mchange.com/projects/c3p0/) 。

3. 使用：

```java
import ...;

public class JDBCUtils {
    private static Connection conn;
    private static ComboPooledDataSource ds = new ComboPooledDataSource();
    
    public static Connection getConnection(){
        try {
            conn = ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
```

### 3.3 Druid连接池的使用

**官方文档**：

​	[https://github.com/alibaba/druid/wiki/%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98](https://github.com/alibaba/druid/wiki/%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98) 

**基本使用步骤**：

1. 导入jar包/添加Maven依赖

   jar包地址：

   ​	[http://central.maven.org/maven2/com/alibaba/druid/ ](http://central.maven.org/maven2/com/alibaba/druid/ ) 

   Maven添加格式：

   ```xml
   ...
   <dependency>
       <groupId>com.alibaba</groupId>
       <artifactId>druid</artifactId>
       <version>${druid-version}</version>
   </dependency>
   ...
   ```

2. 定义配置文件

   ```properties
   url: jdbc:mysql://localhost:3306/test
   driverClassName: com.mysql.jdbc.Driver
   username: root
   password: rootzxcvbnm
   
   filters: stat
   maxActive: 20
   initialSize: 1
   maxWait: 60000
   minIdle: 10
   maxIdle: 15
   timeBetweenEvictionRunsMillis: 60000
   minEvictableIdleTimeMillis: 300000
   validationQuery: SELECT 'x'
   testWhileIdle: true
   testOnBorrow: false
   testOnReturn: false
   maxOpenPreparedStatements: 20
   removeAbandoned: true
   removeAbandonedTimeout: 1800
   logAbandoned: true
   ```

3. 加载配置文件

4. 通过DruidDataSourceFactory工厂来获取DataSource对象

   ```java
   ds = DruidDataSourceFactory.createDataSource(properties);
   ```

5. 获取连接

   ```java
   ds.getConnection();
   ```
