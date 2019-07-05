package cn.net.chestnut.zuul.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RateFilter extends ZuulFilter {

    //todo 令牌桶
    // 每秒产生2个令牌
    private static final RateLimiter RATE_LIMITER =
        RateLimiter.create(2);

    @Override
    public String filterType() {
        // 返回"pre"
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return -5;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("通过过滤");
        return null;
    }

    @Override
    public boolean shouldFilter() {
        // 获取请求上下文
        RequestContext context =
            RequestContext.getCurrentContext();

        if(!RATE_LIMITER.tryAcquire()) {
            log.warn("访问量超载");
            // 指定当前请求未通过zuul过滤，默认值为true
            context.setSendZuulResponse(false);
            // 向客户端响应码429，请求数量过多
            context.setResponseStatusCode(429);
            return false;
        }
        return true;
    }
}
