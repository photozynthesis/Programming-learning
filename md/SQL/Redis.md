# Redis

[TOC]

## 1. *NOSQL概述

### 1.1 NOSQL概述

NOSQL（Not Only SQL），“不仅仅是SQL”，泛指非关系型数据库，是一项全新的数据库理念。NOSQL数据库可以解决一些传统数据库难以解决的问题。

### 1.2 NOSQL与关系型数据库的比较

- **优点**：
  - **成本**：NOSQL数据库简单易部署，基本都是开源软件，不需要像使用oracle那样花费大量成本购买使用，相比关系型数据库价格便宜。
  - **查询速度**：NOSQL数据库将数据存储于**内存**之中，关系型数据库将数据存储在硬盘中，NOSQL的查询速度明显更快。
  - **存储数据的格式**：nosql的存储格式是key,value形式、文档形式、图片形式等等，所以可以存储基础类型以及对象或者是集合等各种格式，而数据库则只支持基础类型。
  - **扩展性**：基于键值对，数据之间没有耦合性，所以非常容易水平扩展。
  - **性能**：NOSQL是基于键值对的，可以想象成表中的主键和值的对应关系，而且不需要经过SQL层的解析，所以性能非常高。
- **缺点**：
  - 维护的工具和资料有限，因为nosql是属于新的技术，不能和关系型数据库10几年的技术同日而语。
  - 不提供对sql的支持，如果不支持sql这样的工业标准，将产生一定用户的学习和使用成本。
  - 不提供关系型数据库对事务的处理。
- **总结**：
  - 关系型数据库与NoSQL数据库并非对立而是**互补的关系**，即通常情况下使用关系型数据库，在适合使用NoSQL的时候使用NoSQL数据库，让NoSQL数据库对关系型数据库的不足进行弥补。
  - 一般会将数据存储在关系型数据库中，**在nosql数据库中备份存储关系型数据库的数据**。

### 1.3 主流的NOSQL

- **键值(Key-Value)存储数据库**：
  - 相关产品：Tokyo Cabinet/Tyrant、Redis、Voldemort、Berkeley DB
  - 典型应用：内容**缓存**，主要用于处理大量数据的高**访问**负载。 
  - 优势： 快速查询
  - 劣势： 存储的数据缺少结构化
- **列存储数据库**：
  - 相关产品：Cassandra, HBase, Riak
  - 典型应用：分布式的文件系统
  - 数据模型：以列簇式存储，将同一列数据存在一起
  - 优势：查找速度快，可扩展性强，更容易进行分布式扩展
  - 劣势：功能相对局限
- **文档型数据库**：
  - 相关产品：CouchDB、MongoDB
  - 典型应用：Web应用（与Key-Value类似，Value是结构化的）
  - 数据模型： 一系列键值对
  - 优势：数据结构要求不严格
  - 劣势： 查询性能不高，而且缺乏统一的查询语法
- **图形(Graph)数据库**：
  - 相关数据库：Neo4J、InfoGrid、Infinite Graph
  - 典型应用：社交网络
  - 数据模型：图结构
  - 优势：利用图结构相关算法。
  - 劣势：需要对整个图做计算才能得出结果，不容易做分布式的集群方案。



## 2. Redis概述

### 2.1 概述

- Redis是用C语言开发的一个开源的高性能**键值对（key-value）数据库**。
- 官方提供测试数据，50个并发执行100000个请求,读的速度是110000次/s,写的速度是81000次/s 。
- Redis通过提供多种键值数据类型来适应不同场景下的存储需求.
- **应用场景**：
  - **缓存**（数据查询、短连接、新闻内容、商品内容等等）
  - 聊天室的在线好友列表
  - 任务队列（秒杀、抢购、12306等等）
  - 应用排行榜
  - 网站访问统计
  - 数据过期处理（可以精确到毫秒）
  - 分布式集群架构中的session分离

### 2.2 下载与安装

- 下载地址：
  - 官网：https://redis.io/
  - 中文网：http://www.redis.net.cn/
- 解压下载的压缩包即可。
- 相关文件说明：
  - redis.windows.conf：配置文件
  - redis-cli.exe：redis的客户端
  - redis-server.exe：redis服务器端



