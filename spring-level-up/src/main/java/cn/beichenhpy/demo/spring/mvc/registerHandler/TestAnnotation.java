package cn.beichenhpy.demo.spring.mvc.registerHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote TestAnnotation description：对应的handlerMapping和HandlerAdapter
 * {@link org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter}
 * {@link org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping}
 * @since 2021/6/3 6:54 下午
 */
@Controller
@Slf4j
public class TestAnnotation {

    @RequestMapping("/testAnnotation")
    @ResponseBody
    public void test(){
        log.info("当前线程：{}",Thread.currentThread().getName());
    }
}
