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

### 3.7 编码相关

- `set character_set_results = 'GBK'`，设置查询结果的编码方式，注意重新通过DOS登录后将失效
- `show variables like '%char%';`查看MySQL相关字符编码方式

### 3.8 关于SQL脚本

- 将SQL语句依次写入一个.sql文件可以创建一个SQL脚本
- `source xxx.sql`可以执行SQL脚本（快速的逐行执行脚本中的语句）

### 3.9 获取当前系统时间

- 使用函数`now()`，返回的是当前时间的date对象



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



## 6.数据处理函数/单行处理函数

常用：

|                   函数名                    |                       说明                        |
| :-----------------------------------------: | :-----------------------------------------------: |
|                 lower(内容)                 |                     转成小写                      |
|                 upper(内容)                 |                     转成大写                      |
|        substr(内容, 起始下标, 长度)         |                    截取字符串                     |
|                 trim(内容)                  |                   去除前后空格                    |
|                length(内容)                 |                      取长度                       |
|              round(内容,位数)               |                     四舍五入                      |
|                   rand()                    |                生成0~1之间的随机数                |
|             ifnull(内容,替换值)             |                将null转换成具体值                 |
| str_to_date('日期字符串'，'日期格式字符串') |     将varchar（字符串）转换成date（日期类型）     |
|   date_format(日期类型,'日期格式字符串')    | 将date（日期类型）转换成varchar（特定格式字符串） |

### 6.1 str_to_date函数

MySql默认的日期格式如下：

| 格式 | 含义 |
| :--: | :--: |
|  %Y  |  年  |
|  %m  |  月  |
|  %d  |  日  |
|  %H  |  时  |
|  %i  |  分  |
|  %s  |  秒  |

举例：

```sql
'%Y-%m-%d'    //MySQL默认日期格式
```



## 7.分组函数/聚合函数/多行处理函数

常用：

| 函数名 |    说明    |
| :----: | :--------: |
| count  | 取得记录数 |
|  sum   |    求和    |
|  avg   |   取平均   |
|  max   | 取最大的数 |
|  min   | 取最小的数 |

注意事项：

- 分组函数会忽略NULL（空值）
- 分组函数若处理了*值，则一般不忽略NULL
- 分组函数不能直接用于where后面（选择语句中）



## 8.重要关键字

- distinct

  放在字段前面，可以去除查询结果中的重复记录。举例：

  ```sql
  select distinct job from emp;	//查询员工有多少种职位
  ```

  注意distinct前面不能有其他字段，distinct作用于后面的多个字段。

- in

  后跟括号，括号内写多个字段值，用于过滤查询结果。

  ```sql
  select ename,job from emp where job in('MANAGER','SALESMAN');
  //查询工作为MANAGER和SALESMAN的员工
  ```

- union

  用于将两个查询结果进行合并。

  将union关键字放在两条select语句之间即可。

  两条select语句查询的**字段数必须相等**。若是在oracle数据库中，字段的**类型也必须相等**。

  ```sql
  select ename,job from emp where job='MANAGER'
  union
  select ename,job from emp where job='SALSEMAN';
  //查询职位为MANAGER和SALESMAN的员工
  ```

- limit

  limit只在MySQL数据库中存在，用来获取一张表的部分数据。

  在select语句的最后加上`limit 起始下标(从0开始),条目个数;`即可。

  ```sql
  select ename,sal from emp order by sal desc limit 0,5;
  //查询工资排名前五名的员工
  //此处的[limit 0,5]也可以写作[limit 5]
  ```

  



## 9.查询(DQL语句)

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

  1. 查询语句后跟`where 条件语句`即可，举例：

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

  2. 查询语句后跟having...

     这种方式先分组后过滤，不像where是先过滤再分组因而不能包含多行处理函数（分组函数）。

- **排序查询**：

    显示排序后的查询结果。

    查询语句后跟`order by 排序字段1 方式1,排序字段2 方式2,...;`

    排序方式有esc（升序）、desc（降序），默认为升序。

    越靠前的字段作用越大。

- **分组查询**：

    分组查询使用group by子句，后跟字段，根据字段不同的值来分组。

    注意事项：

    ​	若一条DQL与剧中有group by子句，那么select后面只能跟参与分组的字段和分组函数。

**总结**：

​	完整的DQL语句具有如下顺序：

```sql
select
	...
from
	...
where
	...
group by
	...
having
	...
order by
	...;
```

​	执行顺序：

