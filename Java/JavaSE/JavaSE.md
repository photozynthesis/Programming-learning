# JavaSE学习笔记

## 目录：

[TOC]

## 1. 概述

### 1.1 Java的起源和特征

**Java 编程语言**最初是由**Sun公司**开发的，该公司由 **James gosling** 于 1995 年创立 。

随着 Java 的发展进步和它的广泛流行， Java 做出了很多调整从而适应不同类型的平台。例如： J2EE 是为企业级应用程序设计的， J2ME 是为移动应用程序设计的。 

Java的特征有：面向对象性，平台独立性，简单性，安全性，体系结构中立性，便捷性，稳健性，多线程，易于理解，高性能，分布式，动态性。

### 1.2 Java跨平台的原因

JVM（Java Virtual Machine）是Java语言实现跨平台的关键。

Java源代码首先会被编译成平台无关的字节码，而字节码可以交给不同平台的JVM去执行。

所以只能说Java是跨平台的，而JVM不是。

### 1.3 JDK环境变量

- Windows：
  1. Java_Home：新建变量`Java_Home`，内容为jdk的目录。
  2. Path：在`path`变量中增加`%JAVA_HOME\bin%`。
  3. classpath：新建变量`CLASSPATH`，内容为`.;%Java_Home%\bin;%Java_Home%\lib\dt.jar;%Java_Home%\lib\tools.jar`。

- Linux：

  编辑`/etc/profile`文件，在末尾加入：

  ```
  export JAVA_HOME=(JDK安装目录)
  export JRE_HOME=${JAVA_HOME}/jre
  export CLASSPATH=.:${JAVA_HOME}/lib/tools.jar:${JRE_HOME}/lib/dt.jar
  export PATH=${JAVA_HOME}/bin:${JAVA_HOME}/jre/bin:$PATH
  ```

- 相关解释：

  1. JAVA_HOME指JDK的目录，很多需要JDK的程序会默认去取这个环境变量。
  2. 将JDK中的bin目录添加到Path环境变量，就可以在任意目录下执行bin中的可执行程序，例如`javac.exe`和`java.exe`。
  3. CLASSPATH的设置，可以使得在任意位置访问指定class文件。CLASSPATH中有多个项，系统将从左到右寻找class文件。



## 2. Java基础知识&语法

### 2.1 关键字与标识符

- 关键字：被Java语言赋予特定含义的单词，一些软件中会高亮显示。

  - Java的关键字有：

  ```
  abstract,assert,boolean,break,byte,case,catch,char,class,const（保留关键字）,continue,default,do,double,else,enum,extends,final,finally,float,for,goto（保留关键字）,if,implements,import,instanceof,int,interface,long,native,new,package,private,protected,public,return,short,static,strictfp,super,switch,synchronized,this,throws,transient,try,void,volatile,while
  ```

  - 常用关键字说明：

    - **continue**：

      只用在循环语句中，跳出单层循环中的一次，可以进行下一次。

    - **break**：

      只用在循环和switch语句中，作用为跳出循环。

      通过标签语句的配合可以跳出多层循环。

    - **finally**：

      位于try {...} catch (...) {...} finally {...}之中。
      被finally控制的语句体一定会执行。
      	存在例外：在执行finally之前jvm推出了，将不会执行finally中的语句。比如System.exit(0)。

      用于释放资源，在io流操作和数据库操作中会见到。

      **相关重要问题：**

      1. **final、finally、finalize的区别？**
         final：是用于修饰类名、成员变量、成员方法的修饰符。
         	final修饰类，该类不能被继承；
         	final修饰成员变量，该变量为常量不能重新赋值；
         	final修饰成员方法，该方法不能被重写。
         finally：是异常处理的一部分，用于释放资源。
         	一般来说finally中的语句一定会执行，

         ​	不过如果在finally之前退出了虚拟机，就不会执行。
         finalize：是Object中的一个方法，用于垃圾回收。

      2. **如果catch里面有return语句，finally中的语句还会执行吗？**
         会，并且会在return之前执行。
         准确来说，走到return语句时会生成一个返回路径，然后接着走finally中的代码，finally走完后再回到返回路径。
         举例：

         ```java
         catch(Exception e){
             a = 30;
             return a;		//返回30
         }finally{
             a = 40;
         }
         ```

    - **private**：

      是一个权限修饰符，被修饰的成员（变量、方法）只能在本类中才能访问。

    - **return**：

      用于结束方法，一旦遇到return，程序就不再继续往后执行。

    - **static**：

      用于修饰成员变量和成员方法，表示该成员是静态的。

      static修饰的成员：

      1. 随着类的加载而加载
      2. 优先于对象存在
      3. 被类中所有对象共享
      4. 可以通过**类名**调用

    - **throw**：

      如果出现了异常情况，我们可以手动把异常抛出（生成一个异常对象）。
      此关键字后面应该跟一个异常对象（throw new Exception()）。

      和throws的区别：

      - throws：
        1. 表示该方法可能会抛出异常，由方法的调用者来处理；
        2. 用在方法声明后面，后跟异常类名；
        3. 可以跟多个异常类名，用逗号隔开。
      - throw：
        1. 表示一定抛出了某异常，由方法体内的语句处理；
        2. 用在方法体内，后跟异常对象；
        3. 只能抛出一个异常对象。

  - 注意事项：

    1. 关键字为全部小写。
    2. 不能作为标识符名。

- 标识符：给类、接口、方法（函数）、变量等起名字的字符序列。

  - 组成规则：

    由大小写的英文字母、数字、`$`和`_`组成。

  - 命名规则：

    - 包：全部小写，例如`com.zxy`。
    - 类/接口：首字母大写，例如`Student`。
    - 方法（函数）/变量：首字母小写，后续单词首字母大写，例如`indexOfElement`。
    - 常量：全部大写，例如`STUDENT_MAX_AGE`。

  - 注意事项：

    1. 标识符不能是关键字。
    2. 不能以数字开头。
    3. 区分大小写。

### 2.2 注释

注释就是对程序进行解释说明的文字，能提高代码的可阅读性。

- 单行注释：

  ```
  // 注释内容
  ```

- 多行注释：

  ```
  /*
  	注释内容
  */
  ```

- 文档注释：

  ```java
  /**
  	这方法天下第一，非常的🐮🍺。
  	@author 作者
  	@version 版本
  	@param a 参数说明
  	@return b 返回值说明
  	@throws NullPointerException 可能抛出的异常说明
  */
  ```

### 2.3 常量&变量&进制

- 常量：程序执行过程中，其值不发生改变的量。

  具体分为如下几类：

  - 整数常量：`12`，`23`。
  - 小数常量：`12.345`。
  - 字符常量：`a`，`A`。
  - 字符串常量：`"Hello!"`。
  - 布尔常量：`true`，`false`。
  - 空常量：`null`。
  - 自定义常量：final修饰。

- 变量：程序执行过程中，其值在某个范围内可以发生改变的量。

  定义格式：

  ```
  数据类型 变量名 = 初始化值;
  数据类型 变量名;
  变量名 = 初始化值;
  ```

- 进制相关：

  - 二进制：以`0b`开头。
  - 八进制：以`0`开头。
  - 十进制：默认就是十进制。
  - 十六进制：以`0x`开头。

### 2.4 数据类型&转换

Java是强类型的语言，针对每种数据提供了对应的数据类型。

- 基本数据类型：

  分为如下八种：

  |  名称   |  占用字节  |                         备注                         |
  | :-----: | :--------: | :--------------------------------------------------: |
  |  byte   |     1      |       0000 0000 包含这8个bit（位），-128 ~ 127       |
  |  short  |     2      |                    -32768 ~ 32767                    |
  |   int   |     4      |               -2147483648 ~ 2147483647               |
  |  long   |     8      |                    -2^63 ~ 2^63-1                    |
  |  float  |     4      |         1bit符号位，8bit指数位，23bit尾数位          |
  | double  |     8      |         1bit符号位，11bit指数位，52bit尾数位         |
  |  char   |     2      |          由于默认采用Unicode编码，故为2字节          |
  | boolean | 1（未定4） | 实际占用字节数未明确规定，编译后可能转成int所以未定4 |

  注意事项：

  1. 关于float和double的具体精度：

     符号位、指数位和尾数位表示一个数的公式：`符号位 1.尾数位 * 2^指数位`，类似十进制科学计数法。

     吉祥物：`0b1.11111111111111111111111 x 10(2)^1000(8)`。

     **指数位决定了大小范围**，因为指数越大，能表示的数就越大。

     **尾数位（小数位）决定了精度**，因为后面的小数越多，精度越高。

     所以对于float，其有23bit小数位，2^23=8,388,608。其最多能有7位有效数字，但只有6位能保证绝对精确。

     同理对于float，其最多有16位有效数字，但只能保证15位绝对精确。

     **然而这些目前并没有卵用，精确计算小数用BigDecimal。**

  2. 关于char：

     占用字节数取决于编码，而Java语言采用Unicode作为默认编码，故默认char占用2字节。

  3. 关于boolean：

     Java并没有明确说明boolean占用多少个字节。

     一说具体取决于JVM，而部分JVM的boolean编译后会变成int。

     所以暂定有1或4个字节两种说法。

  4. 在定义long或者float类型变量的时候，要加L或f。

- 引用数据类型：

  Java有5种引用类型：类、接口、数组、枚举、标注。

- 基本数据类型的转换：

  - 基本数据类型的转换分为**隐式转换**和**显式转换**：

    - 将较小的类型赋值给较大的类型时，不需要强制转换，系统会**隐式转换**。

    - 将较大的类型赋值给较小的类型时，一般需要**强制转换**，可能会损失精度。

    - 对于byte、short和char，进行赋值时**不需**在数字后加上对应字母。

      因为若赋的值没有超过对应范围，系统会自动进行转换。

    - 当赋值给byte、short和char时，若`=`右边为常量（即使是多个常量相加）且大小没有超过对应范围，不需要进行转换操作也不会有任何问题；而如果`=`右边为变量，则会出现问题。

  - 整形和浮点型可以进行从小到大的转换，即：

    byte,short,char -> int -> long		float -> double

  - byte,short,char之间不相互转换，直接转成int参与运算。

  - 可以通过强制转换的方式将大的数据类型转换成小的，不过有可能损失精度，格式如下：

    ```
    目标数据类型 变量名 = (目标数据类型)被转换的数据;
    ```


### 2.5 运算符

Java中的运算符如下（优先级数字越小，越优先）：

|                            运算符                            |                 说明                 | 优先级 |
| :----------------------------------------------------------: | :----------------------------------: | :----: |
|                        `()` `[]` `.`                         |           括号（优先运算）           |   1    |
|                  `!` `+` `-` `~` `++` `--`                   |          非/正/负/自增/自减          |   2    |
|                         `*` `/` `%`                          |             乘/除/取余数             |   3    |
|                           `+` `-`                            |                加/减                 |   4    |
|                       `<<` `>>` `>>>`                        |              移位运算符              |   5    |
|                `<` `<=` `>` `>=` `instanceof`                | 小于/小于等于/大于/大于等于/类型判断 |   6    |
|                          `==` `!=`                           |             等于/不等于              |   7    |
|                             `&`                              |                按位与                |   8    |
|                             `^`                              |               按位异或               |   9    |
|                             `|`                              |                按位或                |   10   |
|                             `&&`                             |                逻辑与                |   11   |
|                             `||`                             |                逻辑或                |   12   |
|                        `xx ? xx : xx`                        |          条件（三元）运算符          |   13   |
| `=` `+=` `-=` `*=` `/=` `%=` `&=` `|=` `^=` `~=` `<<=` `>>=` `>>>=` |              赋值运算符              |   14   |

**重要说明：**

1. `+`有三种用法：

   - 加法
   - 正号，此时优先级高于加减乘除等，负号同理
   - 字符串连接符。注意**字符串连接符**在编译后会将前面的字符串包装成**StringBuilder**，并与后面的字符串作**append**操作。这意味着若在**循环中使用**字符串连接符，会不停地造StringBuilder对象。建议的方式为在循环之前造好StringBuilder对象。

2. `+=`等运算符可以认为包含了**强制类型转换**，举例如下：

   ```java
   short s = 1;
   s += 1;			//编译通过，因为相当于s = (s的类型)s+1
   s = s + 1;		//报错，因为1默认是int类型，运算后返回一个int
   ```

3. `++`和`--`运算符：

   - 若放在操作数后，会先以操作数原数参与运算，再自增/自减操作数，举例：

     ```java
     int x = 1,y = 1;
     y = 1 + x++;		//x = 2, y = 2
     ```

   - 若放在操作数前，会先自增/自减操作数，再参与运算，举例：

     ```java
     int x = 1,y = 1;
     y = 1 + ++x;		//x = 2, y = 3
     ```

4. `&&`和`||`，与`&`和`|`的不同在于：

   一旦`&&`左边的值为false，则**不会继续判断右边的**了，直接返回false。

   同理一旦`||`左边的为true，则直接返回true。

5. `<<`、`>>`和`>>>`位运算符（不常用）：

   - `<<`左移：

     格式：

     ​	操作数<<左移位数

     说明：

     ​	将操作数的二进制按照位数向左移动，左边越界的部分舍弃，右边补0。

     ​	相当于`操作数x2^左移位数`。

     举例：

     ```java
     3<<2;	//12, 相当于3x2^2, 00000011左移两位后变成00001100
     ```

   - `>>`右移：

     格式：

     ​	操作数>>右移位数

     说明：

     ​	将操作数的二进制按照位数向右移动，右边越界的部分舍弃，最左边一位保留原符号位。

     ​	相当于`操作数/2^右移位数`。

     举例：

     ```java
     6>>2;	//1, 相当于6/2^2, 符号位的0保留, 00000110右移两位后变成00000001
     ```

   - `>>>`无符号右移：

     说明：

     ​	无符号右移与右移的区别在于，无符号右移符号位始终补0。

6. `&`、`|`和`^`等作为按位运算符时：

   将两个数对应bit上的二进制数进行对比运算，返回新的数。

   例如：

   ```java
   6 & 3;	//2, 00000110 & 00000011 得 00000010
   ```

### 2.6 原码/反码/补码

概述：

​	原码/反码/补码是数据在计算机中的三种表示形式。

​	计算机在操作的时候，一般都是采用数据对应的二进制**补码**来进行计算的。

说明：

- 原码：

  **最高位为符号位**，0为正，1为负。

  后面的都是数值位，在原码中，数值位直接就是数据的二进制表示。

  例如：

  ​	-7的原码：`10000111`。