## 3. Redis数据类型与操作

redis存储的是**key,value格式**的数据，其中**key都是字符串**，value有**5种**不同的数据结构。

以下列举value的5种数据结构及其命令操作方式。

### 3.1 string：字符串类型

- 存储：

  ```
  > set username zhangsan/"zhangsan"
  OK
  ```

- 获取：

  ```
  > get username
  "zhangsan"
  ```

- 删除：

  ```
  > del username
  (integer) 1
  ```

### 3.2 hash：哈希类型

hash 是一个string类型的field和value的映射表，hash特别**适合用于存储对象**。

类似Map类型。

- 存储：

  ```
  // hset key field value
  > hset myhash username "zhangsan"
  (integer) 1
  > hset myhash password 123
  (integer) 1
  ```

- 获取：

  ```
  // 根据指定field获取值
  // hget key field
  > hget myhash username
  "zhangsan"
  
  // 获取所有field和value
  // hgetall key
  > hgetall myhash
  1) "username"
  2) "zhangsan"
  3) "password"
  4) "123"
  ```

- 删除：

  ```
  // hdel key field
  > hdel myhash username
  (integer) 1
  ```

### 3.3 List：列表类型

可以添加一个元素到列表的头部（左边）或者尾部（右边）。

- 添加：

  ```
  // 将元素添加到列表左/右边
  // lpush/rpush key value
  > lpush myList a
  (integer) 1
  > lpush myList b
  (integer) 2
  > lpush myList c
  (integer) 3
  ```

- 获取：

  ```
  // 范围获取
  // lrange key start end
  > lrange myList 0 -1
  1) "a"
  2) "b"
  3) "c"
  ```

- 删除：

  ```
  // 删除列表最左边的元素，并将元素返回
  // lpop key
  
  // 删除列表最右边的元素，并将元素返回
  // rpop key
  ```

### 3.4 set：集合类型

set**不允许**重复元素。

- 存储：

  ```
  // sadd key value
  > sadd myset a
  (integer) 1
  > sadd myset a
  (integer) 0
  > sadd myset b
  (integer) 1
  ```

- 获取：

  ```
  // smembers key
  > smembers mySet
  1) "a"
  2) "b"
  ```

- 删除：

  ```
  // 删除set中的某个元素
  // srem key value
  > srem mySet a
  (integer) 1
  ```

### 3.5 srotedset：有序集合类型

**不允许重复**元素，且元素**有顺序**。

每个元素都会关联一个double类型的分数（score）。redis正是通过分数来为集合中的成员进行从小到大的排序。

- 存储：

  ```
  // zadd key score value
  > zadd mysort 60 zhangsan
  (integer) 1
  > zadd mysort 80 lisi
  (integer) 1
  > zadd mysort 70 wangwu
  (integer) 1
  > zadd mysort 90 zhangsan
  (integer) 0
  ```

- 获取：

  ```
  // 可选是否获取分数
  // zrange key start end [withscores]
  > zrange mysort 0 -1
  1) "wangwu"
  2) "lisi"
  3) "zhangsan"
  > zrange mysort 0 -1 withscores
  1) "wangwu"
  2) "70"
  3) "lisi"
  4) "80"
  5) "zhangsan"
  6) "90"
  ```

- 删除：

  ```
  // zrem key value
  > zrem mysort wangwu
  (integer) 1
  ```



## 4. Redis通用命令

- 查询所有的键

  > keys *

- 获取键对应的value的类型

  > type key

- 删除指定的key-value

  > del key



## 5. Redis数据持久化

Redis是一个内存数据库，当Redis服务器重启后数据会丢失，不过可以将内存中的数据持久化保存到硬盘的文件中。

Redis提供了两种持久化的方式：

- **RDB**：

  - RDB是**默认**的持久化方式，在一定的间隔时间中，检测key的变化情况，然后持久化数据。

  - 使用方式：

    编辑redis.windows.conf文件，查找一下内容并编辑：

    ```
    #   after 900 sec (15 min) if at least 1 key changed
    save 900 1
    #   after 300 sec (5 min) if at least 10 keys changed
    save 300 10
    #   after 60 sec if at least 10000 keys changed
    save 60 10000
    ```

