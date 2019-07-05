# 项目入门

### Spring Cloud Stream

​	一个轻量级的事件驱动微服务框架，用于快速构建可连接到外部系统的应用程序。 在 Spring Boot 应用程序之间使用 Kafka 或 RabbitMQ 发送和接收消息的简单声明式模型。

​	Spring Cloud Stream 是一个用来为微服务应用构建消息驱动能力的框架。通过使用 Spring Cloud Stream，可以有效简化开发人员对消息中间件的使用复杂度，让系统开发人员 可以有更多的精力关注于核心业务逻辑的处理。但是目前 Spring Cloud Stream 只支持 RabbitMQ 和 Kafka 的自动化配置

### 官网

首页： https://spring.io/projects/spring-cloud-stream

操作手册： https://cloud.spring.io/spring-cloud-static/spring-cloud-stream/2.2.0.RELEASE/home.html

### 程序模型

![](https://hlvan-st.oss-cn-beijing.aliyuncs.com/property/upload/20190705191121.png)

应用程序的核心部分(Application Core)通过 inputs 与 outputs 管道，与中间件连接， 而管道是通过绑定器 Binder 与中间件相绑定的。

