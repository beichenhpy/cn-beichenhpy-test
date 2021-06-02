package cn.beichenhpy.test.service;

import cn.beichenhpy.spring.InitializingBean;
import cn.beichenhpy.spring.anno.Component;
import cn.beichenhpy.spring.anno.Scope;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote OrderService description：
 * @since 2021/6/2 1:11 下午
 */
@Component
@Scope("property")
public class OrderService implements InitializingBean {
    @Override
    public void afterPropertiesSet() {
        System.out.println("orderService初始化");
    }
}
