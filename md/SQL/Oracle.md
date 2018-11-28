# Oracle

[TOC]

## 1. 概述

- ORACLE 数据库系统是美国ORACLE 公司（甲骨文）提供的以分布式数据库为核心的一组
  软件产品，是目前最流行的客户/服务器(CLIENT/SERVER)或 B/S体系结构的数据库之一。
- **由于继承了 SQL 标准，其大部分内容与 MySQL 相似/相同，本文仅列出一些常见的不同之处**。



## 2. 连接、表空间与用户

### 2.1 连接



### 2.2 表空间



### 2.3 用户





## 3. 数据类型





## 4. 序列





## 5. 视图





## 6. 索引

### 6.1 概述

在表的列上构建一个二叉树，可以大幅提高查询的效率，却会影响增删改的效率。

### 6.2 创建索引

- 创建单列索引

  ```
  create index 索引名 on 表名(字段名);
  ```

- 创建复合索引

  ```
  create index 索引名 on 表名(字段名,字段名,...);
  ```

### 6.3 重要说明

- 只有当查询列中的原始值时，才会触发索引；单行函数、模糊查询等不会触发。
- 复合索引中的第一列会优先检索。
- 要触发复合索引，必须包含优先检索列中的原始值。
- or 语句不会触发索引。



## 7. pl/sql

### 7.1 概述

- 是对 sql 语言的扩展，使其具有某些编程语言的特性。
- 主要用来编写存储过程和存储函数等。

### 7.2 示例

- 声明方法 & 入门

  ```plsql
  declare
  	--- 声明部分
  	i number(2) := 10;
  begin
  	--- 执行部分
  	dbms_output.put_line(i);
  end;
  ```

### 7.3 常用操作

- **赋值**

  可以使用 := 也可以使用 into 语句赋值。

  - := 

    >  标识 类型(长度) := 值;

  - into 语句

    > select 字段 into 标识 from 表 where 字段 = 值;

- **连接字符串**

  >  ||

- **引用型变量**

  一个值。

  >  标识 表.字段%type;

- **记录型变量**

  一条记录。

  > 标识 表%rowtype;

- **if 语句**

  ```plsql
  if 条件 then
  	...
  elsif 条件 then
  	...
  else
  	...
  end if;
  ```

- **循环**

  - while

    ```plsql
    while 条件 loop
    	...
    	...(控制语句)
    end loop;
    ```

  - exit

    ```plsql
    loop
    	exit when 条件;
    	...
    	...(控制语句)
    end loop;
    ```

  - for

    ```plsql
    for 变量 in 初始值...最终值 loop
    	...
    end loop;
    ```

- **游标**

  - 可以存储查询返回的多条语句。

  - 定义：

    ```plsql
    declare
    	cursor 游标名[(参数名 数据类型,参数名 数据类型,...)] is select ...;
    ```

  - 使用：

    ```plsql
    -- 打开游标
    open 游标;
    	-- 取一行游标的值（遍历）
    	fetch 游标 into rowtype变量;
    	-- 关闭
    	exit when 游标%notfound;
    -- 关闭游标
    close 游标;
    ```



## 8. 存储过程

### 8.1 概述

- 存储过程是一段提前编译好的 plsql 语言，存储在数据库端。
- 一般都是固定的业务。

### 8.2 编写

```plsql
create [or replace] procedure 过程名 [(参数名 in/out 数据类型)] 
as
begin
	...
end;
```

### 8.3 使用

- 可以通过 java 来使用。

- 可以直接使用：

  ```plsql
  declare
  	
  begin
  	过程名(参数);
  end;
  ```



## 9. 存储函数





## 10. 触发器

### 10.1 概述

- 触发器就是与表相关联的，存储的 plsql 程序。
- 当 insert、update 和 delete 在指定表上发出时，Oracle 会自动执行触发器中定义的语句。

### 10.2 定义

```plsql
create [or replace] trigger 触发器名
<before | after>
<update | insert | delete> [of 列名]
on 表名
[for each row [when 条件]]
declare
	...
begin
	...
end;
```

