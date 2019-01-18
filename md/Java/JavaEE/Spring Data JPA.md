# Spring Data JPA

[TOC]

## 1. ORM & JPA 概述

### 1.1 ORM

#### 1.1.1 ORM 概述

- ORM（Object-Relational Mapping）表示对象关系映射。
- ORM 建立实体类和数据库表之间的关系，以实现通过操作实体类来操作数据库表。

#### 1.1.2 ORM 的好处

- 可以大幅减少重复代码。

#### 1.1.3 常见 ORM 框架

- MyBatis（ibatis）
- Hibernate
- JPA

### 1.2 JPA

#### 1.2.1 JPA 概述

- JPA（Java Persistence API），即 Java 持久化 API，是 SUN 公司推出的一套基于 ORM 的规范，由一系列接口和抽象类构成。
- 描述对象与关系表之间的映射关系，将运行期的对象持久化到数据库中。

#### 1.2.2 JPA 的好处

- 就像 JDBC，任何遵循了 JPA 标准的应用都可以很方便地在 JPA 框架下运行。
- 使用简单方便。
- 可以使用 SQL、JPQL 查询语言，提供非常强大的查询能力。
- 支持高级特性。

#### 1.2.3 常见实现

- Hibernate



## 2. JPA 开发的入门

**以下为 JPA 的保存实体到数据库案例，使用 Hibernate 作为 JPA 实现。**

### 2.1 导入 jar 包

- junit
- log4j
- **hibernate-entitymanager**
- **hibernate-c3p0**
- **mysql-connector-java**

```xml
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  <project.hibernate.version>5.0.7.Final</project.hibernate.version>
</properties>

<dependencies>
    <!-- junit -->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
        <scope>test</scope>
    </dependency>
    <!-- hibernate对jpa的支持包 -->
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-entitymanager</artifactId>
        <version>${project.hibernate.version}</version>
    </dependency>
    <!-- c3p0 -->
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-c3p0</artifactId>
        <version>${project.hibernate.version}</version>
    </dependency>
    <!-- log日志 -->
    <dependency>
        <groupId>log4j</groupId>
        <artifactId>log4j</artifactId>
        <version>1.2.17</version>
    </dependency>
    <!-- Mysql and MariaDB -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.6</version>
    </dependency>
</dependencies>
```

### 2.2 创建数据库表

```sql
// 创建客户表
CREATE TABLE tb_customer (
	customer_id bigint(22) NOT NULL AUTO_INCREMENT,
    customer_name varchar(32) NOT NULL,
    customer_phone varchar(64) DEFAULT NULL,
    PRIMARY KEY ('customer_id')
) ENGINE-InnoDB AUTO_INCREMENT=1 CHARSET=utf8
```

### 2.3 创建对应实体类，并使用注解配置映射关系

```java
@Entity
@Table(name = "tb_customer")
public class Customer implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;
    
    @Column(name = "customer_name")
    private String customerName;
    
    @Column(name = "customer_phone")
    private String customerPhone;
    
    // getter and setter ...
    ...
}
```

### 2.4 创建 persistence.xml 配置文件

在 classpath 创建 `MATA-INF` 文件夹，并在其中创建 `persistence.xml` 文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="http://java.sun.com/xml/ns/persistence" 
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
xsi:schemaLocation="http://java.sun.com/xml/ns/persistence  http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd" version="2.0">
    <!--配置持久化单元 
  name：持久化单元名称 
  transaction-type：事务类型
    RESOURCE_LOCAL：本地事务管理 
    JTA：分布式事务管理 -->
    <persistence-unit name="myJpa" transaction-type="RESOURCE_LOCAL">
        <!--配置JPA规范的服务提供商 -->
 		<provider>
     		org.hibernate.jpa.HibernatePersistenceProvider
        </provider>
        <properties>
            <!-- 数据库驱动 -->
            <property name="javax.persistence.jdbc.driver" value="com.mysql.jdbc.Driver" />
            <!-- 数据库地址 -->
            <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/ssh" />
            <!-- 数据库用户名 -->
            <property name="javax.persistence.jdbc.user" value="root" />
            <!-- 数据库密码 -->
            <property name="javax.persistence.jdbc.password" value="111111" />

            <!--jpa提供者的可选配置：我们的JPA规范的提供者为hibernate，所以jpa的核心配置中兼容hibernate的配 -->
            <property name="hibernate.show_sql" value="true" />
            <property name="hibernate.format_sql" value="true" />
            <property name="hibernate.hbm2ddl.auto" value="create" />
        </properties>
	</persistence-unit>