- **AOF**：

  - 日志记录方式，具体见下方。

  - 非默认模式，启用后服务器将使用这种模式。

  - 开启方法：

    编辑redis.windows.conf文件，更改以下配置项：

    ```
    appendonly no --> appendonly yes
    ```

  - 更改配置：

    ```
    # appendfsync always	#每一次操作都进行持久化
    appendfsync everysec	#每隔一秒进行一次持久化
    # appendfsync no		#不进行持久化
    ```



## 6. Jedis概述

Jedis是一款Java操作Redis数据库的工具。

简要入门如下：

- 导入Jedis包/添加依赖

- 使用：

  ```java
  // 获取连接
  Jedis jedis = new Jedis("localhost", 6379);
  // 操作
  ...
  // 关闭连接
  jedis.close();
  ```



## 7. Jedis操作数据

Jedis操作五类数据结构的方式如下：

### 7.1 操作string

```java
// 存储
jedis.set("username", "zhangsan");
// 获取
String username = jedis.get("username");
// 存储指定秒数后过期的key value
jdeis.setex("activecode", 20, "hehe");	// 20s后删除
```

### 7.2 操作hash

```java
// 存储
jedis.hset("user", "name", "zhangsan");
jedis.hset("user", "age", "23");
jedis.hset("user", "gender", "male");
// 获取
String name = jedis.hget("user", "name");
// 获取为Map
Map<String, String> user = jedis.hgetAll("user");
```

### 7.3 操作list

```java
// 存储
jedis.lpush("myList", "a", "b", "c");	//从左边存
jedis.rpush("myList", "a", "b", "c");	//从右边存
// 范围获取为List
List<String> myList = jedis.lrange("myList", 0, -1);
// 弹出
String element1 = jedis.lpop("myList");
String element2 = dedis.rpop("myList");
```

### 7.3 操作set

```java
// 存储
jedis.sadd("mySet", "java", "php", "c++");
// 获取为set
Set<String> mySet = jedis.smembers("mySet");
```

### 7.4 操作sortedset

```java
// 存储
jedis.zadd("mySortedset", 10, "zhangsan");
jedis.zadd("mySortedset", 20, "lisi");
jedis.zadd("mySortedset", 30, "wangwu");
// 范围获取为Set
Set<String> mySortedset = jedis.zrange("mysortedSet", 0, -1);
```



## 8. Jedis：连接池

JedisPool

### 8.1 入门

- 使用步骤：

  1. 创建配置对象

  2. 创建连接池对象

  3. 获取连接

  4. 使用

  5. 关闭连接（归还到连接池）

- 示范：

  ```java
  // 1.创建配置对象
  JedisPoolConfig config = new JedisPoolConfig();
  config.setMaxTotal(50);
  config.setMaxIdle(10);
  
  // 2.创建连接池对象
  JedisPool jedisPool = new JedisPool(config, "localhost", 6379);
  
  // 3.获取连接
  Jedis jedis = jedisPool.getResource();
  
  // 4.使用
  ...
  
  // 5.关闭（归还）
  jedis.close();
  ```

### 8.2 示范工具类

- JedisPoolUtils.java

  ```java
  public class JedisPoolUtils {
      
      private static JedisPool jedisPool;
      
      static {
          // get config from properties
          InputStream is = JedisPoolUtils.class.getClassLoader()
          	.getResourceAsStream("jedis.properties");
          Properties prop = new Properties();
          try {
              prop.load(is);
          } catch (IOException e) {
              e.printStackTrace();
          }
          JedisPoolConfig config = new JedisPoolConfig();
          config.setMaxTotal(Integer.parseInt(
              prop.getProperty("maxTotal")));
          config.setMaxIdle(Integer.parseInt(
              prop.getProperty("maxIdle")));
          jedisPool = new JedisPool(config, 
          	prop.getProperty("host"), 
              Integer.parseInt(prop.getProperty("port")));
      }
      
      public static Jedis getJedis () {
          return jedisPool.getResource();
      }
      
  }
  ```

- jedis.properties

  ```properties
  maxTotal = 50
  maxIdle = 10
  host = localhost
  port = 6379
  ```

