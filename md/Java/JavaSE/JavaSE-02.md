# JavaSE学习笔记-PART-02

## 目录：

[TOC]

## 8. 异常

### 8.1 异常的概述

异常表示程序出现了不正常的情况。

### 8.2 Java异常体系

顶层类**Throwable**：程序的异常。

子异常：

- 类**Error**

  说明：

  	严重问题，不做处理。
  	
  	因为一般是难以/无法处理的问题，例如内存溢出。

- 类**Exception**

  - 类**RuntimeException**

    说明：

    	运行时异常，不做处理。
    	
    	此类异常的问题在于代码不够严谨，需要修正代码。

  - Exception下**除RuntimeException**以外Exception

    说明：

    	编译时异常，必须进行处理，否则代码不能通过编译。

### 8.3 异常的处理

- **默认处理：**

  如果程序出现问题，并没有做任何处理，Jvm最终会做出默认处理。
  默认处理方式：
  ​	把异常的名称、原因，及出现异常的位置输出在控制台，并**结束程序**。

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

  	输入流（读取数据）
  	
  	输出流（写出数据）

  **按数据类型分：**

  	字节流：
  	
  		任何文件的传输都可以用字节流。
  	
  	字符流：
  	
  		一般文本性的文件才使用字符流。

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

  5. **不论是字节输出流还是字符输出流，只要使用不带boolean append参数的方式打开流，文件都会立即被清空。**

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
  ​	字节数组内存操作流ByteArrayInputStream和ByteArrayOutputStream；
  ​	字符数组内存操作流CharArrayReader和CharArrayWriter；
  ​	字符串内存操作流StringReader和StringWriter。

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

    	Enumeration是有序集合Vector中public Enumeration<E> elements()方法的返回值，有序包含Vector中所有元素。
    	
    	使用该种构造方法，需要先创建Vector集合对象，然后将多个InputStream添加到集合，最后调用集合的elements方法得到Enumeration对象，以构造合并流对象。

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

- **并发和并行：**

  - 并发：是指逻辑上同时发生，在某一个时间段内同时运行多个程序。
  - 并行：是指物理上同时发生，在某一个时间点上同时运行多个程序。

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

- **注意事项：**

  1. **wait()和notify()等方法应该由锁对象调用；**
  2. **wait()和notify()等方法应该处于锁的包围中（拥有对象监视器）；**
  3. **调用wait()方法时将会放开当前锁。**

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

  	public Timer()

  **重要方法：**

  	public void **schedule**(TimerTask, Date time)			
  	
  	//t.schedule(TimerTask, Date)，安排在指定时间执行指定任务
  	
  	public void **schedule**(TimerTask, Date firstTime, long period)
  	
  	//t.schedule(TimerTask, Date, period)，安排任务在指定时间开始固定延迟地执行
  	
  	public void **schedule**(TimerTask task, long delay)
  	
  	//t.schedule(TimerTask, delay)，安排在指定延迟后执行指定任务
  	
  	public void **schedule**(TimerTask task, long delay, long period)
  	
  	//t.schedule(TimerTask, delay, period)，安排任务在指定延迟后开始固定延迟地执行
  	public void **cancel**()
  	
  	//t.cancel()，终止此计时器，并丢弃所有已安排的任务

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

  	public Process **exec**(String command)			//rt.exec(DOS命令)

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

  **要求被代理的类至少要实现一个接口**。

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



## 14. 注解

### 14.1 概述

注解（Annotation），也叫元数据，是一种代码级别的说明。

可以声明在包、类、字段、方法、局部变量、方法参数等的前面，用来对这些元素进行说明。

注解与注释不同，可以通过反射等方式被机器识别。

### 14.2 Java内置的注解

- **@Override**

  表示当前方法是覆盖的超类方法。若被注解的方法不构成重写，编译器就会报错。

- **@Deprecated**

  表示当前方法已过时，调用时编译器会发出警告。

- **@SuppressWarnings**

  关闭当前方法、类的警告信息。

### 14.3 自定义注解

- **格式：**

  ```java
  @Retention(RetentionPolicy.RUNTIME)
  ...
  public @interface MyAnno{
      String value();
      ...
  }
  ```

- **注意事项：**

  1. 注解的属性可以是**八种基本类型、String、Class(ClassName.class)、enum以及上述类型的数组**
  2. 若注解内只有一个属性且名为value或除value外其他属性均有默认值时，赋值时可以省略value

### 14.4 元注解

- **@Target**

  表示该注解可以用在什么地方，可用的ElementType参数：

  - CONSTRUCTOR		构造器
  - FIELD                              字段
  - LOCAL_VARIABLE         局部变量
  - METHOD                       方法
  - PACKAGE                       包
  - PARAMETER                  参数
  - TYPE                               类、接口、枚举

- **@Retention**

  表示注解的存留时期。

  - SOURCE                         仅存在于源代码
  - CLASS                             最多存在于字节码时期，会被VM抛弃
  - RUNTIME                       保存到VM运行时期

- **@Documented**

  表示该注解会被包含在javadoc中。

- **@Inherited**

  表示该注解会被继承。