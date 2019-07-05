package cn.net.chestnut.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
// 指定当前Service所绑定的提供者微服务名称
// fallback指定该接口所绑定的服务降级类
@FeignClient(value = "provider", fallbackFactory = DepartFallbackFactory.class)
@RequestMapping("/provider/depart/dal")
public interface DepartFallbackFactoryService {

    @DeleteMapping("{id}")
    boolean removeDepartById(@PathVariable("id") int id);
}
