# Oracle

[TOC]

## 1. 概述

- ORACLE 数据库系统是美国ORACLE 公司（甲骨文）提供的以分布式数据库为核心的一组
  软件产品，是目前最流行的客户/服务器(CLIENT/SERVER)或 B/S体系结构的数据库之一。
- **由于继承了 SQL 标准，其相当部分内容与 MySQL 相似/相同，本文仅列出一些常见的不同之处**。



## 2. 数据库与表空间、用户、连接

### 2.1 数据库与表空间

- Oracle 数据库的概念与其他数据库软件的有所不同，可以认为一个操作系统只有一个**数据库**。

- 一个数据库可以有多个**实例**，一个实例由多个进程与内存结构组成。

- 一个数据库在逻辑上被分成一到若干个**表空间**，一个表空间可以对应物理上的一到若干个 dbf/ora 文件。

- 映射了表空间的 dbf/ora 文件**不能直接删除**，需要先删除表空间后方可删除。

- 操作示例：

  - 创建表空间：

    ```sql
    create tablespace 表空间名 
    datafile '/usr/demo.dbf' 
    size 100m
    autoextend on 
    next 10m
    ```

### 2.2 用户

- 用户是在实例下建立的，不同实例可以创建相同名字的用户。

- 用户的权限：

  - 新创建的用户没有任何权限，需要授予角色以获取权限。
  - 可用的权限：
    - connect：包含一些基本权限
    - resource：授予开发人员的角色，包含建表、建立触发器、建立类型等。
    - dba：数据库的最高权限。

- 操作示例：

  - 创建用户：

    ```sql
    create user 用户名 
    identified by 密码 
    default tablespace 默认表空间
    ```

  - 给用户授权：

    ```sql
    grant connect|resource|dba to 用户名
    ```

### 2.3 连接

- 使用 sqlplus 连接：

  ```shell
  # 格式
  sqlplus 用户名/密码@服务器地址:端口号/数据库名
  # 实例
  sqlplus system/oracle@localhost:1521/orcl
  ```

- 使用 jdbc 连接：

  db.properties

  ```properties
  jdbc.driver=oracle.jdbc.driver.OracleDriver
  jdbc.url=jdbc:oracle:thin:@local:1521:oracle
  jdbc.username=username
  jdbc.password=password
  ```



## 3. 数据类型

Oracle 的常用数据类型如下表：

| 数据类型          | 描述                                                         |
| ----------------- | ------------------------------------------------------------ |
| varchar, varchar2 | 表示一个字符串                                               |
| number            | number(n)，表示长度n的整数；number(m,n)，m为总长度，n为小数长度 |
| date              | 日期类型                                                     |
| CLOB              | 大对象，可存文本，最多4G                                     |
| BLOB              | 大对象，可存二进制数据，最多4G                               |



## 4. 序列

### 4.1 概述

- Oracle 没有 mysql 中的auto_increment 约束，可以使用序列来完成表中字段值的**自动增长**。
- 序列与表没有必然关系，不过一般各表会使用各自的序列。

### 4.2 创建与使用

- **创建**：

  ```sql
  create sequence 序列名 
  [increment by n] 
  [start with n] 
  [maxvalue/minvalue n | nomaxvalue] 
  [cycle | nocycle] 
  [cache n | nocache]
  ```

  实例：

  ```sql
  create sequence seq_userNum;
  ```

- **使用**：

  ```sql
  -- 获取序列的下一个内容
  序列名.nextval
  -- 获取序列的当前内容
  序列名.currval
  ```

  实例：

  ```sql
  -- 直接获取值
  select seq_userNum.nextval from dual;
  -- 通过序列插入自动增长的值
  insert into user(num, ...) values(seq_userNum.nextval, ...);
  ```



## 5. 视图

### 5.1 概述

- 视图就是封装了一条复杂的查询语句，定义后可方便地调用。

### 5.2 定义与使用

- 定义 view （视图）：

  ```sql
  create [or replace] view 视图名 as 
  -- 定义的 sql 子查询语句
  select * from emp 
  [with read only]
  ```

- 使用视图：

  ```sql
  select * from 视图名
  ```



## 6. 索引

### 6.1 概述

在表的列上构建一个二叉树，可以大幅**提高查询的效率**，却会影响增删改的效率。

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
  	...
  begin
  	过程名(参数);
  end;
  ```



## 9. 存储函数

### 9.1 概述

- 存储函数与存储过程的主要区别在于**存储函数可以有一个返回值**，而存储过程没有返回值。
- 可以利用 out 函数，实现在函数和过程中返回多个值。

### 9.2 范例

```plsql
create or replace function empincome(eno in emp.empno%type) return number is
	psal emp.sal%type;
	pcomm emp.comm%type;
