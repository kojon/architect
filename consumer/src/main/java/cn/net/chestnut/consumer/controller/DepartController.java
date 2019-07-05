package cn.net.chestnut.consumer.controller;

import cn.net.chestnut.consumer.bean.Depart;
import cn.net.chestnut.consumer.service.DepartService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/consumer/depart")
@Slf4j
public class DepartController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DepartService departService;

    // 将“主机名+端口号”方式修改为“微服务名称”
    // private static final String SERVICE_PROVIDER = "http://localhost:8081";
    private static final String SERVICE_PROVIDER = "http://provider";

    @PostMapping("/save")
    public boolean saveHandle(@RequestBody Depart depart) {
        String url = SERVICE_PROVIDER + "/provider/depart/save";
        // 第一个参数：消费者访问提供者的URL
        // 第二个参数：操作对象
        // 第三个参数：方法返回值类型
        return restTemplate.postForObject(url, depart, Boolean.class);
    }

    @PutMapping("/update")
    public void updateHandle(@RequestBody Depart depart) {
        String url = SERVICE_PROVIDER + "/provider/depart/update";
        restTemplate.put(url, depart, Boolean.class);
    }

    // 服务降级：若当前处理器方法发生异常，则执行fallbackMethod属性指定的方法
    @HystrixCommand(fallbackMethod = "getHystrixHandle")
    @GetMapping("/list")
    public List<Depart> listHandle() {
        List<Depart> departList=departService.listAllDeparts();
        return departList;
    }

    public List<Depart> getHystrixHandle(Throwable e) {
        log.error(e.getLocalizedMessage(),e.getMessage());
        return null;
    }
}
