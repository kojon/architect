package cn.net.chestnut.consumer.codeconfig;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class DepartCodeConfig {

    @LoadBalanced    // 开启消息者端的负载均衡功能，默认是轮询策略
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // 设置负载均衡算法为“随机算法”
    @Bean
    public IRule loadBalanceRule() {
        return new RandomRule();
    }

    // @Bean
    // public IRule loadBalanceRule() {
    //     // 指定要排除的Server的端口号
    //     List<Integer> ports = new ArrayList<>();
    //     ports.add(8082);
    //     return new CustomRule(ports);
    // }
}
