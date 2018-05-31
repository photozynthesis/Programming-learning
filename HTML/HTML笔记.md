# HTML笔记

[TOC]

## 1.html语言概述/html5

### 1.1 概述

1. **HTML（Hypertext Markup Language）**是一门超文本标记语言：
   * "超文本"指页面可以包含链接、图片等非文字内容
   * "标记"指使用标签将需要的内容括起来，例如`<head>`内容`</head>`
2. HTML**不是编程语言**；
3. HTML5是HTML最新规范。

### 1.2 html语法和规范

1. 所有html文件后缀名都是.html或.htm，建议使用**.html**；

2. 整个html文件由**头部分**`<head></head>`和**体部分**`<body></body>`组成；

3. html标签都是由**开始标签**和**结束标签**组成，例外如：`<br />`

4. html标签**忽略大小写**，建议使用小写。

    

## 2.常用

- 空格：`&nbsp;`

  

## 3.常用标签

### 3.1 注释

- 使用`<!-- 注释内容 -->`

### 3.2 标题标签

- 使用`<hn></hn>`，n从1到6**逐渐变小**
- 标题标签加粗加黑显示，单独占用一行，与其他行有一定行间距

### 3.3 水平线标签

- 使用`<hr />`
- 一条水平横线

### 3.4 段落标签

- 使用`<p></p>`定义段落，段落上下有较大行间距
- 使用`<br />`插入单个换行

### 3.5 字体标签

- 使用`<font></font>`

- 可在第一个标签内设置各种属性，常见的有color、size、face(字体)等

- size属性范围是1-7，从1到7**逐渐变大**

  举例：

  ```html
  <font color="FF0000" size="7" face="楷体">红色最大楷体</font>
  ```


### 3.6 格式化标签

- 使用`<b>粗体</b>`定义粗体

- 使用`<i>斜体</i>`定义斜体

  

## 4.图片标签

- 使用`<img />`

- 图片标签的常用属性：

  - src：图片文件的路径
  - width/height：图片显示的尺寸
  - alt：无法显示图片时显示的内容

- 举例：

  ```html
  <img src="logo.jpg" width="300px" height="150px" alt="logo"/>
  ```

  

## 5.列表标签

### 5.1 无序列表

重要属性：

- type：项目符号的类型

举例：

```html
<ul type="disc">
    <li>谷歌</li>
    <li>百度</li>
</ul>
```

效果：

> * 谷歌
> * 百度

### 5.2 有序列表

重要属性：

- start：起始项
- reversed：是否逆序
- type：数字、字母、罗马数字

举例：

```html
<ol start="3" reversed="reversed" type="a">
    <li>谷歌</li>
    <li>必应</li>
    <li>百度</li>
</ol>
```

效果：

> c.谷歌
>
> b.必应
>
> a.百度



## 6.超链接标签

1. 使用`<a></a>`

2. 重要属性：

   - href：超链接目标地址
   - target：打开方式（本页打开或新标签页），常见取值：
     - "_self"：本页打开
     - "_blank"：新标签页打开

3. 举例：

   ```html
   <a href="index.html" target="_blank">PornHub</a>
   ```

   

## 7.表格

1. 表格标签使用`<table></table>`

2. 表格标签内嵌多个行标签`<tr></tr>`，行标签又内嵌多个列标签`<td></td>`

3. table标签重要属性：

   - border：边框宽度
   - width/height：宽度/高度
   - align：对齐方式
   - bgcolor：背景颜色
   - cellspacing：边框之间的间距（空隙）
   - cellpadding：边框与内容的间距

4. 行/列标签重要属性：

   - colspan：跨列数
   - rowspan：跨行数

5. 举例：

   ```html
   <table border="1px" width="300px" height="150px" align="center" bgcolor="red" cellspacing="0px" cellpadding="1px">
       <tr>
           <td>第一行第一列</td>
           <td>第一行第二列</td>
       </tr>
       <tr>
           <td>第二行第一列</td>
           <td>第二行第二列</td>
       </tr>
   </table>
   ```

   

## 8.框架结构标签

- 框架结构标签使用`<frameset></frameset>`

- 若要使页面呈多列（左右）框架，使用属性cols

- 若要使页面呈多行（上下）框架，使用属性rows

- frameset一般需要取代body标签

  举例：

  ```html
  <!--一个嵌套的框架-->
  <head></head>
  <frameset rows="20%,*">
  	<frame src="top.html"/>
      <frameset cols="20%,*">
          <frame src="left.html"/>
          <frame src="right.html/">
      </frameset>
  </frameset>
  ```

  

## 9.表单

1. 使用`<form></form>`标签定义html表单，表单所有内容括在标签内
2. 表单中重要元素：
   - 输入框：`<input></input>`，重要属性：
     - type：可选text(文本)、radio(单选)、submit(提交按钮)
3. 表单重要属性：
   - action：向何处提交表单的地址（可以是页面/java代码）
   - method：提交表单时所用的HTTP方法，可选GET、POST
   - target：