# 项目入门

### 简介

​	Eureka 是 Netflix 开发的服务发现框架，本身是一个基于 REST 的服务，主要用于定位运 行在 AWS 域中的中间层服务，以达到负载均衡和中间层服务故障转移的目的。

github: https://github.com/Netflix/eureka

wiki: https://github.com/Netflix/eureka/wiki/Eureka-at-a-glance

### 原理架构

![Eureka High level Architecture](https://github.com/Netflix/eureka/raw/master/images/eureka_architecture.png)

#### Eureka 与 Zookeeper 对比

Eureka 与 Zookeeper 都可以充当服务中心，它们的区别主要体现在对于 **CAP** 原则的支持的不同。

- Eureka与AP原则
- Zookeer与CP原则

### 项目说明

#### 集群说明

- application-peer1.properties

```properties
# 集群配置
eureka.client.service-url.defaultZone=http://peer1:7070/eureka,http://peer2:7171/eureka
```

#### 启动方式

```properties
# 启动server:7070
java -jar eureka-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer1
# 启动server:7171
java -jar eureka-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=peer2
```

### 运行

浏览器打开 http://localhost:7171/

![](https://hlvan-st.oss-cn-beijing.aliyuncs.com/property/upload/20190705154337.png)

保护机制提醒

==EMERGENCY! EUREKA MAY BE INCORRECTLY CLAIMING INSTANCES ARE UP WHEN THEY'RE NOT. RENEWALS ARE LESSER THAN THRESHOLD AND HENCE THE INSTANCES ARE NOT BEING EXPIRED JUST TO BE SAFE==

自我保护机制，预防网络抖动的服务不可用

Eureka Server 在运行期间会去统计心跳失败比例在 15 分钟之内是否低于 85%，如果低于 85%，Eureka Server 会将这些实例保护起来，让这些实例不会过期，但是在保护期内如果服务刚好这个服务提供者非正常下线了，此时服务消费者就会拿到一个无效的服务实例，此时会调用失败，对于这个问题需要服务消费者端要有一些容错机制，如重试，断路器等。