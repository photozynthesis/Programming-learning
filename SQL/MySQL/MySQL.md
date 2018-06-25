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
- show tables from 数据库名;    //在一个库中查看其他库中的表

### 3.6 创建数据库

- create database 数据库名称;
- use 数据库名称;    //选择当前操作的数据库



## 4.表

### 4.1 概述

表（table）是一种结构化的文件，用来存储特定类型的数据。

每个表都有特定名称，且不能重复。

表中的重要概念：列、行、主键，其中列（column）叫做字段，行叫做表中的记录。

每一个字段都有：字段名称、字段数据类型、字段长度、字段约束。

### 4.2 表相关常用操作

- desc 表名;    //查看表的结构（查看字段等信息）
- show create table 表名;    //查看表的创建语句



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



## 6.查询

- **查询表中单个字段**：

    ```sql
    select 字段名 from 表名;
    ```

- **查询表中多个字段**：

    ```sql
    select 字段名1,字段名2,字段... from 表名;
    // 若查询所有字段可以用*代替字段名
    // select * from 表名;
    ```

- **条件查询**：

  查询语句后跟`where 条件语句`即可，举例：

  ```sql
  select salary from employee where salary>=3000;
  ```

  常用条件查询运算符：

  |         运算符          |                     说明                     |
  | :---------------------: | :------------------------------------------: |
  | = , <>/!=, <, >, <=, >= | 等于，不等于，小于，大于，小于等于，大于等于 |
  |   between ... and ...   |                在...和...之间                |
  |         is null         |                判断是否没有值                |
  |           and           |               与，优先级大于or               |
  |           or            |           或，可使用括号改变优先级           |
  |           in            |                     包含                     |
  |           not           |                      非                      |
  |          like           |                   模糊查询                   |

- **排序查找**：

    显示排序后的查询结果。

    查询语句后跟`order by 排序字段1 方式1,排序字段2 方式2,...;`

    排序方式有esc（升序）、desc（降序），默认为升序。

    越靠前的字段作用越大。

- **数据处理函数/单行处理函数**

    |            函数名            |        说明         |
    | :--------------------------: | :-----------------: |
    |         lower(内容)          |      转成小写       |
    |         upper(内容)          |      转成大写       |
    | substr(内容, 起始下标, 长度) |     截取字符串      |
    |          trim(内容)          |    去除前后空格     |
    |         length(内容)         |       取长度        |
    |       round(内容,位数)       |      四舍五入       |
    |            rand()            | 生成0~1之间的随机数 |
    |     ifnull(内容,替换值)      | 将null转换成具体值  |


**注意事项**：

1. 查询语句中字段名后可跟计算表达式，例如`select 字段*12 from table1;`，将显示计算后的表。**不会**修改表中的数据。
2. 可在变量名后跟`as 重命名`来让查询结果显示重命名的字段名，不用as也可以。**不会**修改表中的数据。
3. 若要显示中文的重命名，中文需要用单引号`'`括起来。MySQL中双引号`"`也可以。
4. between...and...语句中，若判断的是数字，则左右皆包；若判断的是字符，则包左不包右。所以主要用于判断数字。