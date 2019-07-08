# 项目入门

### 简介

​	服务消费者：作为eureka客户端向eureka注册服务后，获取到服务注册列表后，调用服务生产者提供的service。

包含组件

- openfeign
- ribbon
- hystrix
- Turbine
- redis

### 参考手册

spring-cloud-netflix：https://cloud.spring.io/spring-cloud-static/spring-cloud-netflix/2.1.2.RELEASE/single/spring-cloud-netflix.html

### 组件说明

#### openfeign简介

​	Spring Cloud OpenFeign 通过自动配置的方式，通过绑定到 Spring 环境的方式和其他 Spring 编程模型风格的方式，提供了集成化的 Spring Boot 应用。

​	声明式 REST 客户端:Feign 通过使用 JAX-RS 或 SpringMVC 注解的装饰方式，生成接 口的动态实现。

​	Feign，假装、伪装。OpenFeign 可以使消费者将提供者提供的服务名伪装为接口进行消 费，消费者只需使用“Service 接口 + 注解”的方式即可直接调用 Service 接口方法，而无 需再使用 RestTemplate 了。

#### Ribbon 与 OpenFeign

说到 OpenFeign，不得不提的就是 Ribbon。Ribbon 是 Netflix 公司的一个开源的负载均衡 项目，是一个客户端负载均衡器，运行在消费者端。
OpenFeign 中使用 Ribbon 进行负载均衡，所以 OpenFeign 直接内置了 Ribbon。即在导入 OpenFeign 依赖后，无需再专门导入 Ribbon 依赖了。

#### Ribbon负载均衡

