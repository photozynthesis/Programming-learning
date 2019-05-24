# Lucene

[TOC]

## 1. 全文检索技术 & Lucene 概述

### 1.1 全文检索技术

- 将非结构化数据（格式、长度等不固定的数据）中的一部分信息提取出来，重新组织，使其具有一定的结构。之后对这种有一定结构的数据进行搜索，速度会更快。
- 以上步骤中提取出来方便查询的数据，称为索引。
- 先建立索引，再对索引进行搜索的过程称为**全文检索**。
- 对于数据量大，数据结构不固定的数据可采用全文检索的方式搜索。

### 1.2 Lucene 概述

- Lucene 是 apache 下的一个开源的全文检索引擎工具包。



## 2. Lucene 实现全文检索的过程

过程概述如下：

- 创建索引
  - 获得文档
  - 构建文档对象
  - 分析文档（分词）
  - 创建索引
- 查询索引
  - 创建查询
  - 执行查询
  - 渲染结果



## 3. 环境设置与入门程序

### 3.1 环境设置

导入包：

- lucene-core
- lucene-analyzers-common
- *commons-io

### 3.2 入门程序：创建与查询索引

- 需求说明

  通过关键字从一堆文本文件中搜索出文件，要求搜索出文件名或内容包含关键字的文件。

- **创建索引代码示例**

  ```java
  public class Test {
      @Test
      public void createIndex() throws Exception {
          // 1. 创建 Directory 对象，指定索引库的位置，为 IndexWriter 做准备
          Directory directory = FSDirectory.open(new File("/usr/index").toPath());
          // 2. 创建配置对象，为 IndexWriter 做准备
          IndexWriterConfig config = new IndexWriterConfig();
          // 3. 使用上述内容创建 IndexWriter
          IndexWriter indexWriter = new IndexWriter(directory, config);
          // 4. 遍历原始文档路径，获取所有文件的相关信息，并封装为 Field（域）对象，添加到 Document 对象，写入索引库
          File dir = new File("/usr/source");
          for (File f : dir) {
              String fileName = f.getName();
              String fileContent = FileUtils.readFileToString(f);
              String filePath = f.getPath();
              long fileSize = FileUtils.sizeOf(f);
              Field fileNameField = new TextField("fileName", fileName, Field.Store.YES);
              Field fileContentField = new TextField("fileContent", fileContent, Field.Store.YES);
              ...
              // 创建 Document（文档）对象
              Document document = new Document();
              document.add(fileNameField);
              document.add(fileContentField);
              ...
              // 将文档写入索引库
              indexWriter.addDocument(document);
          }
          // 5. 关闭 indexWriter
          indexWriter.close();
      }
  }
  ```

- **查询索引代码示例**

  ```java
  pubilc class Test {
      @Test
      public void searchIndex() throws Exception {
          // 1. 创建 Directory 对象，指定索引库位置，用于构造 indexReader
          Directory directory = FSDirectory.open(new File("/usr/index").toPath());
          // 2. 创建 indexReader 对象，用于构造 indexSearcher
          IndexReader indexReader = Directory.open(directory);
          // 3. 创建 indexSearcher 对象
          IndexSearcher indexSearcher = new IndexSearcher(indexReader);
          // 4. 创建查询对象，Term 的第一参数为查询的域，第二参数为查询的关键词
          Query query = new TermQuery(new Term("fileName", "spring"));
          // 5. 执行查询，传递查询对象和返回结果的最大条目数
          TopDocs topDocs = indexSearcher.search(query, 10);
          // 6. 遍历查询结果，通过存储了 document 对象 id 的 topDocs.scoreDocs 得到 Document 对象
          for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
              // 得到 Document 对象
              Document document = indexSearcher.doc(scoreDoc.doc);
              // 获取各个域的值并输出
              System.out.println(document.get("fileName"));
              System.out.println(document.get("fileContent"));
              sout("------");
          }
          // 7. 关闭 indexReader
          indexReader.close();
      }
  }
  ```



## 4. 使用中文分词器

默认的分词器 StandardAnalyzer 只能对英文进行分词，而使用 IKAnalyzer 就可以较好的进行中文分词。

**如何使用 IKAnalyzer**：

- 导入包

- *配置常用词和停用词词典 hotword.dic/ext_stopword.dic，放置到 classpath 下。

- 在创建 IndexWriterConfig 的时候新建一个 IKAnalyzer 作为参数传递。

  ```java
  ​```
  	@Test
      public void addDocument() throws Exception {
      	Directory directory = FSDirectory.open(new File("...").toPath);
      	IndexWriterConfig config = new IndexWriterConfig(new IKAnalyzer());
      	
      	IndexWriter indexWriter = new IndexWriter(directory, config);
      }
  ```


## 5. 多种 Field（域）的说明与属性

### 5.1 属性说明

- 是否分析：

  是否对域的内容进行分词处理。

  一般当需要对域的内容进行查询的时候需要此属性。

- 是否索引：

  只有对内容进行索引后才能搜索到。

- 是否存储：

  是否将 Field 值存储在 Document（文档）中。

  存储在文档中的 Field 才可以从文档中获取。

### 5.2 常用域

| Field 类    | 数据类型  | Analyzed | Indexed | Stored | 说明                                                    |
| ----------- | --------- | -------- | ------- | ------ | ------------------------------------------------------- |
| StringField | 字符串    | N        | Y       | Y / N  | 不进行分析，直接将整个字符串存储在索引中                |
| LongPoint   | Long      | Y        | Y       | N      | 处理数字类型值，可是不进行存储                          |
| StoredField | 多种类型  | N        | N       | Y      | 不分词，不索引，直接存进 Field                          |
| TextField   | 字符串/流 | Y        | Y       | Y / N  | 使用 Reader 时，lucene 猜测内容比较多，可能会不进行存储 |



## 6. 索引库的增删改查

### 6.1 添加

1. 定义索引库存放路径，定义配置（分析器），并构建 IndexWriter。
2. 创建文档对象，添加域。

3. 使用 indexWriter 将文档添加到索引库。

4. 关闭 indexWriter。

```java
...
    @Test
    public void addDocument() throws Exception {
    	// 1. 定义索引库存放路径，定义配置（分析器），并构建 IndexWriter
    	Directory dir = FSDirectory.open(new File("...").toPath());
    	IndexWriterConfig config = new IndexWriterConfig(new IKAnalyzer());
    	IndexWriter indexWriter = new IndexWriter(directory, config);
    	// 2. 创建文档对象，添加域
    	Document document = new Document();
    	document.add(new TextField("fileContent", "Too simple,Sometimes Naive", Field.Store.YES));
    	document.add(new LongPoint("size", 1234));
    	document.add(new StoredField("size", 1234));
    	// 3. 使用 indexWriter 将文档添加到索引库
    	indexWriter.addDocument(document);
    	// 4. 关闭 indexWriter
    	indexWriter.close();
	}
