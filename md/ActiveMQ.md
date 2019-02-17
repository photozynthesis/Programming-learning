# ActiveMQ

[TOC]

## 1. 消息中间件概述

### 1.1 消息中间件概述

- 分布式系统中负责发送和接受消息的服务。
- 利用高效可靠的消息传递机制，进行平台无关的数据交流。并基于数据通信来进行分布式系统的集成。

### 1.2 使用消息中间件的好处

- 改善软件系统中模块间的调用关系，降低耦合。

### 1.3 常用的消息中间件产品

- ActiveMQ
  - Apache 出品，流行，能力强劲。
  - 完全支持 JMS1.1 和 J2EE1.4 规范。
- RabbitMQ
  - AMQP 协议的领导实现，支持多种场景。
- ZeroMQ
  - 很快。
- Kafka
  - 高吞吐，适合海量数据。



## 2. JMS 概述

### 2.1 概述

- JMS 是 Java 平台中有关面向消息中间件的技术规范，提供标准的产生、发送、接受消息的接口，简化应用开发。

### 2.2 JMS 的消息组成

- JMS 中的消息由两部分组成：
  - 报头：路由信息及有关该消息的元数据。
  - 消息主体：包含应用程序的数据或有效负载。
- JMS 中的消息正文支持五种格式：
  - TextMessage: 一个字符串对象
  - MapMessage: 一组键值对
  - ObjectMessage: 一个序列化的 Java 对象
  - BytesMessage: 一个字节的数据流
  - StreamMessage: Java 原始值的数据流

### 2.3 JMS 消息传递类型

#### 2.3.1 点对点消息传递

- 每条消息只有一个消费者。
- 生产者将消息发送到某个特定的队列（queue），一般这个队列对应唯一的消费者。
- 接收者最后确认消息接受和处理成功。

#### 2.3.2 发布/订阅消息传递

- 支持向一个特定主题（topic）生产消息，以及多个消费者接受来自一个主题的消息。
- 客户端只有订阅主题后才能接收到消息。
- 可选持久订阅和非持久订阅。



## 2. ActiveMQ 安装/启动/简单查看

**Linux 安装过程如下**:

1. 官网下载 tar.gz 包。

2. 解压。

3. 赋予执行权限。

   ```shell
   chmod 777 apache-activemq-x.xx.x
   cd apache-activemq-x.xx.x/bin
   chmod 755 activemq
   ```

4. 启动。

   ```shell
   # 默认端口：8161
   ./activemq start
   ```

**简单登录查看**:

- 浏览器输入地址：http://ip:8161
- 输入用户名和密码：admin - admin



## 3. ActiveMQ 发布消息

- 准备工作：

  - 创建工程，引入依赖：

    ```xml
    <dependency> 
      <groupId>org.apache.activemq</groupId> 
      <artifactId>activemq-client</artifactId> 
      <version>5.13.4</version> 
    </dependency
    ```

- **发送消息**：

  - 共同部分：

    ```java
    // 1. 创建连接工厂
    ConnectionFactory cFac = new ActiveMQConnectionFactory("tcp://ip:61616");
    // 2. 获取连接
    Connection conn = cFac.createConnection();
    // 3. 启动连接
    connection.start();
    // 4. 获取 session（参数：(是否启动事务, 消息确认模式)）
    Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
    ```

  - **点对点模式部分**：

    ```java
    // 5. 创建队列对象
    Queue queue = session.createQueue("test-queue");
    // 6. 创建消息生产者
    MessageProducer producer = session.createProducer(queue);
    ```

  - **发布/订阅模式部分**：

    ```java
    // 5. 创建主题对象
    Topic topic = session.createTopic("test-topic");
    // 6. 创建消息生产者
    MessageProducer producer = session.createProducer(topic);
    ```

  - 共同部分：

    ```java
    // 7. 创建消息
    TextMessage textMessage = session.createTextMessage("测试消息");
    // 8. 发送消息
    producer.send(textMessage);
    // 9. 关闭资源
    producer.close();
    session.close();
    connection.close();
    ```



## 4. ActiveMQ 消费消息

- 准备工作：

  - 引入依赖：略

- 共同部分：

  ```java
  // 1. 创建连接工厂
  ConnectionFactory cFac = new ActiveMQConnectionFactory("tcp://ip:61616");
  // 2. 获取连接
  Connection conn = cFac.createConnection();
  // 3. 启动连接
  connection.start();
  // 4. 获取 session（参数：(是否启动事务, 消息确认模式)）
  Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
  ```

