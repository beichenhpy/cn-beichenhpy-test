package cn.beichenhpy.demo.spring.mvc.registerHandler;

import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote TestHttpRequestHandler description：对应的HandlerMapping和HandlerAdapter
 * {@link org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping}
 * {@link org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter}
 * @since 2021/6/3 6:53 下午
 */
@Component("/testHttpRequestHandler")
public class TestHttpRequestHandler implements HttpRequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
