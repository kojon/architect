# 项目入门

### 简介

服务生产者：和一般的web项目没啥差异。作为eureka客户端向eureka注册服务后，为服务消费者提供http service。

### 包含功能

#### ORM

数据库为mysql，orm使用JPA，连接池为druid

Spring Data JPA参考手册  https://docs.spring.io/spring-data/jpa/docs/2.1.9.RELEASE/reference/html/

```yaml
# 设置Spring-Data-JPA
spring:
  application:
    name: provider
  jpa:
    # 指定在Spring容器启动时是否自动建表，默认为false
    generate-ddl: true
    # 指定是否在控制台显示其执行的SQL语句，默认false
    show-sql: true
    # 指定应用重启时是否重新创建更新表
    hibernate:
      ddl-auto: update

  # 配置数据源
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.jdbc.Driver
    url: 'jdbc:mysql://localhost:3306/architect?useUnicode=true&characterEncoding=utf8'
    username: root
    password: xiaohuoban
```

#### 链路跟踪

使用sleuth做链路日志输出，kafka作为数据消费中间件，zipkin分布式跟踪系统。

原理和使用请参考consumer模块下sleuth+zipkin.md

参考手册 https://cloud.spring.io/spring-cloud-static/spring-cloud-sleuth/1.2.6.RELEASE/single/spring-cloud-sleuth.html

配置

```properties
spring:
  zipkin:
    #监控方式1： 直连zipkin服务器地址
    # base-url: http://localhost:9411/

    #监控方式2： 异步将log发布到kafka zipkin服务器消费log
    sender:
      type: kafka
  kafka:
    bootstrap-servers: kafka01:9092,kafka02:9092,kafka03:9092

  # 设置采样比例为1.0，即全部都需要，默认为0.1
  sleuth:
    sampler:
      probability: 1.0
```

MAVEN依赖

```xml
        <!--kafka依赖-->
        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka</artifactId>
        </dependency>

        <!--zipkin客户端依赖，其包含了sleuth依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zipkin</artifactId>
        </dependency>
        <!--sleuth依赖：如果不使用kafka，去掉上面的包 使用这个-->
        <!--<dependency>-->
            <!--<groupId>org.springframework.cloud</groupId>-->
            <!--<artifactId>spring-cloud-starter-sleuth</artifactId>-->
        <!--</dependency>-->
```

#### 消息队列-kafka

参考手册  https://docs.spring.io/spring-kafka/docs/2.2.7.RELEASE/reference/html/