</persistence>
```

### 2.5 编写测试类完成保存操作

```java
public class Test {
    @Test
    public void test() {
        // 创建 EntityManager 和 EntityTransaction
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("myJpa");
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        // 进行保存操作
        tx.begin();
        Customer c = new Customer();
        c.set...(...);
        em.persist(c);
        tx.commit();
        
        // 释放资源
        em.close();
        factory.close();
    }
}
```



## 3. JPA 常用注解

### 3.1 类声明上的注解

- **@Entity**
  - 指定当前类是实体类
- **@Table**
  - 指定实体类和表之间的对应关系
  - 属性：
    - name - 数据库表名

### 3.2 字段上的注解

- **@Id**

  - 指定当前为主键字段

- **@GeneratedValue**

  - 指定当前主键的生成策略

  - 属性：

    - strategy - 主键的生成策略，可选值：

      - GenerationType.INDENTITY - 主键由数据库自动生成

      - GenerationType.SEQUENCE - 由数据库的序列来生成，使用方法见下方
      - GenerationType.AUTO - 主键由程序控制，默认使用 TABLE 的方法
      - GenerationType.TABLE - 使用一个特定的数据库表格来保存主键，使用方法见下方

    - generator - 指定主键生成器

- **@Column**

  - 指定属性和表中字段的关系
  - 属性：
    - name - 表中的列名
    - unique
    - nullable
    - insertable - 是否可以插入
    - updateable - 是否可以更新
    - columnDefinition - 创建此表时的 DDL
    - secondaryTable - 从表名

### 3.3 附：设置序列生成主键

```java
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "increcing_id")
    @SequenceGenerator(name = "increcing_id", sequenceName = "seq_custom_id")
    private Long customerId;
}
```

### 3.4 附：设置特定表生成主键

```java
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "increcing_id")
    @TableGenerator(
    	name = "increcing_id",
        table = "tb_generator",
        pkColumnName = "gen_name",
        valueColumnName = "gen_value",
        pkColumnValue = "INCRECING_PK",
        allocationSize = 1
    )
    private Long customerId;
}
```



## 4. JPA API 常用对象

### 4.1 Persistence

- 主要用于通过配置的 persistence-unit 获取 EntityManagerFactory 对象。

| 方法                                                         | 说明                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| static **createEntityManagerFactory**(String persistenceUnitName) | 获取 EntityManagerFactory 对象，该方法线程安全，创建较为耗费资源 |

### 4.2 EntityManagerFactory

- 主要用来创建 EntityManager 实例

| 方法                  | 说明                    |
| --------------------- | ----------------------- |
| createEntityManager() | 创建 EntityManager 实例 |

### 4.3 EntityManager

- JPA 规范中完成持久化操作的核心对象
- 普通实体对象只有在调用 EntityManager 将其持久化后才变成持久化对象

| 方法                | 说明         |
| ------------------- | ------------ |
| getTransaction()    | 获取事务对象 |
| persist()           | 保存操作     |
| merge()             | 更新操作     |
| remote()            | 删除操作     |
| find/getReference() | 根据 id 查询 |

### 4.4 EntityTransaction

- JPA 中事务操作的核心对象

| 方法       | 说明     |
| ---------- | -------- |
| begin()    | 开启事务 |
| commit()   | 提交事务 |
| rollback() | 回滚事务 |



## 5. JPA 复杂查询 & JPQL

### 5.1 概述

- JPQL（Java Persistence Query Language），即 Java 持久化查询语言。
- JPQL 是可移植的，可以被编译成所有主流数据库服务器上的 SQL。

### 5.2 如何使用 JPQL 进行复杂查询

示例：

```java
String  jpql = "from Customer where customerName like ?";
Query query = em.createQuery(jpql);
query.setParameter(1, "张%");
List list = query.getResultList();
```

### 5.3 JPQL 规则

**大致规则如下**：

- 去除 SQL 语句前面的 "SELECT x"
- 将后面部分中的表名改为实体类名
- 将后面部分中的表列名改为实体类属性名



## 6. Spring Data JPA 概述

### 6.1 Spring Data JPA 概述

- Spring Data JPA 是在 ORM 框架、JPA 规范的基础上封装的一套 JPA 应用框架。
- 使得开发者仅需简单的代码就可以实现对数据库的增删改查操作。

### 6.2 Spring Data JPA 与 Hibernate 与 JPA 之间的关系

- 如同 JDBC，JPA 也是一套规范，Hibernate 是该规范的一个实现。
- Spring Data JPA 是对 **JPA** 更加高级的封装。



## 7. Spring Data JPA 入门使用

### 7.1 引入依赖

- **Spring 相关**
- **JPA 提供者（Hibernate）**
- **数据库驱动**

```xml
<properties>
    <spring.version>4.2.4.RELEASE</spring.version>
    <hibernate.version>5.0.7.Final</hibernate.version>
    <slf4j.version>1.6.6</slf4j.version>
    <log4j.version>1.2.12</log4j.version>
    <c3p0.version>0.9.1.2</c3p0.version>
    <mysql.version>5.1.6</mysql.version>
