# Aliyun OSS SDK for Android 说明 #

本目录一共包含3个文件夹：

* bin 二进制文件，包括sdk jar包以及一个简单的http server用来查看sdk文档（需要python运行环境）
* sourcecode 源代码文件夹
* docs 文档

## SDK安装步骤 ##
SDK基于原生Android API编写，支持Android 2.1 (API Level 7）以上的版本。只需要将aliyunsdk.jar放在android项目的Build Path下即可

## 查看文档 ##
bin文件夹中自带了一个简单的http server，使用方法也非常简单。在sdk根目录下运行：

	python bin/micro-httpd.py

http server就会监听本地端口（默认8080，如果需要改变，在环境变量中设置HTTP_PORT）。访问http://localhost:8080即可查看文档。

如果本地没有python环境，可以查看我们的在线文档（放在aliyun oss中），地址是：http://opendoc.storage.aliyun.com/index.html

目录下还包含了一个设计文档 `design.pdf `，主要阐述了整个SDK的设计想法和实现细节，供参考

## Good Luck to ALL ##