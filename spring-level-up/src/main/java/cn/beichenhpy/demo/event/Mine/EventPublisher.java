package cn.beichenhpy.demo.event.Mine;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote EventPublisher description：用于发布事件--简易版 直接传入监听器 todo:不用内部查询对应的事件的监听器。。。后续再优化
 * <br> {@link E} 为 {@link Event} 的子类
 * <br> {@link #publishEvent(Event, Listener)} 传入参数为事件及对应的监听器
 * @see Event
 * @see Listener
 * @since 2021/5/7 10:34 上午
 */
public interface EventPublisher<E extends Event> {
    /**
     * 发布事件
     * @param event 事件
     * @param listener 监听器
     */
    void publishEvent(E event,Listener<E> listener);
}
