# JavaScript学习笔记

## 目录：

[TOC]

## 1. JavaScript的简介

- **概述：**

  JS是一个脚本语言（无需编译），多运行在浏览器中。

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

- 在`script`标签中写入代码：

  ```html
  <script type="text/javascript">
  	JavaScript代码...
  </script>
  ```

- 使用`script`标签，引入一个外部JS文件：

  ```html
  <script type="text/javascript" src="Demo.js">
  </script>
  ```

**注意事项：**

 	1. 使用第二种方式（引入外部文件）的时候，就不用在标签内写代码了，因为不会执行。
 	2. script标签的位置会影响执行时间。



## 3. 常用方法

- `alert(参数);` 

  弹出消息提示框。

- `document.write(内容);` 

  向页面中写入内容。



## 4. 原始类型和声明变量

- **声明变量：**

  JavaScript使用**var**声明变量。

  注意：若不使用var关键字，定义的将是全局变量；使用则为局部变量。

- **原始类型：**

  JavaScript的原始数据类型有如下五种：

  - string（字符串）

    - 可以使用单引号或双引号。

    ```javascript
    var s = "Hello";
    ```

  - number（数字）

    包含：

    - 整数
    - 小数
    - NaN

    ```javascript
    var n = 10;
    ```

  - boolean（布尔）

    ```javascript
    var flag = false;
    ```

  - null

    - 赋值为空占位符

    - 表示对象引用为空

  - undefined

    - 定义了一个变量，不过并没有赋值，那么就是undefined。

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

- **其他类型作为boolean类变量**参与计算的时候：

  - number类型：NaN和0为false，其余为true
  - string类型：空字符串和Null为false，其余为true
  - Object类型：一般只要非空，就为true

- **==和===的区别**：

  - ==比较的只是值
  - ===比较值和类型

- **typeof运算符**：

  - 后跟变量，获得该变量的原始类型



## 6. 函数

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

- **注意事项**：

  1. JavaScript中**不**存在**函数重载**，若定义了两个重名的函数，后定义的将会**覆盖**先定义的，尽管参数不同。

  2. 传递的参数可以通过**arguments**对象来遍历，以此可以模拟函数重载。

     ```javascript
     function add(){
         var sum = 0;
         for(var i = 0;i < arguments.length;i ++){
             sum += arguments[i];
         }
         return sum;
     }
     ```



## 7. JS的全局变量和局部变量

- **全局变量：**

  在script标签中（函数外）定义的变量，这个变量在任何JS部分都可以使用，包括：

  - 方法外部
  - 方法内部
  - 另一个script标签中
  - 在页面关闭后销毁

- **局部变量：**

  在方法内部定义一个变量，只能在方法内部使用。

  在函数执行完毕后销毁。



## 8. 事件

- **概述**：

  HTML中的元素可以添加属性，来使当发生特定的事件时，执行一些JavaScript代码。

- **举例**：

  ```javascript
  <button onclick="getElementById('demo').innerHTML=Date()">
  <button onclick="demoFunction()">
  ```

- **常见的HTML事件**：

  - onchange：HTML元素改变
  - onclick：用户点击HTML元素
  - onmouseover：用户在一个HTML元素上移动鼠标
  - onmouseout：用户在一个HTML元素上移开鼠标
  - onkeydown：用户按下键盘按键
  - onload：浏览器已完成页面的加载



## 9. 常用对象

### 9.1 Array

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

- **常用方法**：

  |       方法       |                             说明                             |
  | :--------------: | :----------------------------------------------------------: |
  |     concat()     |              连接两个或更多的数组，并返回结果。              |
  |      join()      | 把数组的所有元素放入一个字符串。元素通过指定的分隔符进行分隔。 |
  |      pop()       |                 删除并返回数组的最后一个元素                 |
  |      push()      |       向数组的末尾添加一个或更多元素，并返回新的长度。       |
  |    reverse()     |                    颠倒数组中元素的顺序。                    |
  |     shift()      |                  删除并返回数组的第一个元素                  |
  |     slice()      |                从某个已有的数组返回选定的元素                |
  |      sort()      |                     对数组的元素进行排序                     |
  |     splice()     |                删除元素，并向数组添加新元素。                |
  |    toSource()    |                     返回该对象的源代码。                     |
  |    toString()    |               把数组转换为字符串，并返回结果。               |
  | toLocaleString() |              把数组转换为本地数组，并返回结果。              |
  |    unshift()     |       向数组的开头添加一个或更多元素，并返回新的长度。       |
  |    valueOf()     |                     返回数组对象的原始值                     |

