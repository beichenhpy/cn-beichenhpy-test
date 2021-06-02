package cn.beichenhpy.test.service;

import cn.beichenhpy.spring.InitializingBean;
import cn.beichenhpy.spring.anno.Component;
import cn.beichenhpy.spring.anno.Inject;
import cn.beichenhpy.spring.anno.Scope;
import cn.beichenhpy.spring.aware.BeanNameAware;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote UserService description：
 * @since 2021/6/2 11:05 上午
 */
@Component("user")
@Scope("singleton")
public class UserService implements BeanNameAware, InitializingBean {
    public OrderService getOrderService() {
        return orderService;
    }

    @Inject()
    private OrderService orderService;

    private String beanName;

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("userService初始化");
    }
}