![](https://hlvan-st.oss-cn-beijing.aliyuncs.com/property/upload/20190705173513.png)

#### Ribbon 源码解析

1. 跟踪 RibbonLoadBalancerClient 类
2. 解析 IRule 接口
3. Ribbon 内置负载均衡算法 

- RoundRobinRule
  轮询策略。Ribbon 默认采用的策略。 
-  RandomRule
  随机策略，从所有可用的 provider 中随机选择一个。 
- RetryRule
  先按照 RoundRobinRule 策略获取 provider，若获取失败，则在指定的时限内重试。默认 的时限为 500 毫秒。
- BestAvailableRule
  选择并发量最小的 provider，即连接的消费者数量最少的 provider。
- AvailabilityFilteringRule
  该算法规则是:过滤掉处于断路器跳闸状态的 provider，或已经超过连接极限的 provider， 对剩余 provider 采用轮询策略。

- ZoneAvoidanceRule
  复合判断 provider 所在区域的性能及 provider 的可用性选择服务器。 
- WeightedResponseTimeRule
  “权重响应时间”策略。根据每个 provider 的平均响应时间计算其权重，响应时间越快 权重越大，被选中的机率就越高。在刚启动时采用轮询策略。后面就会根据权重进行选择了。

#### 雪崩熔断降级概念

##### 雪崩效应

  	在复杂的系统中，经常会出现 A 依赖于 B，B 依赖于 C，C 依赖于 D，......这种依赖将会
 产生很长的调用链路，这种复杂的调用链路称为 1->N 的扇出.

​	如果在 A 的调用链路上某一个或几个被调用的子服务不可用或延迟较高，则会导致调用 A 服务的请求被堵住。
堵住的 A 请求会消耗占用系统的线程、IO 等资源，当对 A 服务的请求越来越多，占用 的计算机资源越来越多的时候，会导致系统瓶颈出现，造成其他的请求同样不可用，最终导 致业务系统崩溃，这种现象称为雪崩效应。

![](https://hlvan-st.oss-cn-beijing.aliyuncs.com/property/upload/20190705174214.png)

##### 服务雪崩

![](https://hlvan-st.oss-cn-beijing.aliyuncs.com/property/upload/20190705174320.png)

上图是用户请求的多个服务(A,H,I,P)均能正常访问并返回的情况。

![](https://hlvan-st.oss-cn-beijing.aliyuncs.com/property/upload/20190705174433.png)

上图为请求服务 I 出现问题时，一个用户请求被阻塞的情况。

![](https://hlvan-st.oss-cn-beijing.aliyuncs.com/property/upload/20190705174506.png)

上图为大量用户请求服务 I 出现异常全部陷入阻塞的的情况，即服务发生雪崩的情况。

##### 熔断机制

熔断机制是服务雪崩的一种有效解决方案。当指定时间窗内的请求失败率达到设定阈值 时，系统将通过断路器直接将此请求链路断开。常见的熔断有两种:

- 预熔断
- 即时熔断

##### 服务降级

  服务降级是请求发生问题时的一种增强用户体验的方式。
现代系统中，发生服务熔断，一定会发生服务降级;但发生服务降级，并不一定会发生 服务熔断。

#### Hystrix 熔断机制与服务降级

##### Hystrix简介：

​	在分布式环境中，许多服务依赖中的一些服务发生失败是不可避免的。Hystrix 是一 个库，通过添加延迟容忍和容错逻辑，帮助你控制这些分布式服务之间的交互。Hystrix 通过 隔离服务之间的访问点、停止跨服务的级联故障以及提供回退选项来实现这一点，所有这些 都可以提高系统的整体弹性。

##### 综合说明

​	当 Hystrix 监控到某个服务发生故障后，其不会让该服务的消费者阻塞，或向消费者抛 出异常，而是向消费者返回一个符合预期的、可处理的备选响应(FallBack)，这样就避免了 服务雪崩的发生。

##### fallbackMethod 服务降级

​	Hystrix 对于服务降级的实现方式有两种:fallbackMethod 服务降级，与 fallbackFactory
服务降级。

##### Hystrix 高级属性配置

###### 执行隔离策略 

防止提供者被熔断，防止大量客户端请求被阻塞。

- 类型
    隔离请求的方式有两种类型:
  - 线程隔离thread:Hystrix的默认隔离策略。系统会创建一个依赖线程池，为每个依赖请
  求分配一个独立的线程，而每个依赖所拥有的线程数量是有上限的。当对该依赖的调用 请求数量达到上限后再有请求，则该请求阻塞。所以对某依赖的并发量取决于为该依赖 所分配的线程数量。
  - 信号量隔离:对依赖的调用所使用的线程仍为请求线程，即不会为依赖请求再新创建新 的线程。但系统会为每种依赖分配一定数量的信号量，而每个依赖请求分配一个信号号。 当对该依赖的调用请求数量达到上限后再有请求，则该请求阻塞。所以对某依赖的并发 量取决于为该依赖所分配的信号数量。
  - 对比:
  - 线程是进程的一个执行体，而信号却不是，其仅是线程执行的条件。
  - 线程的数量是有上限的，而信号量不存在上限概念。
  - 线程隔离中对依赖的请求线程与调用线程是不同的线程，而信号量隔离是同一个线
    程
  - 在复杂的长链路调用中，信号量隔离的效率会低于线程隔离，因为执行体差一半。
  - 在简单的短链路调用中，线程隔离的系统性能会低于信号量隔离。
  
###### 修改策略

若是在配置文件中，则可以通过以下设置修改:

```properties
   hystrix.command.default.execution.isolation.strategy=thread
   hystrix.command.default.execution.isolation.strategy=semaphore
```

若是在代码中，则可通过以下语句修改。

```java
 HystrixCommandProperties.Setter().withExecutionIsolationStrategy(ExecutionIsolationStrate gy.THREAD);
 HystrixCommandProperties.Setter().withExecutionIsolationStrategy(ExecutionIsolationStrate gy.SEMAPHORE);
```

###### 默认值

在 HystrixCommandProperties 类的构造器中设置有这些高级属性的默认值。

###### 线程执行超时时限

​	在默认的线程执行隔离策略中，关于线程的执行时间，可以为其设置超时时限。当然， 首先通过下面的属性开启该超时时限，该属性默认是开启的，即默认值为 true。若要关闭， 则可以配置文件中设置该属性的值为 false。
hystrix.command.default.execution.timeout.enabled

​	在开启了执行线程超时时限后，可以通过以下属性设置时限长度。其默认值为 1000 毫秒

​	hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds

###### 超时中断

当线程执行超时时是否中断线程的执行。默认为 true，即超时即中断。通过以下属性进 行设置。
hystrix.command.default.execution.isolation.thread.interruptOnTimeout

###### 取消中断

在线程执行过程中，若请求取消了，当前执行线程是否结束呢?由该值设置。默认为 false， 即取消后不中断。通过以下属性进行设置。
hystrix.command.default.execution.isolation.thread.interruptOnCancel

###### 线程池相关属性

关于执行线程的线程池，可以通过以下的这些属性设置。

#### hystrix-dashboard

运行项目后访问： http://localhost:8888/hystrix

输入监控地址 http://localhost:8888/actuator/hystrix.stream

title: consumer   //这里是项目的application.name 

![](https://hlvan-st.oss-cn-beijing.aliyuncs.com/property/upload/20190708104117.png)

当发生请求时，监控如下

![](https://hlvan-st.oss-cn-beijing.aliyuncs.com/property/upload/20190708103810.png)

#### 仪表盘-Turbine

前面的方式是对单个消费者进行监控，我们也可以对集群进行整体监控。此时就需要使 用 Turbine 技术了。Turbine 能够汇集监控信息，并将聚合后的信息提供给 Hystrix Dashboard 来集中展示和监控。
使用 Turbine 对集群进行监控的实现步骤很简单，只需三步:

- 导入Turbine依赖
- 在配置文件中配置turbine
- 在启动类上添加@EnableTurbine注解

#### 服务降级报警机制

​	无论是由于个别原因导致某个或某些消费者出现问题，还是由于高并发出现消费问题而引发服务熔断，他们都将启用服务降级机制。无论哪种原因启用了服务降级，系统都应该向管理员发出警报通知管理员，例如向管理员发送短信。这种发生服务降级后向管理员发出警 报的机制称为服务熔断报警机制。
在短信发送之前，需要先查看服务降级标识。