1. from			//从某张表中检索数据
2. where                     //通过某条件进行过滤
3. group by                //分组
4. having                    //分组后再过滤
5. select                      //查询出来
6. order by                 //排序输出

**注意事项**：

1. 查询语句中字段名后可跟计算表达式，例如`select 字段*12 from table1;`，将显示计算后的表。**不会**修改表中的数据。
2. 可在变量名后跟`as 重命名`来让查询结果显示重命名的字段名，不用as也可以。**不会**修改表中的数据。
3. 若要显示中文的重命名，中文需要用单引号`'`括起来。MySQL中双引号`"`也可以。
4. between...and...语句中，若判断的是数字，则左右皆包；若判断的是字符，则包左不包右。所以主要用于判断数字。



## 10.连接查询

**概述**：

​	通过多张表连接起来获得有效数据，又称为跨表查询。

**根据连接方式分类**：

- **内连接**

  概述：将A表和B表**能够完全匹配的记录**查询出来

  - 等值连接
  - 非等值连接
  - 自连接

- **外连接**

  概述：除了查询出A表和B表**能够完全匹配的记录**外，无条件地将**其中一张表的记录完全查询出来**。若对方表没有匹配记录，则自动模拟出null值与之匹配。

  - 左外连接（左连接）
  - 右外连接（右连接）

- 全连接（很少使用）

**注意事项**：

1. 连接查询的时候可以给表名起别名，例如：

   ```sql
   select e.ename,d.dname from emp e,dept d where e.deptno=d.deptno;
   // SQL92
   // 通过部门编号对应部门名称表和员工部门编号表，查询员工对应的部门名称
   ```

2. SQL92语句与SQL99语句的不同：

   SQL99的语句格式为：

   ```sql
   select ...,... from ... (inner/outer) (left/right) join ... on 限制/过滤条件 join ... on 限制/过滤条件;
   ```

   若在join前加上left/right，则为**左/右外连接**，分别表示无条件显示左/右表中的全部记录。

   相比于SQL92语句，SQL99后可跟where继续过滤，结构也更清晰。

   内连接中的**inner可以省略**。

   同理外连接中的**outer也可以省略**。

3. 若要进行多于两张表的连接，格式见上条。

   注意是**第一个表分别与后面的多张表进行连接**，而不是后面的表进行连接。

4. 可以给一张表起不同的别名，来进行**自连接**。



## 11.子查询

子查询就是将select语句嵌套在select语句中。

相当于将select语句的结果当作一张临时新表，参与到大的select语句中。

将子select语句左右加上括号即可（句尾不要分号）。



## 12.创建表

MySQL的建表语句如下：

```sql
create table tableName(
	columnName dataType(length),
	columnName dataType(length),
	...
	columnName dataType(length) default defaultValue;	//设置具有默认值的字段
);
```

注意事项：

1. 表格的名字最好以`t_`或者`tbl_`开头，增强可读性。
2. 需要存储中文的地方的varchar，其长度最好是2的倍数。
3. 定义字段语句后跟default+默认值，可以设置某一字段的默认值，实例见上。



## 13.删除表

删除表的语句如下：

```sql
drop table 表名;	//若数据库中没有这张表，则报错
drop table if exists 表名;	//为MySQL特有，如果存在这张表则删除，不会报错
```



## 14.MySQL常用数据类型

- varchar

  可变长度字符串。

  其会**自动判断内容的长度**，以决定占用的存储空间。不过判断过程会消耗一些系统资源。

  适合于长度在**一定范围内变化**的内容。

- char

  定长字符串。

  长度**固定不变**，若实际长度不足则会自动补充空格。

  适合于**定长**的内容。

- int

  整数型

- bigint

  长整型

- float

  单精度浮点型

- double

  双精度浮点型

- date

  日期类型（一般用字符串取代）

- blob

  Binary Large Object 二进制大对象

  一般用来存储视频、图片等大文件。

- clob

  Character Large Object 字符大对象

  可以存储比较大的文本。



## 15.向表格中插入数据

使用insert语句(DML)，格式：

```
insert into 表名(字段名1,字段名2,...) values(值1,值2,...);
```

注意事项：

1. 字段和值必须一一对应，个数必须相同，类型必须匹配。
2. 若插入数据的时候没有给出某个字段的值，且该字段没有约束的话，默认为null。
3. 若省略表名后的括号，则相当于**按照顺序包含了全部字段**。**不建议省略**这个括号，若省略了这条语句会因为表扩充字段而失效（报错），不利于健壮性。



## 16.复制表

使用以下格式复制表：

