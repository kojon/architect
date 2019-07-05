package cn.net.chestnut.consumer.service;

import cn.net.chestnut.consumer.bean.Depart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
// 指定当前Service所绑定的提供者微服务名称
// fallback指定该接口所绑定的服务降级类
@FeignClient(value = "provider")
@RequestMapping("/provider/depart")
public interface DepartService {

    @PostMapping("/save")
    boolean saveDepart(@RequestBody Depart depart);

    @PutMapping("/update")
    boolean modifyDepart(@RequestBody Depart depart);

    @GetMapping("/list")
    List<Depart> listAllDeparts();
}
