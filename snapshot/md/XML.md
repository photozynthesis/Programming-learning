# XML学习笔记

[TOC]

## 1. 概述

- XML（Extensible Markup Language），可扩展标记语言。可扩展指标签都是自定义的。
- XML的功能：
  - 配置文件
  - 在网络中传输



## 2. 语法

- **文档声明**：

  ```xml
  <!-- 格式 -->
  <?xml 属性='值' ...?>
  
  <!-- 示例 -->
  <?xml version="1.0" encoding="UTF-8"?>
  ```

  文档声明是XML的必须部分，而且必须置于文档开头。

  常用属性列表：

  - version：版本号，必须的属性
  - encoding：编码方式，默认IOS-8859-1
  - standalone：是否独立（依赖于其他文件），取值：
    - yes：依赖其他文件
    - no：不依赖其他文件

- **指令（结合CSS）**：

  ```xml
  <?xml-stylesheet type='text/css' href='demo.css'?>
  ```

- **标签**：

  自定义名称的标签。

  规则：

  - 名称可以包含字母、数字等字符
  - 不能以数字或标点符号开头
  - 不能以`xml`开头
  - 名称不能包含空格
  - 标签名称区分大小写

- **属性**：

  注意事项：

  1. ID的属性值需要唯一。

- **文本**：

  - CDATA区：

    该区域中的数据会被原样展示，格式如下：

    ```xml
    <![CDATA[
    	...
    ]]>
    ```



## 3. 约束

- **分类**：

  - DTD

    一种简单的约束技术。

    引入DTD文档到XML文档中：

    - 内部DTD：将约束规则定义在XML文档中

    - 外部DTD：将约束规则定义在外部的DTD文件中

      ```xml
      <!-- 本地DTD文件 -->
      <!DOCTYPE 根标签名 SYSTEM 'dtd文件的位置'>
      
      <!-- 网络DTD文件 -->
      <!DOCTYPE 根标签名 PUBLIC 'dtd文件名' 'dtd文件的位置url'>
      ```

  - Schema

    一种复杂的约束技术。

    引入方式：

    1. 填写XML文档的根元素；
    2. 引入xsi前缀；
    3. 引入xsd文件命名空间；
    4. 为每一个xsd约束声明一个前缀，作为标识。

    ```xml
    <Students xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xmlns="http://www.itcast.cn/xml"
             xsi:schemaLocation="http://www.itcast.cn/xml student.xsd">
        ...
    </Students>
    ```


## 4. 解析/写入

### 4.1 概述

解析XML的方式：

- DOM：一次性将文档加载进内存，形成一棵DOM树
  - 优点：操作方便，可对文档进行CURD操作
  - 缺点：内存占用较多
- SAX：逐行读取，基于事件驱动的
  - 优点：几乎不占用内存
  - 缺点：只能读取，不能增删改

### 4.2 常见解析器及使用

### 4.2.1 JAXP

- **概述**：

  SUN公司提供的解析器，支持DOM和SAX两种思想。

### 4.2.2 DOM4J

- **概述**：

  常用的解析器。

### 4.2.3 Jsoup

- **概述**：

  - 一款Java的超文本解析器，可直接解析某个URL地址或HTML/XML文本内容。
  - 提供了一套十分省力的API，可通过DOM、CSS以及类似Jquery的操作方法来取出和操作数据。
- **重要信息**：
  - 下载地址：[https://jsoup.org/download](https://jsoup.org/download) 
  - 帮助文档：[https://jsoup.org/download](https://jsoup.org/download) 下载source后解压 

- **典型使用步骤**：

  1. 导入jar包
  2. 获取Document对象
  3. 获取对应标签的Element对象
  4. 获取数据

- **常用对象**：

  - Jsoup：解析HTML/XML的工具类。

    常用方法：

    - Document parse(File in, String charsetName)，解析HTML/XML
    - Document parse(String html)，解析String格式的HTML/XML
    - Document parse(URL url, int timeoutMillis)，通过网络路径获得HTML/XML

  - Document：文档对象，代表内存中的DOM树。

    常用方法：

    - getElementById(String id)
    - getElementsByTag(String tagName)，获取的是集合
    - getElementsByAttribute(String key)，获取的是集合
    - getElementsByAttributeValue(String key, String value)，获取的是集合

  - Elements：Element的集合，可当作ArrayList< Element >来使用。

  - Element：元素对象。

    常用方法：

    - 获取子元素对象（与Document的方法相同）：
      - getElementById(String id)
      - getElementsByTag(String tagName)，获取的是集合
      - getElementsByAttribute(String key)，获取的是集合
      - getElementsByAttributeValue(String key, String value)，获取的是集合

    - 获取属性值：
      - String attr(String key)：根据属性名称获取属性值
    - 获取文本内容：
      - String text()：获取文本内容
      - String html()：获取标签体的所有内容

  - Node：结点对象。

    是Document和Element的父类。