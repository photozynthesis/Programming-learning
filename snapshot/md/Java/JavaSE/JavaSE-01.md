# JavaSE学习笔记-PART-01

## 目录：

[TOC]

## (有关异常、IO流、多线程、设计模式、网络编程和反射的内容，见PART-02)



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

    - **final：**

      1. final修饰**类**：该类**不能派生子类；**

      2. final修饰**方法**：该方法**不能被重写；**

      3. **final修饰变量：该变量赋初始值后，不能重新赋值。**

         若一个**final变量被赋了初始值**，那么它本质上是一个**“宏变量”**。代码在**编译时**便会将这些变量**直接替换成值**。

         **因此final可用于延长变量的寿命周期**。

    - **finally**：

      位于try {...} catch (...) {...} finally {...}之中。
      被finally控制的语句体一定会执行。
      ​	存在例外：在执行finally之前jvm推出了，将不会执行finally中的语句。比如System.exit(0)。

      用于释放资源，在io流操作和数据库操作中会见到。

      **相关重要问题：**

      1. **final、finally、finalize的区别？**
         final：是用于修饰类名、成员变量、成员方法的修饰符。
         ​	final修饰类，该类不能被继承；
         ​	final修饰成员变量，该变量为常量不能重新赋值；
         ​	final修饰成员方法，该方法不能被重写。
         finally：是异常处理的一部分，用于释放资源。
         ​	一般来说finally中的语句一定会执行，

         	不过如果在finally之前退出了虚拟机，就不会执行。

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

     	操作数<<左移位数

     说明：

     	将操作数的二进制按照位数向左移动，左边越界的部分舍弃，右边补0。
			
     	相当于`操作数x2^左移位数`。

     举例：

     ```java
     3<<2;	//12, 相当于3x2^2, 00000011左移两位后变成00001100
     ```

   - `>>`右移：

     格式：

     	操作数>>右移位数

     说明：

     	将操作数的二进制按照位数向右移动，右边越界的部分舍弃，最左边一位保留原符号位。
			
     	相当于`操作数/2^右移位数`。

     举例：

     ```java
     6>>2;	//1, 相当于6/2^2, 符号位的0保留, 00000110右移两位后变成00000001
     ```

   - `>>>`无符号右移：

     说明：

     	无符号右移与右移的区别在于，无符号右移符号位始终补0。

6. `&`、`|`和`^`等作为按位运算符时：

   将两个数对应bit上的二进制数进行对比运算，返回新的数。

   例如：

   ```java
   6 & 3;	//2, 00000110 & 00000011 得 00000010
   ```

### 2.6 原码/反码/补码

概述：

	原码/反码/补码是数据在计算机中的三种表示形式。
	
	计算机在操作的时候，一般都是采用数据对应的二进制**补码**来进行计算的。

说明：

- 原码：

  **最高位为符号位**，0为正，1为负。

  后面的都是数值位，在原码中，数值位直接就是数据的二进制表示。

  例如：

  	-7的原码：`10000111`。

- 反码：

  正数的反码**与原码相同**。

  负数的反码**符号位不变**，数值位**全部取反**。

  例如：

  	-7的反码：`11111000`。

- 补码：

  正数的补码**与原码相同**。

  负数的补码，在**反码的基础上加1**。

  例如：

  	-7的补码：`11111001`。

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

	方法是完成特定功能的代码块，在很多语言中被称为函数。

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

- **栈（Stack）**

  存放的都是方法中的局部变量。

  方法也是在栈内存中运行的。

- **堆（Heap）**

  凡是new出来的东西都在堆内存。

  堆内存里的东西都有一个地址值。

  对内存里的数据也都有默认值，整形为0，浮点型为0.0，布尔为false，引用为null。

- **方法区（Method Area）**

  存储.class相关信息，包含方法的信息。

- **本地方法栈**

- **寄存器**



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

  **接口可以包含的内容：**

  - **Java7:**
    - 常量
    - 抽象方法
  - **Java8：**
    - 默认方法
    - 静态方法
  - **Java9：**
    - 私有方法

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
  2. **不能继承父类的构造方法**，不过可以使用`super()`来初始化。
  3. Java中只能**单继承**（类或抽象类），不能多继承。
  4. 类/抽象类/接口可以继承**任意多个**接口。
  5. 子类**重写方法**的**返回值**必须**小于等于父类方法的返回值**范围。
  6. 子类**重写方法**的**权限修饰符**必须**大于等于父类**的。
  7. 当一个类**实现**的**多个接口**中都有**相同方法**时，必须进行**覆盖重写**。
  8. 当一个类实现的**接口**和继承的**父类**中有**相同方法**时，默认使用**父类的方法**。

