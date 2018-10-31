package com.nbs.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class GetwayZuulFilter extends ZuulFilter {
    
    @Override
    public boolean shouldFilter() {
        return true;  //该过滤器需要执行
    }
    
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        System.out.println(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

        Object accessToken = request.getParameter("accessToken");
        if (accessToken == null) {
            System.out.println("access token is empty");
            ctx.setSendZuulResponse(false); //过滤该请求，不对其进行路由
            ctx.setResponseStatusCode(401); //设置响应状态码
            return null;
        }
        System.out.println("access token ok");
        return null;
    }

    @Override
    public String filterType() {
        return "pre";  //设置过滤器类型为pre
    }
    @Override
    public int filterOrder() {
        return 0; //设置执行顺序
    }
}
