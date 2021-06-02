package cn.beichenhpy.test;

import cn.beichenhpy.spring.ApplicationContext;
import cn.beichenhpy.test.service.UserService;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote Test description：
 * @since 2021/6/2 10:49 上午
 */
public class Test {
    public static void main(String[] args) {
        ApplicationContext ctx = new ApplicationContext(AppConfig.class);
        UserService userService = (UserService) ctx.getBean("user");
        UserService userService1 = (UserService) ctx.getBean("user");
        UserService userService2 = (UserService) ctx.getBean("user");
        System.out.println(userService.getOrderService());
        System.out.println(userService1.getOrderService());
        System.out.println(userService2.getOrderService());
        System.out.println(userService.getBeanName());
        System.out.println(userService1.getBeanName());
        System.out.println(userService2.getBeanName());
    }
}
