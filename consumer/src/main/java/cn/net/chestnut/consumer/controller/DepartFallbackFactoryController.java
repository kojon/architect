package cn.net.chestnut.consumer.controller;

import cn.net.chestnut.consumer.service.DepartFallbackFactoryService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/consumer/depart")
@Slf4j
public class DepartFallbackFactoryController {

    @Autowired
    private DepartFallbackFactoryService departService;

    @Autowired
    private StringRedisTemplate template;

    // 创建一个线程池，包含5个线程
    private ForkJoinPool pool = new ForkJoinPool(5);

    // 服务降级：若当前处理器方法发生异常，则执行fallbackMethod属性指定的方法
    @HystrixCommand(fallbackMethod = "getHystrixHandle")
    @DeleteMapping("/del/{id}")
    public Boolean deleteHandle(@PathVariable("id") int id,HttpServletRequest request) {
        log.info("header{gray-mark}",request.getHeader("gray-mark"));
        return departService.removeDepartById(id);
    }

    public Boolean getHystrixHandle(@PathVariable("id") int id,
                                   HttpServletRequest request,Throwable throwable) {
        log.error(throwable.getMessage());
        // 指定存放到Redis中的key为"ip + 发生降级的方法名"
        String ip = request.getLocalAddr();
        String key = ip + "_removeDepartById";
        // 异常发生后的报警
        queryCache(key);

        return false;
    }

    // 异常发生后的报警
    private void queryCache(String key) {
        // 获取Redis操作对象
        BoundValueOperations<String, String> ops =
            template.boundValueOps(key);
        String value = ops.get();
        if (value == null) {
            synchronized (this) {
                value = ops.get();
                if (value == null) {
                    // 使用线程池实现异步短信发送
                    sendFallbackMsg(key);
                    value = "短信已发送";
                    ops.set(value, 10, TimeUnit.SECONDS);
                }
            }
        }
    }

    // 使用线程池实现异步短信发送
    private void sendFallbackMsg(String key) {

        pool.submit(() -> {
            System.out.println("发送服务异常报警短信：" + key);
        });

    }
}
