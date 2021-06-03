package cn.beichenhpy.demo.spring.mvc.beanNameAdapter;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote ControllerTest description：对应的HandlerMapping和HandlerAdapter
 * {@link org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping}
 * {@link org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter}
 * @since 2021/6/3 6:50 下午
 */
@Component("/testController")
public class ControllerTest implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return null;
    }
}
