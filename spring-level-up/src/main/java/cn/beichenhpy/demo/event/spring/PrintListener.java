package cn.beichenhpy.demo.event.spring;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote PrintListener description：时间监听处理器，要将其注册到Map中
 * @since 2021/5/7 9:02 上午
 */
@Component
public class PrintListener implements ApplicationListener<PrintEvent> {
    @Override
    public void onApplicationEvent(PrintEvent event) {
        System.out.println(Thread.currentThread().getName() + "打印" + event.getName());
    }
}