- 反码：

  正数的反码**与原码相同**。

  负数的反码**符号位不变**，数值位**全部取反**。

  例如：

  ​	-7的反码：`11111000`。

- 补码：

  正数的补码**与原码相同**。

  负数的补码，在**反码的基础上加1**。

  例如：

  ​	-7的补码：`11111001`。

要点：

1. 正数的原码、反码和补码都**相同**。
2. 负数的原码、反码和补码的**符号位都为1**。
3. 负数反码的**数值位**在原码的基础上**取反**，补码又在反码的基础上**加1**。

### 2.7 流程控制语句

流程控制语句用于控制程序执行的流程，以实现各种结构方式。

- 选择语句：

  - switch：

    格式：

    ```java
    switch(表达式：byte/short/int/char/Enum(JDK5)/String(JDK7)){
        case 值1:
            语句...;
            break;
        case 值2:
            语句...;
            break;
        ...
        default:
            语句(没有以上项时执行)...;
            break;
    }
    ```

    注意事项：

    1. case后面只能是**常量**。
    2. default可以省略。
    3. break不建议省略，若case后的break省略了，系统会**顺延执行后面case中的语句**。

- 判断语句：

  - if：

    格式：

    ```java
    if(表达式，返回值必须为true或false){
        语句...;
    }else if{
        语句...;
    }else if{
        语句...;
    }
    ...
    else{
        语句...;
    }
    ```

    注意事项：

    1. if控制的语句若为一句，可省略大括号。
    2. `else if{...}`和`else{...}`可以没有。
    3. 表达式的返回值必须是true或false。

- 循环语句：

  - while：

    格式1：

    ```java
    while(判断条件语句){
        循环体语句;
        ...
    }
    ```

    格式2：

    ```java
    do{
        循环体语句;
        ...
    }while(判断条件语句)
    ```

  - for：

    格式：

    ```java
    for(初始化语句;判断条件语句;控制条件语句){
        循环体语句;
        ...
    }
    ```


  注意事项：

  1. 注意循环的控制条件，避免死循环。
  2. 循环体语句如果是一条，可以省略大括号。

### 2.8 方法（函数）

概述：

​	方法是完成特定功能的代码块，在很多语言中被称为函数。

定义格式：

```
修饰符 返回值类型 方法名(参数类型 参数名1, 参数类型 参数名2, ...){
    方法体语句;
    return 返回值;
}
```

相关名词：

- 方法重载：

  在同一个类中，存在**方法名相同**，而**参数列表不同**（参数类型和个数）的多个方法。

  与返回值无关。

注意事项：

1. 方法不调用就不会执行。
2. 方法之间是平级关系，不能嵌套定义。
3. 方法定义的时候，参数是用`,`隔开的。
4. 方法在调用的时候，不用再传递数据类型。
5. 如果方法有明确的返回值类型，就必须有return返回语句。

### 2.9 Java内存分配

Java内存分配包含如下几个部分：

- 栈
- 堆
- 方法区
- 本地方法区
- 寄存器



## 3. 面向对象

### 3.1 面向对象的概述

面向对象，是一种编程思想。相对于面向过程，有如下好处：

- 更接近人的思考习惯
- 把复杂的事情简单化
- 让我们从执行者变成了指挥者

### 3.2 类&对象

Java语言最基本的单位是类，类是一组相关的属性和行为的集合。

（待补全）

### 3.3 抽象类&接口

- 抽象类：

  一个不是具体的功能称为抽象功能，一个类中若存在抽象功能，则该类必是抽象类。

  **抽象类的特点：**

  1. 抽象类和抽象方法必须用abstract关键字修饰
  2. 抽象类中不一定有抽象方法，但是有抽象方法的类必然是抽象类
  3. 抽象类有构造方法，但是不能实例化
  4. 抽象类的子类可以是抽象类或具体类，若要有一个具体子类，该类需重写所有抽象方法

  **抽象类的成员特点：**

  - 成员变量：既可以是变量，也可以是常量
  - 构造方法：有，用于子类访问父类数据的初始化
  - 成员方法：既可以是抽象的，也可以是非抽象的

  **定义格式：**

  ```java
  abstract class{...}			//定义抽象类
  public abstract void function{...}		//定义抽象方法
  ```

  **抽象类的注意事项：**

  1. 一个类如果没有抽象方法，也可以定义为抽象类，意义是不让创建对象。
  2. abstract不能和以下关键字共存：
     - private：共存时报非法的修饰组合，原因是private方法不能被重写，而abstract修饰的方法必须让子类重写。
     - final：共存时报非法的修饰组合，理由同上。
     - static：共存时不报错但是无意义，因为被static修饰的方法可以通过类名直接调用，而抽象方法没有方法体。

- 接口：

  **接口的特点：**

  1. 接口用关键字interface表示：

  ```java
  interface 接口名 {}
  ```

  2. 类实现接口用implements表示

  ```java
  class 类名 implements 接口名 {}
  ```

  3. 接口不可以实例化，不过可以通过多态的方式来实例化
  4. 接口的子类：
     - 可以是抽象类，不过意义不大；
     - 可以是具体类，不过要求子类重写接口中所有的抽象方法。

  **接口成员特点：**

  1. 成员变量：接口的成员变量只能是常量，并且是静态的。

  		默认修饰符：public static final   建议这三个修饰符手动给出。

  2. 构造方法：接口没有构造方法，实现接口的类是继承的Object类的构造方法。

  		（类Object是类层次结构的根类，每个类都使用Object作为超类）

  3. 成员方法：接口的成员方法只能是抽象方法，

  		默认修饰符：public abstract    建议这两个修饰符手动给出。

  **接口、类之间的关系：**

  1. 类与类：

  		继承关系，可以是单继承，也可以是多层继承，**不可以多继承**。

  2. 类与接口：

  		实现关系，可以单实现，也可以多实现，并且还可以在继承一个类的同时实现多个接口。
  	格式举例：

  ```java
  class son extends Object implements Father,Mother
  ```

  3. 接口与接口：

  		继承关系，可以单继承，也可以多继承
  	格式举例：

  ```java
  interface Sister extends Father,Mother
  ```

### 3.4 封装&继承&多态

封装继承多态是Java语言的三大特性。

- 封装：

  隐藏对象的属性和实现细节，仅对外公开访问方法。

  **目的：**

  1. 增强安全性；
  2. 简化编程。

  **基本要求：**

  1. 把所有属性私有化；
  2. 对每个属性提供get和set方法。

- 继承：

  在一个现有类的基础上，增加新的功能/重写已有方法。

  继承更多的是体现`is a`的关系。

  **继承的优点：**

  1. 提高代码的复用性。
  2. 增量式开发模式，提高开发效率。
  3. 避免修改原代码带来的风险。

  **继承中的约束：**

  1. 父类中private修饰的成员不能被继承。
  2. 不能继承父类的构造方法，不过可以使用`super()`来初始化。
  3. Java中只能**单继承**（类或抽象类），不能多继承。
  4. 类/抽象类/接口可以继承**任意多个**接口。

- 多态：

  相同的事务，掉用相同的方法时，表现出不同的行为。

  **实现多态的三个必要条件：**

  1. 继承：多态中必须要存在有继承关系的父类和子类；
  2. 重写：子类重新定义父类中某些方法；
  3. 向上转型：将子类引用赋值给父类对象。



## 4. 常用类

### 4.1 Scanner

- **概述：**

  用来实现键盘录入的类。

- **构造方法：**

  `Scanner(InputStream source)`						//new Scanner(System.in)

- **重要方法：**

  | public boolean hasNextXxx() | 判断是否是某种类型的元素 |
  | --------------------------- | ------------------------ |
  | public Xxx nextXxx()        | 获取该元素               |

- **注意事项：**

  1. public String nextLine()会扫描剩下的所有字符，直到遇到换行符"\n"为止。

     其行为不同于next()方法，该方法会从遇到的第一个有效字符（非空格、换行符）开始扫描，直到遇到空格或者换行符为止。

     这意味着如果在敲了enter后调用了nextLine()方法，nextLine()会立即扫描结束，并返回一个空值。然后继续执行后面的语句。

### 4.2 String

- **概述：**

  字符串就是由多个字符组成的一串数据，也可以看成是一个字符数组。

  通过查看API可知：

  1. 字符串字面值"abc"也可以看成是一个字符串对象；
  2. 字符串为常量，赋值后不能改变（是值不能改变，引用可以改变）。

- **构造方法：**

  | public String()                                      | 空构造                                                      |
  | ---------------------------------------------------- | ----------------------------------------------------------- |
  | public String(**byte[] bytes**)                      | 把字节数组转成字符串                                        |
  | public String(**byte[] bytes,int index,int length**) | 把字节数组的一部分转换成字符串，从index开始，共length个成员 |
  | public String(**byte[] bys, String charsetName**)    | 通过指定字符集（编码表）解码给定字节数组，创建字符串对象    |
  | public String(**char[] value**)                      | 把字符数组转成字符串                                        |
  | public String(**char[] value,int index,int length**) | 把字符数组的一部分转成字符串，从index开始，共length个成员   |
  | public String(**String original**)                   | 把字符串常量值转成字符串                                    |

- **重要方法：**

  - 正则相关：

    | boolean matches(String regex)                                | 判断字符串是否匹配正则表达式                                 |
    | ------------------------------------------------------------ | ------------------------------------------------------------ |
    | public String[] **split**(String regex)                      | 返回拆分的字符串数组，所得的数组不包括结尾空字符串           |
    | public String **replaceAll**(String regex,String replacement) | 字符串.replaceAll(正则表达式,目标)	以目标替换所有字符串中出现的正则表达式 |

  - 判断：

    | boolean equals(String str)               | 比较字符串的 [内容] 是否相同，区分大小写 |
    | ---------------------------------------- | ---------------------------------------- |
    | boolean **equalsIgnoreCase**(String str) | 比较字符串的内容是否相同，不区分大小写   |
    | boolean **contains**(String str)         | 判断大字符串中是否包含小字符串           |
    | boolean **startsWith**(String str)       | 判断字符串是否以某个指定的字符串开头     |
    | boolean **endsWith**(String str)         | 判断字符串是否以某个指定的字符串结尾     |
    | boolean **isEmpty**()                    | 判断字符串是否为空                       |

  - 获取长度&索引：

    | int length()                              | 获取字符串的长度                                             |
    | ----------------------------------------- | ------------------------------------------------------------ |
    | char **charAt**(int index)                | 获取指定索引位置的字符                                       |
    | int **indexOf**(int ch)                   | 返回指定字符在此字符串中第一次出现的索引                     |
    | int **indexOf**(String str)               | 返回指定字符串在此字符串中第一次出现的索引，返回的是首字符的索引 |
    | int **indexOf**(int ch,int fromIndex)     | 返回指定字符在此字符串中从指定位置后第一次出现的索引         |
    | int **indexOf**(String str,int fromIndex) | 返回指定字符串在此字符串中从指定位置后第一次出现的索引       |

  - 截取：

    | String substring(int beginIndex)                  | 从beginIndex开始一直到结尾截取新的字符串并返回 |
    | ------------------------------------------------- | ---------------------------------------------- |
    | String **substring**(int beginIndex,int endIndex) | 从beginIndex开始到endIndex截取新的字符串并返回 |

  - 与其他数据类型转换：

    | byte[] getBytes()                       | 把字符串转换为Byte（字节）数组                 |
    | --------------------------------------- | ---------------------------------------------- |
    | byte[] **getBytes**(String charsetName) | 使用给定字符集（编码表）将字符串编码为字节数组 |
    | char[] **toCharArray**()                | 把字符串转换为字符数组                         |
    | static String **valueOf**(char[] chs)   | 把字符数组转换成字符串                         |
    | static String **valueOf**(int i)        | 把int类型数据转成字符串                        |

  - 大小写转换、拼接、替换：

    | String toLowerCase()                      | 把字符串转成小写                                             |
    | ----------------------------------------- | ------------------------------------------------------------ |
    | String **toUpperCase**()                  | 把字符串转成大写                                             |
    | String **concat**(String str)             | 把字符串拼接                                                 |
    | String **replace**(char old,char new)     | 把字符串中所有old字符替换为new字符                           |
    | String **replace**(String old,String new) | 把字符串中所有old小字符串替换成new小字符串，返回替换后的字符串，原有字符串不发生改变 |

  - 去除空格&比较：

    | String trim()                           | 去除字符串两端的空格         |
    | --------------------------------------- | ---------------------------- |
    | int **compareTo**(String str)           | 按字典顺序比较，区分大小写   |
    | int **compareToIgnoreCase**(String str) | 按字典顺序比较，不区分大小写 |

    字符串比较的说明：

    ​	比较字符串与参数字符串第一个字符，若不等，返回字符串ASCII-参数字符串ASCII；若相等，比较下一位；以此类推。

    ​	若两个字符串不同，且一个字符串为另一个字符串的前面N位，返回字符串长度。

### 4.3 StringBuffer/StringBuilder

- **概述：**

  StringBuffer是可变字符串，数据同步、安全，效率低。

  StringBuilder是不同步，线程不安全，但是速度更快的可变字符串。

- **StringBuffer和String的区别：**

  StringBuffer长度可变，而String长度不可变。

  用StringBuffer做字符串拼接，不会浪费太多系统资源。

- **构造方法：**

  | public StringBuffer()                 | 无参构造方法                     |
  | ------------------------------------- | -------------------------------- |
  | public StringBuffer(**int capacity**) | 指定容量的字符串缓冲区对象       |
  | public StringBuffer(**String str**)   | 指定字符串内容的字符串缓冲区对象 |

- **重要方法：**

  - 获取容量&长度：

    | public int capacity()   | 返回当前容量       |
    | ----------------------- | ------------------ |
    | public int **length**() | 返回长度（字符数） |

  - 拼接&插入：

    | public StringBuffer append(String str)                | 把任意类型数据添加到字符串缓冲区后面，返回字符串缓冲区本身 |
    | ----------------------------------------------------- | ---------------------------------------------------------- |
    | public StringBuffer **insert**(int offset,String str) | 在指定位置把任意类型数据添加到字符串缓冲区里面             |

  - 删除部分内容：

    | public StringBuffer deleteCharAt(int index)       | 删除指定位置的字符，并返回本身                               |
    | ------------------------------------------------- | ------------------------------------------------------------ |
    | public StringBuffer **delete**(int start,int end) | 删除指定位置开始到指定位置结束的字符，并返回本身，包左不包右 |

  - 替换部分内容：

    public StringBuffer **replace**(int start,int end,String str)

    从Start开始到End用str替换，包左不包右

  - 反转：

    public StringBuffer **reverse**()

    将字符串反转

  - 截取：

    | public String subString(int start)             | 从start到结尾/到end截取字符串，包左不包右          |
    | ---------------------------------------------- | -------------------------------------------------- |
    | public String **substring**(int start,int end) | 注意返回值为String，方法不对StringBuffer本身做修改 |

