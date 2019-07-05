package cn.net.chestnut.consumer.controller;

import cn.net.chestnut.consumer.bean.Depart;
import cn.net.chestnut.consumer.service.DepartService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consumer/depart")
@Slf4j
@RefreshScope
public class DepartController {
    @Autowired
    private DepartService service;

    @Value("${suffix}")
    private String suffix;

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

    // 服务降级：若当前处理器方法发生异常，则执行fallbackMethod属性指定的方法
    @HystrixCommand(fallbackMethod = "getHystrixHandle")
    @GetMapping("/get/{id}")
    public Depart getHandle(@PathVariable("id") int id) {
        Depart depart = service.getDepartById(id);
        depart.setName(depart.getName() + " " + suffix);
        return depart;
    }

    public Depart getHystrixHandle(@PathVariable("id") int id,Throwable throwable) {
        log.error("降级原因{},{}",throwable.getMessage(),throwable.getLocalizedMessage());
        Depart depart = new Depart();
        depart.setId(id);
        depart.setName("no this depart  -- 方法级别");
        return depart;
    }

    @GetMapping("/list")
    public List<Depart> listHandle() {
        return service.listAllDeparts();
    }
}
