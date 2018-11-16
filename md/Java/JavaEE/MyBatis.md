# MyBatis

[TOC]

## 1. MyBatis 概述

- MyBatis是一个可以**自定义SQL、存储过程和高级映射**的持久层框架。
- MyBatis 摒除了大部分的JDBC代码、手工设置参数和结果集重获。**MyBatis只使用简单的XML和注解来配置和映射基本数据类型、Map 接口和POJO 到数据库记录**。



## 2. MyBatis 入门

MyBatis简单的查询案例：

1. maven添加MyBatis依赖：

   ```xml
   <!-- 版本号及最新信息可在官网查询 -->
   <dependency>
       <groupId>org.mybatis</groupId>
       <artifactId>mybatis</artifactId>
       <version>3.4.6</version>
   </dependency>
   ```

2. 在数据库创建表，并插入数据；

3. 创建JavaBean（实现Serializable）；

4. 编写Dao（持久层接口）：

   ```java
   public interface UserDao {
       /**
       *	查询并返回所有用户的List
       */
       List<User> getAll();
   }
   ```

5. 在Resources根目录（src/main/java）创建核心配置文件`SqlMapConfig.xml`，内容如下：

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <!DOCTYPE configuration     PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-config.dtd">
   <configuration>
       <!-- 配置 mybatis 的环境 -->
       <environments default="mysql">
           <!-- 配置 mysql 的环境 -->
           <environment id="mysql">
               <!-- 配置事务的类型 -->
               <transactionManager type="JDBC">
               </transactionManager>
               <!-- 配置连接数据库的信息：用的是数据源(连接池) -->
               <dataSource type="POOLED">
                   <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                   <property name="url" value="jdbc:mysql://localhost:3306/test?serverTimezone=CST"/>
                   <property name="username" value="root"/>
                   <property name="password" value="rootzxcvbnm"/>
               </dataSource>
           </environment>
       </environments>
       <!-- 告知 mybatis 映射配置的位置 -->
       <mappers>
           <mapper resource="io/github/photozynthesis/dao/UserDao.xml"/>
       </mappers>
   </configuration>
   ```

   说明：

   - 每添加一个Mapper，需要在`mappers`标签下添加一条`mapper`。

6. 在Dao接口的同目录下，创建同名的xml文件（mapper）。在Resources文件夹中创建时，注意需要创建相同的目录结构。

   UserDao.xml：

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <!DOCTYPE mapper
           PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
   <mapper namespace="io.github.photozynthesis.dao.UserDao">
       <!-- 配置查询所有操作 -->
       <select id="getAll" resultType="io.github.photozynthesis.domain.User">
           select * from user where id = #{id}
       </select>
   </mapper>
   ```

   说明：

   - `mapper`标签的属性`namespace`的值需要为Dao接口的完整包路径。
   - `select`标签的属性`id`的值需要为与Dao中相同的方法名。
   - `select`标签的属性`resultType`的值需要为查询结果JavaBean的完整包路径，若查询结果为List等，会自动封装。
   - 可以使用`#{参数}`作为SQL占位符，参数名需要为方法传递的形参名。

7. 进行测试。

   测试分为多个步骤：

   ```java
   import io.github.photozynthesis.dao.UserDao;
   import io.github.photozynthesis.domain.User;
   import org.apache.ibatis.io.Resources;
   import org.apache.ibatis.session.SqlSession;
   import org.apache.ibatis.session.SqlSessionFactory;
   import org.apache.ibatis.session.SqlSessionFactoryBuilder;
   
   import java.io.IOException;
   import java.io.InputStream;
   import java.util.List;
   
   public class Test {
       public static void main(String[] args) {
           // 1.读取配置文件
           InputStream is = null;
           try {
               is = Resources.getResourceAsStream("SqlMapConfig.xml");
           } catch (IOException e) {
               e.printStackTrace();
               System.exit(1);
           }
   
           // 2.创建SqlSessionFactoryBuilder对象
           SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
   
           // 3.使用构建者创建SqlSessionFactory
           SqlSessionFactory factory = builder.build(is);
   
           // 4.生产SqlSession
           SqlSession session = factory.openSession();
   
           // 5.使用SqlSession创建Dao的代理对象
           UserDao userDao = session.getMapper(UserDao.class);
   
           // 6.使用代理对象执行查询所有的方法
           List<User> list = userDao.getAll();
           for (User user : list) {
               System.out.println(user);
           }
   
           // 7.释放资源
           session.close();
           try {
               is.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
   }
   ```