</properties>
<dependencies>
	<!-- junit单元测试 -->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.9</version>
        <scope>test</scope>
    </dependency>
    <!-- Spring -->
    <dependency>
        <groupId>org.aspectj</groupId>
        <artifactId>aspectjweaver</artifactId>
        <version>1.6.8</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-aop</artifactId>
        <version>${spring.version}</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>${spring.version}</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context-support</artifactId>
        <version>${spring.version}</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-orm</artifactId>
        <version>${spring.version}</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-beans</artifactId>
        <version>${spring.version}</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-core</artifactId>
        <version>${spring.version}</version>
    </dependency>
    <!-- hibernate -->
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>${hibernate.version}</version>
    </dependency>
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-entitymanager</artifactId>
        <version>${hibernate.version}</version>
    </dependency>
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-validator</artifactId>
        <version>5.2.1.Final</version>
    </dependency>
    <!-- c3p0 -->
    <dependency>
        <groupId>c3p0</groupId>
        <artifactId>c3p0</artifactId>
        <version>${c3p0.version}</version>
    </dependency>
    <!-- log -->
    <dependency>
        <groupId>log4j</groupId>
        <artifactId>log4j</artifactId>
        <version>${log4j.version}</version>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>${slf4j.version}</version>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-log4j12</artifactId>
        <version>${slf4j.version}</version>
    </dependency>
    
    <!-- mysql-connector -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>${mysql.version}</version>
    </dependency>
    <!-- spring-data-jpa -->
    <dependency>
        <groupId>org.springframework.data</groupId>
        <artifactId>spring-data-jpa</artifactId>
        <version>1.9.0.RELEASE</version>
    </dependency>
    <!-- spring-test -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-test</artifactId>
        <version>4.2.4.RELEASE</version>
    </dependency>
    <!-- el -->
    <dependency>  
        <groupId>javax.el</groupId>  
        <artifactId>javax.el-api</artifactId>  
        <version>2.2.4</version>  
    </dependency>  
    <dependency>  
        <groupId>org.glassfish.web</groupId>  
        <artifactId>javax.el</artifactId>  
        <version>2.2.4</version>  
    </dependency> 

</dependencies>

