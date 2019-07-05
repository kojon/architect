package cn.net.chestnut.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class GrayHostFilter extends ZuulFilter {
    @Override
    public String filterType() {
        // 返回"pre"
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return -5;
    }
    // 过滤器常开
    @Override
    public boolean shouldFilter() {
        return true;
    }

    // 对请求过滤的核心逻辑
    @Override
    public Object run() throws ZuulException {
        // 获取请求上下文
        RequestContext context = RequestContext.getCurrentContext();
        // 获取请求
        HttpServletRequest request = context.getRequest();

        // 获取指定的头信息，该头信息在浏览器提交请求时携带
        String mark = request.getHeader("gray-mark");

        // 默认将请求路由到running-host上
        // "host-mark"与"running-host"是在消费者工程中
        // 添加的元数据key-value
        RibbonFilterContextHolder.getCurrentContext().add("host-mark", "running-host");

        // 若mark的值不空，且为"enable"，则将请求路由到gray-host上
        if(!StringUtils.isEmpty(mark) && "enable".equals(mark)) {
            RibbonFilterContextHolder.getCurrentContext().add("host-mark", "gray-host");
        }
        return null;
    }
}
