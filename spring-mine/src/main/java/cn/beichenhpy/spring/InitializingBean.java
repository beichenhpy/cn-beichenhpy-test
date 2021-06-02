package cn.beichenhpy.spring;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote InitializingBean description：初始化bean 在创建bean和依赖注入、Aware执行后执行
 * @since 2021/6/2 1:28 下午
 */
public interface InitializingBean {
    void afterPropertiesSet() throws Exception;
}