- **StringBuffer/StringBuilder和String相互转换：**

  - String => StringBuffer

    1. 通过构造方法public StringBuffer(String str)

       ```java
       StringBuffer sb = new StringBuffer(str);
       ```

    2. 通过append()方法public StringBuffer append(String str)

       ```java
       sb.append(s);
       ```

  - StringBuffer => String

    1. 通过构造方法public String(StringBuffer buffer)

       ```java
       String str = new String(buffer);
       ```

    2. 通过toString()方法public String toString()

       ```java
       String str = buffer.toString();
       ```

### 4.4 Arrays

- **概述：**

  Arrays是操作数组的工具类，提供静态方法。

- **重要方法：**

  | public static String toString(int[] arr)              | 把数组转成字符串"[11, 22, 33]"                               |
  | ----------------------------------------------------- | ------------------------------------------------------------ |
  | public static void **sort**(int[] arr)                | 对数组进行升序排序                                           |
  | public static int **binarySearch**(int[] arr,int key) | 二分查找key在arr中的位置，注意在查找前需对数组进行排序（sort） |
  | public static <T> List<T> **asList**(T... a)          | 把数组转成集合，这里转成的是Arrays私有的特殊ArrayList，不包含add和remove等方法，长度不变 |

### 4.5 Integer

- **概述：**

  Integer类在在对象中包装了一个基本类型int的值。

  Integer类型的对象包含一个int类型的字段。

  此外，该类提供了多个方法，能在int类型和String类型之间相互转换，还提供了处理int类型时非常有用的其他一些常量和方法。

- **构造方法：**

  | public Integer(int value)    | 使用一个int初始化        |
  | ---------------------------- | ------------------------ |
  | public Integer(**String s**) | 注意此处String只能有数字 |

- **重要方法：**

  | public static int parseInt(String s)           | 将字符串转换为十进制整数int，其他基本类型的包装类中也有类似方法 |
  | ---------------------------------------------- | ------------------------------------------------------------ |
  | public static int **parseInt**(String s,int radix) | 将其他进制数字转换为十进制，范围2-36                         |
  | public static String **toString**(int i,int radix) | 将十进制数字转转为其他进制，范围2-36                         |

- **相关问题：**

  1. 自动拆装箱（JDK5）：

     在一些情景，系统会将基本类型自动转化为包装类类型，例如：

     `Integer i = 100;` 等价于 `Integer i = Integer.valueOf(100);`。

     同理有些时候包装类类型也可以自动转换为基本类型，例如：

     `Integer i = 100;`  `i += 200;`，等价于`i = Integer.valueOf(i.intValue() + 200);`。

     注意：在使用时若Integer i = null;	上述过程会出现NullPointerException空指针异常。

  2. int缓冲池：

     Integer的数据直接赋值，如果在-128~127之间，会直接从缓冲池里获取数据。

     ```java
     Integer i = 127;
     Integer ii = 127;
     System.out.println(i == ii);	//true
     ```

### 4.6 Character

- **概述：**

  Character类在对象中包装了一个基本类型为char的值。

  Character类型的对象包含类型为char的单个字段。

  此外，该类提供了几种方法，以确定字符的类别（小写字母，数字，等等），并将字符从大写转换成小写，反之亦然。

- **构造方法：**

  public Character(char value)			//使用一个char来初始化

- **重要方法：**

  | public static boolean isUpperCase(char ch)     | 判断给定字符是否是大写字符 |
  | ---------------------------------------------- | -------------------------- |
  | public static boolean **isLowerCase**(char ch) | 判断给定字符是否是小写字符 |
  | public static boolean **isDigit**(char ch)     | 判断给定字符是否是数字字符 |
  | public static char **toUpperCase**(char ch)    | 把给定字符转成大写字符     |
  | public static char **toLowerCase**(char ch)    | 把给定字符转成小写字符     |

### 4.7 Pattern&Matcher

- **概述：**

  Pattern是正则表达式的编译表示形式。

  Matcher是匹配器。

  这里只介绍两个常用操作。

- **使用方法：**

  - 判断（匹配）功能：

    ```java
    Pattern p = Pattern.compile("a*b");		//把正则表达式编译成模式对象
    Matcher m = p.matcher("aaaaab");		//通过模式对象调用matcher方法输入被匹配的字符串得到匹配器对象
    boolean b = m.matches();				//调用匹配器对象的功能
    ```

  - 获取字符串中所有满足正则表达式的子字符串：

    ```java
    Pattern p = Pattern.compile(regex);		//把正则表达式编译成模式对象
    Matcher m = p.matchers(s);				//通过模式对象调用matcher方法输入被匹配的字符串得到匹配器对象
    while(m.find()){
        System.out.println(m.group());		//第一个括号为group(1)，以此类推。group(0)为整个匹配到的字符串
    }
    ```

### 4.8 Math

- **概述：**

  Math类包含用于执行基本数学运算的方法，如初等指数，对数，平方根和三角函数。

- **重要方法：**

  | public static int abs(int a)                    | 取绝对值                  |
  | ----------------------------------------------- | ------------------------- |
  | public static double **ceil**(double a)         | 向上取整                  |
  | public static double **floor**(double a)        | 向下取整                  |
  | public static int **max**(int a,int b)          | 取较大值，同理还有min方法 |
  | public static double **pow**(double a,double b) | a的b次幂                  |
  | public static double **random**()               | 返回[0.0, 1.0)的随机数    |
  | public static int **round**(float a)            | 将float四舍五入后返回int  |
  | public static double **sqrt**(double a)         | a的正平方根               |

### 4.9 Random

- **概述：**

  产生随机数的类。

- **构造方法：**

  | public Random()              | 使用默认种子（当前时间毫秒值）                     |
  | ---------------------------- | -------------------------------------------------- |
  | public Random(**long seed**) | 使用给定种子（给定种子后每次都会得到相同的随机数） |

- **重要方法：**

  | public int nextInt()          | 获取int范围内随机数    |
  | ----------------------------- | ---------------------- |
  | public int **nextInt**(int n) | 获取[0, n)范围内随机数 |

### 4.10 System

- **概述：**

  在 System 类提供的设施中，有：

  - 标准输入、标准输出和错误输出流
  - 对外部定义的属性和环境变量的访问
  - 加载文件和库的方法
  - 快速复制数组的一部分的实用方法

- **重要方法：**

  | public static void gc()                                      | 调用垃圾回收器。实际上是调用了Object类的finalize()方法。建议除非是回收大量对象，不要使用该方法 |
  | ------------------------------------------------------------ | ------------------------------------------------------------ |
  | public static void **exit**(int status)                      | 关闭当前运行的Java虚拟机。参数作为状态码，非0状态码表示异常终止 |
  | public static long **currentTimeMillis**()                   | 返回自1970年1月1日开始的当前时间的毫秒值表示                 |
  | public static void **arraycopy**(Object src,int srcPos,Object dest,int deskPos,int length) | 从原数组中复制一个子数组到目标数组(arraycopy全为小写)  System.arraycopy(原数组,原数组起始索引,目标数组,目标数组起始索引,要复制元素个数) |

### 4.11 BigInteger

- **概述：**

  可表示/计算超出int范围的整数。

- **重要构造方法：**

  public BigInteger(**String val**)		//字符串表示的大整形

- **重要方法：**

  | public BigInteger add(BigInteger val)                      | 返回bi + bi2                       |
  | ---------------------------------------------------------- | ---------------------------------- |
  | public BigInteger **subtract**(BigInteger val)             | 返回bi - bi2                       |
  | public BigInteger **multiply**(BigInteger val)             | 返回bi * bi2                       |
  | public BigInteger **divide**(BigInteger val)               | 返回bi / bi2                       |
  | public BigInteger[] **divideAndRemainder**(BigInteger val) | 返回BigInteger[]数组	{商，余数} |

### 4.12 BigDecimal

- **概述：**

  该类可以精确的表示及计算浮点数，不同于bi2float和double在运算时有时会丢失精度。

  不可变的，任意精度的有符号十进制数。

- **重要构造方法：**

  public BigDecimal(String val)			//将字符串转换为BigDecimal

  以上方法稳定，结果可预知，推荐使用。

  也可以传入double进行构造，而此方法会产生不可预知的结果。

- **重要方法：**

  | public BigDecimal add(BigDecimal augend)                     | 返回和                                                       |
  | ------------------------------------------------------------ | ------------------------------------------------------------ |
  | public BigDecimal **subtract**(BigDecimal subtrahend)        | 返回差                                                       |
  | public BigDecimal **multiply**(BigDecimal multiplicand)      | 返回乘积                                                     |
  | public BigDecimal **divide**(BigDecimal divisor)             | 返回商                                                       |
  | public BigDecimal **divide**(BigDecimal divisor,int scale,int roundingMode) | (除数，保留几位小数，舍取方法)，舍取方法可填入`BigDecimal.ROUND_HALF_UP`来表示四舍五入 |

### 4.13 Date/DateFormat/Calender

1. **Date类：**

   - 概述：

     表示特定的时间，精确到毫秒。

   - 构造方法：

     | Date()              | 根据当前毫秒值构造日期对象     |
     | ------------------- | ------------------------------ |
     | **Date(long date)** | **根据给定毫秒值构造日期对象** |

   - 重要方法：

     | toString()                         | 星期 月份 日期 时:分:秒 时区 年份 |
     | ---------------------------------- | --------------------------------- |
     | **public long getTime()**          | **返回Date对象dt的毫秒值(long)**  |
     | **public void setTime(long time)** | **给Date对象dt设置毫秒值**        |

2. **DateFormat：**

   - 概述：

     抽象类。

     其子类如**SimpleDateFormat**允许实现**格式化**（日期 --> 文本）和**解析**（文本 --> 日期）和标准化。

   - 重要方法：

     | public final String format(Date date)                      | 将一个Date格式化为String（日期/时间）                        |
     | ---------------------------------------------------------- | ------------------------------------------------------------ |
     | public Date **parse**(String source) throws ParseException | 将一个String（日期/时间）解析为Date。注意此处提供的String需要与sdf构造的模式相匹配 |

   - **重要子类SimpleDateFormat：**

     构造方法：

     ​	SimpleDateFormat()					//使用默认的模式

     ​	SimpleDateFormat(**String pattern**)		//使用给定的模式

     pattern的格式：

     | 说明 | 对应字母 |
     | :--: | :------: |
     |  年  |    y     |
     |  月  |    M     |
     |  日  |    d     |
     |  时  |    H     |
     |  分  |    m     |
     |  秒  |    s     |

     举例：

     ```
     "2018年01月13日 19:14:30"
     "yyyy年MM月dd日 HH:mm:ss"
     ```

3. **Calender：**

   - **概述：**

     Calendar日历类是一个抽象类，其包含诸如YEAR,MONTH,DAY_OF_MONTH,HOUR等的int字段，并提供了一些转换的方法。

     注意：MONTH字段从0开始，0表示一月。

   - **重要方法：**

     public static Calendar **getInstance**()			//Calendar.getInstance()，获取一个子类对象，使用当前的时间/默认时区等。

     public int **get**(int field)						//cl.get(Calendar.YEAR)，获取该对象给定日历字段的值。

     public void **add**(int field,int amount)		//cl.add(Calendar.YEAR , -5)，根据给定字段和时间，对日历对象进行操作。

     public final void **set**(int year,int month,int date)		//cl.set(2018,0,14)，设置日历对象的年月日。

### 4.14 Collections

- **概述：**

  针对集合进行操作的工具类，提供静态方法。

- **重要方法：**

  public static <T> void **sort**(List<T> list)		//Collections.sort(List<T>)，对集合进行排序。该构造方法使用自然排序（参数需要Comparable），可追加一个Comparator进行比较器排序。
  public static <T> int **binarySearch**(List<?> list, T key)	//Collections.binarySearch(List<? extends T>, key)，使用二分查找法查找给定key在集合中的索引，需要传入已排序的list。
  public static <T> T **max**(Collection<?> c)		//Collections.max(Collection)，获取集合中的最大值。
  public static void **reverse**(List<?> list)		//Collections.reverse(List)，反转集合中的元素。
  public static void **shuffel**(List<?> list)		//Collections.shuffel(List)，随机置换集合中的元素。

- **Collection和Collections的区别：**

  Collection：是单列集合的顶层接口，有子接口List和Set。

  Collections：是针对集合进行操作的工具类，包含排序、二分查找等实用方法。



## 5. 通用知识

### 5.1 排序&查找

- **排序：**

  - 冒泡排序（bubbleSort）：

  相邻元素两两比较，大的往后放。

  第一次执行完毕，最大值出现在最大索引处。

  所以要循环执行`n-1`次。

  - 选择排序：

  从0索引开始，依次和后面的元素比较，小的往前放。

  第一次完毕，最小值出现在最小索引处。

- **查找：**

  - 二分查找：

    在数组元素有序的前提下，通过先查询中间数来判断目标数取值范围的方式，达到减少计算量的目的。

    要求首先对数组进行排序。

### 5.2 正则表达式

- **概述：**

  是指一个用来描述或者匹配一系列**符合某个句法规则的字符串**的单个字符串。

  其实就是一种规则，有自己特殊的应用。

- **常用规则：**

  |  表达式  |             含义              |
  | :------: | :---------------------------: |
  |    x     |             字符x             |
  |    \\    |          反斜线字符           |
  |    \n    |            换行符             |
  |    \r    |            回车符             |
  |  [abc]   |           a、b或者c           |
  |  [^abc]  |      除a、b、c外任何字符      |
  | [a-zA-Z] | a到z或A到Z，两头字母包括在内  |
  |  [0-9]   |       0到9的字符都包括        |
  |    .     | 任何字符，若要表示'.'字符，\. |
  |    \d    |             数字              |
  |    \w    |     单词字符[a-zA-Z_0-9]      |
  |    x?    |      X，一次或一次也没有      |
  |    x*    |         X，零次或多次         |
  |    x+    |         X，一次或多次         |
  |   x{n}   |          X，恰好n次           |
  |  x{n,}   |          X，至少n次           |
  |  x{n,m}  | X，至少 n 次，但是不超过 m 次 |

