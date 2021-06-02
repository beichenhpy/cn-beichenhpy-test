package cn.beichenhpy.test.service;

import cn.beichenhpy.spring.BeanPostProcessor;
import cn.beichenhpy.spring.anno.Component;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote MyBeanPostProcessor description：
 * @since 2021/6/2 2:00 下午
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        System.out.println("实例化前");
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        System.out.println("实例化后");
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
