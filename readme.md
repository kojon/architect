# 项目入门

### 各项目说明

分布式服务模块

- eureka-server	服务注册中心
- provider             服务生产者
- consumer          服务消费者
- zuul                    网关
- stream-kafka    事件驱动流

 基于config-center的模块

- config-server					config配置服务

- config-eureka-server      服务注册中心
- config-provider               服务生产者
- config-consumer            服务消费者

### 技术栈列表

spring系列

- spring

  版本：5.1.5.RELEASE

  - spring-kafka  kafka-api包

- spring boot  

  版本：2.1.3.RELEASE

  - spring-boot-starter-web	web容器
  - mybatis-spring-boot-starter 连接mybatis
  - spring-boot-starter-test  测试模块
  - spring-boot-starter-actuator  监控
  - spring-boot-starter-data-jpa  数据库orm
  - spring-boot-starter-data-redis  redis-api包

- spring colud  

  版本:**Greenwich.SR1**

  - spring-cloud-netflix
    - pring-cloud-starter-netflix-eureka-server	注册中心服务器
    - pring-cloud-starter-netflix-eureka-client    注册中心客户端
    - spring-cloud-starter-openfeign     rest请求封装器
    - spring-cloud-starter-netflix-hystrix     熔断器
    - spring-cloud-starter-netflix-hystrix-dashboard  熔断器仪表盘
    - spring-cloud-starter-netflix-zuul  zuul网关
    - spring-cloud-zuul-ratelimit  zuul限流组件
    - ribbon-discovery-filter-spring-cloud-starter  zuul过滤模块
    - spring-cloud-config-server 配置中心服务器
    - spring-cloud-starter-config  配置中心客户端
  - spring-cloud-starter-sleuth     sleuth链路日志记录
  - spring-cloud-starter-zipkin     zipkin链路日志收集器

其他：

- alibaba-druid   数据库连接池
- mysql-connector-java  mysql驱动
- lombok  代码生成工具包

### 配置hosts

```shell
# eureka server1 
127.0.0.1 peer1
# eureka server2
127.0.0.1 peer2
# 配置中心
127.0.0.1 configserver.com
# 配置中心 eureka server1 
127.0.0.1 eureka8001.com
# 配置中心 eureka server1 
127.0.0.1 eureka8002.com
# kafka集群
192.168.1.185 kafka01
192.168.1.186 kafka02
192.168.1.187 kafka03
# 链路监控服务器
127.0.0.1 zipkinserver
```

### 环境依赖

jdk 1.8

maven 3

redis

zookeeper集群

kafka集群

### 提示

实现细节参考各模块的README.MD

### 参考文献

https://spring.io/projects/spring-boot

https://spring.io/projects/spring-cloud

https://spring.io/projects/spring-data