- **使用方式：**

  1. String类的matches、split和replaceAll方法，详见第四章。
  2. Pattern类和Matcher类，详见上一章。

### 5.3 数据结构

- **概述：**

  数据结构，就是数据的组织方式。

- **常见数据结构：**

  - **栈：**

    特点：先入（压栈）后出（弹栈）。

  - **队列：**

    特点：先进先出。

  - **数组：**

    概述：
    	存储同一种类型的多个元素的容器，有索引方便获取元素。
    特点：
    	查询快，增删慢（由于长度固定需新建数组）。

  - **链表：**

    概述：
    	由一个“链子”把多个结点连起组成的数据。
    	结点：由数据和地址组成（数据域和指针域）
    特点：
    	查询慢（需从头开始），增删快。

### 5.4 JDK5新特性

1. **泛型：**

   - **概述：**

     不同于数组，泛型是一种把类型确定工作推迟到创建对象/调用方法的时候的一种特殊类型。将类型当作参数传递。

   - **格式：**

     ```
     <数据类型>		//此处的数据类型只能是引用类型
     
     ```

     泛型类：

     ```
     class ObjectTools<T>	//把泛型定义在类上
     
     ```

     泛型方法：

     ```
     public <T> void show(T t)	//把泛型定义在方法上
     ```

     泛型通配符：

     ```
     <?>						//任意类型
     <? extends E>			//E及其子类
     <? super E>				//E及其父类
     
     ```

   - **好处：**

     1. 把运行期间的问题提前到编译时期；
     2. 避免了强制类型转换；
     3. 消除了警告，优化了程序设计。

2. **增强for：**

   - **概述：**

     是for循环的一种，可以替代迭代器，简化集合遍历。

   - **格式：**

     ```
     for(元素数据类型 变量名 : 数组或者集合名){
         ...
     }
     ```

   - **注意事项：**

     1. 增强for的目标不能为null，否则会报NullPointerException；
     2. 同迭代器一样，在遍历的时候更改集合元素会报并发修改异常。

3. **静态导入：**

   - **概述：**

     import可以直接导入到方法的级别。

   - **格式：**

     ```
     import static 包名.类名.方法名;
     ```

   - **举例：**

     ```java
     import static java.lang.Math.pow;
     ...
     d = pow(2.0, 3.0);		//直接使用
     ```

   - **注意事项：**

     1. 导入的方法必须为静态方法（static）；
     2. 如果本类下有多个同名的静态方法，必须加类名前缀调用。

4. **可变参数：**

   - **概述：**

     定义方法的时候参数的个数可以不确定。

   - **格式：**

     ```
     修饰符 返回值类型 方法名(数据类型... 变量名){
         ...
     }
     ```

   - **举例：**

     ```java
     public int sum(int... a){
         int s = 0;
         for(int x : a){
             s += a;
         }
         return s;
     }
     ```

   - **注意事项：**

     1. 可变参数的变量是一个数组（长度为所传参数的个数）；
     2. 可变参数的位置可以传入对应数据类型的数组；
     3. 如果除了可变参数还需传入其他参数，可变参数必须放到最后。

### 5.5 递归

- **概述：**

  方法定义中调用方法本身的现象。

  递归方法，需要规律和出口条件。

- **举例：**

  ```java
  public void method(){
      method();
  }
  ```

- **注意事项 ：**

  1. 递归一定要有出口，否则就是死递归；
  2. 递归次数不能太多，否则会不断加载方法到栈内存导致内存溢出；
  3. 构造方法不能递归使用。

### 5.6 编码表

- **概述：**

  由现实世界的字符和对应的数值组成的一张表。

- **常见编码表：**

  - ASCII码表：最高位为符号位，其余位为数值位。
  - ISO-8859-1：拉丁码表，八位表示一个数据。
  - GB2312：中国的中文码表。
  - GBK：中国的中文码表升级，融合了更多的中文文字符号。
  - GB18030：GBK的取代版本。
  - BIG-5码：通行于台湾、香港等地的一个繁体字编码方案，俗称大五码。
  - Unicode：国际标准码，融合了多种文字。所有文字都用两个字节来表示，Java语言用的就是Unicode。
  - UTF-8：最多用三个字节来表示一个字符。



## 6. 数组

### 6.1 概述

数组是存储**同一种数据类型多个元素**的容器。

数组的每一个元素都有编号，称为**索引**（index），从0开始。

### 6.2 定义数组&初始化

**定义数组格式：**

```
数据类型[] 数组名;		//推荐方式
数据类型 数组名[];
```

**数组的初始化：**

- 动态初始化（只给长度，系统给出默认值）：

  ```
  int[] arr = new int[3];
  ```

- 静态初始化（给出各个值，系统决定长度）：

  ```
  int[] arr = new int[]{1,2,3};
  int[] arr = new int{1,2,3};			//简化写法
  ```

**遍历数组：**

​	使用for循环即可，举例：

```java
for(int x = 0;x < arr.length;x ++){
    arr[x]...;
}
```

### 6.3 二维数组

二维数组是元素类型为一维数组的数组。

​	定义格式：

```
int[][] arr = new int[外层数组长度][里层数组长度];	//动态初始化
int[][] arr = new int[外层数组长度][];			//可以暂不指定里层数组长度
int[][] arr = new int[][]{{...},{...},...};		//静态初始化
int[][] arr = {{...},{...},...};				//简化的写法
```

遍历二维数组，用循环嵌套即可。

### 6.4 注意事项

1. 数组一旦初始化完毕，其长度不再发生改变。



## 7. 集合框架

### 7.1 概述

- **为什么会出现集合类？**

  面向对象语言对事物的体现都是以对象的形式，为了方便对多个对象的操作，Java就提供了集合类。

- **数组和集合类同时容器，有何不同？**

  1. 长度：数组长度固定/集合的长度可变；
  2. 类型：数组只能存储同一种类型的元素/集合可以存储不同类型的元素；
  3. 基本/引用：数组可以存储基本和引用类型数据/集合只能存储引用类型数据。

- **集合框架体系图如下：**

  ![](集合框架.png)

### 7.2 Collection接口

- **概述：**

  Collection是集合的顶层接口，其子体系有重复的/唯一的/有序的/无序的。

- **重要功能：**

  - **添加功能：**

    boolean **add**(Object obj)		//c.add(o)，添加一个元素，成功操作返回true
    boolean **addAll**(Collection c)	//c1.addAll(c2)，添加一个集合的元素，成功操作返回true

  - **删除功能：**

    void **clear**()					//c.clear()，清除所有元素
    boolean **remove**(Object obj)	//c.remove(o)，移除一个元素，成功操作返回true

    boolean **removeAll**(Collection c)	//c1.removeAll(c2)，从c1中移除c2包含的所有元素

  - **判断功能：**

    boolean **contains**(Object obj)		//c.contains(o)，判断集合中是否包含指定元素（底层是equals）
    boolean **containsAll**(Collection c)	//c1.containsAll(c2)，判断c1中是否包含所有c2中的元素

  - **迭代器：**

    Iterator<E> **iterator**()				//Iterator it = c.iterator()，Iterator是一个接口，这里返回的是一个子类对象
    迭代器的最终实现，是通过各集合最终实现类（如ArrayList）中的内部类。
    	Iterator的功能：
    		boolean **hasNext**()	//it.hasNext()，如果仍有元素可以迭代，返回true
    		Object **next**()		//it.next()，获取元素，并移动到下一位置

  - **长度&交集&转换：**

    int **size**()						//c.size()，返回集合中元素的个数
    注意：此处为集合与数组和String的不同之处，后两者的长度功能为length()

    boolean **retainAll**(Collection c)	//c1.retainAll(c2)，c1和c2做交集，结果保存在c1中，c2不变。如果c1发生了改变返回true

    Object[] **toArray**()			//c.toArray()，返回此collection中所有元素的数组


### 7.3 List接口及其子类

- **概述：**

  **有序**的Collection，又称为序列。

  此接口可根据整数**索引**访问元素，根据索引搜索元素，精确控制插入元素的位置等。
  与Set不同，List通常**允许重复**的元素。

- **特有功能：**

  - **添加功能：**

    void **add**(int index,Object element)		//list.add(索引,元素)，用[元素]替代原来在[索引]处的元素，原来[索引]开始的元素全体后移一位

  - **获取功能：**

    Object **get**(int index)					//list.get(索引)，返回[索引]处的元素

  - **删除功能：**

    Object **remove**(int index)				//list.remove(索引)，删除[索引处的元素] (后面的集体前移一位)，返回被删除的元素

  - **修改功能：**

    Object **set**(int index,Object element)	//list.set(索引,元素)，根据索引修改元素，返回被修改的元素

  - **列表迭代器：**

    ListIterator **listIterator**()				//list.listIterator()，返回List集合特有的迭代器ListIterator（同为子类对象实现）
    	ListIterator：一个接口，List集合特有的迭代器，继承了Iterator（可以直接使用hasNext()和next()），部分特有功能：
    		Object **previous**()			//lit.previous()，返回上一个元素
    		boolean **hasPrevious**()	//lit.hasPrevious()，判断是否有上一个元素
    		void **add**(E e)				//lit.add(元素)，将指定元素添加到List（注意不是添加到迭代器），添加的位置为当前迭代器迭代指针的位置

- **三个重要子类ArrayList、Vector和LinkedList：**

  1. **ArrayList：**

     概述：

     ​	经典的List子类，使用List中的方法即可，不过多介绍。

     ​	底层数据结构是**数组**（**查询快，增删慢**），线程**不安全**，效率**高**。

  2. **Vector:**

     概述：

     ​	底层数据结构是**数组**（**查询快，增删慢**），线程**安全**，效率**低**。

     特有功能：

     ​	public Object **elementAt**(int index)		//vec.elementAt(索引)，返回索引处

     ​										的元素，场用get(index)替代

     ​	public Enumeration **elements**()	//vec.elements()，常用Iterator 

     ​									iterator()替代

     ​	boolean **hasMoreElements**()		//en.hasMoreElements()，常用

     ​									hasNext()替代

     ​	Object **nextElement**()				//en.nextElement()，常用next()替代

  3. **LinkedList：**

     概述：

     ​	底层数据结构是**链表**（**查询慢，增删快**），线程**不安全**，效率**高**。

     部分特有功能：

     - 添加功能：

       public void **addFirst**(Object e)	//list.addFirst(元素)，将元素插入列表开头
       public void **addLast**(Object e)	//list.addLast(元素)，将元素插入列表末尾

     - 获取功能：

       public Object **getFirst**()		//list.getFirst()，返回列表第一个元素
       public Object **getLast**()		//list.getLast()，返回列表最后一个元素

     - 删除功能：

       public Object **removeFirst**()	//list.removeFirst()，移除并返回列表第一个元素
       public Object **removeLast**()	//list.removeLast()，移除并返回列表最后一个元素

### 7.4 Set接口

- **概述：**

  - 一个不包含重复元素（不包含满足equals关系的元素对）（唯一），元素无序的Collection（无序）。
  - 最多只能有一个null。
  - 相比Collection没有特有方法。

- **两个重要子类：**

  - **HashSet<E>：**

    - **概述：**

      HashSet<E>的底层由HashMap支持。

    - **重要子类：**

      LinkedHashSet：

      唯一、有序的HashSet，底层数据结构由哈希表和链表组成。

      哈希表保证元素唯一性，链表保证元素有序。

    - **相关问题：**

      存储多个相同字符串时只会留下一个的解释：
      	public boolean add(E e)方法底层依赖hashCode()和equals()。
      	比较基于hashCode()的值是否相同，
      		相同：继续比较equals()，若也相同就不添加，否则就添加；
      		不同：添加。

  - **TreeSet<E>：**

    - **概述：**

      - 基于TreeMap的NavigableSet实现，能够按照**某种规则对元素进行过排序**。
      - 默认使用[自然顺序]对元素进行排序，或者根据创建Set时提供的Comparator进行排序，具体取决于使用的构造方法。
      - 不论是自然排序还是比较器排序，该类会依次**从小到大排序**。

    - **特点：**

      唯一和排序。

    - **两种排序：**

      - **自然排序：**

        - 概述：

          使用public TreeSet()构造方法。
          元素所属类需要实现Comparable<T>接口并重写int comparaTo(T o)，自行定义比较规则：
          	若返回值大于0则认为`元素`大于`参数`，

          ​	返回值小于0认为`元素`小于`参数`，

          ​	返回值等于0认为`元素`等于`参数` (不添加到集合)。

        - 原理：

          - 自然排序底层的数据结构是二叉树，加入集合的第一个元素为root，后来加入集合的元素将首先与root进行比较，若小于root则往root左边挂，大于root则往root右边挂，等于root则不添加到集合。

          - 二叉树具体查看传智播客Java基础Day17.19。

          - 自然排序中的比较是依赖元素的compareTo()方法。compareTo()定义在接口Comparable中，需要子类实现该接口并按照需求重写该方法。

          - 自然排序中检查compareTo()的返回值：

            若返回值大于0则认为`元素`大于`参数`，

            返回值小于0认为`元素`小于`参数`，

            返回值等于0认为`元素`等于`参数` (不添加到集合)。

          - 例如需要对学生类的元素进行比较，规定age更大的学生对象为较大者，则需要在学生类中实现Comparable接口重写compareTo()，返回this.age - age 。

      - **比较器排序：**

        - 使用public TreeSet(Comparator<? super E> comparator)构造方法。

        - 需要自行写一个类实现Comparator<T>接口并重写int compare(T o1, T o2)，自行定义比较规则：

          ​	若返回值大于0则认为`o1`大于`o2`，

          ​	返回值小于0认为`o1`小于`o2`，

          ​	返回值等于0认为`o1`等于`o2` (不添加到集合)

        - 建议不单独写一个Comparator<T>实现类，而是在参数处定义匿名内部类。格式：

          ```java
          TreeSet ts<Student> = new TreeSet<Student>(
          new Comparator<Student>() {
              public int compare(Student s1, Student s2){
                  ...
              }
          });
          ```

### 7.5 Map<K, V>接口

- **概述：**

  - 集合框架另一个根接口（类似Collection），存储键值对（映射）。

  - 一个Map不能包含相同的键，不过可以有相同的值存在。

