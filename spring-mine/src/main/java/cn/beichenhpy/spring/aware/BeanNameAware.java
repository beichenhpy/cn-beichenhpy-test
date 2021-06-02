package cn.beichenhpy.spring.aware;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote BeanNameAware description：
 * @since 2021/6/2 1:17 下午
 */
public interface BeanNameAware extends Aware{
    void setBeanName(String beanName);
}
