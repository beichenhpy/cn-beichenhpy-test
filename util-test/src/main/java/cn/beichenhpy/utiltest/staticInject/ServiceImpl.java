package cn.beichenhpy.utiltest.staticInject;

import org.springframework.stereotype.Component;

/**
 * @author beichenhpy
 * @version 1.0
 * @description TODO
 * @since 2021/2/23 10:27
 */
@Component
public class ServiceImpl implements Service{
    @Override
    public void test() {
        System.out.println("测试静态注入");
    }
}