- **重要功能：**

  - **添加功能：**

    V **put**(K key, V value)			//m.put(key, value)，添加元素。若添加的key存在，用现在key对应的value替换之前的value并返回之前的value；若添加的key不存在，返回null。删除功能

  - **删除功能：**

    void **clear**()					//m.clear()，移除所有键值对
    V **remove**(Object key)			//m.remove(key)，根据键删除键值对，返回值。如果键不存在返回null。

  - **判断功能：**

    boolean **containsKey**(Object key)		//m.containsKey(key)，判断集合是否包含指定的键
    boolean **containsValue**(Object value)	//m.containsValue(value)，判断集合是否包含指定的值
    boolean **isEmpty**()					//m.isEmpty()，判断集合是否为空

  - **获取功能：**

    Set<Map.Entry<K, V>> **entrySet**()		//m.entrySet()，返回该map中所有键值对对象的Set集合
    	*需结合Map.Entry接口的K getKey()和V getValue()来获取键值对对象中的键和值。
    V **get**(Object key)				//m.get(key)，根据键获取值。若键不存在返回null。
    Set<V> **keySet**()				//m.keySet()，获取map中所有键的Set集合
    Collection<V> **values**()		//m.values()，获取map中所有值的Collection集合

  - **长度功能：**

    int **size**()						//m.size()，返回map中键值对的对数

- **重要子类：**

  - **HashMap：**

    基于哈希表的Map接口实现。键的唯一性由哈希表保证，也就是键是否唯一取决于键所在类的hashCode()和equals()。

    - 重要子类LinkedHashMap：

    		Map接口的哈希表和链表实现，具有可预知的迭代顺序（有序）。

    		哈希表保证元素的唯一性，链表保证键的顺序（存入和取出顺序一致）。

  - **TreeMap：**

    - **概述：**

      基于红黑树的Map接口实现。默认根据key的自然顺序进行排序，或者根据创建map时提供的comparator进行排序，具体取决于构造方法。

    - **排序概述：**

      - 自然排序需要存储的key所在类实现Comparable<T>接口并重写int compareTo(T o)。
      - 比较器排序需要自行写一个类实现Comparator<T>接口并重写int compare(T o1, T o2)，建议使用匿名内部类。

- **Map与Collection的区别：**

  1. Map存储的是成对的元素，

     Collection存储的是单个元素；

  2. Map集合的键（KEY）是唯一的，值（VALUE）是可重复的；

     Collection集合的Set是唯一的，List是可重复的。

- **Hashtable和HashMap的区别：**

  1. Hashtable线程安全，效率低，不允许null键和null值。
  2. HashMap线程不安全，效率高，允许null键和null值。

### 7.6 枚举

- **枚举类的概述：**

  1. 一种特殊的类，用关键字enum定义，与class、interface平级；
  2. 与单例模式类似又有所不同，当一个类的对象有且只有有限个时（例如一个星期只有七天，一年只有十二个月），这样的类被称为枚举类。

- **枚举类的重要特征：**

  1. 枚举类的首行必须是枚举值的列表，各个枚举值用逗号隔开。若枚举值之后还有内容，则需在最后一个枚举值后加上分号；
  2. 枚举类默认继承了Enum类，并实现了Serializable和Comparable<E>接口；
  3. 所有的枚举值都是public static final的，非抽象的枚举类不能再派生子类。

- **枚举类的重要方法：**

  1. static **values**()

     //WeekEnum.values()，返回包含所有枚举值的数组，返回的数组类型是当前枚举类（WeekEnum[]）

  2. public final int **comparaTo**(E o)

     //WeekEnum.MONDAY.comparaTo(WeekEnum.TUESDAY)，同一个枚举实例只能和同类型的枚举实例比较，返回两实例“位置编号之差”（-1）

  3. pubilc final String **name**()

     //WeekEnum.SUNDAY.name()，返回枚举实例的名称

  4. public String **toString**()

     //WeekEnum.SUNDAY.toString()，返回枚举实例的名称，可重写

  5. public static <T extends Enum<T>> T **valueOf**(Class<T> enumType, String name)

     //WeekEnum.valueOf(WeekEnum.class, MONDAY)，返回带指定名称的指定枚举类型的枚举常量

- **举例：**

  ```java
  public enum WeekEnum {
      MONDAY, TUESDAY, WENDSDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
  }
  
  public enum WeekEnum {
      MONDAY("周一"),  TUESDAY("周二"),  WENDSDAY("周三"),  THURSDAY("周四"),  FRIDAY("周五"),  SATURDAY("周六"),  SUNDAY("周日");
  
      private String date;
  
      private WeekEnum (String date) {
          this.date = date;
      }
  
      public String toString () {
          return "这是一个日子";
      }
  }
  ```



## 8. 异常

### 8.1 异常的概述

异常表示程序出现了不正常的情况。

### 8.2 Java异常体系

顶层类**Throwable**：程序的异常。

子异常：

- 类**Error**

  说明：

  ​	严重问题，不做处理。

  ​	因为一般是难以/无法处理的问题，例如内存溢出。

- 类**Exception**

  - 类**RuntimeException**

    说明：

    ​	运行时异常，不做处理。

    ​	此类异常的问题在于代码不够严谨，需要修正代码。

  - Exception下**除RuntimeException**以外Exception

    说明：

    ​	编译时异常，必须进行处理，否则代码不能通过编译。

### 8.3 异常的处理

- **默认处理：**

  如果程序出现问题，并没有做任何处理，Jvm最终会做出默认处理。
  默认处理方式：
  	把异常的名称、原因，及出现异常的位置输出在控制台，并**结束程序**。

- **手动处理：**

  **格式：**

  ```java
  try{
      ...
  }catch(异常类名 变量名){		//可以有多个catch
      ...(处理)
  }finally{					//可以没有finally
      ...
  }
  ```

  **注意事项：**

  1. 尽量不要用大的异常类名来处理，能明确的尽量明确；
  2. 一旦try中出现了匹配的异常，就会执行处理并结束`try...catch`语句；
  3. 平级关系的异常谁先catch无所谓，如果出现了父子关系的异常，父异常必须在后面。

- **抛出：**

  有些时候我们没有权限处理/处理不了/不想处理，就不处理了。

  为了解决此时的出错问题，提供了抛出解决方案。

  **格式：**

  ```java
  修饰符 返回值类型 方法名(参数) throws 异常类名1,异常类名2,... {
      ...
  }
  ```

  **说明：**

  - 方法抛出了异常，将交给调用者处理。
  - 编译期异常抛出，调用者必须处理（否则报错）；运行期异常抛出，调用者可以不处理。
  - 尽量不要在main方法上抛出异常。

- **异常处理方式的选择：**

  1. 如果该功能内部可以将问题处理，用try；如果处理不了，就交给调用者处理，用throws；
  2. 后续程序如果需要继续运行就用try；不用继续运行就用throws。

### 8.4 Throwable重要方法

- public String **getMessage**()

  e.getMessage()，返回异常消息的字符串。

- public String **toString**()

  e.toString()，返回异常信息的简单描述，按照如下的格式：

  此对象的全路径完整类名	+ ": "(冒号+空格) + 调用此对象getLocalizedMessage()方法的结果（默认返回的是getMessage()的结果）。

- void **printStackTrace**()

  e.printStackTrace()，获取异常类名和异常信息，以及异常在程序中的位置。

  把结果输出在控制台。

### 8.5 自定义异常

- **概述：**

  Java不可能所有异常都考虑到（比如需要分数在0-100之间），所以在实际开发中，可能需要自己定义异常。
  若要让一个类成为异常类，必须继承自Exception类或者RuntimeException类。

- **注意事项：**

  1. 自定义的异常类不用写任何成员方法；

  2. 可以通过写一个构造方法传一个字符串参数覆盖Exception类的构造方法，实现错误信息的输出，举例如下：

     ```java
     class MyException extends Exception {
     	public MyException () {}
     	public MyException (String message) {
     		super(message);
     	}
     }		//只需要throw new MyException("错误信息")即可
     ```

  3. 如果自定义的异常继承自Exception，则为编译期异常；继承自RuntimeException，则为运行期异常。

### 8.6 异常中涉及到继承的注意事项

1. 子类重写父类方法时，子方法不能抛出比父方法级别更大的异常；
2. 如果父方法抛出了多个异常，子方法不能抛出比父方法更多的异常，也不能抛出父方法没有抛出的异常（**只能抛出父亲所抛出异常的子集**）；
3. 如果父方法没有抛出异常，子方法不能抛出异常，只能try...catch，不能throws。



## 9. IO流

### 9.1 File类

- **概述：**

  File类是文件和目录的抽象表现形式。

- **重要构造方法：**

  - File(**String pathName**)

    根据一个路径得到File对象<目录/文件>

  - File(**String parent, String child**)

    根据parent<目录>和child<目录/文件>得到File对象

  - File(**File parent, String child**)

    根据一个父File对象<目录>和一个子文件/目录得到File对象

- **重要功能：**

  1. **创建功能：**

     - public boolean **createNewFile**()	

       file1.createNewFile()，创建文件。

       如果存在这样的文件就不创建了，并返回false。

     - public boolean **mkdir**()

       file1.mkdir()，创建文件夹。

       如果存在这样的文件夹就不创建了，并返回false。

     - public boolean **mkdirs**()

       file1.mkdirs()，创建文件夹。

       如果父文件夹不存在，会自动创建。

  2. **删除功能：**

     - public boolean **delete**()

       file1.delete()，删除文件或文件夹，如果没有删除返回false。

  3. **重命名功能：**

     - public boolean **renameTo**(File dest)

       file1.renameTo(file2)，如果路径名相同就是改名，如果路径名不同则为改名+剪切粘贴。

  4. **判断功能：**

     - public boolean **isDirectory**()

       file1.isDirectory()，判断是否为目录

     - public boolean **isFile**()

       file1.isDirectory()，判断是否为文件

     - public boolean **exists**()

       file1.exists()，判断是否存在

     - public boolean **canRead**()

       file1.canRead()，判断是否可读

     - public boolean **canWrite**()

       file1.canWrite()，判断是否可写

     - public boolean **isHidden**()

       file1.isHidden()，判断是否隐藏

  5. **获取功能：**

     - public String **getAbsolutePath**()

       file1.getAbsolutePath()，获取绝对路径

     - public String **getPath**()

       file1.getPath()，获取路径

     - public String **getName**()

       file1.getName()，获取名称

     - public long **length**()

       file1.length()，获取长度（字节数）

     - public long **lastModified**()

       file1.lastModified()，获取最后一次修改时间（毫秒值）

     - public String[] **list**()

       file1.list()，获取指定目录下所有文件/文件夹的名称数组

     - public String[] **list**(FilenameFilter filter)

       file1.list(filter)，根据一个文件名称过滤器的实现类对象，获取指定目录下满足要求的所有文件/文件夹的名称数组

     - public File[] **listFiles**()

       file1.listFiles()，获取指定目录下所有文件/文件夹的File数组

     - public File[] **listFiles**(FilenameFilter filter)

       file1.listFiles(filter)，根据一个文件名称过滤器的实现类对象，获取指定目录下满足要求的所有文件/文件夹的File数组

- **接口FileNameFilter概述：**

  文件名称过滤器，包含一个方法boolean accept(File dir, String name)。
  dir为被找到的文件所在目录，name为文件的名称。
  文件满足要求时返回true，否则返回false。
  通常以匿名内部类的方式实现。

- **注意事项：**

  1. 创建文件或者文件夹时，如果没有写盘符路径，会创建在默认路径下；
  2. 删除功能中，如果要删除一个文件夹 ，该文件夹内不能包含文件或文件夹；
  3. Windows系统下，Java的删除不会走系统回收站；
  4. 如果File的路径以盘符开始，则为绝对路径，否则为相对路径。

### 9.2 IO流概述

IO流用来处理设备间的数据传输（上传文件和下载文件）。

Java对数据的操作是通过流的方式，用于操作流的对象都在IO包中。

- **IO流的分类：**

  **按流向分：**

  ​	输入流（读取数据）

  ​	输出流（写出数据）

  **按数据类型分：**

  ​	字节流：

  ​		任何文件的传输都可以用字节流。

  ​	字符流：

  ​		一般文本性的文件才使用字符流。

- **IO流类名的规律：**

  - 字节输入流：**InputStream**
  - 字节输出流：**OutputStream**
  - 字符输入流：**Reader**
  - 字符输出流：**Writer**

  大部分IO流的类名都是以上四种结尾。

- **重要方法：**

  - void **flush**()

    该方法是IO流根类OutputStream和Writer都有的方法。

    作用是刷新该流的缓冲，将流的缓冲中所有write()方法保存的内容写入到文件中。

    **flush()和close()的区别：**

    - close()关闭流对象，在关闭之前会刷新一次缓冲区。关闭之后流对象就不能继续使用了；
    - flush()仅仅刷新缓冲区，刷新之后流对象还可以继续使用。

### 9.3 FileOutputStream文件字节输出流

- **概述：**

  用于将数据写入File的文件字节输出流。

- **构造方法：**

  - FileOutputStream(**File file**)

    创建一个向给定File所表示的文件中写入数据的文件字节输出流，

    从内容开头写入（如果文件存在会先删除所有原有内容）

  - FileOutputStream(**File file, boolean append**)

    创建一个向给定File所表示的文件中写入数据的文件字节输出流，

    如果第二参数为true，从内容末尾写入

  	 FileOutputStream(**String name**)	

    会根据给定String造一个File，并创建指向该File的fos对象，

    从内容开头写入（如果文件存在会先删除所有原有内容）

  - FileOutputStream(**String name, boolean append**)

    会根据给定String造一个File，并创建指向该File的fos对象，

    如果第二参数为true，从内容末尾写入

- **重要方法：**

  - public void **write**(int b)

    fos.write(int)，输入一个ASCII数字表示的**字节**

  - public void **write**(byte[] b)

    fos.write(byte[])，输入一个**字节数组**的所有字节

  - public void **write**(byte[] b, int off, int len)

    fos.write(byte[],起始索引,长度)，从一个**字节数组的off开始，输入len个字节**

- **重要问题：**

  1. **创建字节输出流到底做了哪些事情？**

     a. 调用系统功能创建文件；

     b. 创建fos对象；

     c. 把fos对象指向这个文件。

  2. **数据写成功后，为何要close()？**

     - fos对象变成垃圾，这样就可以被系统回收了
     - 通知系统回收跟该文件相关的资源

  3. **如何实现数据的换行？**

     写入换行符号即可。
     一般来说，换行符号是"\n"，然而不同操作系统对换行符号的识别不同，Windows自带记事本可以识别的换行符号为"\r\n"。
     一些常见的高级记事本，是可以识别任意换行符号的。

  4. **如何实现数据的追加写入？**

     使用带有第二参数boolean append的构造方法即可，append为true。

### 9.4 FileInputStream文件字节输入流

- **概述：**

  从文件系统的某个文件中获得输入字节。

