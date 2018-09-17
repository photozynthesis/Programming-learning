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