- **重要事项：**

  1. 数组可以通过**属性length**获取**长度**，和Java相同。
  2. JS数组的**长度可变**，若直接给索引为超出数组长度的元素赋值，会自动扩充数组，中间的元素被定义为**undefined**。
  3. 数组中**可以**存放**不同数据类型**的数据。

### 9.2 Date

- **概述**：

  Date对象用于处理时间和日期。

- **定义**：

  ```javascript
  var myDate=new Date();
  ```

- **常用方法**：

  |                      方法                      |                    说明                     |
  | :--------------------------------------------: | :-----------------------------------------: |
  |            [Date()](jsref_Date.asp)            |           返回当日的日期和时间。            |
  |         [getDate()](jsref_getDate.asp)         | 从 Date 对象返回一个月中的某一天 (1 ~ 31)。 |
  |          [getDay()](jsref_getDay.asp)          |  从 Date 对象返回一周中的某一天 (0 ~ 6)。   |
  |        [getMonth()](jsref_getMonth.asp)        |       从 Date 对象返回月份 (0 ~ 11)。       |
  |     [getFullYear()](jsref_getFullYear.asp)     |      从 Date 对象以四位数字返回年份。       |
  |        [getHours()](jsref_getHours.asp)        |       返回 Date 对象的小时 (0 ~ 23)。       |
  |      [getMinutes()](jsref_getMinutes.asp)      |       返回 Date 对象的分钟 (0 ~ 59)。       |
  |      [getSeconds()](jsref_getSeconds.asp)      |       返回 Date 对象的秒数 (0 ~ 59)。       |
  | [getMilliseconds()](jsref_getMilliseconds.asp) |       返回 Date 对象的毫秒(0 ~ 999)。       |
  |         [getTime()](jsref_getTime.asp)         |    返回 1970 年 1 月 1 日至今的毫秒数。     |
  |                    setXxx()                    |      以上所有**get()**方法的**set()**       |
  |              **moreFunctions...**              |            **更多方法查看文档**             |

### 9.3 Math

- **概述**：

  用于执行数学任务。

- **常用属性**：

  |        属性        |                      说明                       |
  | :----------------: | :---------------------------------------------: |
  |  [E](jsref_e.asp)  | 返回算术常量 e，即自然对数的底数（约等于2.718） |
  | [PI](jsref_pi.asp) |           返回圆周率（约等于3.14159）           |
  |    **more...**     |              **更多属性查阅文档**               |

- **常用方法**：

  |                 方法                  |                             描述                             |
  | :-----------------------------------: | :----------------------------------------------------------: |
  |        [abs(x)](jsref_abs.asp)        |                       返回数的绝对值。                       |
  |       [acos(x)](jsref_acos.asp)       |                      返回数的反余弦值。                      |
  |       [asin(x)](jsref_asin.asp)       |                      返回数的反正弦值。                      |
  |       [atan(x)](jsref_atan.asp)       |   以介于 -PI/2 与 PI/2 弧度之间的数值来返回 x 的反正切值。   |
  |     [atan2(y,x)](jsref_atan2.asp)     | 返回从 x 轴到点 (x,y) 的角度（介于 -PI/2 与 PI/2 弧度之间）。 |
  |       [ceil(x)](jsref_ceil.asp)       |                       对数进行上舍入。                       |
  |        [cos(x)](jsref_cos.asp)        |                        返回数的余弦。                        |
  |        [exp(x)](jsref_exp.asp)        |                       返回 e 的指数。                        |
  |      [floor(x)](jsref_floor.asp)      |                       对数进行下舍入。                       |
  |        [log(x)](jsref_log.asp)        |                 返回数的自然对数（底为e）。                  |
  |       [max(x,y)](jsref_max.asp)       |                   返回 x 和 y 中的最高值。                   |
  |       [min(x,y)](jsref_min.asp)       |                   返回 x 和 y 中的最低值。                   |
  |       [pow(x,y)](jsref_pow.asp)       |                      返回 x 的 y 次幂。                      |
  |     [random()](jsref_random.asp)      |                  返回 0 ~ 1 之间的随机数。                   |
  |      [round(x)](jsref_round.asp)      |                 把数四舍五入为最接近的整数。                 |
  |        [sin(x)](jsref_sin.asp)        |                        返回数的正弦。                        |
  |       [sqrt(x)](jsref_sqrt.asp)       |                       返回数的平方根。                       |
  |        [tan(x)](jsref_tan.asp)        |                        返回角的正切。                        |
  | [toSource()](jsref_tosource_math.asp) |                     返回该对象的源代码。                     |
  |  [valueOf()](jsref_valueof_math.asp)  |                   返回 Math 对象的原始值。                   |

