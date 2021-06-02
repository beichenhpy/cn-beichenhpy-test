package cn.beichenhpy.spring;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote BeanPostProcessor description：bean初始哈前置后置处理器
 * @since 2021/6/2 1:46 下午
 */
public interface BeanPostProcessor {

    default Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }
    default Object postProcessAfterInitialization(Object bean, String beanName) throws Exception {
        return bean;
    }

}
