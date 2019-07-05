package cn.net.chestnut.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author tarzan
 * @Date 2019/3/22 9:49 AM
 **/
@RestController
public class IndexController {

    @Value("${eureka.instance.hostname}")
    private String hostName;

    @RequestMapping("/host")
    public String index() {
        return "eureka.instance.hostname=" + hostName;
    }
}