## 3. SqlMapConfig.xml 配置文件

SqlMapConfig.xml是MyBatis的核心配置文件，包含很多框架基本的配置信息。

放在Resources根目录（/src/main/java下）。

### 3.1 内容与顺序

```
-properties（属性）
	--property
-settings（全局配置参数）
	--setting 
-typeAliases（类型别名）
	--typeAliase
	--package
-typeHandlers（类型处理器）
-objectFactory（对象工厂）
-plugins（插件） 
-environments（环境集合属性对象）  
	--environment（环境子属性对象）   
		---transactionManager（事务管理）   
		---dataSource（数据源） 
-mappers（映射器）  
	--mapper  
	--package
```

具体可以参考http://mybatis.org/dtd/mybatis-3-config.dtd约束文件。

### 3.2 properties

- **概述**：

  配置name - value键值对，用以在xml中可以使用`${name}`来表示value。

- **编写方式**：

  - 方式一：在properties中写property，编写name和value属性。

    ```xml
    <!-- SqlMapConfig.xml -->
    <properties>
    	<property name="jdbc.driver" value="com.mysql.cj.jdbc.driver" />
        <property ... />
    </properties>
    ```

  - 方式二：编写外部properties文件，通过properties的url属性引入。

    ```properties
    # db.properties
    jdbc.driver=com.mysql.cj.jdbc.driver
    ...
    ```

    ```xml
    <!-- SqlMapConfig.xml -->
    <properties url="file:///D:/.../db.properties"></properties>
    ```

- **使用**：

  在任意位置`"${name}"`即可。

  ```xml
  <environments>
  	<environment>
      	<dataSource type="POOLED">
          	<property name="driver" value="${jdbc.driver}" />
              <property ... />
          </dataSource>
      </environment>
  </environments>
  ```

### 3.3 typeAliases

- **概述**：

  可以给类自定义别名。

  别名定义后，直接使用即可。

- **编写**：

  - 定义单个类的别名

    ```xml
    <typeAliases>
    	<typeAlias alias="user" type="io.github.photozynthesis.domain.User" />
        <typeAlias ... />
    </typeAliases>
    ```

  - 批量定义一整个包下所有类的别名（别名为类名，不区分大小写）

    ```xml
    <typeAliases>
    	<package name="io.github.photozynthesis.domain" />
        <package ... />
    </typeAliases>
    ```

### 3.4 mappers

- **概述**：

  定义映射。

- **编写**：

  - 指定相对于classpath的资源（xml）

    ```xml
    <mappers>
    	<mapper resource="io/github/photozynthesis/dao/UserDao.xml" />
    </mappers>
    ```

  - 指定mapper接口的类路径

    ```xml
    <mappers>
    	<mapper class="io.github.photozynthesis.dao.UserDao" />
    </mappers>
    ```

    说明：需要接口与映射文件名称相同，且放在同一个目录中。

  - （package）注册指定包下所有接口

    ```xml
    <mappers>
    	<package name="io.github.photozynthesis.dao" />
    </mappers>
    ```

    说明：需要接口与映射文件名称相同，且放在同一个目录中。



## 4. Mapper：映射器概述

### 4.1 概述

- **概述**：
  - 映射器是MyBatis最强大的工具，也是使用最多的工具。
  - 通过映射器，可以很容易地进行数据的增删改查操作
- **使用概述**：
  - 映射器是由**Java接口**和**XML文件（或注解）**共同组成的，Java接口主要定义调用者接口，XML文件是配置映射器的核心文件。
  - 使用MyBatis，只需定义接口和配置文件即可方便地进行增删改查操作。借此可以更多的关注于SQL语句本身。

