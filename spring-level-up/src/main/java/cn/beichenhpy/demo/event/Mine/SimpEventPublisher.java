package cn.beichenhpy.demo.event.Mine;


import org.springframework.context.ApplicationEvent;
import org.springframework.core.ResolvableType;

import java.util.concurrent.Executor;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote SimpEventPublisher description：简单发布实现
 * <br>传入的{@link E}需要为事件{@link Event}的子类
 * <br>{@link #publishEvent(Event, Listener)}会检测 {@link #taskExecutor} 是否为空，不为空
 * <br>参考Spring的{@link org.springframework.context.event.SimpleApplicationEventMulticaster#multicastEvent(ApplicationEvent, ResolvableType)}
 * @see Event
 * @see cn.beichenhpy.demo.event.spring.EventConfig
 * 则使用异步线程去执行，否则使用主线程
 * @since 2021/5/7 10:42 上午
 */
public class SimpEventPublisher<E extends Event> implements EventPublisher<E> {

    private Executor taskExecutor;

    public void setTaskExecutor(Executor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    @Override
    public void publishEvent(E event, Listener<E> listener) {
        if (taskExecutor != null) {
            taskExecutor.execute(() -> {
                listener.doEvent(event);
            });
        } else {
            listener.doEvent(event);
        }
    }
}
