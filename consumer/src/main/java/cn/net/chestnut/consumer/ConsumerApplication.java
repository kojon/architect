package cn.net.chestnut.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

// 指定Service接口所在的包，开启OpenFeign客户端
@EnableFeignClients(basePackages = "cn.net.chestnut.consumer.service")
@SpringCloudApplication
//SpringCloudApplication 包含下面两注解
// @EnableCircuitBreaker  // 开启熔断器
// @SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

}