### 4.2 格式

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="io.github.photozynthesis.dao.UserDao">
    <其他标签... />
</mapper>
```

### 4.3 主要元素

- **select**：查询语句
  - 参数：自定义
  - 返回：结果集
- **insert**：插入语句
  - 返回：整数（插入的条数）
- **update**：更新语句
  - 返回：整数（更新的条数）
- **delete**：删除语句
  - 返回：整数（删除的条数）
- **sql**：定义SQL引用
- **resultMap**：从数据库结果集中加载对象，并配置关联关系
- **cache**：给定命名空间的缓存配置



## 5. Mapper：基本增删改查

### 5.1 重要元素属性介绍

- **id**：
  - 说明：对应Dao接口中的方法名。

- **parameterType**：传入参数类型
  - 范围：基本类型及包装类、String、Map、JavaBean
  - 说明：
    - **基本类型**和**String**可以直接写类名，也可以使用完整包路径名。
    - 若传入基本类型或String，占位符中的表达式可以随便写，不过推荐使用"value"。
    - JavaBean若没有取别名，则必须使用完整路径。
    - MyBatis默认已经为很多常用的Java类取了别名，如基本类型、集合相关、Date、BigDecimal等。具体可以查阅文档。
- **resultType**：返回结果类型
  - 范围：基本类型及包装类、JavaBean
  - 说明：
    - 当类型为JavaBean时，实体类中的**属性名称**必须要和**查询结果中的列名**一致，否则无法实现封装。
    - 若JavaBean没有取别名，则必须使用完整类路径。
- **resultMap**：结果集映射
  - 说明：强大的工具，不能和resultType一起使用。
  - **下文将以单独章节介绍**。
- more...

### 5.2 重要说明

- **ognl表达式**：
  - 它是 apache 提供的一种表达式语言，全称是：   Object Graphic Navigation Language  对象图导航语言。

  - 它是按照一定的语法格式来获取数据的。
  -  语法格式：#{对象.对象}
  - **在MyBatis映射器中，若已在`parameterType`指定了参数类型，可以直接在占位符`#{}`或`${}`中填入参数的字段名**。

- **占位符`#{}`与`${}`**：

  - `#{}`：
    - 表示一个占位符，类似于？。
    - 实现 preparedStatement 向占位符中设置值。
    - 自动进行 java 类型和 jdbc 类型转换.
    - 可以有效防止 sql 注入。
    - 可以接收简单类型值或 pojo 属性值。
  - `${}`：
    - 表示拼接 sql 串。 
    - 不进行 jdbc 类型转换。
    - 可以接收简单类型值或 pojo 属性值。
    - sql 注入风险。

### 5.3 代码示例

- SQL

  | id   | username | age  | birthday   |
  | ---- | -------- | ---- | ---------- |
  | 1    | Jack     | 20   | 2010-10-10 |
  | 2    | Rose     | 22   | 2008-08-08 |

- User.java

  ```java
  public class User {
      String username;
      int age;
      Date birthday;
      
      constructor ...
      getter & setter ...
      toString ...
  }
  ```

- Test.java

  ```java
  public class Test {
      
      SqlSession sqlSession;
      
      // 来自junit，在所有Test方法执行之前执行
      @Before
      public void init() {
          // 1. 读取核心配置文件
          InputStream is = null;
          try {
              is = Resources.getResourceAsStream("SqlMapConfig.xml");
          } catch (IOException e) {
              e.printStackTrace();
          }
          
          // 2. 创建工厂建造者对象
          SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
          
          // 3. 使用建造者，以inputstream创建工厂
          SqlSessionFactory factory = builder.build(is);
          
          // 4. 生产SqlSession
          sqlSession = factory.openSession();
      }
      
      // 来自junit，在Test方法执行完毕后执行
      @After
      public void destroy() {
          sqlSession.close();
      }
  }
  ```

#### 5.3.1 查询

