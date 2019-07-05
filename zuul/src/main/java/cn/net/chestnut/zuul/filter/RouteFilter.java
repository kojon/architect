package cn.net.chestnut.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class RouteFilter extends ZuulFilter {

    // 指定路由之前执行过滤
    @Override
    public String filterType() {
        // 返回"pre"
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        // 在系统最小值-3的前面执行
        return -4;
    }

    // 对请求通过过滤的后的逻辑
    @Override
    public Object run() throws ZuulException {
        log.info("通过过滤");
        return null;
    }

    // 对请求进行过滤器的核心逻辑
    @Override
    public boolean shouldFilter() {
        // 获取请求上下文
        RequestContext context =
                RequestContext.getCurrentContext();
        // 获取请求
        HttpServletRequest request = context.getRequest();

        // 获取请求参数
        String user = request.getParameter("user");
        boolean isAdmin= StringUtils.isNotBlank(user) && (user.equals("admin"));
        // 获取请求URI
        String uri = request.getRequestURI();

        // 当请求访问的是/del/且user不为admin时是不能通过过滤的
        if(uri.contains("/del/") && !isAdmin) {
            log.warn("user用户不为admin");
            // 指定当前请求未通过zuul过滤，默认值为true
            context.setSendZuulResponse(false);
            // 向客户端响应码401，未授权
            context.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
            return false;
        }

        return true;
    }
}
