package cn.net.chestnut.consumer.service;

import cn.net.chestnut.consumer.bean.Depart;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;

@Component
@RequestMapping("/fallback/consumer/depart")
public class DepartFallback implements DepartFallbackService {
//     fallbackFactory的优先级高于fallbackMethod的
    @Override
    public Depart getDepartById(int id) {
        System.out.println("执行getDepartById()的服务降级处理方法");
        Depart depart = new Depart();
        depart.setId(id);
        depart.setName("no this depart -- 类级别");
        return depart;
    }
}