- 多态：

  相同的事务，掉用相同的方法时，表现出不同的行为。

  **实现多态的三个必要条件：**

  1. 继承：多态中必须要存在有继承关系的父类和子类；
  2. 重写：子类重新定义父类中某些方法；
  3. 向上转型：将子类引用赋值给父类对象。

### 3.5 重要事项

1. 局部变量没有默认值，需要赋值后使用；而成员变量定义后具有默认值。
2. 对于基本数据类型中的boolean，其Getter必须写成isXxx的形式。
3. 一旦编写了构造方法，编译器就不再赠送构造方法了。



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
  3. 字面上可以当作是一个char数组，而底层是byte数组。

- **重要问题：**

  - **字符串常量池：**

    若使用引号的方法`String s = "123";`创建字符串，会先在字符串常量池中查找是否存在：

    	若存在，则会使用常量池中存在的字符串；
			
    	若不存在，则会在常量池中新建该常量并使用。

    若使用new的方法`String s = new String("123");`创建字符串，仅会在常量池外新建String对象。

  - **equals和==：**

    对于String：

    	equals()方法比较字符串字面值；
			
    	==比较内存地址。

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

    	比较字符串与参数字符串第一个字符，若不等，返回字符串ASCII-参数字符串ASCII；若相等，比较下一位；以此类推。
			
    	若两个字符串不同，且一个字符串为另一个字符串的前面N位，返回字符串长度。

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

     	SimpleDateFormat()					//使用默认的模式
			
     	SimpleDateFormat(**String pattern**)		//使用给定的模式

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

  - public static <T> void **sort**(List<T> list)		//Collections.sort(List<T>)，对集合进行排序。该构造方法使用自然排序（参数需要Comparable），可追加一个Comparator进行比较器排序。
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

    特点：先进（压栈）后出（弹栈）。

  - **队列：**

    特点：先进先出。

  - **数组：**

    概述：

    	存储同一种类型的多个元素的容器，有索引方便获取元素。

    特点：

    	查询快，增删慢（由于长度固定需新建数组）。

  - **链表：**

    概述：
    ​	由一个“链子”把多个结点连起组成的数据。

    	结点：由数据和地址组成（数据域和指针域）。
			
    	存在一种双向的链表，其具有两个指针域，分别指向前一个和后一个结点。

    特点：

    	查询慢（需从头开始），增删快。

- **红黑树：**

  - **概述：**

    红黑树是一种自平衡二叉查找树，有着良好的最坏情况运行时间，可以在O(log n)时间内做查找、插入和删除。

  - **红黑树的规则：**

    1. 节点是红色或者黑色；
    2. 根是黑色；
    3. 所有叶子（NIL，即末端节点）都是黑色；
    4. 红色节点的子节点必须是黑色；
    5. 从任一节点到其每个叶子的所有简单路径都包含相同数目的黑色节点。

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

   - **注意事项：**

     1. 泛型只能是引用类型，不能是基本类型。
     2. 从jdk1.7开始，可以使用泛型推断。

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

### 5.7 JDK7新特性





### 5.8 JDK8新特性

1. **默认方法**

   - JDK8开始接口中可以有默认方法了，不需要实现类去实现
   - 默认方法需要用**default**关键字修饰

2. **函数式接口**

   - 函数式接口（functional interface）就是**有且仅有一个抽象方法**，但是**可以有多个非抽象方法**的接口
   - 友好地支持Lambda表达式

3. **Lambda表达式**

   **说明：**

   - 又被称为闭包，是JDK8重要的新特性
   - 允许把函数作为参数传递进方法中
   - 可以使代码更加简洁紧凑

   **格式：**

   ```java
   new Thread(
   	(参数列表) -> {代码;return 结果;}
   ).start;
   
   new Thread(
   	(参数列表) -> 代码;
   )
   ```

   **注意事项：**

   1. **必须在一个函数式接口上使用**（有且仅有一个抽象方法）；
   2. **表达式的大括号和分号**必须**一起出现**，或者**一起省略**；
   3. 参数列表中多个参数的**类型如果要省略**，必须**一起省略**；
   4. 如果函数的参数列表有**不同类型的参数**，也**可以省略类型**；
   5. 若一个方法**构成重载**，并且分别需要**两个参数列表相同的函数式接口**，此时使用Lambda表达式就会发生错误。因为系统不知道该使用哪一个方法，此时应老实用匿名内部类。

