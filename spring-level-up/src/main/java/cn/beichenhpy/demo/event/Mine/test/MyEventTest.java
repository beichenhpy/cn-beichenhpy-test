package cn.beichenhpy.demo.event.Mine.test;

import cn.beichenhpy.demo.common.modal.Car;
import cn.beichenhpy.demo.event.Mine.Listener;
import cn.beichenhpy.demo.event.Mine.SimpEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.Executor;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote Test description：发布测试
 * @since 2021/5/7 10:50 上午
 */
@Component
public class MyEventTest {
    @Resource(name = "taskExecutor")
    private Executor taskExecutor;
    public void test(){
        EventTest eventTest = new EventTest(new Car("benz",12000000));
        Listener<EventTest> listener = new MyListener();
        SimpEventPublisher<EventTest> publisher = new SimpEventPublisher<>();
        publisher.setTaskExecutor(taskExecutor);
        publisher.publishEvent(eventTest,listener);
    }
}
