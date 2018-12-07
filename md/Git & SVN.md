# Git & SVN

[TOC]

## 1. Git：概述

- Git 是一个开源的分布式的版本管理系统。



## 2. Git：创建与克隆仓库

- 创建仓库

  ```bash
  git init
  ```

- 克隆仓库

  ```shell
  git clone url [localFolderName]
  ```



## 3. Git：暂存区相关

- 概述

  `git add` 命令可以将工作区中指定的内容添加到暂存区。

  暂存区不等同于版本库，存入暂存区的内容需要经过 `git commit` 方能进入本地版本库。

- 查看版本库

  ```shell
  # 查看暂存区文件信息与工作区文件信息的对比
  git status
  
  # 查看工作区文件概览，AD表示暂存区中存在但工作区中已删除，AM表示工作区中文件已更改，??表示新加入的文件
  git status -s
  ```

- 将文件添加到暂存区

  ```shell
  # 指定文件进行添加
  git add README
  git add README HelloWorld.java
  
  # 添加全部文件
  git add .
  ```



## 4. Git：设置名字和邮箱

```shell
git config --global user.name 'photozynthesis'
git config --global user.email photozynthesis@outlook.com
```



## 5. Git：将缓存区中的内容提交到仓库

```shell
# 指定更新信息，不使用 -m 则会打开 vim，保存后会进行提交
git commit -m '今日更新了README'

# 跳过提交到暂存区的步骤，将暂存区已跟踪的文件提交到本版库
git commit -a
git commit -am '今日又又更新了'
```

- 查看提交历史

  ```shell
  git log [--reverse] [--oneline] [--graph]
  ```



## 6. Git：分支相关

- 查看分支信息

  ```shell
  git branch
  ```

- 新增分支

  ```shell
  git branch newBranch
  ```

- 切换当前分支

  ```shell
  # 切换分支后，会显示目标分支下已提交的内容
  git checkout newBranch
  ```

- 删除分支

  ```shell
  git branch -d newBranch
  ```

- 合并分支

  ```shell
  # 将指定分支合并到当前分支上，文件的增删会自动处理，而修改会发生冲突
  git merge newBranch
  ```

- 处理合并中的冲突

  - 产生冲突后对应文件中会出现提示。
  - 可以手动对出现提示的地方进行处理，处理完毕后进行 `add` 和 `commit` 操作即合并完成。


## 7. Git：标签

- 给最新一次注解打上版本标签

  ```shell
  git tag -a v1.0
  ```