```

### 7.2 编写配置文件

applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:aop="http://www.springframework.org/schema/aop"
	xmlns:context="http://www.springframework.org/schema/context"
	xmlns:jdbc="http://www.springframework.org/schema/jdbc" xmlns:tx="http://www.springframework.org/schema/tx"
	xmlns:jpa="http://www.springframework.org/schema/data/jpa" xmlns:task="http://www.springframework.org/schema/task"
	xsi:schemaLocation="
		http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd
		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
		http://www.springframework.org/schema/jdbc http://www.springframework.org/schema/jdbc/spring-jdbc.xsd
		http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd
		http://www.springframework.org/schema/data/jpa 
		http://www.springframework.org/schema/data/jpa/spring-jpa.xsd">
    
    <!-- dataSource -->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<property name="driverClass" value="com.mysql.jdbc.Driver" />
		<property name="jdbcUrl" value="jdbc:mysql://localhost:3306/jpa" />
		<property name="user" value="root" />
		<property name="password" value="root" />
	</bean>
	
    <!-- entityManagerFactory -->
    <bean id="entityManagerFactory" class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
		<property name="dataSource" ref="dataSource" />
		<property name="packagesToScan" value="cn.itcast.entity" />
		<property name="persistenceProvider">
			<bean class="org.hibernate.jpa.HibernatePersistenceProvider" />
		</property>
		<!--JPA的供应商适配器-->
		<property name="jpaVendorAdapter">
			<bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter">
				<property name="generateDdl" value="false" />
				<property name="database" value="MYSQL" />
				<property name="databasePlatform" value="org.hibernate.dialect.MySQLDialect" />
				<property name="showSql" value="true" />
			</bean>
		</property>
		<property name="jpaDialect">
			<bean class="org.springframework.orm.jpa.vendor.HibernateJpaDialect" />
		</property>
	</bean>
    
    <!-- JPA 事务管理器 -->
	<bean id="transactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
		<property name="entityManagerFactory" ref="entityManagerFactory" />
	</bean>

    <!-- spring data jpa -->
    <jpa:repositories base-package="cn.itcast.dao"
		transaction-manager-ref="transactionManager"
		entity-manager-factory-ref="entityManagerFactory">
    </jpa:repositories>
    
    <!-- 配置事务通知 txAdvice -->
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
		<tx:attributes>
			<tx:method name="save*" propagation="REQUIRED"/>
			<tx:method name="insert*" propagation="REQUIRED"/>
			<tx:method name="update*" propagation="REQUIRED"/>
			<tx:method name="delete*" propagation="REQUIRED"/>
			<tx:method name="get*" read-only="true"/>
			<tx:method name="find*" read-only="true"/>
			<tx:method name="*" propagation="REQUIRED"/>
		</tx:attributes>
	</tx:advice>

    <!-- 配置 AOP 切面 -->
    <aop:config>
		<aop:pointcut id="pointcut" expression="execution(* cn.itcast.service.*.*(..))" />
		<aop:advisor advice-ref="txAdvice" pointcut-ref="pointcut" />
	</aop:config>
    
    <!-- 包扫描 -->
    <context:component-scan base-package="cn.itcast"></context:component-scan>

</beans>
```

### 7.3 编写映射实体类

见上方，略。

### 7.4 创建 DAO 层接口

- 创建的 DAO 接口仅需实现 **JpaRepository** 和 **JpaSpecificationExecutor** 接口，并提供相应泛型。

```java
/*
	JpaRepository<实体类类型, 主键类型> - 用于完成基本 CRUD 操作
	JpaSpecificationExecutor<实体类类型> - 用于复杂查询（分页查询等）
*/
public interface CustomerDao extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    
}
```

- 实现的两个接口包含 findOne、findAll、save、saveAll、delete、deleteById 等操作，所以可以在不编写任何方法的情况下就实现一些基本的 CRUD 操作。

### 7.4 调用接口完成 CRUD 操作

查询示例：

```java
@Runwith(...)
@ContextConfiguration(...)
public class CustomerDaoTest {
    
    @Autowired
    private CustomerDao customerDao;
    
    @Test
    public void testFindById() {
        Customer customer = customerDao.findOne(11);
        sout(customer);
    }
}
```



## 8. Spring Data 使用 JPQL、SQL即方法命名的方式查询