### 9.4 RegExp

- **概述**：

  RegExp表示正则表达式对象。

- **创建**：

  ```javascript
  var reg = new RegExp('<regex...>');
  var reg = /<regex...>/;
  ```

- **使用**：

  ```javascript
  // 测试给定字符串是否符合规则
  var flag = reg.test('<stringToTest...>');
  ```

- **正则表达式的规则**：

  |  表达式  |              含义               |
  | :------: | :-----------------------------: |
  |    x     |              字符x              |
  |    \     |           反斜线字符            |
  |    \n    |             换行符              |
  |    \r    |             回车符              |
  |  [abc]   |            a、b或者c            |
  |  [^abc]  |       除a、b、c外任何字符       |
  | [a-zA-Z] |  a到z或A到Z，两头字母包括在内   |
  |  [0-9]   |        0到9的字符都包括         |
  |    .     | 任何字符，若要表示'.'字符：`\.` |
  |    \d    |              数字               |
  |    \w    |     单词字符`[a-zA-Z0-9_]`      |
  |    x?    |       X，一次或一次也没有       |
  |    x*    |          X，零次或多次          |
  |    x+    |          X，一次或多次          |
  |   x{n}   |           X，恰好n次            |
  |  x{n,}   |           X，至少n次            |
  |  x{n,m}  |  X，至少 n 次，但是不超过 m 次  |



## 10. DOM

### 10.1 DOM概述

- DOM（Document Object Model）文档对象模型，定义了访问和处理访问文档的标准方法。

### 10.2 常用文档对象

#### 10.2.1 Document

- **概述**：
  - 每个载入浏览器的 HTML 文档都会成为 Document 对象。
  - Document 对象使我们可以从脚本中对 HTML 页面中的所有元素进行访问。
  - 可通过 window.document 或 document 直接获取。
- **常用方法**：

|           方法           |                             说明                             |
| :----------------------: | :----------------------------------------------------------: |
|     getElementById()     |            返回对拥有指定 id 的第一个对象的引用。            |
|   getElementsByName()    |                 返回带有指定名称的对象集合。                 |
|  getElementsByTagName()  | 返回带有指定标签名的对象集合，可后跟`[<index...>]`来访问集合中的元素 |
| getElementsByClassName() | 返回带有指定类名的对象集合，可后跟`[<index...>]`来访问集合中的元素 |
|         write()          |          向文档写 HTML 表达式 或 JavaScript 代码。           |
|        writeln()         | 等同于 write() 方法，不同的是在每个表达式之后写一个换行符。  |
|     createElement()      |               创建新的元素，参数为一个节点名称               |
|     createTextNode()     |            创建文本结点，传入字符串形式的文本内容            |
|       **more...**        |                    **更多方法需查阅文档**                    |

#### 10.2.2 Element

- **概述**：

  - 在 HTML DOM 中，Element 对象表示 HTML 元素。

  - Element 对象可以拥有类型为元素节点、文本节点、注释节点的子节点。

  - NodeList 对象表示节点列表，比如 HTML 元素的子节点集合。

  - 元素也可以拥有属性，属性是属性节点。

