package cn.net.chestnut.consumer.service;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 服务降级处理类，该类需要实现FallbackFactory接口，
 * 其泛型为该服务降级类所对应的Feign接口
 */
@Slf4j
@Component
public class DepartFallbackFactory implements FallbackFactory<DepartFallbackFactoryService> {

    @Override
    public DepartFallbackFactoryService create(Throwable throwable) {
        return new DepartFallbackFactoryService() {
            @Override
            public boolean removeDepartById(int id) {
                log.info("fallback; reason was:", throwable);
                System.out.println("执行removeDepartById()的服务降级处理方法");
                return false;
            }
        };
    }
}
