# 项目入门

### SpringCloudConfig简介

​	SpringCloudConfig 为分布式系统中的外部化配置提供服务器和客户端支持。使用 Config 服务器，可以在中心位置管理所有环境中应用程序的外部属性。

### 官网

项目首页：https://spring.io/projects/spring-cloud-config

操作手册：https://cloud.spring.io/spring-cloud-static/spring-cloud-config/2.1.3.RELEASE/single/spring-cloud-config.html

### 说明

​	Spring Cloud Config 就是对微服务的配置文件进行统一管理的。其工作原理是，我们首 先需要将各个微服务公共的配置信息推送到 GitHub 远程版本库。然后我们再定义一个 Spring Cloud Config Server，其会连接上这个 GitHub 远程库。这样我们就可以定义 Config 版的 Eureka Server、提供者与消费者了，它们都将作为 Spring Cloud Config Client 出现，它们都会通过连 接 Spring Cloud Config Server 连接上 GitHub 上的远程库，以读取到指定配置文件中的内容。

### 原理图

![](https://hlvan-st.oss-cn-beijing.aliyuncs.com/property/upload/20190705184515.png)

### github配置中心

本项目配置github：https://github.com/kojon/config-center

### 运行

http://localhost:9999/application-dev.yml  
http://localhost:9999/application-test.yml

这里后缀名可以是yml、properties、json，不同类型返回不同结构体

### 说明

Config Server 可以组装的最终配置文件格式有三种:yml、properties、json

