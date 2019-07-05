#  sleuth+zipkin 链路监控

### sleuth简介

​	微服务架构是一个分布式架构，它按业务划分服务单元，一个分布式系统往往有很多个服务单元。由于服务单元数量众多，业务的复杂性，如果出现了错误和异常，很难去定位。主要体现在，一个请求可能需要调用很多个服务，而内部服务的调用复杂性，决定了问题难以定位。所以微服务架构中，必须实现分布式链路追踪，去跟进一个请求到底有哪些服务参与，参与的顺序又是怎样的，从而达到每个请求的步骤清晰可见，出了问题，很快定位。

### sleuth流程图

![](https://hlvan-st.oss-cn-beijing.aliyuncs.com/property/upload/20190705161811.png)

1. trace 与 span
   - trace:跟踪单元是从客户端所发起的请求抵达被跟踪系统的边界开始，到被跟踪系统向 客户返回响应为止的过程，这个过程称为一个 trace。
   - span:每个 trace 中会调用若干个服务，为了记录调用了哪些服务，以及每次调用所消 耗的时间等信息，在每次调用服务时，埋入一个调用记录，这样一个调用记录称为一个 span。
   - 关系:一个 trace 由若干个有序的 span 组成。
     Spring Cloud Sleuth 为服务之间调用提供链路追踪功能。为了唯一的标识 trace 与 span，
     系统为每个 trace 与 span 都指定了一个 64 位长度的数字作为 ID，即 traceID 与 spanID。

2. annotation
   Spring Cloud Sleuth 中有三个重要概念，除了 trace、span 外，还有一个就是 annotation。 但需要注意，这个 annotation 并不是我们平时代码中写的@开头的注解，而是这里的一个专 有名词，用于及时记录事件的实体，表示一个事件发生的时间点。这些实体本身仅仅是为了原理叙述的方便，对于 Spring Cloud Sleuth 本身并没有什么必要性。这样的实体有多个，常用的有四个:
   - cs:Client Send，客户端发送请求的时间点
   - sr:Server Receive，服务端接收到请求的时间点
   - ss:Server Send，服务端发送响应的时间点  cr:Client Receive，客户端接收响应的时间点

### sleuth日志采样

```sql
2019-07-04 15:15:30.931  INFO [consumer,dc59f615e48c6072,3e99491c906efdf9,true] 3222 --- [ackController-2] c.n.c.c.c.DepartFallbackController       : 消费者的处理器方法被调用
```

简单说明：

- consumer为application name  
- dc59f615e48c6072 为 traceid ，可通过zipkin精确搜索

### 日志采样率

Sleuth 对于这些日志支持抽样收集，即并不是所有日志都会上传到日志收集服务器，日 志收集标记就起这个作用。默认的采样比例为: 0.1，即 10%。在配置文件中可以修改该值。 若设置为 1 则表示全部采集，即 100%。
  采集算法:水塘抽样算法。



### Zipkin简介

​	Zipkin是一种分布式跟踪系统。它有助于收集解决微服务架构中的延迟问题所需的时序数据。它管理这些数据的收集和查找。Zipkin的设计基于 [Google Dapper](http://research.google.com/pubs/pub36356.html)论文。

​	官网：https://zipkin.io/

### zipkin架构图

![](https://hlvan-st.oss-cn-beijing.aliyuncs.com/property/upload/20190705161650.png)

### zipkin安装 

客户端下载

方式1：地址 https://dl.bintray.com/openzipkin/maven/io/zipkin/java/zipkin-server/
选择版本后下载（推荐）

方式2：官方方式(速度慢不推荐)
curl -sSL https://zipkin.io/quickstart.sh | bash -s

下载后的文件：zipkin-server-2.12.9-exec.jar

### zipkin运行

直连方式运行

```sh
java -jar zipkin-server-2.12.9-exec.jar
```

消费kafka方式运行

kafka01:9092为fakfa的服务器ip和端口

```
java -DKAFKA_BOOTSTRAP_SERVERS=kafka01:9092 -jar zipkin-server-2.12.9-exec.jar
```
### 访问zipkin

url http://localhost:9411

![](https://hlvan-st.oss-cn-beijing.aliyuncs.com/property/upload/20190704153051.png)

查看详情

![](https://hlvan-st.oss-cn-beijing.aliyuncs.com/property/upload/20190704153134.png)