package cn.beichenhpy.demo.staticInject;

import org.springframework.stereotype.Component;

/**
 * @author beichenhpy
 * @version 1.0
 * @description TODO 测试静态变量注入 使用构造函数注入
 * @since 2021/2/20 17:23
 */
@Component
public class StaticInjectTest {

    private static Service service;
    public StaticInjectTest(Service service){
        StaticInjectTest.service = service;
    }

    public static void test(){
        service.test();
    }
}
