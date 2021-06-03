package cn.beichenhpy.demo.spring.mvc.interceptors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote InterceptorConfig description：
 * @since 2021/6/3 9:59 下午
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/testAnnotation");
        super.addInterceptors(registry);
    }
}
