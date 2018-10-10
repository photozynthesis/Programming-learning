# Spring Framework

[TOC]

## 1. Spring JDBC Template

### 1.1 概述

Spring框架对JDBC的简单封装，提供一个JdbcTemplate对象来简化JDBC开发。

### 1.2 Maven添加依赖

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

### 1.3 使用步骤

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

