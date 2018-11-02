# JQuery

[TOC]

## 1. JQuery简介与概述

### 1.1 JQuery简介

- **JQuery是什么？**
  - JQuery是一个JavaScript函数库
  - JQuery是当下十分流行的 JS 框架，提供了大量扩展

- **JQuery能做什么？**
  - HTML元素获取与操作
  - HTML事件函数
  - HTML DOM 遍历和修改
  - CSS操作
  - JavaScript特效和动画
  - AJAX
  - Utilities

- **浏览器兼容性**：

  1.x版本兼容IE6、7、8，而其他版本的JQuery不能兼容。

### 1.2 JQuery的安装使用

JQuery可以通过以下方式使用：

- 下载并引入

  - 下载地址 [jquery.com](http://jquery.com/download/) 

  - 引入举例：

    ```html
    <head>
        <script src="jquery-1.10.2.min.js"></script>
    </head>
    ```

- 引用自CDN

  - 举例：

    ```html
    <head>
        <script src="https://cdn.bootcss.com/jquery/1.10.2/jquery.min.js">
        </script>
    </head>
    ```

  - 可用的CDN如下：

    - 百度："https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"
    - 新浪："http://lib.sinaapp.com/js/jquery/2.0.2/jquery-2.0.2.min.js"
    - Google："http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"



## 2. JQuery对象概述

- JQ对象是通过JQuery选择器获得的对象，而DOM对象是通过常规方法获得的对象

- JQ对象不能使用任何常规DOM对象的方法，DOM对象也不能使用任何JQ对象的方法

- DOM对象可以通过以下方式转为JQ对象：

  ```javascript
  $(DOM对象)
  ```

- JQ对象本质上是数组，所以可以通过下标转为DOM对象：

  ```javascript
  // index from 0 
  $("selector")[index];
  $("selector").get(index);
  ```



## 3. JQuery选择器

- **概述**：

  JQuery选择器允许对HTML单个元素或元素组进行操作。

  JQuery选择器基于已存在的CSS选择器，也包含一些自定义的选择器。

- **格式**：

  ```javascript
  // 基本格式
  $("选择器")
  ```

- **常用选择器**：

  以下选择器大部分都是CSS中已存在的选择器：

### 3.1 标签/id/class/并集选择器

| 示例                       | 说明                     |
| :------------------------- | :----------------------- |
| $("标签名")                | 获得所有匹配标签名的元素 |
| $("#id")                   | 根据id获取               |
| $(".class")                | 根据class获取            |
| $("选择器1, 选择器2, ...") | 选择多个                 |

### 3.2 层级选择器

| 示例       | 说明                                           |
| :--------- | ---------------------------------------------- |
| $("A B")   | 选择A元素内部的所有B元素（所有的子+孙元素）    |
| $("A > B") | 选择A元素内部的所有B子元素（所有的直接子元素） |
| $("A + B") | 选择紧接在A元素之后的所有B元素                 |

### 3.3 属性选择器

| 示例                          | 说明                                |
| ----------------------------- | ----------------------------------- |
| $("A[属性名]")                | 选择带有指定属性的所有A元素         |
| $("A[属性名='值']")           | 选择带有指定属性及属性值的所有A元素 |
| `$("A[属性...][属性...]...")` | 选择满足多个属性条件的A元素         |

### 3.4 过滤选择器

| 示例               | 说明                             |
| ------------------ | -------------------------------- |
| $("A:first")       | 获取第一个A元素                  |
| $("A:last")        | 获取最后一个A元素                |
| $("A:not(选择器)") | 选取不包括指定选择器的A元素      |
| $("A:even")        | 选取索引为偶数的A元素            |
| $("A:odd")         | 选取索引为奇数的A元素            |
| $("A:eq(index)")   | 选取指定索引的A元素，从0开始     |
| $("A:gt(index)")   | 选取大于指定索引的A元素，从0开始 |
| $("A:lt(index)")   | 选取小于指定索引的A元素，从0开始 |
| $("header")        | 选取所有标题元素（h1 - h6）      |

### 3.5 表单过滤选择器

| 示例                             | 说明                        |
| -------------------------------- | --------------------------- |
| $("input:enabled")               | 获得可用元素                |
| $("input:disabled")              | 获得不可用的元素            |
| $("input:checked")               | 获得单选框/复选框选中的元素 |
| $("#select > option : selected") | 获得下拉框选中的元素        |



## 4. JQuery操作属性

### 4.1 操作通用属性

| 方法         | 说明                |
| ------------ | ------------------- |
| attr()       | 获取/设置元素的属性 |
| removeAttr() | 删除属性            |
| prop()       | 获取/设置元素的属性 |
| removeProp() | 删除属性            |

`prop()`为JQuery1.6新增的方法。

表单中的`checked`、`selected`、`disabled`等属性，应尽量使用`prop()`来获取。

```javascript
$("input[checked]").prop("checked", "checked/true/false");
```

### 4.2 操作class

| 方法          | 说明                     |
| ------------- | ------------------------ |
| addClass()    | 添加class属性值          |
| removeClass() | 移除class属性值          |
| toggleClass() | 来回移除/添加class属性值 |

```javascript
$("#btn[id='xx']").click(function () {
    $("#div_demo").toggleClass("pink");
});
```

### 4.3 操作CSS

| 方法  | 说明    |
| ----- | ------- |
| css() | 添加css |

```javascript
$("#div_demo").css("background-color", "gold");
// or
$("#div_demo").css("backgroundColor", "gold");

// 以下方法可以删除设置的css
$("#div_demo").css("backgroundColor", "");
```



## 5. JQuery操作内容/DOM

### 5.1 操作元素内容

| 方法   | 说明                           |
| ------ | ------------------------------ |
| html() | 获取元素中的内容，包括html标签 |
| text() | 获取元素中的文本内容           |

```html
<p>
	<b>Hello</b><b>World<i>Java</i></b>
</p>
<script>
    $("p").html();		// <b>Hello</b><b>World<i>Java</i></b>
    $("p").text();		// HelloWorldJava
</script>
```

### 5.2 操作DOM

- **在元素内部添加新元素**：

  | 方法        | 说明                                    |
  | ----------- | --------------------------------------- |
  | append()    | 将**指定元素**添加到**元素**的内部+末尾 |
  | prepend()   | 将**指定元素**添加到**元素**的内部+开头 |
  | appendTo()  | 将**元素**添加到**指定元素**的内部+末尾 |
  | prependTo() | 将**元素**添加到**指定元素**的内部+开头 |

  ````html
  <p>Hello</p>
  <b>World</b>
  <script>
  	$("b").prependTo($("p"));		// WorldHello (p的内部)
  </script>
  ````

- **在元素外部添加新元素**：

  | 方法           | 说明                                    |
  | -------------- | --------------------------------------- |
  | after()        | 将**指定元素**添加到**元素**的外部+末尾 |
  | before()       | 将**指定元素**添加到**元素**的外部+开头 |
  | insertAfter()  | 将**元素**添加到**指定元素**的外部+末尾 |
  | insertBefore() | 将**元素**添加到**指定元素**的外部+开头 |

  ```html
  <p>Hello</p>
  <b>World</b>
  <script>
  	$("b").insertBefore($("p"));		// WorldHello (p的外部)
  </script>
  ```

- **其他常用功能**：

  | 方法     | 说明                                             |
  | -------- | ------------------------------------------------ |
  | remove() | 移除元素                                         |
  | empty()  | 清空元素的所有后代元素，仅保留当前元素及属性节点 |



## 6. JQuery遍历

JQuery提供了更加方便的遍历数组和JQ对象的方法。

- **方式一**：

  ```javascript
  // 格式
  jQ对象.each(回调函数);
  
  // 示例
  $("p").each(function () {
  	alert($(this).text());
  });
  ```

- **方式二**：

  ```javascript
  // 格式
  $.each(<数组/jQ对象>, [回调函数]);
  
  $.each([1, 2, 3, 5], function (index, element) {
  	alert(index + ": " + element);
  })
  ```

- **重要说明**：

  - 回调函数的参数`index`表示本轮循环中内容的索引，从0开始。
  - 回调函数的参数`element`表示本轮循环中的内容，方法体中可以使用`this`代替。
  - 回调函数的返回值可以控制循环：
    - 若返回true，则相当于continue，跳过本轮循环
    - 若返回false，则相当于break，结束循环



## 7. JQuery事件绑定

JQuery提供了多种事件绑定的方法。

- **标准方法**：

  ```javascript
  jQ对象.事件方法(回调函数);
  
  // demo
  $('#btn_demo').click(function () {
      alert('Hello');
  });
  ```

  事件方法如`click`、`mouseout`、`blur`等。

  注意：若事件方法不传递回调函数，则会触发浏览器默认行为。

  例如：表单对象.submit();	//直接提交表单

- **submit**：

  只能为表单元素绑定该事件。

  ```javascript
  $('#form_demo').submit(function () {
      ...
  })
  ```

  说明：

  - 该函数规定表单提交时发生的事件
  - 若传递的函数没有return或return true，则在return后提交表单
  - 若传递的函数return false，则return后不进行提交

- **on & off**：

  ```javascript
  // 给JQ对象绑定事件
  jq对象.on("事件名称", 回调函数);
  
  // 给JQ对象解绑指定事件
  jq对象.off("事件名称");
  
  // 给JQ对象解绑全部事件
  jq对象.off();
  
  // demo
  $('#btn_demo').on('dblclick', function () {
      alert('Hello');
  })
  ```

  事件方法如`click`、`mouseout`、`blur`等。

- ***toggle**：

  注意：此方法已于JQ1.9删除，需要jQuery Migrate（迁移）插件来恢复功能。

  新版JQ中toggle用于动画。

  使用方式与on/off类似，可以来回给元素绑定/解绑事件。



## 8. JQuery动画：显示与隐藏

| 方法                                     | 说明          |
| ---------------------------------------- | ------------- |
| **show**([speed], [easing], [fn])        | 默认显示      |
| **hide**([speed], [easing], [fn])        | 默认隐藏      |
| **toggle**([speed], [easing], [fn])      | 默认切换      |
| **slideDown**([speed], [easing], [fn])   | 滑动显示      |
| **slideUp**([speed], [easing], [fn])     | 滑动隐藏      |
| **slideToggle**([speed], [easing], [fn]) | 滑动切换      |
| **fadeIn**([speed], [easing], [fn])      | 淡入显示      |
| **fadeOut**([speed], [easing], [fn])     | 淡出隐藏      |
| **fadeToggle**([speed], [easing], [fn])  | 淡入/淡出切换 |

参数说明：

- speed：动画的速度，可使用`'slow'`、`'normal'`、`'fast'`和毫秒值
- easing：动画速度变化曲线预设，可选`'swing'`（慢->快->慢）、`'linear'`（线性）
- fn：回调函数，动画结束时执行



## 9. JQuery扩展功能

- **扩展JQ对象的功能**：

  ```javascript
  // 经过以下方式扩展后，JQ对象就有了新功能
  // jQuery等同于$
  jQuery.fn.demoNewFunction = function () {
      alert('demo');
  }
  
  $.fn.extend({
      demoNewFunction : function () {
          alert('demo');
      }
  })
  ```

- **扩展JQ自身的功能**：

  ```javascript
  // 经过以下方式扩展后，JQ自身就有了新功能
  $.extend({
      demoNewFunction : function () {
          alert('HelloWorld');
      }
  })
  ```