begin
	select t.sal into psal from emp t where t.empno = eno;
	return psal * 12 + nvl(pcomm, 0);
end;
```

### 9.3 调用

```plsql
declare
	income number;
begin
	empincome(123, income);
	dbms_output.put_line(income);
end;
```



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



## 11. Oracle jdbc

### 11.1 数据库版本与 ojdbc 版本的对应

- Oracle 10g - ojdbc 14
- Oracle 14g - ojdbc 6

### 11.2 连接信息

db.properties

```properties
driver = oracle.jdbc.OracleDriver
url = jdbc:oracle:thin:@地址:1521:数据库
username = ...
password = ...
```

### 11.3 调用存储过程概述

plsql

```plsql
create or replace procedure proc_countyearsal(eno in number,esal out number) as
begin    
	select sal*12+nvl(comm,0) into esal from emp where empno=eno; 
end;
```

java

```java
public class Test {
    String driver = ...;
    String url = ...;
    ...
    public void test throws Exception() {
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url,username,password);
        
        // 定义预编译的存储过程
        CallableStatement callSt = conn.prepareCall("call proc_countyearsal(?,?)");
        // 设置参数
        callSt.setInt(1, 123);
        // 注册返回值
        callSt.registerOutParameter(2, OracleTypes.NUMBER);
        // 执行
        callSt.execute();
        // 输出返回值
        System.out.println(callSt.getObject(2));
        
    }
}
```



## #. 补充

### #.1 方言：外连接的简略写法

```sql
select e.*,d.* from emp e and dept d where e.empno(+) = d.deptno
```

### #.2 方言：Rownum 与分页查询

- rownum 是一个伪列，存在于每一张表中。

- 若要在查询结果中显示 rownum 列，直接在 select 后面写上 rownum 即可，可以同时带上表的别名。

- rownum 从 1 开始。

- rownum 不支持使用 `>` 的逻辑判断，所以需要使用子查询来实现分页。

- 分页示例：

  ```sql
  -- 查询第六到第十条信息
  select * from (select rownum r,emp.* from emp) t
  where b.r > 5 and b.r < 11
  ```

### #.3 Win7 Oracle 11g 安装笔记

- 安装后解锁需要使用的账户。

- 安装后更改 `product/${version}/${home}/NETWORK/ADMIN` 下的配置文件，以保证外部连接正常。

  - listener.ora

    ```
    # listener.ora Network Configuration File: C:\Users\Photo\Desktop\Oracle\product\11.2.0\dbhome_1\network\admin\listener.ora
    # Generated by Oracle configuration tools.
    
    SID_LIST_LISTENER =
      (SID_LIST =
        (SID_DESC =
          (SID_NAME = ORCL)
          (ORACLE_HOME = C:\Users\Photo\Desktop\Oracle\product\11.2.0\dbhome_1)
    #      (PROGRAM = extproc)
          (ENVS = "EXTPROC_DLLS=ONLY:C:\Users\Photo\Desktop\Oracle\product\11.2.0\dbhome_1\bin\oraclr11.dll")
        )
      )
    
    LISTENER =
      (DESCRIPTION_LIST =
        (DESCRIPTION =
          (ADDRESS = (PROTOCOL = IPC)(KEY = EXTPROC1521))
          (ADDRESS = (PROTOCOL = TCP)(HOST = 192.168.25.131)(PORT = 1521))
        )
      )
    
    ADR_BASE_LISTENER = C:\Users\Photo\Desktop\Oracle
    ```

    - 更改 `SID_NAME`、`HOST`、`PORT`、注释`(PROGRAM = extproc) `。

  - sqlnet.ora

    ```
    # sqlnet.ora Network Configuration File: C:\Users\Photo\Desktop\Oracle\product\11.2.0\dbhome_1\network\admin\sqlnet.ora
    # Generated by Oracle configuration tools.
    
    # This file is actually generated by netca. But if customers choose to 
    # install "Software Only", this file wont exist and without the native 
    # authentication, they will not be able to connect to the database on NT.
    
    SQLNET.AUTHENTICATION_SERVICES= (NONE)
    
    NAMES.DIRECTORY_PATH= (TNSNAMES, EZCONNECT)
    ```

    - `SQLNET.AUTHENTICATION_SERVICES= (NONE)` 。

- 变更配置后需要停止并启动服务 `OracleOraDb11g_home1TNSListener` 和 `OracleServiceXXXX`。