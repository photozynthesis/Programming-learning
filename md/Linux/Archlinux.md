# Archlinux

[TOC]

## 0. 需求

- ~~secure boot相关问题~~
- 引导美化
- 驱动相关
- 桌面相关
- 语言相关
  - 显示语言
  - console语言
  - 输入法
- 软件安装相关
- shadowsocks
- ~~chrome~~
- ~~文本编辑器~~
- ide
- 媒体
- 模拟器&兼容层
- 办公套件
- ~~java~~
- git
- maven
- mysql
- tomcat



## 1. 基本安装

### 1.0 全新硬盘的磁盘分区

- 查看引导方式

  ```shell
  ls /sys/firmware/efi/efivars
  ```

- 查看设备和分区信息

  ```shell
  fdisk -l
  ```

- 联网并测试

  - 有线：

    ```shell
    dhcpcd
    ```

  - 无线：

    ```shell
    wifi-menu
    ```

  - 检测：

    ```shell
    ping baidu.com
    ```

- 更新系统时间

  ```shell
  timedatectl set-ntp true
  ```

- 进行分区

  - 创建引导分区

    ```shell
    # 选择 Device 下对应设备进入fdisk
    fdisk /dev/xxx
    
    # 按 o 来创建新的 MBR 分区表，按 g 来创建gpt
    ...
    
    # 按 n 创建引导分区，指定大小时使用+xxxM，可以使用单位 M 或 G，创建完后使用 p 来查看
    ...
    
    # 使用 t 查看可用的分区类型，使用相应代码更改分区类型为 EFI ...
    ...
    
    # 使用 w 来保存更改
    ...
    
    # 格式化创建的引导分区（fat32）
    mkfs.fat -F32 /dev/xxx
    ```

  - 创建根分区

    ```shell
    # 大部分与上述步骤相同，只是大小使用默认大小，以及格式化为 ext4
    ...
    mkfs.ext4 /dev/xxx
    ```

### 1.1 基本安装

- 挂载分区

  ```shell
  # 挂载根分区
  mount /dev/xxx /mnt
  
  # 挂载引导分区
  mount /dev/xxx /mnt/boot
  ```

- 选择镜像源

  ```shell
  # 编辑 pacman 的配置文件，搜索 china 镜像源，复制到开头
  vim /etc/pacman.d/mirrorlist
  ```

- 安装基本包

  ```shell
  pacstrap /mnt base base-devel
  ```

- 设置分区自动挂载

  ```shell
  # 生成 fstab 文件
  genfstab -L /mnt >> /mnt/etc/fstab
  
  # 检查生成的 fstab
  cat /mnt/etc/fstab
  ```

### 1.2 基本设置与软件安装

- 切换到新系统

  ```shell
  arch-chroot /mnt
  ```

- 设置时区

  ```shell
  # 设置时区为上海，并生成相关文件
  ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
  hwclock --systohc
  ```

- 安装常用软件包

  ```shell
  pacman -S vim dialog wpa_supplicant ntfs-3g networkmanager
  ```

- 设置 Locale （语言）

  ```shell
  # 编辑
  vim /etc/locale.gen
  
  # 找到一个英文、三个中文（UTF-8）取消注释
  ...
  
  # 执行
  locale-gen
  
  # 编辑 locale 设置文件
  vim /etc/locale.conf
  
  # 添加如下内容
  LANG=en_US.UTF-8
  ```

- 设置主机名

  ```shell
  vim /etc/hostname
  ```

  在第一行自行设置 hostname

- 设置 host

  ```
  vim /etc/hosts
  ```

  添加如下内容：

  > ```
  > 127.0.0.1	localhost
  > ::1		localhost
  > 127.0.1.1	myhostname.localdomain	myhostname
  > ```

- 设置 root 密码

  ```shell
  passwd
  ```

  按提示设置。

- 安装`intel-ucode` （非intel跳过）

  ```
  pacman -S intel-ucode
  ```

### 1.3 安装 Bootloader：refind

- 安装

  ```shell
  pacman -S refind-efi
  ```

- 部署

  ```shell
  refind-install
  ```

  手动部署参照wiki

- 手动创建条目

  refind.conf：

  ```
  menuentry "Arch Linux" {
          icon     /EFI/refind/icons/os_arch.icns
          volume   1:
          loader   /boot/vmlinuz-linux
          initrd   /boot/initramfs-linux.img
          options  "root=PARTUUID=3518bb68-d01e-45c9-b973-0b5d918aae96 rw rootfstype=ext4"
  }
  ```

  说明：PARTUUID需改为正确的UUID，通过`blkid`或`lsblk -f`查看。

  文件路径需设置正确。

- 删除多余的Linux条目

  refind.conf中找到自动检测kernal，取消注释那一行即可。

### 1.4 检查

EFI 分区中需要有`initramfs-linux-fallback.img`、`initramfs-linux.img`、`intel-ucode.img`、`vmlinuz-linux`几个文件。

若没有，

```shell
pacman -S linux
```



## 2. 交换文件





## 3. 新建用户





## 4. 配置 sudo





## 5. 图形服务与驱动

### 5.1 nvidia

- 查看显卡情况

  ```shell
  lspci -k | grep -A 2 -E "(VGA|3D)"
  ```

- pacman 安装 `nvidia`或`nvidia-lts` 

- 重启

- 进行自动配置（可以先安装 xorg）

  ```shell
  nvidia-xconfig
  ```

### 5.2 xorg

- 安装`xorg`或`xorg-server` 

### 5.3 sddm

- 安装`sddm` 

- 启用：

  ```shell
  systemctl enable sddm.service
  ```



## 6. 桌面环境





## 7. 常用软件

### 7.1 浏览器



### 7.2 shadowsocks



### 7.3 办公软件与文档查看器





## 8. 开发软件

### 8.1 JDK



### 8.2 IntelliJ IDEA

安装：

- AUR：`intellij-idea-ultimate-edition` 

- 安装位置：`/opt` 

  ```shell
  yay -S intellij-idea-ultimate-edition
  ```

### 8.3 MySQL



### 8.4 MAVEN



### 8.5 Tomcat



### 8.6 Git



### 8.7 Redis



## 9. 美化插件

