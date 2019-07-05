# 项目入门

### 项目说明

config-eureka-server基于配置中心的微服务注册中心，是spring-config的client，从config-center读取配置文件。

### Spring Cloud Bus

​	用于将服务和服务实例与分布式消息系统链接在一起的事件总线。在集群中传播状 态更改很有用(例如配置更改事件)。

#### 官网

首页 https://spring.io/projects/spring-cloud-bus

操作手册： https://cloud.spring.io/spring-cloud-static/spring-cloud-bus/2.1.2.RELEASE/single/spring-cloud-bus.html

#### 原理图

![](https://hlvan-st.oss-cn-beijing.aliyuncs.com/property/upload/20190705185709.png)

#### 配置自动更新原理

![](https://hlvan-st.oss-cn-beijing.aliyuncs.com/property/upload/20190705185749.png)

#### 项目依赖

kafka消息队列

### 触发更新

​	在github上修改配置中心数据之后，想要cofig-server立马更新yml，应用到服务中，需要调用任意一个config-client的bus-refresh post请求 
http://localhost:7070/actuator/bus-refresh