- **构造方法：**

  - FileInputStream(**File file**)

    从File表示的文件创建文件字节输入流

  - FileInputStream(**String name**)

    从String表示的文件创建文件字节输入流

- **重要方法：**

  - int **read**()

    fis.read()，读取下一个字节，返回该字节的int表示。

    读完后指针会移动到已读字节后方，读完了还read()则会返回-1。

  - int **read**(byte[] bys)

    fis.read(byte[])，一次读取一个字节数组，返回实际读取到的字节个数。

### 9.5 缓冲流BufferedInputStream/BufferedOutputStream

- **概述：**

  测试发现一次读取一个数组的方式比一次读取一个字节的方式要快上很多，所以Java提供了带缓冲区的字节类（为了高效）。

  构造方法需要传一个基本的输入/输出流对象。

- **构造方法：**

  - BufferedInputStream(**InputStream in**)

    创建具有默认缓冲区大小的缓冲输入流

  - BufferedOutputStream(**OutputStream out**)

    创建具有默认缓冲区大小的缓冲输出流

  - 可加第二参数int size，来指定缓冲区大小。

    一般不用指定缓冲区大小，默认就够用了。

### 9.6 字符流OutputStreamWriter/InputStreamReader

- **重要构造方法：**

  - OutputStreamWriter(**OutputStream out**)

    根据默认编码把字节流的数据转换为字符流

  - OutputStreamWriter(**OutputStream out, String charsetName**)

    根据给定编码把字节流的数据转换为字符流

  - InputStreamReader(**InputStream in**)

    根据默认编码读取数据

  - InputStreamReader(**InputStream in, String charsetName**)

    根据指定编码读取数据

- **OutputStreamWriter的重要方法：**

  - public void write(**int c**)

    osw.write(int)，写一个**字符**

  	 public void write(**char[] cbuf**)	

    osw.write(char[])，写一个**字符数组**

  - public void write(**char[] cbuf, int off, int len**)

    osw.write(char[], 开始索引, 长度)，写一个**字符数组的一部分**

  - public void write(**String str**)

    osw.write(String)，写一个**字符串**

  - public void write(**String str, int off, int len**)

    osw.write(String, 开始索引, 长度)，写一个**字符串的一部分**

- **InputStreamReader的重要方法：**

  - int **read**()

    isr.read()，一次读取一个**字符**

  - int **read**(**char[] chs**)

    isr.read(char[])，一次读取一个**字符数组**

### 9.7 字符缓冲流BufferedWriter/BufferedReader

- **概述：**

  字符流为了高效读写，也提供了对应的字符缓冲流。

  - **BufferedWriter：字符缓冲输出流**

  		将文本写入字符输出流，缓冲各个字符，从而提供单个字符，数组和字符串的高效写入。
  	可以指定缓冲区的大小，然而在大多数情况下，默认值就足够大了。

  - **BufferedReader：字符缓冲输入流**

  		从字符输入流中读取文本，缓冲各个字符，从而实现字符、数组和行的高效读取。

  		可以指定缓冲区大小，然而在大多数情况下，默认值就足够大了。

- **重要构造方法：**

  - BufferedWriter(**Writer out**)

    创建一个具有默认大小缓冲区的缓冲字符输出流

  - BufferedReader(**Reader in**)

    创建一个具有默认大小缓冲区的缓冲字符输出流

- **重要特殊方法：**

  - BufferedWriter：

    public void **newLine**()

    bw.newLine()，根据系统来写入换行符

  - BufferedReader：

    public String **readLine**()

    br.readLine()，一次读取一行数据。结束依据为任何换行符，返回的字符串不包含任何换行符。如果已达流末尾，返回null。

### 9.8 数据输入/输出流DataInputStream/DataOutputStream

- **概述：**

  操作基本数据类型的流。

- **重要构造方法：**

  - DataInputStream(**InputStream in**)

    传一个InputStream构造DataInputStream对象

  - DataOutputStream(**OutputStream out**)

    传一个OutputStream构造DataOutputStream对象

- **重要方法：**

  - DataOutputStream：

    public final void write基本数据类型(基本数据类型对象)

    dos.writeInt(0)

  - DataInputStream：

    public final 基本数据类型 read基本数据类型()

    dis.readInt()

### 9.9 内存操作流

- **概述：**

  用于临时存储信息，程序结束，临时存储的信息就从内存中消失。

  此种流的close()方法没有意义，因为其源码中没有任何内容，是空实现。

  此处将概述：
  	字节数组内存操作流ByteArrayInputStream和ByteArrayOutputStream；
  	字符数组内存操作流CharArrayReader和CharArrayWriter；
  	字符串内存操作流StringReader和StringWriter。

- **构造方法：**

  输入流一般传入一个对应数据类型对象构造输入流对象。

  输出流一般使用空参构造，缓冲区会随着写入数据的增加自动扩充。

  也可以传一个size指定缓冲区大小，不过一般没有必要。

- **使用举例：**

  - 写数据：

    ```java
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    baos.write("abc".getBytes());
    byte[] bys = baos.toByteArray();
    ```

  - 读数据：

    ```java
    ByteArrayInputStream bais = new ByteArrayInputStream(bys);
    int len = 0;
    byte[] arr = new byte[1024];
    while((len = bais.read(arr)) != -1) {
        System.out.println(new String(arr, 0, len));
    }
    ```

### 9.10 打印流

- **概述：**

  打印流包括字节打印流PrintStream和字符打印流PrintWriter。

- **打印流的特点：**

  1. 只有写数据的，没有读取数据的。只能操作目的地，不能操作数据源；
  2. 可以操作任意类型的数据；
  3. 如果启用了自动刷新，可以自动进行刷新无需手动flush()；
  4. 该流可以直接操作文本文件（不像缓冲流需要传一个操作文件的基本流）。

  		所以，常用的可以直接操作文本文件的流有：
  	FileInputStream，FileOutputStream，FileReader，FileWriter，PrintStream，PrintWriter

- **PrintWriter概述：**

  - **重要构造方法：**

    可以传一个File或文件名字符串构造打印输出流，不过这样将不能自动刷新。

    也可以传一个字节输出流OutputStream或字符输出流Writer，这种方式若想要自动刷新，需第二参数boolean autoFlush。

    public PrintWriter(OutputStream out, boolean autoFlush)		//需注意print()方法不能自动刷新，println()才可以
    public PrintWriter(Writer out, boolean autoFlush)

  - **使用举例：**

    ```java
    PrintWriter pw = new PrintWriter(new FileWriter("demo.txt"), true);
    pw.println("hello");
    pw.println("100");
    //此处println()一个方法相当于BufferedWriter的bw.write()，bw.newLine()，bw.flush()三者的组合。
    ```

### 9.11 标准输入/输出流

- **概述：**

  System类中的字段in和out。

  分别代表了系统标准的输入和输出设备，默认输入设备是键盘，默认输出设备是显示器。

  System.in的类型为**InputStream**，System.out的类型为**PrintStream**（字节打印流，OutputStream的子类FilterOutputStream的子类）。

- **键盘录入的三种方式：**

  1. main方法的String[] args接收参数。举例：

     ```
     java HelloWorld argStr1 argStr2 ...
     ```

  2. 使用Scanner（JDK5以后），举例：

     ```java
     Scanner sc = new Scanner(System.in);
     sc.nextInt();
     ```

  3. 通过字符缓冲输入流BufferedReader包装标准输入流实现：

     ```java
     BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
     String s = br.readLine();
     ```

### 9.12 随机访问流RandomAccessFile

- **概述：**

  - **可以在文件指定位置进行读写操作**
  - 支持对文件的随机访问和写入。
  - RandomAccessFile包含了一个记录指针以标识当前独写的位置。当新创建对象时，指针位于文件开头；读/写了n个字节后，指针也会向后移动n个字节。
  - RandomAccessFile在io包下，不过不属于任何一个io流根类，其继承自Object。
  - 融合了InputStream和OutputStream的功能。
  - RandomAccessFile不能向指针位置（除末尾）插入内容（将会覆盖原本存在的内容）。若想插入内容，可以先将指针后的内容存储到临时文件，追加内容，最后追加临时文件中的内容。

- **重要构造方法：**

  public RandomAccessFile(String name, String mode)

  // 第一个参数是文件名，第二个参数为操作文件的模式

  // 模式有"r"，"rw"，"rws"，"rwd"可选。常用"rw"进行读写。

- **重要方法：**

  - public long **getFilePointer**()			//raf.getFilePointer()，返回文件记录指针的当前位置
  	 public long **seek**(long pos)				//raf.seek(偏移量)，将文件记录指针设置到pos位置
  	 InputStream具有的三个read()方法		//单个字节，字节数组，字节数组的一部分
  	 OutputStream具有的三个write()方法	//单个字节，字节数组，字节数组的一部分

### 9.13 合并流

- **概述：**

  - **将多个字节输入流合并为一个**
  - 表示其他输入流的逻辑串联。将会从第一个（输入流的有序集合第一个）开始，读完了读第二个，直到最后一个读完为止。
  - 继承自InputStream。

- **重要构造方法：**

  - public SequenceInputStream(**InputStream s1, InputStream s2**)

    // 创建包含两个InputStream的合并流，先读s1后读s2

  - public SequenceInputStream(**Enumeration<? extends InputStream> e**)

    // 可以进行多个流的合并，具体说明如下：

    **Enumeration构造方法的具体说明：**

    ​	Enumeration是有序集合Vector中public Enumeration<E> elements()方法的返回值，有序包含Vector中所有元素。

    ​	使用该种构造方法，需要先创建Vector集合对象，然后将多个InputStream添加到集合，最后调用集合的elements方法得到Enumeration对象，以构造合并流对象。

### 9.14 序列化流ObjectInputStream/ObjectOutputStream

- **概述：**

  - **把对象存入文件/从文件读出对象**
  - 把对象按照流一样的方式存入文本文件或者在网络中传输/把文本文件或网络中的流对象数据还原成对象。
  - 注意，写入和读取的对象需要实现Serializable接口，否则在操作的时候会抛出NotSerializableException。
  - Serializable为标记接口，内部没有任何方法。

- **重要构造方法：**

  - public ObjectInputStream(**InputStream in**)

    //创建从指定InputStream读取的ObjectInputStream

  - public ObjectOutputStream(**OutputStream out**)

    //创建用指定OutputStream输出的ObjectOutputStream

- **重要方法：**

  - public final Object **readObject**()	

    //ois.readObject()，从ObjectInputStream中读取Object对象

  - public final void **writeObject**(Object o)

    //oos.writeObject(Object)，将指定对象写入ObjectOutputStream

- **注意事项：**

  1. 如果写入一个已序列化类的对象，之后对该类的Java文件进行了改动，再执行读取的时候会报错。

     要解决该问题，可以点击IDE中黄色叹号，自动生成成员变量序列化ID值。

     该方式同时也可以去除黄色警告线。

  2. 若不想实例化某成员变量，可以用关键字transient修饰。

### 9.15 属性集合类Properties

- **概述：**

  - **可以和IO流相结合使用的Map集合类**
  - 该类的对象可以保存在流中或从流中加载，属性列表中每一个键及其对应值都是一个字符串。
  - 是Hashtable的子类，是一个map集合。

- **重要构造方法：**

  public Properties()		//创建一个无默认值的空属性列表

- **重要特殊方法：**

  - public Object **setProperty**(String key, String value)

    //p.setProperty(str1, str2)，添加元素（原理为调用父类Hashtable的put()）

  - public String **getProperty**(String key)

    //p.getProperty(key)，根据键找值

  - public Set<String> **stringPropertyNames**()

    //p.stringPropertyNames()，获取所有键的集合

  - public void **load**(Reader reader)

    //p.load(Reader)，把文件中的数据读取到集合中

  	 public void **store**(Writer writer, String comments)	

    //p.store(Writer, 注释)，把集合中的数据和注释存储到文件，注释可以为null

- **存储到文件中的格式：**

  ```
  #comments
  #时间
  键1=值1
  键2=值2
  ...
  ```

### 9.16 NIO（新IO）

- **概述：**

  - 于JDK4出现nio，其和传统io有着相同的目的，都是用于输入和输出。不过新io采用了不同的处理方式，其原理是将文件或文件的一段区域映射到内存，像访问内存一样访问文件，效率更高。
  - 目前旧io使用较多，此处内容仅需了解。

- **了解内容：**

  - Path（1.7）：

    为java.nio.file包下一个接口，表示路径。

  - Paths（1.7）：

    一个最终类，其中有一个静态方法static Path get(URI uri)，根据给定URI确定文件路径。

  - Files（1.7）：

    操作文件的工具类，方法全部为静态。

    需要了解的方法：

    - public static long **copy**(Path source, OutputStream out)

      //Files.copy(Path, OutputStream)，将一个文件中的全部字节复制到一个OutputStream

    - public static Path **write**(Path path, Iterable<? extends CharSequence> lines, Charset cs, OpenOption... options)

    		//把集合的元素写到文件

- **使用举例：**

  ```java
  Files.copy(Paths.get("Demo.java"), new FileOutputStream("Copy.java"));		//复制文件
  File.write(Paths.get("List.txt"), new ArrayList<String>(...), Charset.forName("GBK"));		//将集合的元素写到文件
  ```



## 10. 多线程

### 10.1 多线程的概述

由于线程是依赖于进程而存在，所以先了解进程。

- **进程：**

  就是正在运行的程序。

  进程是系统进行资源分配和调用的独立单位，没一个进程都有它自己的内存空间和系统资源。

- **多进程的意义：**

  1. 可以在一个时间段内执行多个任务；
  2. 可以提高CPU的使用率。

- **线程：**

  同一个进程内又可能包含多个任务，这每一个任务就可以看成是一个线程。

  线程是程序的执行单元/执行路径，是程序使用CPU的基本单位。

  如果程序只有一条执行路径即为单线程，如果程序有多条执行路径即为多线程。

- **多线程的意义：**

  1. 提高应用程序的使用率。
  2. 如果一个进程有多个线程，将会有更高的几率获得更多系统资源。

- **并行和并发：**

  - 并行：是指逻辑上同时发生，在某一个时间段内同时运行多个程序。
  - 并发：是指物理上同时发生，在某一个时间点上同时运行多个程序。

- **相关问题：**

  - Java程序运行时的线程调度：

    java命令会先启动Java虚拟机（JVM），也就是启动了一个程序（启动了一个进程）。该程序会启动一个主线程，然后该线程去调用某个类的main方法。

  - 多个进程在单CPU上是同时进行的吗？

    不是，因为单CPU在某一个时间点上只能做一件事情。

    我们感觉到的同时进行，是因为CPU在做着进程间的高效切换。

  - Java虚拟机的启动是单线程的还是多线程的？

    是多线程的。

    因为至少启动了主线程和垃圾回收器线程。