```sql
create table tableName as select columnName1,columnName ... from tableName;
//将select语句返回的表直接赋值给新表
```



## 17.将查询结果插入到某张表中

使用以下格式：

```sql
insert into 表名 select语句;
```



## 18.增/删/改表的结构

1. 给表增加一个字段：

   ```sql
   alter table 表名 add 字段名 数据类型(长度);
   ```

2. 更改某个字段的数据类型：

   ```sql
   alter table 表名 modify 字段名 新的数据类型(新的长度);
   ```

3. 删除表中某字段：

   ```sql
   alter table 表名 drop 字段名;
   ```

   

## 19.删/改表中的数据

### 19.1 修改表中的数据

```sql
update 表名 set 字段名1=值1,字段名2=值2,... where 条件;
```

注意事项：

1. 若没有where限制条件，则会修改表中**所有记录**。

### 19.2 删除表中的数据

```sql
delete from 表名 where 条件;
```

注意事项：

1. 若没有where限制条件，则会删除表中**所有记录**。



## 20.约束

约束（constraint），对表中字段进行**限制**。

约束分为**列级约束**和**表级约束**，列级约束跟在建表语句字段定义后面，表级约束独占建表语句一行，具体如下。

### 20.1 添加约束

- 列级约束：在建表语句中将约束条件加在字段描述的后面，举例：

  ```sql
  create table tbl_student(
  	name int(10) not null,
  	num int(10) unique
  )
  ```

- 表级约束：在建表语句中独占一行，可对多个字段联合添加约束，也可以给约束起名字

  ```sql
  create table xxx(
  	...,
  	(constraint 约束名) 约束类型(约束字段1,约束字段2,...)
  )
  ```

  

- 使用alter语句

  ```sql
  alter table 表名 add cnostraint 约束名 约束类型(约束字段) 附加信息;
  ```

### 20.2 删除约束

- 使用alter语句

  ```sql
  alter table 表名 drop primary key;		//删除主键约束
  alter table 表名 drop foreign key 约束名;	//删除外键约束
  ```

### 20.3 修改约束

- 其实就是修改字段：

  ```sql
  alter table 表名 modify 字段名 新的字段数据类型(新的长度) 新的约束信息;
  ```

- 还可以删除字段重新添加（感觉不太好）

### 20.4 约束类型

- **非空约束：not null**

  not null和unique约束可以同时存在。

- **唯一性约束：unique**

  注意unique约束的字段不能重复，不过可以存在多个null。

- **主键约束（PK，primary key）**

  **概述：**

  - 表中某个字段在添加**主键约束**后，该字段就被成为**主键字段**，该字段中的每一个数据都被成为**主键值**。
  - “**主键值**”是表中**当前行的唯一标识**，相当于“身份证号”。
  - 主键字段自带not null unique（非空唯一），不过其不完全相同于not null unique修饰的字段。
  - 一张表应该要有主键字段，若没有，表示这张表是无效的。

  **分类：**

  - 按照约束的字段数量分：
    - 单一主键：即给单一字段添加主键约束
    - 复合主键：给多个字段联合添加约束
  - 根据主键性质分：
    - 自然主键：主键为自然数，和当前表的业务没有任何任何关系
    - 业务主键：主键值和当前表的业务紧密相关，使用较少

  **添加方式：**

  - 单一主键约束：建表语句中，在某个字段后添加`primary key`，也可以使用表级约束的方法。

    ```sql
    create table tbl_student(
    	id int(10) primary key,		//向学生的id添加主键标识
    	name varchar(20)
    )
    
    create table tbl_student(
    	id int(10),
    	name varchar(20),
    	(constraint xxx) primary key(id)
    							//也可以使用表级约束的方法添加
    )
    ```

  - 复合主键约束：只能使用表级约束的方法添加（因为有多个字段）。

    ```sql
    create table tbl_student(
    	id int(10),
    	name varchar(20),
    	email varchar(40),
    	(constraint xxx) primary key(id,name)		//添加复合主键约束
    )
    ```


  **其他事项：**

  1. MySQL提供了一个自增的数字，用来自动生成主键值。

     ```sql
     create table tbl_student(
     	id int(10) primary key auto_increment,	//自增的id
     	name varchar(20)
     )
     ```

     自增的数字默认从1开始，以1递增，每个数字只能使用一次（即若删除了某条记录，对后续记录没有影响）。

