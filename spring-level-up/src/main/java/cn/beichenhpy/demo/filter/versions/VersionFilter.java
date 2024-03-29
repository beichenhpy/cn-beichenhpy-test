package cn.beichenhpy.demo.filter.versions;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote VersionFilter description：实现接口版本管理的filter
 * <br> 使用header中添加app-version字段来控制版本，如果不存在这个字段，则会跳转到无版本的接口
 * @since 2021/6/15 21:51
 */
@WebFilter("/*")
public class VersionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();
        String version = httpServletRequest.getHeader("app-version");
        if (version != null && !version.equals("")) {
            requestURI = "/v" + version + requestURI;
            httpServletRequest.getRequestDispatcher(requestURI).forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }
}