- UserDao.java

  ```java
  /**
  *	查询所有用户
  */
  public interface UserDao {
      List<User> getAllUsers();
  }
  ```

- UserDao.xml

  ```xml
  <?xml ...?>
  <!DOCTYPE ...>
  <mapper namespace="io.github.photozynthesis.dao.UserDao">
  	<select id="getAllUsers" resultType="io.github.photozynthesis.domain.User">			SELECT * FROM user
      </select>
  </mapper>
  ```

- Test.java

  ```java
  ...
      public void queryTest() {
      	UserDao userDao = sqlSession.getMapper(UserDao.class);
      	List<User> list = userDao.getAllUsers();
      	// 遍历输出，省略
      	...
  	}
  ...
  ```

#### 5.3.2 增加

- UserDao.java

  ```java
  /**
  *	添加一个用户
  */
  public interface UserDao {
      int insertUser(User user);
  }
  ```

- UserDao.xml

  ```xml
  <?xml ...?>
  <!DOCTYPE ...>
  <mapper namespace="io.github.photozynthesis.dao.UserDao">
  	<select id="insertUser" parameterType="io.github.photozynthesis.domain.User">
          INSERT INTO user(username,age,birthday) values(#{username},#{age},#{birthday})
      </select>
  </mapper>
  ```

- Test.java

  ```java
  ...
      public void queryTest() {
      	UserDao userDao = sqlSession.getMapper(UserDao.class);
      	User user = new User(...);
      	...
      	int rowsAffected = userDao.insertUser(user);
      	sqlSession.commit();
  	}
  ...
  ```

#### 5.3.3 删除

- UserDao.java

  ```java
  /**
  *	添加一个用户
  */
  public interface UserDao {
      int insertUser(User user);
  }
  ```

- UserDao.xml

  ```xml
  <?xml ...?>
  <!DOCTYPE ...>
  <mapper namespace="io.github.photozynthesis.dao.UserDao">
  	<delete id="deleteUser" parameterType="java.lang.Integer">
          DELETE FROM user WHERE uid = #{随便写}
      </delete>
  </mapper>
  ```

- Test.java

  ```java
  ...
      public void deleteTest() {
      	UserDao userDao = sqlSession.getMapper(UserDao.class);
      	int rowsAffected = userDao.deleteUser(2);
  	}
  ...
  ```

#### 5.3.4 修改

- UserDao.java

  ```java
  /**
  *	修改一个用户
  */
  public interface UserDao {
      int updateUser(User user);
  }
  ```

- UserDao.xml

  ```xml
  <?xml ...?>
  <!DOCTYPE ...>
  <mapper namespace="io.github.photozynthesis.dao.UserDao">
  	<update id="updateUser" parameterType="io.github.photozynthesis.domain.User">
          update user set username = #{username},age = #{age},birthday = #{birthday} where uid = #{uid}
      </update>
  </mapper>
  ```

- Test.java

  ```java
  ...
      public void queryTest() {
      	UserDao userDao = sqlSession.getMapper(UserDao.class);
      	User user = new User(...);
      	...
      	int rowsAffected = userDao.updateUser(user);
      	sqlSession.commit();
  	}
  ...
  ```

#### 5.3.5 模糊查询、聚合函数查询等不再赘述



## 6. Mapper：resultMap

### 6.1 概述

- resultMap 标签可以建立查询的**列名**和实体类的**属性名**称**不一致**时建立**对应关系**。从而实现封装。 

- resultMap 还可以实现**集合属性**和 **bean 属性**的封装。

- 编写后如何使用？在`select`标签中指定 resultMap 的 id 引用即可。

- 示例：

  ```xml
  <resultMap type="io.github.photozynthesis.domain.User" id="userMap">
      <id column="id" property="userId"/>
      <result column="username" property="userName"/>
      <result column="sex" property="userSex"/>
      <result column="address" property="userAddress"/>
      <result column="birthday" property="userBirthday"/>
  </resultMap>
  ```

### 6.2 重要属性介绍

- **type**：

  指定 JavaBean 的完整类名。