4. **Stream流**

   **说明：**

   - Stream是Java8中对集合、数组对象的增强，专注于对集合对象进行某些高效的聚合操作，或进行大批量数据操作。
   - 可以很方便地写出高性能地并发代码。

   **获取流：**

   - 集合：
     - Collection：Java8中添加了默认方法stream()，可以直接colle.stream()获取流。
     - Map：需要分keySet、values和Map.entry等情况来使用，例如：m.keySet().stream()。
   - 数组：
     - Stream.of(arr)。

   **常用方法：**

   - 延迟方法：

     返回Stream，可以继续调用Stream中地其他方法。

     - filter(Predicate<? super T> p)：过滤。
     - map(Function<? super T, ? extends R> mapper)：映射，将一个流中的元素映射到另一个流中。
     - limit(long MaxSize)：取用前几个。
     - skip(long n)：跳过前几个。
     - **static** <T> Stream<T> concat(Stream<? extends T> a, Stream<? extends T>)：合并流

   - 终结方法：

     不再返回Stream。

     - forEach(Consumer<? super T> action)：逐一处理（遍历）。
     - count()：个数。



### 5.9 JDK9新特性

1. **集合的of方法**

   **该方法可以方便地创建集合的不可变实例。**

   of()方法是List、Set和Map这三个接口的静态方法，并不在其他地方出现。

   需注意：若修改了of()生成的集合，会直接抛出异常。



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

	使用for循环即可，举例：

```java
for(int x = 0;x < arr.length;x ++){
    arr[x]...;
}
```

### 6.3 二维数组

二维数组是元素类型为一维数组的数组。

	定义格式：

```
int[][] arr = new int[外层数组长度][里层数组长度];	//动态初始化
int[][] arr = new int[外层数组长度][];			//可以暂不指定里层数组长度
int[][] arr = new int[][]{{...},{...},...};		//静态初始化
int[][] arr = {{...},{...},...};				//简化的写法
```

遍历二维数组，用循环嵌套即可。

### 6.4 注意事项

1. 数组一旦初始化完毕，其长度不再发生改变。
2. 数组静态初始化的省略模式（int[] arr = {1, 2, 3}）不能拆分表示。
3. 动态初始化时：
   - 若数据类型为整形，则默认值为0
   - 若数据类型为浮点型，则默认值为0.0
   - 如果是布尔类型，则默认值为false
   - 如果是引用类型，则默认值为null



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

    - boolean **add**(Object obj)		//c.add(o)，添加一个元素，成功操作返回true
    	 boolean **addAll**(Collection c)	//c1.addAll(c2)，添加一个集合的元素，成功操作返回true

  - **删除功能：**

    - void **clear**()					//c.clear()，清除所有元素
    	 boolean **remove**(Object obj)	//c.remove(o)，移除一个元素，成功操作返回true
    	 boolean **removeAll**(Collection c)	//c1.removeAll(c2)，从c1中移除c2包含的所有元素

  - **判断包含功能：**

    - boolean **contains**(Object obj)		//c.contains(o)，判断集合中是否包含指定元素（底层是equals）
    	 boolean **containsAll**(Collection c)	//c1.containsAll(c2)，判断c1中是否包含所有c2中的元素

  - **迭代器：**

    - Iterator<E> **iterator**()				//Iterator it = c.iterator()，Iterator是一个接口，这里返回的是一个子类对象

      迭代器的最终实现，是通过各集合最终实现类（如ArrayList）中的内部类。
      ​	Iterator的功能：
      ​		boolean **hasNext**()	//it.hasNext()，如果仍有元素可以迭代，返回true
      ​		Object **next**()		//it.next()，获取元素，并移动到下一位置

  - **长度&交集&转换：**

    - int **size**()							//c.size()，返回集合中元素的个数

    注意：此处为集合与数组和String的不同之处，后两者的长度功能为length()

    - boolean **retainAll**(Collection c)		//c1.retainAll(c2)，c1和c2做交集，结果保存在c1中，c2不变。如果c1发生了改变返回true
    	 Object[] **toArray**()			        //c.toArray()，返回此collection中所有元素的数组


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
    ​	ListIterator：一个接口，List集合特有的迭代器，继承了Iterator（可以直接使用hasNext()和next()），部分特有功能：
    ​		Object **previous**()			//lit.previous()，返回上一个元素
    ​		boolean **hasPrevious**()	//lit.hasPrevious()，判断是否有上一个元素
    ​		void **add**(E e)				//lit.add(元素)，将指定元素添加到List（注意不是添加到迭代器），添加的位置为当前迭代器迭代指针的位置

