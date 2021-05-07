package cn.beichenhpy.demo.event.spring;

import cn.beichenhpy.demo.common.modal.Car;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote PrintPublisher description：
 * @since 2021/5/7 9:04 上午
 */
@Component
public class PrintController implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    public void test() {
        PrintEvent printEvent = new PrintEvent(new Car("benz", 120000), "car");
        applicationEventPublisher.publishEvent(printEvent);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