- **id**：

  此 resultMap 的唯一标识，用于给 select 标签进行引用。

### 6.3 重要子标签及子标签属性

- **子标签**：

  - **id**：

    指定查询结果中的主键字段。

  - **result**：

    指定查询结果中的**非**主键字段。

  - **association**：

    下方独立小节详细描述。

  - **collection**：

- **重要子标签属性**：

  - **column**：

    指定查询结果表中的字段名。

  - **property**：

    指定实体类的属性名称。

### 6.4 重要标签：association 和 collection

#### 6.4.1 association

- **概述**：

  - association 位于 resultMap 根标签下，就像是映射一个字段/属性一样。

  - 用于 多**对一**/一**对一** 的查询。当查询的结果 JavaBean 包含类型为其他 JavaBean 的属性时，可进行封装。

  - 举例：一个用户可能有多个账户，但一个账户一般只为一个用户所拥有。此时若要查询一个账户的相关信息，同时包含该账户所属的用户的相关信息时，就需要用到 association 。

- **示例**：

  AccountWithUser.java：

  ```java
  public class AccountWithUser {
      // Account 自身的属性
      private ...
          
      // Account 所属的 User
      private User user;
  }
  ```

  UserDao.xml：

  ```xml
  ...
  <resultMap id="accountMap" type="io.github.photozynthesis.domain.AccountWithUser">
  	<!-- 映射其他字段 -->
      <id ...></id>
      <result ...></result>
      ...
      <!-- 映射 User 对象 -->
      <association property="user" javaType="io.github.photozynthesis.domain.User">
      	<id column="..." property="..." />
          <result column="..." property="..." />
          ...
      </association>
  </resultMap>
  
  <select id="findAll" resultMap="accountMap">
  	select u.*,a.id aid,a.money from account a,user u where a.uid = u.id
  </select>
  ...
  ```

- **说明**：

  - association 的 property 属性为指定 JavaBean 的属性名称（用于封装）。
  - association 的 javaType 属性为 JavaBean的完整名称。
  - association 的子标签相关内容与前文所述内容完全一致，无需赘述。

#### 6.4.2 collection

- **概述**：

  - collection 位于 resultMap 根标签下，就像是映射一个字段/属性一样。
  - 用于 一**对多**/多**对多** 的查询。当查询的结果 JavaBean 包含类型为其他 JavaBean集合 的属性时，可进行封装。
  - 举例：一个用户可能有多个账户。此时若要查询一个用户的相关信息，同时包含该用户所有的账户的相关信息时，就需要用到 collection 。

- **示例**：

  UserWithAccounts.java：

  ```java
  public class UserWithAccounts {
      // User 自身的属性
      private ...
          
      // 所有的 Account 集合
      private List<Account> accounts;
  }
  ```

  UserDao.xml：

  ```xml
  ...
  <resultMap id="userMap" type="io.github.photozynthesis.domain.UserWithAccounts">
  	<!-- 映射其他字段 -->
      <id ...></id>
      <result ...></result>
      ...
      <!-- 映射 List<Account> 集合 -->
      <collection property="accounts" ofType="io.github.photozynthesis.domain.Account">
      	<id column="..." property="..." />
          <result column="..." property="..." />
          ...
      </association>
  </resultMap>
  
  <select id="findAll" resultMap="userMap">
  	select a.*,u.id id,u.username username from user u,account a where a.uid=u.id
  </select>
  ...
  ```

- **说明**：

  - collection 的 property 属性为指定 集合 的名称（用于封装）。
  - collection 的 ofType 属性为 集合的泛型类名。
  - collection 的子标签相关内容与前文所述内容完全一致，无需赘述。



## 7. MyBatis：连接池相关

### 7.1 MyBatis连接池的分类

- UNPOOLED

  不使用连接池的数据源

- POOLED

  使用连接池的数据源，一般使用这种

- JNDI

  使用JNDI实现的数据源

### 7.2 设置数据源类型

在核心配置文件中的`SqlMapConfig.xml`中：

