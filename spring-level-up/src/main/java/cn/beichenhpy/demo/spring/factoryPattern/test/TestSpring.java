package cn.beichenhpy.demo.spring.factoryPattern.test;

import cn.beichenhpy.demo.spring.factoryPattern.factory.BeanFactory;
import cn.beichenhpy.demo.spring.factoryPattern.service.UserService;
import cn.beichenhpy.demo.spring.factoryPattern.service.UserServiceImpl;
import org.junit.jupiter.api.Test;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote TestSpring description：
 * @since 2021/6/1 7:54 下午
 */

public class TestSpring {

    @Test
    void test1(){
        UserService userService = new UserServiceImpl();
        userService.addUser();
    }

    @Test
    void test2(){
        UserService userService = (UserService) BeanFactory.getBean("userService");
        userService.addUser();
    }
}
