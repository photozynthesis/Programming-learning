# JavaScript学习笔记

## 目录：

[TOC]

## 1. JavaScript的简介

- **概述：**

  JS是基于对象和事件驱动的语言，应用于客户端：

  - 基于对象：指提供了好多对象，可以直接拿过来用。
  - 事件驱动：可以做动态效果。
  - 客户端：专指浏览器。

- **JS的特点：**

  - 交互性：

    信息的动态交互。

  - 安全性：

    JS不能访问本地磁盘文件。

  - 跨平台性：

    只要是支持JS的浏览器，都可以运行

- **JavaScript和Java的区别：**

  1. Java是Oracle公司的，JavaScript是网景公司的；
  2. JavaScript是基于对象的，Java是面向对象的；
  3. Java是强类型语言，JavaScript是弱类型语言；
  4. JavaScript只需解析就能执行，Java需要先编译成字节码文件，再执行。

- **JavaScript的组成：**

  - **ECMAScript**：

    ECMA是欧洲计算机协会，此处指由欧洲计算机协会制定的语法。

  - **BOM**：

    Browser Object Model，浏览器对象模型。

  - **DOM**：

    Document Object Model，文档对象模型。



## 2. JS和HTML结合的两种方式

- 在<script>标签中写入代码：

  ```html
  <script type="text/javascript">
  	JavaScript代码...
  </script>
  ```

- 使用<script>标签，引入一个外部JS文件：

  ```html
  <script type="text/javascript" src="Demo.js">
  </script>
  ```

**注意事项：**

	使用第二种方式（引入外部文件）的时候，就不用在标签内写代码了，因为不会执行。



## 3. 常用

- alert(参数)

  弹出消息提示框。

- document.write(内容);

  向页面中写入内容。



## 4. 原始类型和声明变量

- **声明变量：**

  JavaScript使用**var**声明变量。

- **原始类型：**

  JavaScript的原始数据类型有如下五种：

  - string（字符串）

    ```javascript
    var s = "Hello";
    ```

  - number（数字）

    ```javascript
    var n = 10;
    ```

  - boolean（布尔）

    ```javascript
    var flag = false;
    ```

  - null

    表示对象引用为空。

  - undefined

    定义了一个变量，不过并没有赋值，那么就是undefined。

- **扩展：**

  **typeof()**函数可以返回当前变量的数据类型。



## 5. 运算符

JavaScript运算符的使用方法**基本与Java运算符的相同**，此处仅列出一些常见的**不同之处**：

- JavaScript的**number类型不区分整数和小数**，所以当进行如下运算时，会有不同结果：

  ```
  int/var j = 123;
  j / 1000 * 1000;		//Java：0，JavaScript：123
  ```

  JavaScript的数字型num包含整数和小数。

- 当用**一个字符串减去一个数字**的时候，Java会报错，而JavaScript：

  - 若字符串是一个数字，会返回一个正确的计算结果
  - 若字符串不是一个数字，会返回NaN

- **boolean类变量**参与计算的时候：

  true的值相当于是1，false相当于是0。

- **==和===的区别**：

  - ==比较的只是值
  - ===比较值和类型



## 6. 数组

- **JS数组的定义方式：**

  1. 

     ```javascript
     var arr = [1, 2, 3];
     var arr2 = [1, "2", true]
     ```

  2. 

     ```javascript
     var arr = new Array(5);		//定义一个数组，长度是5
     ```

  3. 

     ```javascript
     var arr = new Array(1, "2", false);
     ```

- **重要事项：**

  1. 数组可以通过属性length获取长度，和Java相同。
  2. 数组中可以存放不同数据类型的数据。



## 7. 函数

- **定义函数的三种方式：**

  1. 正常定义

     ```javascript
     function 方法名(参数列表){
         方法体;
         return 返回值;		//根据需要可有可无
     }
     ```

  2. 匿名函数

     ```javascript
     function(参数列表){
         方法体;
         return 返回值;		//根据需要可有可无
     }
     ```

     ```javascript
     var 函数名 = function(参数列表){
         方法体;
         return 返回值;		//根据需要可有可无
     }
     ```

  3. 动态函数（使用较少）：

     ```javascript
     var 方法名 = new Function("参数列表", "方法体和返回值");
     ```

     举例：

     ```javascript
     var add = new Function("a,b", "var sum;sum = a + b;return sum;");
     ```


## 8. JS的全局变量和局部变量

- **全局变量：**

  在script标签中（函数外）定义的变量，这个变量在任何JS部分都可以使用，包括：

  - 方法外部
  - 方法内部
  - 另一个script标签中
  - 在页面关闭后销毁

- **局部变量：**

  在方法内部定义一个变量，只能在方法内部使用。

  在函数执行完毕后销毁。



## 9. 事件

- **概述**：

  HTML中的元素可以添加属性，来使当发生特定的事件时，执行一些JavaScript代码。

- **举例**：

  ```javascript
  <button onclick="getElementById('demo').innerHTML=Date()">
  ```

- **常见的HTML事件**：

  - onchange：HTML元素改变
  - onclick：用户点击HTML元素
  - onmouseover：用户在一个HTML元素上移动鼠标
  - onmouseout：用户在一个HTML元素上移开鼠标
  - onkeydown：用户按下键盘按键
  - onload：浏览器已完成页面的加载

