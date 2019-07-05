package cn.net.chestnut.consumer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * zuul灰度测试的功能
 */
@RestController
@RequestMapping("/host")
@Slf4j
public class GrayHostController {

    @Autowired
    private EurekaInstanceConfigBean eurekaInstanceConfigBean;

    @GetMapping("/get")
    public String getHandle(@RequestHeader(name = "token",required = false) String token) {
        log.info("token:{}",token);
        return "当前访问:"+eurekaInstanceConfigBean.getMetadataMap();
    }
}
