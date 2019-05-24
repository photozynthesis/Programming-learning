# Freemarker

[TOC]

## 1. 网页静态化技术概述

### 1.1 概述

- 顾名思义，网页静态化就是将数量庞大或是内容不怎么变化的页面以静态化的形式提供。

### 1.2 好处

- 网页静态化可以减轻数据库访问压力，适合于高并发场景。
- 有利于 SEO（Search Engine Optimization 搜索引擎优化）。



## 2. Freemarker 概述

### 2.1 概述

- Freemarker 是一个 Java 语言编写的模板引擎，基于模板来生成文本输出。
- Freemarket 可以输出几乎任何形式的文本文件。
- 通过模板和数据模型来生成目标文件。

### 2.2 搭建使用环境与入门使用

1. **引入依赖。**

   ```xml
   <dependency> 
     <groupId>org.freemarker</groupId> 
     <artifactId>freemarker</artifactId> 
     <version>2.3.23</version> 
   </dependency>
   ```

2. **创建 .ftl 模板文件。**

   具体规则下文描述。

3. **编写测试类生成目标文件。**

   ```java
   public class Test {
       public static void main(String[] args) {
           // 1. 创建配置类
           Configuration conf = new Configuration(Configuration.getVersion());
           // 2. 设置模板所在目录
           conf.setDirectoryForTemplateLoading(new File("..."));
           // 3. 设置字符集
           conf.setDefaultEncoding("UTF-8");
           // 4. 加载模板
           Template template = conf.getTemplate("test.ftl");
           // 5. 创建数据模型
           Map map = new HashMap();
           map.put("name", "zhangsan");
           // 6. 创建 Writer
           Writer out = new FileWriter(new File("..."));
           // 7. 输出
           template.process(map, out);
           // 8. 关闭 writer
           out.close();
       }
   }
   ```

   也可以使用 StringTemplateLoader 使用字符串生成模板。



## 3. Freemarker 常用指令

### 3.1 注释

```
<#-- 注释 -->
```

### 3.2 变量定义

```
<#assign name="zhangsan">
联系人：${name}

<#assign info={"name": "zhangsan", "age": 20}>
姓名： ${info.name}
年龄： ${info.age}
```

### 3.3 包含其他页面

```
<#include "other.ftl">
```

### 3.4 条件判断

```
<#if flag=true>
	pass
<#else>
	negative
</#if>
```

### 3.5 集合遍历

- **遍历 list 结构集合**

  ```
  <#list itemList as items>
  	${items_index + 1}. 名称： ${items.name}, 价格： {items.price}
  </#list>
  ```

  > itemList 的结构如下：
  >
  > [{"name": "apple", "price": "10"}, {"name": "banana", "price": "20"}, ...]

- **遍历 map 结构集合**

  ```
  <#list myMap?keys as k>
  	姓名：${myMap[k].name}
  	年龄：${myMap[k].age}
  </list>
  ```

  > myMap 的结构如下：
  >
  > [{"name": "xiaoming", "age": 20}, {"name": "xiaohong", "age": 18}, ...]

### 3.6 内建函数

- 使用内建函数的格式：

  ```
  ${变量?函数名}
  ```

- 常用内建函数：

  - **集合大小**

    ```
    ${itemList?size}
    ```

  - **json 字符串转为可用数据对象**

    ```
    <#assign jsonText="{'name': 'zhangsan', 'age': 20}">
    
    <#assign person=jsonText?eval />
    
    姓名： ${person.name}, 年龄： ${person.age}
    ```

  - **日期格式化**

    ```java
    // java 数据模型
    modelMap.put("today", new Date());
    ```

    ```
    日期： ${today?date}
    时间： ${today?time}
    日期+时间： ${today?datetime}
    格式化： ${today?string("yyyy-MM-dd")}
    ```

  - **数字显示**

    ```
    <#-- 可避免数字显示时带千分位逗号，如 1,000,000 -->
    
    #{num?c}
    ```

  - **空值/无效值判断**

    ```
    <#-- 判断变量是否存在 -->
    <#if item??>
    	存在
    <#else>
    	不存在
    </#if>
    
    <#-- 变量空值判断 -->
    ${num!0}
    ```

### 3.7 运算符支持

- Freemarker 支持大部分 java 中的逻辑运算符和比较运算符。
- 其中 >、>= 等可以用 gt、gte 等代替。
- 运算符可以结合上述内建函数使用。

