# MySQL学习笔记

[TOC]

## 1.MySQL概述

MySQL最初是由“MySQL AB”公司开发的一套关系型数据库管理系统，是流行的开源数据库管理系统。

2008年Sun公司收购了MySQL AB公司，2009年Oracle公司收购了Sun公司，MySQL已并入Oracle的数据库产品线。



## 2.MySQL安装过程的注意事项

- MySQL的经典端口号是3306
- 最好指定一个数据库的字符集，如UTF-8



## 3.MySQL常用操作

### 3.1 启动与停止服务

一般MySQL服务是自动启动的，不过在Windows下可以找到“服务”中的“MySQL”手动关闭与开启。

Windows下也可以：

- net start mysql 启动
- net stop mysql 停止

### 3.2 登录

在CMD窗口：

- mysql -u用户名 -p密码

  ```
  mysql -uroot -p123
  ```

- mysql -u用户名 -p

  回车后再输入密码，可避免显示密码

### 3.3 退出

- 输入exit

### 3.4 查看MySQL版本

- mysql -version
- mysql --V
- 登录后select version();

### 3.5 显示所有数据库（database）/表（table）

- show databases;    //显示所有数据库
- show tables;    //显示所有表
- select database();    //可显示当前正在使用的数据库

### 3.6 创建数据库

- create database 数据库名称;
- use 数据库名称;    //选择当前操作的数据库

## 4.表

### 4.1 概述

表（table）是一种结构化的文件，用来存储特定类型的数据。

每个表都有特定名称，且不能重复。

表中的重要概念：列、行、主键，其中列（column）叫做字段，行叫做表中的记录。

每一个字段都有：字段名称、字段数据类型、字段长度、字段约束。



## 5.SQL语句的分类

1. 数据查询语言（DQL-Data Query Language）

   代表关键字：select

2. 数据操纵语言（DML-Data Manipulation Language）（针对表的**内容**）

   代表关键字：insert、delete、update

3. 数据定义语言（DDL-Data Definition Language）（针对表的**结构**）

   代表关键字：create、drop、alter

4. 事务控制语言（TCL-Transactional Control Language）

   代表关键字：commit、rollback

5. 数据控制语言（DCL-Data Control Language）

   代表关键字：grant、revolve