- **外键约束（FK，foreign key）**

  **概述：**

  - 外键约束用于将表中**某个字段的值**限制在**另一张表中某个字段值**的范围之内（例如学生表中有个字段班级编号，范围限制在班级编号与班级名对应表中）。
  - 某个字段添加**外键约束**之后，这个字段就称为**外键字段**，该字段下所有值成为**外键值**。
  - 外键约束的好处是减少了数据冗(rong3)余。

  **添加方式：**

  ```sql
  create table tbl_student(
  	id int(10) primary key,
  	name varchar(20),
  	classno int(3),
  	(constraint 约束名) foreign key(字段名) references 表名(被参考字段名)
  	//添加外键约束，若参考多个字段，用逗号分隔即可
  )
  ```

  外键约束只能以表级约束的方式添加，不能用列级约束。

  **注意事项：**

  - 外键值**可以为NULL**。
  - 外键字段所引用的另一张表中的字段，必须具有**unique**约束。
  - 引用外键的表称为子表，被引用的表称为父表。创建表时应先创建父表后创建子表，插入数据的顺序也应是先父后子，而删除数据的时候应先删除子表中的数据。

### 20.5 相关事项

1. 带有名字的表级约束，可以在MySQL自带数据库information_schema中的表table_constraints中进行相关操作。



## 21.级联更新/删除

概述：

​	一个关于外键约束中父子表关系的概念。

​	在更新/删除父表中数据的时候，级联更新/删除子表中的数据。

​	谨慎使用。

使用：

- 建表时在约束定义后加上附加信息：on delete/update cascade

  ```sql
  create table 表名(
  	...,
  	constraint 约束名 foreign key(字段名) references 表名(被参考字段) on delete/update cascade
  )
  ```

- 也可以直接（删除约束之后重新）添加约束

  ```sql
  alter table 表名 add constraint 约束名 foreign key(字段名) references 表名(被参考字段) on delete/update cascade;
  ```

  

## 22.存储引擎

**概述：**

​	MySQL特色，在创建表的时候可以指定这张表的存储引擎（即数据的存储方式）。

​	Java程序员需要了解的常用存储引擎有MyISAM、MEMORY和InnoDB。

**常用操作：**

- 查看有哪些存储引擎可用：

  ```sql
  show engines\G
  ```

- 查看某张表所用的存储引擎， 可以通过show create：

  ```sql
  show create table 表名;
  ```


**常用存储引擎：**

- MyISAM
  - 每张表使用三个文件表示：mytable.frm（存储表结构定义），mytable.MYD（存储表行的内容），mytable.MYI（存储表上的索引）
  - 可以压缩、设置成只读表来节省空间
- InnoDB
  - MySQL默认存储引擎
  - 支持事务
  - 支持级联更新、级联删除，支持外键及引用的完整性
  - 每个表以.frm文件表示
  - 在MySQL服务器崩溃后提供自动恢复
- MEMORY
  - 每个表以.frm文件表示
  - 表数据及索引存储在内存中，速度快



## 23.事务

**概述：**

​	事务（Transaction），是一个最小的不可再分的工作单元，一个事务通常对应了一个完整的业务。

​	一个完整的业务需要批量的DML（insert, delete, update）语句来协同完成，事务只和DML语句有关，只有DML语句才有事务。

​	事务中的DML语句执行后只能同时成功，或同时失败。在使用事务进行操作时，操作的结果暂时在内存中；直到最后一条DML语句执行成功，才会将内存中的结果同步到数据库，若有DML语句执行失败，则删除所有的操作记录（不同步）。

**事务的四个特性：**

- 原子性（A）

  事务是最小的工作单元，不可再分。

- 一致性（C）

  事务种的DML语句必须同时成功或同时失败。

- 隔离性（I）

  事务A和事务B之间具有隔离。

- 持久性（D）

  是事务的保证，事务终结的标志。

**提交和回滚：**

- 提交（commit）：

  提交标志着一个事务成功的结束，将事务中DML语句的操作记录同步到硬盘上的文件。

- 回滚（rollback）：

  回滚标志着一个事务失败的结束，清空事务中所有DML语句的操作记录。

  事务中任何一条DML语句执行标志着事务的开始，而提交和回滚都标志着事务的结束。

**使用方法：**

​	MySQL中默认是开启自动提交的，即正常执行一条DML语句相当于开启了一个事务并进行了提交。使用下面几个方法来使用事务：

1. 手动开启事务：

   ```sql
   start transaction;
   DML语句...;
   commit/rollback;
   ```

2. 在当前窗口中关闭自动提交：

   ```sql
   
   ```

   

