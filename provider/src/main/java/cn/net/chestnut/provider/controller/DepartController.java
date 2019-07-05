package cn.net.chestnut.provider.controller;

import cn.net.chestnut.provider.bean.Depart;
import cn.net.chestnut.provider.service.DepartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/provider/depart")
@RestController
@Slf4j
public class DepartController {

    @Autowired
    private DepartService service;

    // 注入服务发现客户端
    @Autowired
    private DiscoveryClient client;

    @PostMapping("/save")
    public boolean saveHandle(@RequestBody Depart depart) {
        return service.saveDepart(depart);
    }

    @DeleteMapping("/del/{id}")
    public boolean deleteHandle(@PathVariable("id") int id) {
        return service.removeDepartById(id);
    }

    @PutMapping("/update")
    public boolean updateHandle(@RequestBody Depart depart) {
        return service.modifyDepart(depart);
    }

    @GetMapping("/get/{id}")
    public Depart getHandle(@PathVariable("id") int id) {
        log.info("生产者的处理器方法被调用");
        return service.getDepartById(id);
    }

    @GetMapping("/list")
    public List<Depart> listHandle() {
        return service.listAllDeparts();
    }

    @GetMapping("/discovery")
    public Object discoveryHandle() {
        // 获取Eureka中所有的微服务名称
        List<String> springApplicationNames = client.getServices();
        // 遍历所有微服务
        for (String name : springApplicationNames) {
            // 根据微服务名称获取到所有提供该服务的主机信息
            List<ServiceInstance> instances = client.getInstances(name);
            for (ServiceInstance instance : instances) {
                String host = instance.getHost();
                int port = instance.getPort();
                System.out.println(host + " : " + port);
            }
        }
        return springApplicationNames;
    }
}
