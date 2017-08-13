package test.ygy.filter;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by guoyao on 2017/8/13.
 */
@WebFilter(filterName = "hystrixContextFilter",urlPatterns = "/*",asyncSupported = true)
public class HystrixFilter  implements Filter {

        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            HystrixRequestContext context = HystrixRequestContext.initializeContext();
            try {
                chain.doFilter(request, response);
            } finally {
                context.shutdown();
            }
        }

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {

        }

        @Override
        public void destroy() {

        }
    }

