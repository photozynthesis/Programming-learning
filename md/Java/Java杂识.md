# 一些Java杂识

[TOC]

## 1. BeanUtils

### 1.1 概述

BeanUtils工具由Apache软件基金组织编写，提供给我们使用，主要解决的问题是：把对象的属性数据封装到对象中。

**依赖于commons-loggin.jar，部分操作也会依赖于commons-collections.jar**，建议全部导入。

谷歌搜索即可下载。

JavaBean即标准的Java类，使用BeanUtils需要至少Bean满足以下条件：

- 类必须被public修饰
- 必须提供空参构造器
- 成员变量必须使用private修饰
- 提供公共的setter和getter方法

### 1.2 常用方法

|             方法              |               说明                |
| :---------------------------: | :-------------------------------: |
| populate(Object obj, Map map) | 将Map中的内容填充到传递的JavaBean |
|                               |                                   |
|                               |                                   |

