# Bootstrap 3 学习笔记

[TOC]

## 1. Bootstrap概述

- **概述**：
  - Bootstrap 是一个用于快速开发 Web 应用程序和网站的**前端框架**。Bootstrap 是基于 HTML、CSS、JAVASCRIPT 的。
  - Bootstrap 是由 *Twitter* 的 *Mark Otto* 和 *Jacob Thornton* 开发的。Bootstrap 是 2011 年八月在 GitHub 上发布的开源产品。

- **优点**：
  - **移动设备优先**：自 Bootstrap 3 起，框架包含了贯穿于整个库的移动设备优先的样式。
  - **浏览器支持**：所有的主流浏览器都支持 Bootstrap。
  - **响应式设计**：Bootstrap 的响应式 CSS 能够自适应于台式机、平板电脑和手机。
- **内容**：
  - **基本结构**：Bootstrap 提供了一个带有网格系统、链接样式、背景的基本结构。
  - **CSS**：Bootstrap 自带以下特性：全局的 CSS 设置、定义基本的 HTML 元素样式、可扩展的 class，以及一个先进的网格系统。
  - **组件**：Bootstrap 包含了十几个可重用的组件，用于创建图像、下拉菜单、导航、警告框、弹出框等等。
  - **JavaScript 插件**：Bootstrap 包含了十几个自定义的 jQuery 插件。您可以直接包含所有的插件，也可以逐个包含这些插件。
  - **定制**：您可以定制 Bootstrap 的组件、LESS 变量和 jQuery 插件来得到您自己的版本。

(above from [www.runoob.com](www.runoob.com) )



## 2. Bootstrap的安装/引入

BootStrap需要的基本文件如下（按引入顺序）：

1. Bootstrap.min.css
2. JQuery.min.js
3. Bootstrap.min.js

部署Bootstrap环境的方法如下：

- **从官网下载预编译的js和css** 

  官网地址：

  - Bootstrap 3：[https://getbootstrap.com/docs/3.3/](https://getbootstrap.com/docs/3.3/) 
  - Bootstrap 4：[https://getbootstrap.com/](https://getbootstrap.com/) 

  下载`Compiled JS and CSS`并解压，将需要需要的文件复制到工作空间，最后使用`link`和`script`标签引入。

- **引入CDN链接** 

  - 官网CDN

  ```html
  <!-- Latest compiled and minified CSS -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
  
  <!-- Optional theme -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
  
  <!-- Latest compiled and minified JavaScript -->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
  ```

  - BootCDN国内CDN

  ```html
  <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
  <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
  
  <!-- 可选的 Bootstrap 主题文件（一般不用引入） -->
  <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
  
  <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
  <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
  ```


## 3. Bootstrap栅格系统

- **概述**：

  Bootstrap 提供了一套**响应式**、**移动设备优先**的流式栅格系统，随着屏幕或视口（viewport）尺寸的增加，系统会自动分为**最多12列**。它包含了易于使用的[预定义类](https://v3.bootcss.com/css/#grid-example-basic)，还有强大的[mixin 用于生成更具语义的布局](https://v3.bootcss.com/css/#grid-less)。

  栅格系统用于通过一系列的**行（row）**与**列（column）**的组合来创建页面布局，你的内容就可以放入这些创建好的布局中。

- 