### 10.2 多线程的实现

由于线程是依赖进程存在的，所以首先应创建一个进程。

进程是由系统创建的，所以我们需要调用系统功能去创建一个进程。

Java不能直接调用系统功能，没有办法直接实现多线程程序，不过可以通过调用C/C++写好的程序来实现。

将C/C++写好的程序功能封装成Java类，就可以实现Java多线程程序了。

Java提供的线程类是：Thread。

- **方式一（继承Thread类）：**

  1. 自定义类MyThread继承自Thread类；
  2. MyThread类中重写run()方法；
  3. 创建对象；
  4. 启动线程。

  实例：

  ```java
  public class MyThread extend Thread{
      public void run(){
          ...				//线程中要执行的内容
      }
  }
  public class Demo{
      public static void main(String[] args){
          MyThread tr1 = new MyThread();
          MyThread tr2 = new MyThread();
          
          tr1.start();	//启动线程
          tr2.start();
      }
  }
  ```

- **方式二（实现Runnable）：**

  1. 自定义类MyRunnable实现接口Runnable；
  2. 重写run()方法；
  3. 创建MyRunnable类的对象；
  4. 创建Thread类的对象，把创建的MyRunnable对象当成参数传递；
  5. 启动线程。

  实例：

  ```java
  public class MyRunnable implments Runnable{
      public void run(){
          ...		//线程中要执行的内容
      }
  }
  public class Demo{
      public static void main(String[] args){
          MyRunnable mr = new MyRunnable();
          
          Thread th1 = new Thread(mr, "线程1");
          Thread th2 = new Thread(mr);
          
          th1.start();
          th2.start();
      }
  }
  ```

- **方式三（线程池）：**

  **（仅了解）**

  使用ExecutorService（线程池）、Callable和Future实现有返回值的多线程：

  1. 自定义类实现Callable<V>；
  2. 重写<V> call()方法，返回值为V；
  3. 创建线程池；
  4. 创建Callable<V>实现类对象；
  5. 将对象submit到常量池，用Future<V>对象接收；
  6. Future<V>对象调用get()方法得到返回值；
  7. 关闭线程池。

  实例：

  ```java
  class MyCallable implements Callable<Integer>{
      private int number;
      public MyCallable(int number){
          this.number = number;
      }
      public <Integer> call() throws Exception{
          int sum = 0;
          for(int x = 1;x <= number;x ++){
              sum += x;
          }
          return sum;
      }
  }
  class Demo{
      public static void main(String[] args) throws Exception{
          ExecutorService pool = Executors.newFixedThreadPool(2);
          
          Future<Integer> f1 = pool.submit(new MyCallable(100));
          Future<Integer> f2 = pool.submit(new MyCallable(200));
          
          Integer i1 = f1.get();
          Integer i2 = f2.get();
          
          System.out.println(i1);
          System.out.println(i2);
          
          pool.shutdown();
      }
  }
  ```

- **相关重要问题：**

  1. **为什么要重写run()方法？**

     不是类中所有代码都要被线程执行的。

     为了区分哪些代码需要被线程执行，Java提供了Thread中的run()方法来包含被线程执行的代码。

  2. **调用run()方法为什么是单线程的呢？**

     因为直接调用run()方法就是相当于调用了普通的方法，看到的自然是单线程的效果。

  3. **run()和start()的区别：**

     - run()：仅仅是封装需要被线程执行的代码，直接调用仅相当于普通方法调用。
     - start()：启动线程，然后Java虚拟机调用该线程的run()方法。

  4. **线程能不能多次启动？**

     不能。

     不过可以创建不同的线程对象分别启动。

  5. **多线程实现方式二的好处？**

     1. 若一个类已经继承了其他类就无法使用方式一了，使用接口可以避免Java单继承带来的局限性；
     2. 方式二可以实现多个相同的线程处理同一份资源。
     3. 把线程同程序的代码、数据有效分离，较好地体现了面向对象的设计思想。

### 10.3 Thread类

- **概述：**

  Thread是程序中的执行线程。

- **重要构造方法：**

  - public Thread()

    //分配新的Thread对象，自动生成的名称的形式为"Thread-" + n

  - public Thread(**String name**)

    //使用给定的名称构造新的Thread对象

  - public Thread(**Runable target**)

    //传一个Runnable实现类，构造Thread对象

  - public Thread(**Runable target, String name)**

    //传一个Runnable实现类和线程名称，构造Thread对象

  - public Thread(**ThreadGroup tg, Runnable target, String name**)

    //根据线程组，Runnable和名称构建Thread对象

- **重要方法：**

  - public final void **setName**(String name)

    //th.setName("线程名")，设置线程名称

  - public final String **getName**()

    //th.getName()，返回线程名称

  - public void **run**()

    //th.run()，Thread的子类应该重写该方法

  - public void **start**()

    //th.start()，使该线程开始执行，Java虚拟机调用该线程的run()方法

  - public static Thread **currentThread**()

    //Thread.currentThread()，返回当前正在执行的Thread对象

  - public final int **getPriority**()

    //th.getPriority()，返回线程对象的优先级

  - public final void **setPriority**(int newPriority)

    //th.setPriority()，更改线程的优先级

  - public static void **sleep**(long millis)

    //Thread.sleep(毫秒数)，在指定的毫秒数内让正在执行的线程休眠（暂停执行），此操作受系统计时器、调度精度等因素影响

  - public final void **join**()

    //th.join()，等待该线程终止

  - public static void **yield**()

    //Thread.yield()，暂停当前正在执行的线程对象，并执行其他线程

  - public final void **setDaemon**(boolean on)

    //th.setDaemon()，将该线程标记为守护线程/用户线程/后台线程。当正在运行的线程都是守护线程时，Java虚拟机退出。必须在启动线程前调用

  - public void **interrupt**()

    //th.interrupt()，中断线程。把线程的状态终止，并抛出一个InterruptedException

  - public final void **stop**()

    //th.stop()，已过时，强迫线程终止执行。

### 10.4 线程调度/优先级

- **概述：**

  线程只有得到CPU的使用权，才可以执行指令。

  线程有两种调度模型：

  1. 分时调度模型

  		所有线程轮流获得CPU的使用权，平均分配每个线程占用CPU的时间片。

  2. 抢占式调度模型：

  		优先让优先级高的线程使用CPU。

  		如果线程的优先级相同，会随机选择一个。
			
  		优先级高的线程获得的CPU时间片会多一些。

  **Java使用的是抢占式调度模型。**

- **Java线程优先级：**

  线程默认优先级是：5。

  线程优先级的范围是：1~10。

  线程优先级高表示线程获取CPU时间片的几率高，调用次数越多将越能体现这一点，次数少会更多的表现出随机性。

### 10.5 线程控制

- **线程休眠：**

  在线程的run()方法内静态调用sleep(long millis)方法，可将当前实例休眠指定的毫秒数。

- **线程加入：**

  th.join()，将等待该线程终止才会去执行其他线程/执行后面的代码。

- **线程礼让：**
  在线程的run()方法内静态调用yield()方法，暂停当前正在执行的线程对象，并执行其他线程。

- **后台线程/守护线程：**

  在启动线程前调用th.setDaemon(boolean on)方法，可以将该线程标记为守护线程/后台线程。

  当所有正在运行的线程都是守护线程时，Java虚拟机退出。

- **线程终止：**

  th.interrupt()，把线程的状态终止，并抛出一个InterruptedException。

### 10.6 线程生命周期

1. **新建：**

   创建线程对象。

   调用start()方法可进入就绪状态。

2. **就绪：**

   有执行资格，没有执行权。

   若得到了CPU的执行权即进入运行状态。

3. **运行：**

   有执行资格，有执行权。

   若被别的线程得到了CPU执行权，则回到就绪状态。

   若运行了sleep()或wait()等方法，则进入阻塞状态。

   若run()方法执行结束或中断了线程，进入死亡状态。

4. **阻塞：**

   没有执行资格，没有执行权。

   若sleep()时间到或运行了notify()等方法，可恢复到就绪状态。

5. **死亡：**

   线程对象变成垃圾，等待被回收。

### 10.7 线程安全问题/线程同步

- **判断是否会出现线程安全问题：**

  1. 是否多线程环境；
  2. 是否有共享数据；
  3. 是否有多条语句操作共享数据。

- **解决方案（同步代码块）：**

  格式：

  ```java
  synchronized(对象){
      ...			//需要同步的代码
  }
  ```

  小括号内所传的对象是重点，该对象相当于“锁”的功能，多个线程对象需要同一把锁（相同的对象）。

- **同步的前提：**

  1. 要有多个线程；
  2. 多个线程使用的是同一个锁对象。

- **同步的好处：**

  同步的出现解决了多线程的安全问题。

- **同步的弊端：**

  1. 当线程很多时，每个线程都会去判断同步上的锁。

     这将会耗费更多资源，降低程序运行效率。

  2. 容易产生死锁。

- **相关问题：**

  1. **同步代码块的锁对象是什么？**

     是任意对象。

  2. **同步方法的格式及锁对象问题：**

     同步方法的格式：

     ```java
     public synchronized method(){
         ...
     }
     ```

     同步方法的锁对象是this。

  3. **静态方法的锁对象是什么？**

     是类的字节码文件对象。

### 10.8 Lock锁（JDK5）

- **概述：**
  - 为了更清晰的表达如何加锁和释放锁
  - java.util.concurrent.locks下的接口Lock，提供了比使用synchronized方法和语句可获得的更广泛的锁定操作。
  - 允许更灵活的结构。

- **重要方法：**
  - void **lock**()			//lock.lock()，获取锁
  	 void **unlock**()			//lock.unlock()，释放锁

- **常用实现类：**

  ReentrantLock

### 10.9 线程死锁问题

- **概述：**

   两个或两个以上的线程在争夺资源的过程中，发生的一种互相等待的现象。

- **举例：**

  如下的程序在创建两个对象并分别传入0和1时，极有可能出现线程死锁现象。

  ```java
  public class DeadlockThread implements Runnable{
  
      private Object lockA = new Object();
      private Object lockB = new Object();
      private int x;
  
      public DeadlockThread (int x) {
          this.x = x;
      }
  
      public void run () {
          while (true) {
              if (x%2 == 0) {
                  synchronized (lockA) {
                      System.out.println("已经进入lockA，正在等待lockB");
                      synchronized (lockB) {
                          System.out.println("成功进入lockB")
                      }
                  }
              } else {
                  synchronized (lockB) {
                      System.out.println("已经进入lockB，正在等待lockA");
                      synchronized (lockA) {
                          System.out.println("成功进入lockA")
                      }
                  }
              }
              x ++;
          }
      }
  }
  ```


### 10.10 等待唤醒机制

- **概述：**

  Object类中提供了多个等待和唤醒方法。

  这些方法定义在Object中的原因是，锁的对象可以是任意对象。

- **方法（位于Object）：**

  - public final void **wait**()			//lock.wait()，让当前线程等待
  	 public final void **notify**()		        //lock.notify()，唤醒在此对象监视器上等待的单个线程
  	 public final void **notifyAll**()		//lock.notifyAll()，唤醒在此对象监视器上等待的所有线程

### 10.11 线程组

- **概述：**

  - 把多个线程合并到一起
  - 它可以对一批线程进行分类管理，Java允许程序直接对线程组进行控制。
  - 默认情况下，所有线程都属于主线程组（main）。
  - 通过Thread类的构造方法public Thread(ThreadGroup tg, Runnable target, String name)，可将Thread在创建时就加入某一线程组。

- **重要构造方法：**

  public ThreadGroup(String name)

  //构建一个新线程组，名字为name

### 10.12 线程池

- **概述：**

  程序启动一个新线程的成本是比较高的，因为要涉及到与操作系统的交互。

  使用线程池可以很好地提高性能，尤其是需要创建大量生存期很短地线程时。

  线程池中每一个线程代码结束后，不会死亡，而是回到线程池中成为空闲状态，等待下一个对象来使用。

  此处将使用Executors类。

- **固定大小的线程池newFixedThreadPool：**

  public static ExecutorService newFixedThreadPool(**int nThreads**)

  //Executors.newFixedThreadPool(线程数)，创建一个固定大小的线程池

  **使用举例：**

  ```java
  ExecutorService pool = Executors.newFixedThreadPool(2);
  pool.submit(new MyRunnable());
  pool.submit(new MyRunnable());
  pool.shutdown();
  ```

- **其他线程池：**

  - 单任务线程池：

    public static ExecutorService **newSingleThreadExecutor**()

  - 可变尺寸线程池：

    public static ExecutorService **newCachedThreadPool**()

### 10.13 定时器

- **概述：**

  可以让我们在指定时间做某件事情，还可以重复做某件事情。

  主要依赖Timer和TimerTask这两个类。

- **Timer定时器类：**

  **重要构造方法：**

  ​	public Timer()

  **重要方法：**

  ​	public void **schedule**(TimerTask, Date time)			

  ​	//t.schedule(TimerTask, Date)，安排在指定时间执行指定任务

  ​	public void **schedule**(TimerTask, Date firstTime, long period)

  ​	//t.schedule(TimerTask, Date, period)，安排任务在指定时间开始固定延迟地执行

  ​	public void **schedule**(TimerTask task, long delay)

  ​	//t.schedule(TimerTask, delay)，安排在指定延迟后执行指定任务

  ​	public void **schedule**(TimerTask task, long delay, long period)

  ​	//t.schedule(TimerTask, delay, period)，安排任务在指定延迟后开始固定延迟地执行
  	public void **cancel**()

  ​	//t.cancel()，终止此计时器，并丢弃所有已安排的任务

- **TimerTask任务类：**

  抽象类，实现了Runnable。

  需要创建子类，并重写run()方法，run()方法中的内容就是要执行的任务。



## 11. 设计模式

### 11.1 概述

设计模式是经验的总结。

设计模式是一种思想，和具体的语言无关。

学习设计模式，可以和面向对象思想的理解相辅相成。

**学习设计模式的目的**在于建立面向对象的思想，尽可能地面向接口编程，高内聚，低耦合，使设计的程序可复用。

**设计模式总体分为三种类型：**

1. 创建型		//创建对象
	. 结构型		//对象的组成
	. 行为型		//对象的功能

### 11.2 简单工厂模式