- **常用属性和方法**：

  |     属性/方法     |                    说明                    |
  | :---------------: | :----------------------------------------: |
  |       style       |       设置或返回元素的 style 属性。        |
  |     innerHTML     |           设置或返回元素的内容。           |
  |   appendChild()   | 向元素添加新的子节点，作为最后一个子节点。 |
  |   firstChild()    |             返回元素的首个子。             |
  |  setAttribute()   |    设置属性，参数1为属性名，参数2为内容    |
  | removeAttribute() |                  移除属性                  |
  |    **more...**    |           **更多方法需查阅文档**           |

#### 10.2.3 Node

- **概述**：

  - 所有DOM对象都可以认为是一个结点。





## 11. BOM

### 11.1 BOM概述

- BOM（Browser Object Model）浏览器对象模型，使JavaScript有能力与浏览器"对话"。
- BOM尚无正式标准 。

### 11.2 常用浏览器对象

#### 11.2.1 Navigator

- **概述**：

  浏览器对象，具有与浏览器相关的相关方法和属性。

#### 11.2.2 Window

- **概述**：

  - 浏览器窗口对象。
  - 可以直接通过属性获取该window的其他对象。

- **常用方法**：

  |   方法    |                             说明                             |
  | :-------: | :----------------------------------------------------------: |
  |  alert()  |                          弹出警告框                          |
  | confirm() | 弹出具有确定和取消按钮的警告框，点击确定将返回true，点击取消返回false |
  | prompt()  |            弹出具有输入框的警告框，返回输入的内容            |
  |  close()  |        关闭当前窗口（关闭调用者window对象表示的窗口）        |
  |  open()   | 打开新窗口，可以传递url以打开指定页面。返回新创建的window对象 |

- **注意事项**：
  1. window对象不需要创建就可以直接使用，格式：`window.demoFunction()` 
  2. window引用可以省略

#### 11.2.3 Location

- **概述**：

  - Location 对象包含有关当前 URL 的信息。
  - Location 对象是 Window 对象的一个部分，可通过 window.location 属性来访问。

- **常用属性和方法**：

  | 属性/方法 |           说明           |
  | :-------: | :----------------------: |
  |   href    |  设置或返回完整的 URL。  |
  | reload()  |    重新加载当前文档。    |
  | replace() | 用新的文档替换当前文档。 |

#### 11.2.4 History

- **概述**：

  - 历史记录对象，表示当前窗口的历史记录。
  - History 对象是 Window 对象的一个部分，可通过 window.history 属性来访问。

- **常用属性和方法**：

  | 属性/方法 |              说明              |
  | :-------: | :----------------------------: |
  |  length   |  返回当前窗口中的历史URL数量   |
  |  back()   |  加载history列表中的前一个URL  |
  | forward() |  加载history列表中的下一个URL  |
  |   go()    | 加载history列表中某个特定的URL |

#### 11.2.5 Screen

- **概述**：

  显示器屏幕对象，具有与显示器屏幕相关的属性。



## 12. 定时器

- **概述**：

  使用定时器，可以在指定时间后执行JavaScript代码，或周期性地执行JavaScript代码。

  定时器的方法属于window对象，可以不加引用直接调用。

- **使用**：

  可以使用如下两个方法：

  - **setInterval(<code...>, <millisec...>, [lang...])** ：按照**指定的周期**（以毫秒计）来调用函数或计算表达式。方法会**不停**地调用函数，直到 **clearInterval()** 被调用或窗口被关闭。
    - code：要执行的代码，用引号包裹
    - millisec：周期的毫秒值
    - lang：可选：JScript | VBScript | JavaScript
  - **setTimeout(<code...>, <millisec...>, [lang...])** ：在**指定的毫秒数**后调用函数或计算表达式。若调用了 **clearTimeout()** 则会取消定时器。
    - code：要执行的代码，用引号包裹
    - millisec：延迟的毫秒值
    - lang：可选：JScript | VBScript | JavaScript

- **示例**：

  ```javascript
  var i = 0;
  setInterval("document.write(++i)", 500);
  ```

- **注意事项**：

  1. 若传入已定义的方法，方法名后不需要加上括号。