```xml
<dataSource type="POOLED">
	...
</dataSource>
```



## 8. MyBatis：事务相关

### 8.1 设置自动提交

使用`SqlSessionFactory`创建 SqlSession 时，传入参数 `true`，即在此 SqlSession 中事务会自动提交。

```java
SqlSession sqlSession = sqlSessionFactory.openSession(true);
```

注意：

- 此处若不传入参数，事务将不自动提交。

### 8.2 手动提交与回滚

若没有开启事务自动提交，可以在操作后使用`SqlSession`对象调用`commit()`或`rollback()`来手动提交/回滚。

```java
...
sqlSession.commit();
```



## 9. Mapper：动态SQL

动态SQL可以实现某些复杂的逻辑。

### 9.1 < if > 标签

示例如下：

```xml
<select id="findByUser" resultType="user" parameterType="user">
	SELECT * FROM user where 1=1
    <!-- 若username字段不为空则拼接子句执行模糊查询 -->
    <if test="username != null and username != '' ">
    	and username like #{username}
    </if>
    <!-- 若address字段不为空则拼接子句执行模糊查询 -->
   	<if test="address != null">
    	and address like #{address}
    </if>
</select>
```

说明：

- if标签写在select、delete、update、insert等标签下。
- 若其 test 属性中的表达式返回true，则拼接标签体中的字符串。
- test 表达式的内容为**类Java代码**，不同之处在于`与`和`或`使用`and`和`or`而不是`&&`和`||`。
- 若参数类型为JavaBean，则注意 if 标签中需要遵循OGNL表达式。
- test 属性中的标识符由于只是变量名，不是具体值，所以直接写字段名即可。

### 9.2 < where > 标签

引入where标签之后，就不用再写利于拼接字符串的`where 1=1`了。

示例如下：

```xml
<select id="findByUser" resultType="user" parameterType="user">
	SELECT * FROM user
    <where>
        <!-- 若username字段不为空则拼接子句执行模糊查询 -->
        <if test="username != null and username != '' ">
            and username like #{username}
        </if>
    </where>
</select>
```

### 9.3 < foreach > 标签

foreach 标签用于填充一个传入的集合中的值。

典型的使用场景如通过多个 id 查询多个用户，此时将多个 id 装入集合，并将集合作为 JavaBean 的一个属性随 Bean 一并传入。之后就可以通过 foreach 标签遍历出数据，并动态生成 SQL 语句。

示例如下：

- QueryVo.java

  ```java
  public class QueryVo implements Serializable {
      private List<Integer> ids;
      // getters and setters ...
      ...
  }
  ```

- UserDao.java

  略。

- UserDao.xml

  ```xml
  ...
  <select id="findByIds" resultType="user" parameterType="queryVo">
  	SELECT * FROM user
      <where>
      	<if test="ids != null and ids.size() > 0">
          	<foreach collections="ids" open="id in (" close=")" item="uid" separator=",">
              	#{uid}
              </foreach>
          </if>
      </where>
  </select>
  ...
  ```

说明：

- collection：要遍历的集合，来自 JavaBean 的一个属性。
- open：语句开始的部分，可以认为是循环开始前拼接的字符串。
- close：语句结束的部分，可以认为是循环结束后拼接的字符串。
- separator：代表分隔多个循环元素间的字符串。
- item：代表从集合遍历出的元素，随意取名，可写在循环体中的占位符`#{}`中。



## 10. Mapper：重用SQL片段

重用 SQL 可以将重复的 SQL 语句提取出来，使用的时候直接用 include 标签引入即可。

**sql 标签**：

```xml
<sql id="defaultSql">
	SELECT * FROM user
</sql>
```

**引用实例**：

```xml
<select ...>
	<include refid="defaultSql"></include>
    <!-- 其他拼接操作 -->
    ...
</select>
```

**说明**：

- sql 标签的 id 为改标签的唯一标识，供 include 标签引用。
- include 标签的 refid 属性填写上述 sql 标签的 id。



## #. 延迟加载





## #. 缓存





## #. Mapper：注解方式