```

### 6.2 删除

- **删除全部**

  1. 创建 IndexWriter。
  2. 调用删除全部方法。
  3. 关闭 indexWriter。

  ```java
  ...
      @Test
      public void deleteDocument() throws Exception {
      	// 1. 创建 IndexWriter
      	Directory dir = FSDirectory.open(new File("...").toPath());
      	IndexWriterConfig config = new IndexWriterConfig();
      	IndexWriter indexWriter = new IndexWriter(directory, config);
      	// 2. 调用删除全部方法
      	indexWriter.deleteAll();
      	// 3. 关闭 indexWriter
      	indexWriter.close();
  	}
  ```

- **根据查询条件删除**

  1. 创建 IndexWriter。
  2. 创建 Query（查询条件）。
  3. 调用根据 Query 删除的方法。
  4. 关闭 indexWriter。

  ```java
  ...
      @Test
      public void deleteDocument() throws Exception {
      	// 1. 创建 IndexWriter
      	Directory dir = FSDirectory.open(new File("...").toPath());
      	IndexWriterConfig config = new IndexWriterConfig();
      	IndexWriter indexWriter = new IndexWriter(directory, config);
      	// 2. 创建 Query（查询条件）
      	Query query = new TermQuery(new Term("fileContent", "naive"));
      	// 3. 调用根据 Query 删除的方法
      	indexWriter.deleteDocument(query);
      	// 4. 关闭 indexWriter
      	indexWriter.close();
  	}
  ```

### 6.3 修改

1. 创建 IndexWriter。
2. 创建 Document。
3. 通过 Term 查找目标文档，用刚创建的文档进行替换。
4. 关闭 indexWriter。

```java
...
    @Test
    public void updateDocument() throws Exception {
    	// 1. 创建 IndexWriter
    	Directory dir = FSDirectory.open(new File("...").toPath());
    	IndexWriterConfig config = new IndexWriterConfig();
    	IndexWriter indexWriter = new IndexWriter(directory, config);
    	// 2. 创建 Document
    	Document doc = new Document();
    	doc.add(new TextField("fileContent", "I'm angry", Field.Store.YES));
    	// 3. 通过 Term 查找目标文档，用刚创建的文档进行替换
    	indexWriter.updateDocument(new Term("fileContent", "naive"), doc);
    	// 4. 关闭 indexWriter
    	indexWriter.close();
	}
```

### 6.4 查询

- **使用 TermQuery**

  1. 通过 Directory 构建 IndexReader，最终创建 IndexSearcher。
  2. 创建查询（TermQuery）对象。
  3. 执行查询，得到 TopDocs。
  4. 遍历得到结果。
  5. 关闭 indexReader。

  ```java
  ...
      @Test
      public void queryForDocuments() throws Exception {
      	// 1. 通过 Directory 构建 IndexReader，最终创建 IndexSearcher
      	Directory dir = FSDirectory.open(new File("...").toPath());
      	IndexReader indexReader = DirectoryReader.open(dir);
      	IndexSearcher indexSearcher = new IndexSearcher(indexReader);
      	// 2. 创建查询（TermQuery）对象
      	Query query = new TermQuery(new Term("fileContent", "naive"));
      	// 3. 执行查询，得到 TopDocs
      	TopDocs docs = indexSearcher.search(query, 10);
      	// 4. 遍历得到结果
      sout(topDocs.totalHits);
      for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
          Document document = indexSearcher.doc(scoreDoc.doc);
          sout(document.get("fileContent"));
      }
      	// 5. 关闭 indexReader
      	IndexSearcher.getIndexReader().close();
  	}
  ```

- **使用 QueryParser（带分析器）**

  1. 创建 IndexSearcher。
  2. 创建 QueryParser 。
  3. queryParser 解析字符串得到 Query 对象。
  4. 执行查询，得到 TopDocs。
  5. 遍历得到结果。
  6. 关闭 indexReader。

  ```java
  ...
      @Test
      public void queryForDocuments() throws Exception {
      	// 1. 创建 IndexSearcher
      	...
          // 2. 创建 QueryParser
          QueryParser queryParser = new QueryParser("content", new IKAnalyzer());
      	// 3. queryParser 解析字符串得到 Query 对象
      	Query query = queryParser.parse("too simple, sometimes naive");
      	// 4. 执行查询，得到 TopDocs
      	...
          // 5. 遍历得到结果
          ...
          // 6. 关闭 indexReader
          ...
  	}
  ```