- **三个重要子类ArrayList、Vector和LinkedList：**

  1. **ArrayList：**

     概述：

     	经典的List子类，使用List中的方法即可，不过多介绍。
			
     	底层数据结构是**数组**（**查询快，增删慢**），线程**不安全**，效率**高**。

  2. **Vector:**

     概述：

     	底层数据结构是**数组**（**查询快，增删慢**），线程**安全**，效率**低**。

     特有功能：

     - public Object **elementAt**(int index)		//vec.elementAt(索引)，返回索引处

          的元素，场用get(index)替代

          

     	 public Enumeration **elements**()	//vec.elements()，常用iterator()替代

     	 boolean **hasMoreElements**()		//en.hasMoreElements()，常用hasNext()替代

     	 Object **nextElement**()				//en.nextElement()，常用next()替代

  3. **LinkedList：**

     概述：

     	底层数据结构是**链表**（**查询慢，增删快**），线程**不安全**，效率**高**。

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

      1. 存储多个相同字符串时只会留下一个的解释：

      		public boolean add(E e)方法底层依赖hashCode()和equals()。
      	比较基于hashCode()的值是否相同，
      	​	相同：继续比较equals()，若也相同就不添加，否则就添加；
      	​	不同：添加。

      2. JDK1.8后，HashSet的数据结构为`数组`+`链表`+`红黑树`。

         准确来说，在同一hash下，若链表的节点数达到8，则该链表转换为红黑树。

  - **TreeSet<E>：**

    - **概述：**

      - 基于TreeMap的NavigableSet实现，能够按照**某种规则对元素进行过排序**。
      - 默认使用`自然顺序`对元素进行排序，或者根据创建Set时提供的Comparator进行排序，具体取决于使用的构造方法。
      - 不论是自然排序还是比较器排序，该类会依次**从小到大排序**。

    - **特点：**

      唯一和排序。

    - **两种排序：**

      - **自然排序：**

        - 概述：

          使用public TreeSet()构造方法。
          元素所属类需要实现Comparable<T>接口并重写int comparaTo(T o)，自行定义比较规则：
          ​	若返回值大于0则认为`元素`大于`参数`，

          	返回值小于0认为`元素`小于`参数`，
						
          	返回值等于0认为`元素`等于`参数` (不添加到集合)。

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

          	若返回值大于0则认为`o1`大于`o2`，

          	返回值小于0认为`o1`小于`o2`，
				​		
          	返回值等于0认为`o1`等于`o2` (不添加到集合)

        - **简记：**

          1. **返回o1 - o2即为升序排序；**
          2. **返回o2 - o1即为降序排序。**

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

    V **put**(K key, V value)			//m.put(key, value)，添加元素。若添加的key存在，用现在key对应的value替换之前的value并返回之前的value；若添加的key不存在，返回null。

  - **删除功能：**

    void **clear**()					//m.clear()，移除所有键值对
    V **remove**(Object key)			//m.remove(key)，根据键删除键值对，返回值。如果键不存在返回null。

  - **判断功能：**

    boolean **containsKey**(Object key)		//m.containsKey(key)，判断集合是否包含指定的键
    boolean **containsValue**(Object value)	//m.containsValue(value)，判断集合是否包含指定的值
    boolean **isEmpty**()					//m.isEmpty()，判断集合是否为空

  - **获取功能：**

    Set<Map.Entry<K, V>> **entrySet**()		//m.entrySet()，返回该map中所有键值对对象的Set集合
    ​	*需结合Map.Entry接口的K getKey()和V getValue()来获取键值对对象中的键和值。
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



