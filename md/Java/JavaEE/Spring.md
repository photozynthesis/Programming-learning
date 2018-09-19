# Spring Framework

[TOC]

## 1. Spring JDBC Template

### 1.1 概述

Spring框架对JDBC的简单封装，提供一个JdbcTemplate对象来简化JDBC开发。

### 1.2 使用步骤

1. 导入jar包

2. 根据DataSource创建JDBCTemplate对象

   ```java
   JdbcTemplate template = new JdbcTemplate(ds);
   ```

3. 调用JdbcTemplate的方法来进行CUID操作，常用方法如下：

   - **update** (sql, args...)，执行DML语句（增删改数据）
   - **queryForMap** (sql, args...)，查询并将结果封装为Map集合
     - 封装为Map时，查询的结果集只能有一条。
   - **queryForList** (sql, args...)，查询并将结果封装为List集合
     - 将每一条记录封装为一个Map集合，再将Map集合装载到List集合中。
   - **query** (sql, RowMapper)，查询并将结果封装为JavaBean对象
     - 使用RowMapper的实现类，可以实现数据到JavaBean的自动封装
     - new BeanPropertyRowMapper<Type...> (Type.class...)
   - **queryForObject** (sql, args...)，查询并将结果封装为对象
     - 一般用于聚合函数查询。