- Spring Data JPA 的 DAO 除了可以使用提供的方法进行操作外，还可以通过以下方式构建灵活的查询条件。

### 8.1 自定义 JPQL 查询

- 使用方法：在 DAO 中自定义方法，并使用 **@Query** 注解，设置 value 属性为 目标 JPQL 语句。

- 若要进行更新操作，需要同时使用 **@Modifying** 注解。

- 示例：

  ```java
  public interface CustomerDao extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
      
      // 示例 1
      @Query(value = "from Customer where customerName = ?")
      public Customer findCustomer(String customerName);
      
      // 示例 2
     	@Query(value = "update Customer set customerName = ?1 where customerId = ?2")
      @Modifying
      public void updateCustomer(String name, Long id);
      
  }
  ```

### 8.2 使用 SQL 语句查询

- 与 JPQL 查询的唯一区别在于需要在 @Query 注解中添加属性 **nativeQuery = true**。

- 示例：

  ```java
  public interface CustomerDao extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
      
      @Query(value = "select * from tb_customer", nativeQuery = true)
      public List<Customer> findCustomers();
      
  }
  ```

### 8.3 方法命名规则查询

- 根据 Spring Data JPA 提供的方法命名规则定义方法名称，就可以定义相应的复杂查询。

- 方法名应以 findBy 等开头，使用下表列出的关键字连接。

- 可以使用的关键字如下：

  | 序号 | 分类       | 关键字                                                       |
  | ---- | ---------- | ------------------------------------------------------------ |
  | 1    | 连接类     | And、Or                                                      |
  | 2    | 比较类     | Is、Equals、Between、LessThan、LessThanEqual、GreaterThan、GreaterThanEqual、After、Before、Not、In、NotIn |
  | 3    | 空值判断   | IsNull、IsNotNull、NotNull                                   |
  | 4    | 模糊查询   | Like、NotLike、StartingWith、EndingWith、Containing          |
  | 5    | 顺序       | OrderBy                                                      |
  | 6    | 布尔判断   | True、False                                                  |
  | 7    | 大小写忽略 | IgnoreCase                                                   |

- 示例：

  ```java
  public interface CustomerDao extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
      
      
      
  }
  ```



## 9. Spring Data JPA 动态、分页查询

- 使用 Specification 和 JpaSpecificationExecutor 的动态查询

  示例如下：

  ```java
  @Runwith(...)
  @ContextConfiguration(...)
  public class Test {
      
      @AutoWired
      private CustomerDao customerDao;
      
      // 1. 定义 Specification，通过匿名内部类的方式
      Specification<Customer> spec = new Specification<Customer>() {
          @Override
          public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder db) {
              // 构建查询
              return cb.like(root.get("customerName").as(String.class), "张%");
          }
      }
      
      // 2. 调用 CustomerDao 进行查询
      Customer customer = customerDao.findOne(spec);
      
      // 输出
      sout(customer);
      
  }
  ```

- 使用 Specification 和 JpaSpecificationExecutor 的分页查询

  使用方式大体与上方动态查询无异，只是查询前需要传递分页查询对象。

  ```java
  @Runwith(...)
  @ContextConfiguration(...)
  public class Test {
      
      @AutoWired
      private CustomerDao customerDao;
      
      // 1. 定义 Specification，通过匿名内部类的方式
      Specification<Customer> spec = new Specification<Customer>() {
          @Override
          public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder db) {
              // 构建查询
              return cb.like(root.get("customerName").as(String.class), "张%");
          }
      }
      
      // 2. 创建分页查询对象
      // Pageable pageable = new PageRequest(0, 5);
      Pageable pageable = PageRequest.of(0, 5);
      
      // 3. 调用 CustomerDao 进行查询
      Page<Customer> page = customerDao.findAll(spec, pageable);
      
      // 4. 通过 page 获取结果输出测试
      sout(page.getTotalPages());
      sout(page.getTotalElements());
      sout(page.getContent());
      
  }
  ```



## 10. Spring Data JPA 多表查询

待补全