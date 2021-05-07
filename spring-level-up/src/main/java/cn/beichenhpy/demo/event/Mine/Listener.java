package cn.beichenhpy.demo.event.Mine;

import java.util.EventListener;

/**
 * @author beichenhpy
 * @version 0.0.1
 * @apiNote Listener description：事件监听执行者
 * <br> {@link E} 为 {@link Event} 子类
 * <br> {@link #doEvent(Event)} 执行对应事件
 * @see Event
 * @see EventListener
 * @since 2021/5/7 10:32 上午
 */
public interface Listener<E extends Event> extends EventListener {
    /**
     * 对应事件的执行内容
     * @param event 对应事件
     */
    void doEvent(E event);
}
