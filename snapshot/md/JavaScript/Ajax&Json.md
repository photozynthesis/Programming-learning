# Ajax & Json

[TOC]

## 1. Ajax概述

Asynchronous Javascript And Xml，即异步的Javascript和XML，是一种无需刷新整个页面即可更新页面部分内容的技术。

异步是指客户端在发送请求后，无需一直等待服务端的响应，可以同时进行其他操作；而同步则必须一直等待服务端响应。



## 2. Ajax：原生JS实现

*仅需了解

```javascript
 //1.创建核心对象
var xmlhttp;
if (window.XMLHttpRequest){
    // code for IE7+, Firefox, Chrome, Opera, Safari
    xmlhttp = new XMLHttpRequest();
}else{
    // code for IE6, IE5
    xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
}

//2. 建立连接
/*
    参数：
    1. 请求方式：GET、POST
    	* get方式，请求参数在URL后边拼接。send方法为空参
    	* post方式，请求参数在send方法中定义
    2. 请求的URL：
    3. 同步或异步请求：true（异步）或 false（同步）
*/
xmlhttp.open("GET","ajaxServlet?username=tom",true);

//3.发送请求
xmlhttp.send();

//4.接受并处理来自服务器的响应结果
//		获取方式 ：xmlhttp.responseText
//		什么时候获取？当服务器响应成功后再获取

//当xmlhttp对象的就绪状态改变时，触发事件onreadystatechange。
xmlhttp.onreadystatechange = function () {
    //判断readyState就绪状态是否为4，判断status响应状态码是否为200
    if (xmlhttp.readyState==4 && xmlhttp.status==200) {
        //获取服务器的响应结果
        var responseText = xmlhttp.responseText;
        alert(responseText);
    }
}
```



## 3. Ajax：jQuery实现

### 3.1 $.ajax()

*不常用，常用get()和post()。

实例如下：

```javascript
// 键是否使用引号皆可
$.ajax({
    url: "demoServlet",
    type: "POST",
    // data: "username=jack&age=23",
    data: {
        "username": "jack",
        "age": 23
    },
    // 成功相应后的回调函数
    success: function (data) {
        alert(data);
    },
    // 请求响应出现错误执行的回调函数
    error: function () {
        alert("error");
    },
    // 接收到的响应的数据格式
    dataType: "text"
});
```

### 3.2 $.get() & $.post()

- 发送get请求：

  ```javascript
  $.get(url, [data], [callback], [type]);
  
  // 示例
  $.get("/demoPath/demoServlet", {'username':'Jack','password':123}, function (data, status) {
      // 此处的data若为JSON格式，可直接通过.获取属性值
      ...
  }, "json");
  ```

  说明：

  - [data]处除使用JSON外，也可以使用键值对的字符串表示：

    > username=jack&password=123

- 发送post请求：

  与get完全相同。




## 4. JSON概述

### 4.1 概述

- JSON（JavaScript Object Notation），是一个轻量级的，用于存储和交换数据的语言。
- JSON比XML更小、更快、更易解析。

### 4.2 语法规则

JSON示例如下：

```json
{
    'name': 'jack',
    'age': '22',
    'hobby': ['game','music'],
    'sleep': function () {
        alert('sleeping...zzz');
    }
}
```

重要说明：

- 键**可以**也**可以不**使用引号引起来
- 多个键值对用`,`分隔，不过最后一个键值对后不需要`,` 
- 使用`{}`保存对象，使用`[]`保存数组
- 值的可选类型有：
  - 数字
  - 字符串（`'xxx'`）
  - 布尔值
  - 数组（`[xx,xx]`）
  - 对象（`{...}`）
  - null

### 4.3 获取数据的方式

1. JSON对象.键名

   ```javascript
   xiaoming.name;
   ```

2. JSON对象['键名']

   ```javascript
   // 字符串中的内容可以替换为变量
   xiaoming['name'];
   ```

3. 数组对象.索引

4. for in 循环遍历：

   ```javascript
   for (var key in xiaoming) {
       alert(key + xiaoming[key]);
   }
   ```



## 5. JSON解析器：jackson

### 5.0 常见的Java JSON解析器

- Jsonlib
- Gson
- fastjson
- jackson

### 5.1 Jackson：使用准备

Jackson使用需要三个包：

- jackson-core
- jackson-annotations
- jackson-databind

可以使用添加Maven依赖的方式导入：

```xml
<dependencies>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-core</artifactId>
        <version>2.9.6</version>
    </dependency>

    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-annotations</artifactId>
        <version>2.9.6</version>
    </dependency>

    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.9.6</version>
    </dependency>
</dependencies>
```

### 5.2 Jackson：JSON转Java对象

1. 创建ObjectMapper对象；

2. 调用ObjectMapper的`T readValue(JSON字符串, Class<T>)`进行转换：

   ```java
   ObjectMapper mapper = new ObjectMapper();
   Student stu = new Student();
   try {
       stu = mapper.readValue(new File("C:\\Users\\Photo\\Desktop\\xiaoming.json"), Student.class);
   } catch (IOException e) {
       e.printStackTrace();
   }
   System.out.println(stu);
   ```

### 5.3 Jackson：Java对象转JSON

1. 创建ObjectMapper对象；

2. 使用以下方法转换：

   | 方法                                      | 说明                                                      |
   | ----------------------------------------- | --------------------------------------------------------- |
   | void **writeValue**(File, Object)         | 将obj对象转换为JSON字符串，并保存到指定的文件中           |
   | void **writeValue**(Writer, Object)       | 将obj对象转换为JSON字符串，并将json数据填充到字符输出流中 |
   | void **writeValue**(OutputStream, Object) | 将obj对象转换为JSON字符串，并将json数据填充到字节输出流中 |
   | String **writeValueAsString**(Object)     | 将对象转为JSON字符串                                      |

相关说明：

- 可以在JavaBean中的某属性上使用注解，来获得转换过程中的一些效果：

  - `@JsonIgnore`：排除属性，转换为JSON时不转换该属性。

  - `@JsonFormat`：属性值格式化后转换，例如：

    ```java
    // 格式化后转换日期
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    ```

- 可以转换List为JSON，不过会转成JS数组。

- 可以转换Map为JSON。