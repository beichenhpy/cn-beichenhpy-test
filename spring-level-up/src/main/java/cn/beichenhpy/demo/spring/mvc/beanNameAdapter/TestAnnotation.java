package cn.beichenhpy.demo.spring.mvc.beanNameAdapter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote TestAnnotation description：对应的handlerMapping和HandlerAdapter
 * {@link org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter}
 * {@link org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping}
 * @since 2021/6/3 6:54 下午
 */
@Controller
public class TestAnnotation {

    @RequestMapping("/testAnnotation")
    public void test(){
        System.out.println(1);
    }
}
