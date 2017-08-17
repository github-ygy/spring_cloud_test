package test.ygy.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by guoyao on 2017/8/17.
 */
@Component
public class ZuulRequestFilter extends ZuulFilter {

    private static final Logger log=LoggerFactory.getLogger(ZuulRequestFilter.class);

    //"pre" for pre-routing filtering,
    //  "route" for routing to an origin,
    // "post" for post-routing filters
    // "error" for error handling.
    //过滤器类型，决定过滤器运行时机
    @Override
    public String filterType() {
        return "pre";
    }

    //过滤器执行顺序
    @Override
    public int filterOrder() {
        return 0;
    }

    // a "true" return from this method means that the run() method should be invoked
    @Override
    public boolean shouldFilter() {
        return true;
    }


    //过滤器逻辑
    @Override
    public Object run() {
        log.info(" filter is running ");

        //  模仿token实现权限控制，实际可由会话获取
        RequestContext requestContext =  RequestContext.getCurrentContext();
        HttpServletRequest httpRequest=requestContext.getRequest();

        String token  = httpRequest.getParameter("accessToken");
        if (!"token".equals(token)) {
            log.warn(" you do not have the token ");
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(401);
            return null;
        }
        log.info(" access successful");
        return null;
    }
}