- **点对点模式部分**:

  ```java
  // 5. 创建队列对象
  Queue queue = session.createQueue("test-queue");
  // 6. 创建消费者
  MessageConsumer consumer = session.createConsumer(queue);
  ```

- **发布/订阅模式部分**：

  ```java
  // 5. 创建主题对象
  Topic topic = session.createTopic("test-topic");
  // 6. 创建消费者
  MessageConsumer consumer = session.createConsumer(queue);
  ```

- 共同部分：

  ```java
  // 7. 监听消息
  consumer.setMessageListener(
      new MessageListener() {
          public void onMessage(Message message) {
              // 处理收到的消息
              TextMessage textMessage = (TextMessage)message;
              try {
                  sout("收到的消息"： + textMessage.getText());
              } catch (JMSException e) {
                  e.printStackTrace();
              }
          }
      }
  );
  // 8. 关闭资源
  producer.close();
  session.close();
  connection.close();
  ```


## 5. Spring JMS

### 5.1 点对点使用概述

- **工程引入依赖：**

  - spring-jms
  - activemq
  - spring-test

- **创建配置文件，编写公共部分：**

  applicationContext-jms.xml

  ```xml
  <!-- 厂商提供的连接工厂 -->
  <bean id="targetConnectionFactory" class="org.apache.activemq.ActiveMQConnectionFactory">   
       <property name="brokerURL" value="tcp://192.168.25.135:61616"/>   
  </bean> 
  
  <!-- 连接工厂 -->
  <bean id="connectionFactory" 
  class="org.springframework.jms.connection.SingleConnectionFactory">
       <property name="targetConnectionFactory" ref="targetConnectionFactory"/>   
  </bean>
  ```

- **配置文件发送者部分：**

  ```xml
  <!-- jms 模板 -->
  <bean id="jmsTemplate" class="org.springframework.jms.core.JmsTemplate">
      <property name="connectionFactory" ref="connectionFactory"/>
  </bean>
  
  <!-- 目的地：队列名 -->
  <bean id="queueTextDestination" 
  class="org.apache.activemq.command.ActiveMQQueue">   
       <constructor-arg value="queue_text"/>   
  </bean>
  ```

- **配置文件接收者部分：**

  ```xml
  <!-- 目的地：队列名 -->
  <bean id="queueTextDestination" 
  class="org.apache.activemq.command.ActiveMQQueue">   
       <constructor-arg value="queue_text"/>   
  </bean>
  
  <!-- 自行编写的消息监听类 -->
  <bean id="myMessageListener" class="io.pz.consumer.MyMessageListener" />
  
  <!-- 消息监听容器 -->
  <bean class="org.springframework.jms.listener.DefaultMessageListenerContainer"> 
    	<!-- 连接工厂 -->
      <property name="connectionFactory" ref="connectionFactory" /> 
    	<!-- 目的地 -->
      <property name="destination" ref="queueTextDestination" /> 
    	<!-- 监听类 -->
      <property name="messageListener" ref="myMessageListener" /> 
  </bean>
  ```

- **发送消息的代码：**

  Test.java

  ```java
  @RunWith(SpringJUnit4ClassRunner.class) 
  @ContextConfiguration(locations="classpath:applicationContext-jms-producer.xml")
  public class Test {
      
      @Autowired
      private JmsTemplate jmsTemplate;
      @Autowired
      private Destination queueTextDestination;
      
      @Test
      public void testSend() {
          jmsTemplate.send(queueTextDestination, new MessageCreator() {
              public Message createMessage(Session session) throws JMSException {
                  return session.createTextMessage("test message");
              }
          });
      }
      
  }
  ```

  以上代码可自行封装为消息发送者类。

- **接收消息的代码：**

  MyMessageListener.java

  ```java
  public class MyMessageListener implements MessageListener {
      public void onMessage(Message message) {
          TextMessage textMessage = (TextMessage)message;
          try {
              // 消息处理
              sout(textMessage);
          } catch (JMSException e) {
              e.printStackTrace();
          }
      }
  }
  ```

  以上代码可自行封装。

### 5.2 发布/订阅模式使用概述

仅列出一些与点对点模式的不同之处。

- 目的地的类修改为主题。

  ```xml
  <bean id="topicTextDestination" 
  class="org.apache.activemq.command.ActiveMQTopic">   
       <constructor-arg value="topic_text"/>   
  </bean>   
  ```

- 接收者的消息监听容器配置也跟着修改。