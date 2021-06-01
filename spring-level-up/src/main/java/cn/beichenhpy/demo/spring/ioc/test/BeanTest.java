package cn.beichenhpy.demo.spring.ioc.test;

import cn.beichenhpy.demo.spring.ioc.bean.Person;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote BeanTest description：
 * @since 2021/6/1 11:32 下午
 */
public class BeanTest {

    static ApplicationContext ctx;

    @BeforeAll
    static void ready() {
        //获取Spring的工厂
        ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
    }

    @Test
    void test() {
        //获取bean
        Person person = (Person) ctx.getBean("person");
        System.out.println(person);
    }

    @Test
    void test2() {
        Person personNick = ctx.getBean("personNick", Person.class);
        System.out.println("BeanTest.test2" + personNick);
    }

    @Test
    void test3(){
        boolean person = ctx.containsBean("person");
        boolean personNick = ctx.containsBean("personNick");
        System.out.println(person);
        System.out.println(personNick);
        //只能判断id
        boolean person1 = ctx.containsBeanDefinition("person");
        boolean nick = ctx.containsBeanDefinition("personNick");
        System.out.println(person1);
        System.out.println(nick);
    }
}