- **概述：**

  又叫静态工厂模式，特点是定义一些具体的工厂类负责创建一些类的实例（例如提供工厂类，专门造猫和狗，这样就不用手动new了）。

- **优点：**

  明确了各个类的职责，客户端不再需要负责对象的创建。

- **缺点：**

  若有新的对象增加，或某些对象创建方法不同，需要后期不断修改工厂类，不利于后期维护。

### 11.3 工厂方法模式

- **概述：**

  工厂方法模式会有抽象工厂类，负责定义创建对象的接口。

  当增加一个需要创建的类的时候，就需要造一个小工厂继承抽象工厂，专门负责造这种类的对象。

- **优点：**

  - 解决了简单工厂模式的一些缺陷，后期维护不再需要修改工厂类。
  - 当增加了需要创建的对象的时候，只需要增加一个具体的工厂即可。
  - 增加了可维护性和系统的扩展性。

- **缺点：**

  - 需要编写额外的代码，增加了工作量。

### 11.4 单例模式

- **概述：**

  单例模式就是确保类在内存中只有一个对象，该实例必须自动创建，并对外提供。

  单例模式分为**饿汉式**和**懒汉式**。

- **实现过程：**

  1. 私有类的构造方法；

  2. 设置一个类型为本类的成员变量，使用以下修饰：

     private：防止外界访问和修改

     static：随着类的加载而加载，同时也是由于 提供对象的方法也是静态修饰

     synchronized：用于懒汉式，因为懒汉式存在线程安全问题（多条语句操作同一变量）

  3. 提供公共访问的方式（静态方法）。

- **饿汉式与懒汉式的区别：**

  - **饿汉式：**

    开始时就造好对象，举例：

    ```java
    private static Student s = new Sdudent();
    ```

    这种方式一般不存在线程安全问题。

  - **懒汉式：**

    在需要时再造对象，举例：

    ```java
    private static Student s = null;
    public synchronized static Student getStudent() {
        if(s == null){
            s = new Student();
        }
        return s;
    }
    ```

- **优点：**

  由于在系统中只存在一个对象，所以可以节约系统资源。

  对于需要频繁创建和销毁对象的操作，单例模式可以提高系统性能。

- **缺点：**

  没有抽象层，扩展很难。

  职责过重，一定程度上违背了单一职责。

- **实例类：RunTime**

  该类属于单例模式饿汉式。

  每个Java程序都有一个Runtime类实例，使应用程序能够与其运行的环境相连接，可以通过getRuntime()获取当前的运行时。

  重要方法：

  ​	public Process **exec**(String command)			//rt.exec(DOS命令)

### 11.5 适配器设计模式

- **概述：**

  适配器(Adapter)模式，可以在需要实现一个有众多方法的接口（或难以实现的接口）时，做到只实现需要的方法，不用实现全部。

- **使用方法：**

  写一个抽象的适配器类实现需要实现的接口，将所有方法空实现。

  然后提供具体类重写需要实现的方法即可。

### 11.6 模板设计模式

- **概述：**

  定义一个算法的框架，而将具体算法推迟到具体子类中实现。

  例如计算一段代码的执行时间，将两个System.currentTimeMillis()之间的代码定义为一个抽象方法，方法中的内容在具体子类中实现。

- **优点：**

  在定义骨架算法的同时，可以灵活地实现具体算法，满足不同的需求。

- **缺点：**

  如果算法骨架有修改的话，则需要修改抽象类。

### 11.7 装饰设计模式

- **概述：**

  使用被装饰类的子类的一个实例，在客户端将这个实例交给装饰类，以扩展类的功能。

  是继承的一个替代方案。

- **举例：**

  ```java
  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  ```

- **优点：**

  相比于继承，装饰模式可以更为灵活地扩展对象的功能。

  装饰模式甚至可以动态添加、随意组合添加的功能。

- **缺点：**

  随意组合中可能会出现一些不合理的逻辑。



## 12. 网络编程

### 12.1 基础知识

- **概述：**

  网络编程就是用来实现网络互联的不同计算机上运行的程序之间可以进行数据交换。

  计算机网络之间以何种规则进行通信，就是网络模型研究的问题。

- **网络模型：**

  - OSI(Open System Interconnection 开放系统互连)参考模型
  - TCP/IP参考模型

- **网络编程三要素：**

  1. **IP地址 ：**

     网络中计算机的唯一标识。

     IP地址的组成为网络号段+主机号段

     - A类IP地址：第一号段为网络号段 + 后三号段为主机号段

     		1.0.0.1 - 127.255.255.254 (10.x.x.x是私有地址)

     - B类IP地址：前二号段为网络号段 + 后二号段为主机号段

     		128.0.0.1 - 191.255.255.254(172.16.0.0 - 172.31.255.255是私

     有地址，169.254.x.x是保留地址)

     - C类IP地址：前三号段为网络号段 + 后一号段为主机号段

     		192.0.0.1 - 223.255.255.254(192.168.x.x是私有地址)

     - D类...
     - E类...
     - 特殊IP地址：

     		127.0.0.1 (回环地址，表示本机)

     		x.x.x.255 (广播地址)
		
     		x.x.x.0 (网络地址)

  2. **端口：**

     计算机中程序的标识。

     端口分为物理端口、网卡口和逻辑端口，这里指的是逻辑端口。

     说明：

     1. 每个程序都至少会有一个逻辑端口；
     2. 有效端口：0 - 65535，其中0 - 1024为系统使用或保留端口。

  3. **协议：**

     通信的规则。

     分为UDP协议和TCP协议：

     1. **UDP特点：**

     		把数据打包

     		数据有限制(64K)
		
     		不建立连接
		
     		速度快
		
     		不可靠

     2. **TCP特点：**

     		建立连接通道

     		数据无限制
		
     		速度慢
		
     		可靠

### 12.2 InetAddress类

- **概述：**

  为了方便我们对IP地址的获取和操作，Java提供了InetAddress类供使用。

- **获取对象：**

  public static InetAddress **getByName**(String host)	

  //根据主机名或ip地址的字符串表示得到InetAddress对象

- **重要方法：**

  public String **getHostName**()

  //address.getHostName()，获取主机名

  public String **getHostAddress**()

  //address.getHostAddress()，获取主机IP地址

### 12.3 Socket（UDP）

- **概述：**

  网络套接字。

  Socket编程，就是套接字编程/网络编程。

  Socket包含了IP地址+端口。

  这里使用类DatagramSocket。

- **Socket原理：**

  通信的两端都有Socket，网络通信其实就是Socket间的通信，数据在两个Socket间通过IO传输。

- **使用摘要：**

  - **Socket发送数据：**

    ```java
    // 1.创建发送端Socket对象：
    DatagramSocket ds = new DatagramSocket();
    // 2.创建数据，并把数据打包
    byte[] bys = "Hello, Socket, UDP".getBytes();
    int length = bys.length();
    InetAddress address = InetAddress.getByName("192.168.1.102");
    int port = 10086;
    DatagramPacket dp = new DatagramPacket(bys, length, address, port);		//使用数据（字节数组）、长度、目标IP地址和端口构建DatagramPacket数据包
    // 3.发送数据
    ds.send(dp);
    // 4.释放资源
    ds.close();
    ```

  - **Socket接收数据：**

    ```java
    // 1.创建接收端Socket对象：
    DatagramSocket ds = new DatagramSocket(10086);
    	//因为不用发送数据包，所以在构造里指定接收端口
    // 2.创建一个数据包（接收容器）
    byte[] bys = new byte[1024];
    int length = bys.length;
    DatagramPacket dp = new DatagramPacket(bys, length);
    // 3.调用Socket的接收方法接收数据包
    ds.receive(dp);						//该方法在接收到数据包之前一直阻塞
    // 4.解析数据包，并显示数据在控制台
    InetAddress address = dp.getAddress();
    String ip = address.getHostAddress();	//获取发送方的ip地址
    byte[] bys2 = dp.getData();
    int len = dp.getLength();
    String s = new String(bys2, 0, len);	//将获取到的数据转成字符串
    System.out.println(s);
    // 5.释放资源
    ds.close();
    ```


### 12.4 Socket(TCP)

- **概述：**

  这里使用类Socket和类ServerSocket。

- **Socket类的特殊功能：**

  - public void shutdownInput()

    //s.shutdownInput()，将此套接字的流置于"流的末尾"。调用此方法后若继续从输入流读取数据，则将返回EOF（文件结束符）

  - public void shutdownOutput()

    //s.shutdownOutput()，禁用此套接字的输出流，任何之前写入的数据都将被发送，并后跟TCP正常连接终止序列。

- **使用概要：**

  1. Socket发动数据：

     ```java
     // 1.Socket发送数据：
     Socket s = new Socket("192.168.1.114", 12306);
     // 2.获取输出流，写数据
     OutputStream os = s.getOutputStream();
     os.write("Hello,TCP".getBytes());
     // 3.释放资源
     s.close();
     ```

  2. Socket接收数据：

     ```java
     // 1.创建接收端Socket对象
     ServerSocket ss = new ServerSocket(12306);
     // 2.监听客户端连接，返回一个对应的Socket对象
     Socket s = ss.accept();
     	//监听并接收此套接字的连接，此方法在连接传入之前一直阻塞
     // 3.获取输入流，读取数据显示在控制台
     InputStream is = s.getInputStream();	//得到套接字连接的InputStream
     byte[] bys = new byte[1024];
     int len = is.read(bys);						//收到数据之前一直阻塞
     String str = new String(bys, 0, len);		//得到数据
     String ip = s.getInetAddress().getHostAddress();//获取发送端的ip地址
     System.out.println(ip + "：" + str);	//输出发送端的ip地址和发送的数据
     // 4.释放资源
     s.close();
     	//注意ServerSocket（连接监听器）不应关闭，只需关闭Socket（本次连接）
     ```



## 13. 反射

### 13.1 概述

反射就是通过class文件对象，去使用该文件中的成员变量、构造方法和成员方法。

### 13.2 Class文件对象

- **获取Class文件对象的方式：**

  1. Object类的getClass()方法；

  2. 数据类型的静态属性class；

  3. Class类中的静态方法

     public static Class **forName**(String className)

- **重要方法：**

  1. **获取构造方法：**

     - **获取单个**

       - public Constructor<T> **getDeclaredConstructor**(Class<?>... parameterTypes)			

         //c.getDeclaredConstructor(要获取的构造方法的参数个数及数据类型的class字节码文件对象)

       - public Constructor<T> **getConstructor**(Class<?>... parameterTypes)

         //c.getConstructor(要获取的构造方法的参数个数及数据类型的class字节码文件对象)，根据参数获取指定构造方法

     - **获取所有**

       - public Constructor[] **getConstructors**()

         //c.getConstructors()，获取该类所有公共（public修饰）构造方法

       - public Constructor[] **getDeclaredConstructors**()

         //c.getDeclaredConstructors()，获取该类所有声明的构造方法

  2. **获取成员变量：**

     - **获取单个**

       - public Field **getField**(String name)

         //c.getField(成员变量名)，获取指定的公共字段

       - public Field **getDeclaredField**(String name)

         //c.getDeclaredField(字段名)，获取指定的声明的字段

     - **获取所有**

       - public Field[] **getFields**()

         //c.getFields()，返回所有公共字段的数组

       - public Field[] **getDeclaredFields**()

         //c.getDeclaredFields()，获取所有声明的字段的数组

  3. **获取成员方法：**

     - **获取单个**

       - pubilc Method **getMethod**(String name, class<?>... parameterTypes)

         //c.getMethod(方法名, 参数.class数组)，获取指定公共成员方法

       - public Method **getDeclaredMethod**(String name, class<?>... parameterTypes)

         //c.getDeclaredMethod(方法名, 参数.class数组)，获取指定本类声明的成员方法

     - **获取所有**

       - public Method[] **getMethods**()

         //c.getMethods()，获取本类包括父亲的公共方法

       - public Method[] **getDeclaredMethods**()

         //c.getDeclaredMethods()，获取本类声明的所有方法

### 13.3 Constructor<T>构造方法对象

- **重要方法：**

  - public T **newInstance**(Object... initargs)

    //cons.newInstance(参数)，使用该构造方法构造一个新对象

  - public void **setAccessible**(boolean flag)

    //cons.setAccessible(true)，设置为true则对象在使用时取消Java的访问检查（无视权限修饰符）

### 13.4 Field成员变量(域)对象

- **重要方法：**

  - public void **set**(Object obj, Object value)

    //nameField.set(s1, "周树人")，将obj的该变量设置值为value

  - public void **setAccessible**(boolean flag)

    //nameField.setAccessible(true)，设置为true则对象在使用时取消Java的访问检查（无视权限修饰符）

### 13.5 Method成员方法对象

- **重要方法：**

  - pubilc Object **invoke**(Object obj, Object... args)

    //method1.invoke(s1, 参数...)，用obj调用方法

  - public void **setAccessible**(boolean flag)

    //method1.setAccessible(true)，设置为true则对象在使用时取消Java的访问检查（无视权限修饰符）

### 13.6 动态代理

- **概述：**

  利用反射机制实现方法的增强，在不修改源码仅仅扩展的情况下，在方法执行前后做你想做的事情（例如权限校验、日志记录等）。

  这里指Java提供的，使用接口辅助的动态代理，使用Proxy类和InvocationHandler接口。

- **使用步骤：**

  1. 创建接口和实现类；

  2. 新建类实现InvocationHandler接口，重写其中的方法：

     Object invoke(Object proxy, Method method, Object[] args)

     //在方法中写Object result = method.invoke(target, args[])，并在前后加上需要做的事情（例如权限校验、日志记录等），返回值为invoke产生的Object

     举例：

     ```java
     public class MyInvocationHandler implments InvocationHandler {
         private Object target;
         public MyInvocationHandler (Object target) {
             this.target = target;
         }
         public Object invoke (Object proxy, Method method, Object[] args) throws Throwable {
             System.out.println("日志记录");
             Object result = method.invoke(target, args);
             System.out.println("权限校验");
             return result;
         }
     }
     ```

  3. 创建上述类的对象；

  4. 通过Proxy类的静态方法newProxyInstance得到代理对象：

     public static Object **newProxyInstance**(ClassLoader loader, Class<?>[] interfaces, InvocationHandler handler)

     //loader：接口实现类.getClass().getClassLoader()即可；

     //interfaces：接口实现类.getClass().getInterfaces()即可；

     //handler：刚才创建的MyInvocationHandler。

     //返回值类型为Object，强转为接口实现类接收

  5. 使用得到的代理对象来调用方法，即可实现在方法前后添加功能。